package tt;

import ek.html.WebCrawler;

public class TestWebCrawler
{

    public static void main(String[] args) throws Exception
    {
        String url = "https://pds.nasa.gov/data/pds4/context-pds4";
        
        WebCrawler crawler = new WebCrawler();
        crawler.crawl(url);
        
    }

}
