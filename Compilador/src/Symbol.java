class Symbol 
{
    private String name;
    private String token;
    private String lexeme;
    private int symbolClass;
    private int symbolType;
    private String address;

    Symbol(String name, String token, String lexeme, int symbolClass)
    {
        this.name = name;
        this.token = token;
        this.lexeme = lexeme;
        this.symbolClass = symbolClass;
        this.symbolType = -1;
        this.address = "";
    }

    Symbol(String name, String token, String lexeme, int symbolClass, int symbolType)
    {
        this.name = name;
        this.token = token;
        this.lexeme = lexeme;
        this.symbolClass = symbolClass;
        this.symbolType = symbolType;
        this.address = "";
    }

    public String getName()
    {
        return name;
    }

    public String getToken()
    {
        return token;
    }

    public String getLexeme()
    {
        return lexeme;
    }

    public int getSymbolClass()
    {
        return symbolClass;
    }

    public int getSymbolType()
    {
        return symbolType;
    }

    public String getAddress()
    {
        return address;
    }

    public void setSymbolClass(int symbolClass)
    {
        this.symbolClass = symbolClass;
    }

    public void setSymbolType(int symbolType)
    {
        this.symbolType = symbolType;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}