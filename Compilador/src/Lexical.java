import java.io.BufferedReader;
import java.io.FileReader;

public class Lexical
{
    public static void analysis(String filePath)
    {
        SymbolTable symbolsTable = SymbolTable.getInstance();
        TokenList tokens = TokenList.getInstance();
        Simbolo symbol;
        Token token;

        int line = 1;
        int column = 0;
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

                if(currentByte == '\n') 
                {
                    line++;
                    column = 0;
                } else 
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
                    reader.mark(1);

                    nextByte = reader.read();

                    String composed = Character.toString((char) currentByte) + (char) nextByte;

                    if(symbolsTable.contains(composed))
                    {
                        token = Token.createToken(symbolsTable.getSymbol(composed));
                        tokens.addToken(token);
                    }
                    else
                    {
                        reader.reset();

                        String simple = Character.toString((char) currentByte);

                        if(symbolsTable.contains(simple))
                        {
                            token = Token.createToken(symbolsTable.getSymbol(simple));
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

                    symbol = new Simbolo("CONTS", "2", lexeme);

                    if(!symbolsTable.contains(lexeme))
                        symbolsTable.addSymbol(symbol);

                    token = Token.createToken(symbol);
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
                        if(symbolsTable.contains(lexeme))
                        {
                            token = Token.createToken(symbolsTable.getSymbol(lexeme));
                            tokens.addToken(token);
                        }
                        else if(lexeme.matches("\\d+"))
                        {
                            symbol = new Simbolo("CONTS", "2", lexeme);
                            token = Token.createToken(symbol);

                            symbolsTable.addSymbol(symbol);
                            tokens.addToken(token);
                        }
                        else
                        {
                            if(!lexeme.matches("[a-zA-Z_][a-zA-Z0-9_]*"))
                            {
                                WizardSpeller.castWarning("Identificador inválido ou mal formado: " + lexeme, line, column);
                            }

                            symbol = new Simbolo("ID", "1", lexeme);
                            token = Token.createToken(symbol);

                            symbolsTable.addSymbol(symbol);
                            tokens.addToken(token);
                        }
                    }

                    lexeme = "";
                }
                // endregion
            }
            while(currentByte != -1);

            if(isComment)
            {
                WizardSpeller.castError("Comentário não fechado antes do fim do arquivo", line, column);
            }

            reader.close();
        }
        catch (Exception e) 
        {
            WizardSpeller.castError("Erro ao ler o arquivo: " + e.getMessage(), 0, 0);
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