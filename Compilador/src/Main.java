public class Main 
{
    public static void main(String[] args) throws Exception 
    {
        if (args.length < 1)
        {
            System.out.println("É necessário informar o caminho de um arquivo .lc como parâmetro. Use: java Main <arquivo>");
            return;
        }

        String filePath = args[0];
        
        if (!filePath.toLowerCase().endsWith(".lc"))
        {
            System.out.println("Erro: o arquivo deve ter a extensão '.lc'");
            return;
        }

        Lexical.analysis(filePath);
        Sintatical.analysis();
    }
}