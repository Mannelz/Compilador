import java.util.HashMap;
import java.util.Map;

public class SymbolTable 
{
    private static SymbolTable instance = null;
    private Map<String, Symbol> symbolsTable;

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
        addSymbol(new Symbol("FINAL", "3", "final"));
        addSymbol(new Symbol("INT", "4", "int"));
        addSymbol(new Symbol("BYTE", "5", "byte"));
        addSymbol(new Symbol("STRING", "6", "string"));
        addSymbol(new Symbol("WHILE", "7", "while"));
        addSymbol(new Symbol("IF", "8", "if"));
        addSymbol(new Symbol("ELSE", "9", "else"));
        addSymbol(new Symbol("AND", "10", "and"));
        addSymbol(new Symbol("OR", "11", "or"));
        addSymbol(new Symbol("NOT", "12", "not"));
        addSymbol(new Symbol("IGUAL", "13", "=="));
        addSymbol(new Symbol("ATRIB", "14", "="));
        addSymbol(new Symbol("ABRE_PAR", "15", "("));
        addSymbol(new Symbol("FECHA_PAR", "16", ")"));
        addSymbol(new Symbol("MENOR", "17", "<"));
        addSymbol(new Symbol("MAIOR", "18", ">"));
        addSymbol(new Symbol("MAIOR_IGUAL", "19", ">="));
        addSymbol(new Symbol("MENOR_IGUAL", "20", "<="));
        addSymbol(new Symbol("VIRGULA", "21", ","));
        addSymbol(new Symbol("SOMA", "22", "+"));
        addSymbol(new Symbol("SUB", "23", "-"));
        addSymbol(new Symbol("MULT", "24", "*"));
        addSymbol(new Symbol("DIV", "25", "/"));
        addSymbol(new Symbol("PONTO_VIRG", "26", ";"));
        addSymbol(new Symbol("BEGIN", "27", "begin"));
        addSymbol(new Symbol("END", "28", "end"));
        addSymbol(new Symbol("READLN", "29", "readln"));
        addSymbol(new Symbol("WRITE", "30", "write"));
        addSymbol(new Symbol("WRITELN", "31", "writeln"));
        addSymbol(new Symbol("TRUE", "32", "true"));
        addSymbol(new Symbol("FALSE", "33", "false"));
        addSymbol(new Symbol("BOOLEAN", "34", "boolean"));
    }

    public void addSymbol(Symbol symbol)
    {
        symbolsTable.put(symbol.getLexeme(), symbol);
    }

    public Symbol getSymbol(String lexema)
    {
        return symbolsTable.get(lexema);
    }

    public boolean contains(String lexema)
    {
        return symbolsTable.containsKey(lexema);
    }
}
