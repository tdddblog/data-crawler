package ek.html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;


public class TestWebCrawler
{
    private static class MyCB implements WebCrawler.Callback
    {
        @Override
        public void onFile(String url, String name) throws Exception
        {
            System.out.println(url + "    -->  " + name);
        }
        
    }

    
    private static class MyCB2 implements WebCrawler.Callback
    {
        @Override
        public void onFile(String url, String name) throws Exception
        {
            if(name.toLowerCase().endsWith(".xml"))
            {
                System.out.println(url);
            }
        }
        
    }

    
    public static void main(String[] args) throws Exception
    {
        //testWeb();
    }

    
    public static void test1() throws Exception
    {
        WebCrawler crawler = new WebCrawler(new MyCB2());
        crawler.setBaseUrl("http://localhost");
        
        
        File file = new File("/tmp/context1.html");
        Reader rd = new BufferedReader(new FileReader(file));
        crawler.process(rd, "/data/pds4/context-pds4");
        
        rd.close();
    }


    public static void testWeb() throws Exception
    {
        String url = "https://pds.nasa.gov/data/pds4/context-pds4";
        
        HrefWriter writer = new HrefWriter(new File("/tmp/refs.txt"));
        WebCrawler crawler = new WebCrawler(writer);
        crawler.crawl(url);
        writer.close();
    }

}
