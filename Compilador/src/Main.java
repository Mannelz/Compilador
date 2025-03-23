import java.io.BufferedReader;
import java.io.FileReader;

public class Main 
{
    public static void main(String[] args) 
    {
        String filePath = "codigo_fonte.lc";

        var file = FileReader(filePath);

        System.out.println(file);

        var fileChar = file.toCharArray();

        for (int i = 0; i < fileChar.length; i++) 
        {
            System.out.println(fileChar[i]);
        }
    }

    static String FileReader(String filePath)
    {
        String file = "";

        try 
        {
            FileReader fileReader = new FileReader(filePath);
             BufferedReader reader = new BufferedReader(fileReader);

            String newLine;

            do
            {
                newLine = reader.readLine();
                file += newLine;
            }
            while(newLine != null);

            reader.close();
        }
        catch (Exception e) 
        {
            System.out.println("Error: " + e);
        }

        return file;
    }
}