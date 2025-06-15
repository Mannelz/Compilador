public enum SymbolType 
{
    INT(1),
    LOGICAL(2),
    BYTE(3),
    STRING(4);

    private final int value;

    SymbolType(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}