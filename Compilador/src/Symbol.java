class Symbol 
{
    private String name;
    private String token;
    private String lexeme;
    private String symbolClass;
    private String symbolType;
    private String address;

    Symbol(String name, String token, String lexeme) 
    {
        this.name = name;
        this.token = token;
        this.lexeme = lexeme;
        this.symbolClass = "";
        this.symbolType = "";
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

    public String getSymbolClass()
    {
        return symbolClass;
    }

    public String getType()
    {
        return symbolType;
    }

    public String getAddress()
    {
        return address;
    }

    public void setSymbolClass(String symbolClass)
    {
        this.symbolClass = symbolClass;
    }

    public void setType(String symbolType)
    {
        this.symbolType = symbolType;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}