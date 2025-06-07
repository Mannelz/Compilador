public class Token
{
    private String type;
    private String value;
    private int line;
    private int column;

    Token(String type, String value, int line, int column)
    {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    public static Token createToken(Symbol symbol, int line, int column)
    {
        return new Token(symbol.getName(), symbol.getLexeme(), line, column);
    }

    public String getType()
    {
        return type;
    }

    public String getValue()
    {
        return value;
    }

    public int getLine()
    {
        return line;
    }

    public int getColumn()
    {
        return column;
    }
}