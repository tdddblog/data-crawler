package ek.html;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


public class HrefWriter implements WebCrawler.Callback, Closeable
{
    private Writer writer;
    
    
    public HrefWriter(File file) throws Exception
    {
        writer = new FileWriter(file);
    }
    
    
    @Override
    public void onFile(String url, String name) throws Exception
    {
        String lcName = name.toLowerCase();
        if(lcName.endsWith(".xml"))
        {
            writer.write(url);
            writer.write("\n");
        }
    }


    @Override
    public void close() throws IOException
    {
        writer.close();
    }

}
