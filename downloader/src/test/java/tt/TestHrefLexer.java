package tt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import ek.html.HrefLexer;


public class TestHrefLexer
{

    public static void main(String[] args) throws Exception
    {
        File file = new File("/tmp/context2.html");
        Reader rd = new BufferedReader(new FileReader(file));
        HrefLexer lex = new HrefLexer(rd);
        
        int ttype = 0;
        while((ttype = lex.getNextToken()) != HrefLexer.YYEOF)
        {
            if(ttype == HrefLexer.HREF)
            {
                String href = lex.getString();
                
                String tmp = href.toLowerCase();
                if(tmp.endsWith(".xml"))
                {
                    System.out.println(href);
                }
                else if(href.endsWith("/"))
                {
                    if(isValidSubdir(tmp))
                    {
                        System.out.println(href);
                    }
                }
            }
        }
        
        rd.close();

    }
    
    
    private static boolean isValidSubdir(String href)
    {
        if(href.startsWith("/")) return false;
        if(href.equals("../") || href.equals("./")) return false;
        
        if(href.startsWith("http://") || href.startsWith("https://")) return false;
        
        return true;
    }

}
