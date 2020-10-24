package ek.html;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;


public class WebCrawler
{
    public static interface Callback
    {
        public void onFile(String url, String name) throws Exception;
    }
    
    /////////////////////////////////////////////////////////////
    
    String baseUrl;
    private Callback cb;

    private Queue<String> folders = new LinkedList<>();
    
    
    public WebCrawler(Callback cb)
    {
        this.cb = cb;
    }
    
    
    public void crawl(String strUrl) throws Exception
    {
        setBaseUrl(strUrl);

        folders.add(strUrl);
        while(folders.size() > 0)
        {
            String folderUrl = folders.poll();
            if(folderUrl != null)
            {
                processUrl(folderUrl);
            }
        }
    }
    
    
    void setBaseUrl(String strUrl) throws Exception
    {
        URL url = new URL(strUrl);

        StringBuilder bld = new StringBuilder();
        bld.append(url.getProtocol());
        bld.append("://");
        bld.append(url.getHost());
        if(url.getPort() > 0)
        {
            bld.append(":" + url.getPort());
        }
        
        baseUrl = bld.toString();
    }

    
    void processUrl(String strUrl) throws Exception
    {
        System.out.println("Processing " + strUrl);
        
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        Reader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

        process(reader, url.getPath());
    }
    
    
    void process(Reader reader, String path) throws Exception
    {
        if(!path.endsWith("/"))
        {
            path = path + "/";
        }
        
        Set<String> uniqueFolders = new TreeSet<>();
        
        HrefLexer lex = new HrefLexer(reader);
        
        int ttype = 0;
        while((ttype = lex.getNextToken()) != HrefLexer.YYEOF)
        {
            if(ttype == HrefLexer.HREF)
            {
                String href = lex.getString();
                // Folder
                if(href.endsWith("/") && isValidSubdir(path, href))
                {
                    uniqueFolders.add(href);
                }
                // File
                else
                {
                    processFile(path, href);
                }
            }
        }

        reader.close();
        
        // Folders
        for(String folder: uniqueFolders)
        {
            String folderUrl = (folder.startsWith("/")) ? baseUrl + folder : baseUrl + path + folder;
            folders.add(folderUrl);
        }        
    }

    
    private void processFile(String path, String href) throws Exception
    {
        String name = href;
        if(href.startsWith("/"))
        {
            int idx = href.lastIndexOf('/');
            name = href.substring(idx+1);
        }
        
        String fileUrl = baseUrl + path + name;
        cb.onFile(fileUrl, name);
    }
    

    boolean isValidSubdir(String path, String href)
    {
        if(href.startsWith("/")) 
        {
            if(href.startsWith(path) && (href.length() > path.length())) return true;
            return false;
        }
        
        if(href.equals("../") || href.equals("./")) return false;
        
        if(href.startsWith("http://") || href.startsWith("https://")) return false;
        
        return true;
    }

    
}
