public enum SymbolClass 
{
    EMPTY("EMPTY"),
    VARIABLE("VARIABLE"),
    CONSTANT("CONSTANT");

    private final String value;

    SymbolClass(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public static SymbolClass fromString(String text)
    {
        for(SymbolClass sClass : SymbolClass.values())
        {
            if(sClass.getValue().equalsIgnoreCase(text))
            {
                return sClass;
            }
        }
        
        throw new IllegalArgumentException("Tipo inválido: " + text);
    }
}