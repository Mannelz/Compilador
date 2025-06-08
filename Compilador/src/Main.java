public class Main 
{
    public static void main(String[] args) throws Exception 
    {
        String filePath = "codigo_fonte.lc";
        
        Lexical.analysis(filePath);
        Sintatical.analysis();
    }
}