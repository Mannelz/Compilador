public enum SymbolClass 
{
    EMPTY(0),
    VARIABLE(1),
    CONSTANT(2);

    private final int value;

    SymbolClass(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}