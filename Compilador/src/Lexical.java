import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexical
{
    public static void Analysis(String filePath)
    {
        Map<String, Simbolo> symbolsTable = CreateTable();
        List<Token> tokens = new ArrayList<Token>();
        Simbolo symbol;
        Token token;

        int currentByte;
        int nextByte;
        String lexeme = "";
        boolean isComment = false;

        try 
        {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(fileReader);

            do
            {
                currentByte = reader.read();
                
                // region Verifica comentários
                if(currentByte == '/')
                {
                    reader.mark(1);

                    nextByte = reader.read();

                    if(nextByte == '*')
                    {
                        isComment = true;

                        continue;
                    }
                    else
                    {
                        reader.reset();
                    }
                }

                if (currentByte == '{') 
                {
                    isComment = true;

                    continue;
                }

                if(isComment)
                {
                    if(currentByte == '*')
                    {
                        nextByte = reader.read();

                        if(nextByte == '/')
                        {
                            isComment = false;

                            continue;
                        }
                        else
                        {
                            reader.reset();
                        }
                    }

                    if(currentByte == '}')
                    {
                        isComment = false;

                        continue;
                    }

                    continue;
                }
                // endregion

                // region Cria Lexemas 
                
                
                
                //TALVEZ JA TO VERIFICANDO ISSO LA EMBAIXO
                if(IsSimbol(currentByte)){
                    token = new Token(symbolsTable.get(currentByte).nome, Character.toString((char) currentByte));
                    tokens.add(token);
                    continue;
                }

                if(currentByte == '"')
                {
                    lexeme += (char) currentByte;

                    do
                    {
                        currentByte = reader.read();
                        lexeme += (char) currentByte;
                    }
                    while(currentByte != '"');

                    lexeme = lexeme.substring(1, lexeme.length() - 1);

                    symbol = new Simbolo("CONTS", "2", lexeme);

                    if(!symbolsTable.containsKey(lexeme)) 
                    {
                        symbolsTable.put(lexeme, symbol);
                    }

                    lexeme = "";
                }
                 
                if(!Character.isWhitespace(currentByte))
                {
                    lexeme += (char) currentByte;
                }
                else
                {
                    if (!lexeme.isEmpty()) 
                    {
                        //Verifica ID se não estiver na tabela adiciona
                        if(lexeme.matches("[a-zA-Z_][a-zA-Z0-9]*"))
                        {
                            symbol = new Simbolo("ID", "1", lexeme);
                            if (!symbolsTable.containsKey(lexeme)) 
                            {
                                symbolsTable.put(lexeme, symbol);
                            }
                            
                            token = new Token("ID", lexeme);
                            tokens.add(token);
                        }
                        // Verifica const se não estiver na tabela adiciona
                        else if (lexeme.matches("[a-zA-Z0-9]+")) 
                        {
                            symbol = new Simbolo("CONST", "2", lexeme);
                            
                            if (!symbolsTable.containsKey(lexeme))
                             {
                                symbolsTable.put(lexeme, symbol);
                            }
                            
                            token = new Token("CONST", lexeme);
                            tokens.add(token);
                        }
                        // Verifica int
                        else if(lexeme.matches("[+-]?[0-9]+" ))
                        {
                            token = new Token("INT", lexeme);
                            tokens.add(token);
                        }
                        // Verifica operador
                        else if (lexeme.matches("\\+|\\-|\\*|/")) 
                        {
                            token = new Token("OPERADOR", lexeme);
                            tokens.add(token);
                        }
                        
                        // Verifica Hexa
                        else if (lexeme.matches("0h[0-9A-F]+")) 
                        {
                            token = new Token("HEXA", lexeme);
                            tokens.add(token);
                        } 

                        else
                        {
                            //como vamos tratar se n passar por nenhum???
                        }    

                        
                    }

                    lexeme = "";
                }
                // endregion
            }
            while(currentByte != -1);

            reader.close();
        }
        catch (Exception e) 
        {
            System.out.println("Error: " + e);
        }
    }

    public static Map<String, Simbolo> CreateTable() 
    {
        Map<String, Simbolo> symbolsTable = new HashMap<>();

        symbolsTable.put("final", new Simbolo("FINAL", "3", "final"));
        symbolsTable.put("int", new Simbolo("INT", "4", "int"));
        symbolsTable.put("byte", new Simbolo("BYTE", "5", "byte"));
        symbolsTable.put("string", new Simbolo("STRING", "6", "string"));
        symbolsTable.put("while", new Simbolo("WHILE", "7", "while"));
        symbolsTable.put("if", new Simbolo("IF", "8", "if"));
        symbolsTable.put("else", new Simbolo("ELSE", "9", "else"));
        symbolsTable.put("and", new Simbolo("AND", "10", "and"));
        symbolsTable.put("or", new Simbolo("OR", "11", "or"));
        symbolsTable.put("not", new Simbolo("NOT", "12", "not"));
        symbolsTable.put("==", new Simbolo("IGUAL", "13", "=="));
        symbolsTable.put("=", new Simbolo("ATRIB", "14", "="));
        symbolsTable.put("(", new Simbolo("ABRE_PAR", "15", "("));
        symbolsTable.put(")", new Simbolo("FECHA_PAR", "16", ")"));
        symbolsTable.put("<", new Simbolo("MENOR", "17", "<"));
        symbolsTable.put(">", new Simbolo("MAIOR", "18", ">"));
        symbolsTable.put(">=", new Simbolo("MAIOR_IGUAL", "19", ">="));
        symbolsTable.put("<=", new Simbolo("MENOR_IGUAL", "20", "<="));
        symbolsTable.put(",", new Simbolo("VIRGULA", "21", ","));
        symbolsTable.put("+", new Simbolo("SOMA", "22", "+"));
        symbolsTable.put("-", new Simbolo("SUB", "23", "-"));
        symbolsTable.put("*", new Simbolo("MULT", "24", "*"));
        symbolsTable.put("/", new Simbolo("DIV", "25", "/"));
        symbolsTable.put(";", new Simbolo("PONTO_VIRG", "26", ";"));
        symbolsTable.put("begin", new Simbolo("BEGIN", "27", "begin"));
        symbolsTable.put("end", new Simbolo("END", "28", "end"));
        symbolsTable.put("readln", new Simbolo("READLN", "29", "readln"));
        symbolsTable.put("write", new Simbolo("WRITE", "30", "write"));
        symbolsTable.put("writeln", new Simbolo("WRITELN", "31", "writeln"));
        symbolsTable.put("true", new Simbolo("TRUE", "32", "true"));
        symbolsTable.put("false", new Simbolo("FALSE", "33", "false"));
        symbolsTable.put("boolean", new Simbolo("BOOLEAN", "34", "boolean"));

        return symbolsTable;
    }

    public static boolean IsSimbol(int currentByte)
    {
        switch(currentByte)
        {
            case '=', '(', ')', '<', '>', ',', '+', '-', '*', '/', ';':
                return true;
            default:
                return false;
        }
    }
}