import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Lexico
{
    public static void AnalisadorLexico(String filePath)
    {
        TabelaSimbolo x = new TabelaSimbolo();
         HashSet<TabelaSimbolo> tabelaSimbolo = new HashSet<>
         (Arrays.asList(x));
        
        try 
        {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(fileReader);

            int currentByte;
            int nextByte;
            boolean isComment = false;

            do
            {
                currentByte = reader.read();

                if(currentByte == '/')
                {
                    reader.mark(1);

                    nextByte = reader.read();

                    System.out.println((char) nextByte);

                    if(nextByte == '*')
                    {
                        isComment = true;

                        continue;
                    }
                    else
                    {
                        reader.reset();
                    }
                }

                if(isComment)
                {
                    if(currentByte == '*')
                    {
                        nextByte = reader.read();

                        if(nextByte == '/')
                        {
                            isComment = true;
                        }
                        else
                        {
                            reader.reset();
                        }
                    }

                    continue;
                }
            }
            while(currentByte != -1);

            reader.close();
        }
        catch (Exception e) 
        {
            System.out.println("Error: " + e);
        }
    }
}
