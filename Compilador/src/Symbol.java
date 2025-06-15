class Symbol 
{
    private String name;
    private String token;
    private String lexeme;
    private SymbolClass symbolClass;
    private SymbolType symbolType;
    private String address;

    Symbol(String name, String token, String lexeme, SymbolClass symbolClass)
    {
        this.name = name;
        this.token = token;
        this.lexeme = lexeme;
        this.symbolClass = symbolClass;
        this.symbolType = null;
        this.address = "";
    }

    Symbol(String name, String token, String lexeme, SymbolClass symbolClass, SymbolType symbolType)
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

    public SymbolClass getSymbolClass()
    {
        return symbolClass;
    }

    public SymbolType getSymbolType()
    {
        return symbolType;
    }

    public String getAddress()
    {
        return address;
    }

    public void setSymbolClass(SymbolClass symbolClass)
    {
        this.symbolClass = symbolClass;
    }

    public void setSymbolType(SymbolType symbolType)
    {
        this.symbolType = symbolType;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}