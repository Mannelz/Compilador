public class WizardSpeller
{
    public static void castError(String message, int line, int column)
    {
        System.err.println("🪄 [Erro] Linha " + line + ", Coluna " + column + ": " + message);
    }

    public static void castWarning(String message, int line, int column)
    {
        System.err.println("✨ [Aviso] Linha " + line + ", Coluna " + column + ": " + message);
    }
}
