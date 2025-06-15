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
        addSymbol(new Symbol("FINAL", "3", "final", SymbolClass.EMPTY));
        addSymbol(new Symbol("INT", "4", "int", SymbolClass.EMPTY, SymbolType.INT));
        addSymbol(new Symbol("BYTE", "5", "byte", SymbolClass.EMPTY, SymbolType.BYTE));
        addSymbol(new Symbol("STRING", "6", "string", SymbolClass.EMPTY, SymbolType.STRING));
        addSymbol(new Symbol("WHILE", "7", "while", SymbolClass.EMPTY));
        addSymbol(new Symbol("IF", "8", "if", SymbolClass.EMPTY));
        addSymbol(new Symbol("ELSE", "9", "else", SymbolClass.EMPTY));
        addSymbol(new Symbol("AND", "10", "and", SymbolClass.EMPTY));
        addSymbol(new Symbol("OR", "11", "or", SymbolClass.EMPTY));
        addSymbol(new Symbol("NOT", "12", "not", SymbolClass.EMPTY));
        addSymbol(new Symbol("IGUAL", "13", "==", SymbolClass.EMPTY));
        addSymbol(new Symbol("ATRIB", "14", "=", SymbolClass.EMPTY));
        addSymbol(new Symbol("ABRE_PAR", "15", "(", SymbolClass.EMPTY));
        addSymbol(new Symbol("FECHA_PAR", "16", ")", SymbolClass.EMPTY));
        addSymbol(new Symbol("MENOR", "17", "<", SymbolClass.EMPTY));
        addSymbol(new Symbol("MAIOR", "18", ">", SymbolClass.EMPTY));
        addSymbol(new Symbol("MAIOR_IGUAL", "19", ">=", SymbolClass.EMPTY));
        addSymbol(new Symbol("MENOR_IGUAL", "20", "<=", SymbolClass.EMPTY));
        addSymbol(new Symbol("VIRGULA", "21", ",", SymbolClass.EMPTY));
        addSymbol(new Symbol("SOMA", "22", "+", SymbolClass.EMPTY));
        addSymbol(new Symbol("SUB", "23", "-", SymbolClass.EMPTY));
        addSymbol(new Symbol("MULT", "24", "*", SymbolClass.EMPTY));
        addSymbol(new Symbol("DIV", "25", "/", SymbolClass.EMPTY));
        addSymbol(new Symbol("PONTO_VIRG", "26", ";", SymbolClass.EMPTY));
        addSymbol(new Symbol("BEGIN", "27", "begin", SymbolClass.EMPTY));
        addSymbol(new Symbol("END", "28", "end", SymbolClass.EMPTY));
        addSymbol(new Symbol("READLN", "29", "readln", SymbolClass.EMPTY));
        addSymbol(new Symbol("WRITE", "30", "write", SymbolClass.EMPTY));
        addSymbol(new Symbol("WRITELN", "31", "writeln", SymbolClass.EMPTY));
        addSymbol(new Symbol("TRUE", "32", "true", SymbolClass.EMPTY, SymbolType.LOGICAL));
        addSymbol(new Symbol("FALSE", "33", "false", SymbolClass.EMPTY, SymbolType.LOGICAL));
        addSymbol(new Symbol("BOOLEAN", "34", "boolean", SymbolClass.EMPTY));
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