import java.io.BufferedReader;
import java.io.FileReader;

public class Lexical
{
    private static SymbolTable symbolsTable = SymbolTable.getInstance();
    private static TokenList tokens = TokenList.getInstance();
    private static Symbol symbol;
    private static Token token;

    private static int line = 1;
    private static int column = 0;
    private static boolean isComment = false;

    public static void analysis(String filePath) throws Exception
    {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader reader = new BufferedReader(fileReader);

        int currentByte;
        int nextByte;
        String lexeme = "";
        
        try 
        {
            do
            {
                currentByte = reader.read();

                if(currentByte == '\n') 
                {
                    line++;
                    column = 0;
                }
                else 
                {
                    column++;
                }
                
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

                if(currentByte == '{') 
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
                if(isSimbol(currentByte))
                {
                    if(!lexeme.isEmpty())
                    {
                        processLexeme(lexeme);
                        lexeme = "";
                    }

                    reader.mark(1);

                    nextByte = reader.read();

                    String composed = Character.toString((char) currentByte) + (char) nextByte;

                    if(symbolsTable.contains(composed))
                    {
                        token = Token.createToken(symbolsTable.getSymbol(composed), line, column);
                        tokens.addToken(token);
                    }
                    else
                    {
                        reader.reset();

                        String simple = Character.toString((char) currentByte);

                        if(symbolsTable.contains(simple))
                        {
                            token = Token.createToken(symbolsTable.getSymbol(simple), line, column);
                            tokens.addToken(token);
                        }
                        else
                        {
                            WizardSpeller.castError("Símbolo inválido: " + simple, line, column);
                        }
                    }

                    continue;
                }

                if(currentByte == '"')
                {
                    if(!lexeme.isEmpty())
                    {
                        processLexeme(lexeme);
                        lexeme = "";
                    }
                    
                    lexeme += (char) currentByte;

                    do
                    {
                        currentByte = reader.read();

                        if(currentByte == -1 || currentByte == '\n')
                        {
                            WizardSpeller.castError("String não terminada com aspas", line, column);
                            break;
                        }

                        lexeme += (char) currentByte;
                    }
                    while(currentByte != '"');

                    lexeme = lexeme.substring(1, lexeme.length() - 1);

                    symbol = new Symbol("CONTS", "2", lexeme);

                    if(!symbolsTable.contains(lexeme))
                        symbolsTable.addSymbol(symbol);

                    token = Token.createToken(symbol, line, column);
                    tokens.addToken(token);

                    lexeme = "";

                    continue;
                }
                 
                if(!Character.isWhitespace(currentByte))
                {
                    lexeme += (char) currentByte;
                }
                else
                {
                    if(!lexeme.isEmpty())
                    {
                        processLexeme(lexeme);
                        lexeme = "";
                    }
                }
                // endregion
            }
            while(currentByte != -1);

            if(isComment)
            {
                WizardSpeller.castError("Comentário não fechado antes do fim do arquivo", line, column);
            }
        }
        catch (Exception e) 
        {
            WizardSpeller.castError("Erro ao ler o arquivo: " + e.getMessage(), 0, 0);
        }
        finally
        {
            reader.close();
        }
    }

    public static void processLexeme(String lexeme)
    {
        if(symbolsTable.contains(lexeme))
        {
            token = Token.createToken(symbolsTable.getSymbol(lexeme), line, column);
            tokens.addToken(token);
        }
        else if(lexeme.matches("[a-zA-Z_][a-zA-Z0-9]*"))
        {
            symbol = new Symbol("ID", "1", lexeme);
            token = Token.createToken(symbol, line, column);

            symbolsTable.addSymbol(symbol);
            tokens.addToken(token);
        }
        else if(lexeme.matches("\\d+"))
        {
            symbol = new Symbol("CONTS", "2", lexeme);
            token = Token.createToken(symbol, line, column);

            symbolsTable.addSymbol(symbol);
            tokens.addToken(token);
        }
        else
        {
            WizardSpeller.castWarning("Lexema não reconhecido: " + lexeme, line, column);
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