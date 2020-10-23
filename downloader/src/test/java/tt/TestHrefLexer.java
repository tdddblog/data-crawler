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
        File file = new File("/ws3/test2.html");
        Reader rd = new BufferedReader(new FileReader(file));
        HrefLexer lex = new HrefLexer(rd);
        
        int ttype = 0;
        while((ttype = lex.getNextToken()) != HrefLexer.YYEOF)
        {
            if(ttype == HrefLexer.HREF)
            {
                String href = lex.getString();
                System.out.println(href);
                
                /*
                String tmp = href.toLowerCase();
                if(tmp.endsWith(".xml"))
                {
                    System.out.println(href);
                }
                */
            }
        }
        
        rd.close();

    }

}
