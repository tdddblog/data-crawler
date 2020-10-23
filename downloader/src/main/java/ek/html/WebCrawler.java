package ek.html;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WebCrawler
{
    public WebCrawler()
    {
    }
    
    
    public void crawl(String url) throws Exception
    {
        processUrl(url);
    }
    

    private void processUrl(String strUrl) throws Exception
    {
        System.out.println("Processing " + strUrl);
        
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        Reader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        
        HrefLexer lex = new HrefLexer(reader);
        
        int ttype = 0;
        while((ttype = lex.getNextToken()) != HrefLexer.YYEOF)
        {
            if(ttype == HrefLexer.HREF)
            {
                System.out.println(lex.getString());
            }
        }

        reader.close();
    }

    
}
