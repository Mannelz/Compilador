import java.io.BufferedReader;
import java.io.FileReader;

public class Lexico 
{
    public static void AnalisadorLexico(String filePath)
    {
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
