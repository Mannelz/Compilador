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
        addSymbol(new Symbol("FINAL", "3", "final", 0));
        addSymbol(new Symbol("INT", "4", "int", 0, 1));
        addSymbol(new Symbol("BYTE", "5", "byte", 0, 3));
        addSymbol(new Symbol("STRING", "6", "string",0, 4));
        addSymbol(new Symbol("WHILE", "7", "while",0));
        addSymbol(new Symbol("IF", "8", "if",0));
        addSymbol(new Symbol("ELSE", "9", "else",0));
        addSymbol(new Symbol("AND", "10", "and",0));
        addSymbol(new Symbol("OR", "11", "or",0));
        addSymbol(new Symbol("NOT", "12", "not",0));
        addSymbol(new Symbol("IGUAL", "13", "==",0));
        addSymbol(new Symbol("ATRIB", "14", "=",0));
        addSymbol(new Symbol("ABRE_PAR", "15", "(",0));
        addSymbol(new Symbol("FECHA_PAR", "16", ")",0));
        addSymbol(new Symbol("MENOR", "17", "<",0));
        addSymbol(new Symbol("MAIOR", "18", ">",0));
        addSymbol(new Symbol("MAIOR_IGUAL", "19", ">=",0));
        addSymbol(new Symbol("MENOR_IGUAL", "20", "<=",0));
        addSymbol(new Symbol("VIRGULA", "21", ",",0));
        addSymbol(new Symbol("SOMA", "22", "+",0));
        addSymbol(new Symbol("SUB", "23", "-",0));
        addSymbol(new Symbol("MULT", "24", "*",0));
        addSymbol(new Symbol("DIV", "25", "/",0));
        addSymbol(new Symbol("PONTO_VIRG", "26", ";",0));
        addSymbol(new Symbol("BEGIN", "27", "begin",0));
        addSymbol(new Symbol("END", "28", "end",0));
        addSymbol(new Symbol("READLN", "29", "readln",0));
        addSymbol(new Symbol("WRITE", "30", "write",0));
        addSymbol(new Symbol("WRITELN", "31", "writeln",0));
        addSymbol(new Symbol("TRUE", "32", "true",0, 2));
        addSymbol(new Symbol("FALSE", "33", "false",0, 2));
        addSymbol(new Symbol("BOOLEAN", "34", "boolean",0));
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
