public class Token
{
    private String tipo;
    private String valor;

    Token(String tipo, String valor)
    {
        this.tipo = tipo;
        this.valor = valor;
    }

    public static Token createToken(Simbolo symbol)
    {
        return new Token(symbol.getNome(), symbol.getLexema());
    }

    public String getTipo()
    {
        return tipo;
    }

    public String getValor()
    {
        return valor;
    }
}