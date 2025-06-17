public enum SymbolType 
{
    INT("INT"),
    BOOLEAN("BOOLEAN"),
    BYTE("BYTE"),
    STRING("STRING");

    private final String value;

    SymbolType(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public static SymbolType fromString(String text)
    {
        for(SymbolType type : SymbolType.values())
        {
            if(type.getValue().equalsIgnoreCase(text))
            {
                return type;
            }
        }
        
        throw new IllegalArgumentException("Tipo inválido: " + text);
    }
}