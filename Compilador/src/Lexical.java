import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexical
{
    public static void analysis(String filePath)
    {
        SymbolTable symbolsTable = SymbolTable.getInstance();
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
                /*
                if(isSimbol(currentByte)){
                    token = new Token(symbolsTable.getSymbol(currentByte).nome, Character.toString((char) currentByte));
                    tokens.add(token);
                    continue;
                }
                */

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

                    if(!symbolsTable.contains(lexeme)) 
                    {
                        symbolsTable.addSymbol(symbol);
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
                            if (!symbolsTable.contains(lexeme)) 
                            {
                                symbolsTable.addSymbol(symbol);
                            }
                            
                            token = new Token("ID", lexeme);
                            tokens.add(token);
                        }
                        // Verifica const se não estiver na tabela adiciona
                        else if (lexeme.matches("[a-zA-Z0-9]+")) 
                        {
                            symbol = new Simbolo("CONST", "2", lexeme);
                            
                            if (!symbolsTable.contains(lexeme))
                            {
                                symbolsTable.addSymbol(symbol);
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

    public static boolean isSimbol(int currentByte)
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