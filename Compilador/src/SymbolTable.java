import java.util.HashMap;
import java.util.Map;

public class SymbolTable 
{
    private static SymbolTable instance = null;
    private Map<String, Simbolo> symbolsTable;

    private SymbolTable()
    {
        this.symbolsTable = new HashMap<>();
        initSymbols();
    }

    public static SymbolTable getInstance()
    {
        if(instance == null)
            instance = new SymbolTable();
        
        return instance;
    }

    private void initSymbols() 
    {
        addSymbol(new Simbolo("FINAL", "3", "final"));
        addSymbol(new Simbolo("INT", "4", "int"));
        addSymbol(new Simbolo("BYTE", "5", "byte"));
        addSymbol(new Simbolo("STRING", "6", "string"));
        addSymbol(new Simbolo("WHILE", "7", "while"));
        addSymbol(new Simbolo("IF", "8", "if"));
        addSymbol(new Simbolo("ELSE", "9", "else"));
        addSymbol(new Simbolo("AND", "10", "and"));
        addSymbol(new Simbolo("OR", "11", "or"));
        addSymbol(new Simbolo("NOT", "12", "not"));
        addSymbol(new Simbolo("IGUAL", "13", "=="));
        addSymbol(new Simbolo("ATRIB", "14", "="));
        addSymbol(new Simbolo("ABRE_PAR", "15", "("));
        addSymbol(new Simbolo("FECHA_PAR", "16", ")"));
        addSymbol(new Simbolo("MENOR", "17", "<"));
        addSymbol(new Simbolo("MAIOR", "18", ">"));
        addSymbol(new Simbolo("MAIOR_IGUAL", "19", ">="));
        addSymbol(new Simbolo("MENOR_IGUAL", "20", "<="));
        addSymbol(new Simbolo("VIRGULA", "21", ","));
        addSymbol(new Simbolo("SOMA", "22", "+"));
        addSymbol(new Simbolo("SUB", "23", "-"));
        addSymbol(new Simbolo("MULT", "24", "*"));
        addSymbol(new Simbolo("DIV", "25", "/"));
        addSymbol(new Simbolo("PONTO_VIRG", "26", ";"));
        addSymbol(new Simbolo("BEGIN", "27", "begin"));
        addSymbol(new Simbolo("END", "28", "end"));
        addSymbol(new Simbolo("READLN", "29", "readln"));
        addSymbol(new Simbolo("WRITE", "30", "write"));
        addSymbol(new Simbolo("WRITELN", "31", "writeln"));
        addSymbol(new Simbolo("TRUE", "32", "true"));
        addSymbol(new Simbolo("FALSE", "33", "false"));
        addSymbol(new Simbolo("BOOLEAN", "34", "boolean"));
    }

    public void addSymbol(Simbolo symbol)
    {
        symbolsTable.put(symbol.getLexema(), symbol);
    }

    public Simbolo getSymbol(String lexema)
    {
        return symbolsTable.get(lexema);
    }

    public boolean contains(String lexema)
    {
        return symbolsTable.containsKey(lexema);
    }
}
