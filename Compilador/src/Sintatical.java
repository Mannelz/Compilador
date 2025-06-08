public class Sintatical 
{
    static SymbolTable symbolsTable = SymbolTable.getInstance();
    static TokenList tokens = TokenList.getInstance();
    static Symbol symbol;
    static Token token;

    public static void analysis()
    {
        boolean isDeclaration;

        while(!tokens.isEmpty())
        {
            token = tokens.getToken();

            isDeclaration = token.getType().equals("FINAL") || token.getType().equals("INT") || token.getType().equals("BYTE") || token.getType().equals("STRING") || token.getType().equals("BOOLEAN");

            if(token.getType().equals("ID"))
            {
                assignment();
            }
            else if(isDeclaration)
            {
                declaration();
            }
            else if(token.getType().equals("READLN"))
            {
                read();
            }
            else if(token.getType().equals("WRITE") || token.getType().equals("WRITELN"))
            {
                write();
            }
            else if(token.getType().equals("WHILE"))
            {
                loop();
            }
            else if(token.getType().equals("BEGIN"))
            {
                block();
            }
            else
            {
                WizardSpeller.castError("Não foi possível encontrar nenhum token válido.", token.getLine(), token.getColumn());
            }
        }
    }

    private static void assignment()
    {
        token = tokens.getToken();

        if(token.getType().equals("ATRIB"))
        {
            token = tokens.getToken();

            expression();

            if(token.getType().equals("PONTO_VIRG"))
            {
                // Análise semântica aqui.
            }
            else
            {
                WizardSpeller.castError("Esperado ';' após a expressão.", token.getLine(), token.getColumn());
            }
        }
        else
        {
            WizardSpeller.castError("Esperado '=' após identificador.", token.getLine(), token.getColumn());
        }
    }

    private static void declaration()
    {
        token = tokens.getToken();

        if(token.getType().equals("ID"))
        {
            token = tokens.getToken();

            if(token.getType().equals("PONTO_VIRG"))
            {
                // Análise semântica aqui.
            }
            else if(token.getType().equals("ATRIB"))
            {
                token = tokens.getToken();

                expression();

                if(token.getType().equals("PONTO_VIRG"))
                {
                    // Análise semântica aqui.
                }
                else
                {
                    WizardSpeller.castError("Esperado ';' após a expressão de inicialização.", token.getLine(), token.getColumn());
                }
            }
            else
            {
                WizardSpeller.castError("Esperado '=' para inicialização ou ';' para fim da declaração.", token.getLine(), token.getColumn());
            }
        }
        else
        {
            WizardSpeller.castError("Esperado identificador após o tipo.", token.getLine(), token.getColumn());
        }
    }

    private static void read()
    {
        token = tokens.getToken();

        if(token.getType().equals("VIRGULA"))
        {
            token = tokens.getToken();

            if(token.getType().equals("ID"))
            {
                token = tokens.getToken();

                if(token.getType().equals("PONTO_VIRG"))
                {
                    // Análise semântica aqui.
                }
                else
                {
                    WizardSpeller.castError("Esperado ';' após o identificador em readln.", token.getLine(), token.getColumn());
                }
            }
            else
            {
                WizardSpeller.castError("Esperado identificador após ',' em readln.", token.getLine(), token.getColumn());
            }
        }
        else
        {
            WizardSpeller.castError("Esperado ',' após 'readln'.", token.getLine(), token.getColumn());
        }
    }

    private static void write()
    {
        token = tokens.getToken();

        if(token.getType().equals("VIRGULA"))
        {
            token = tokens.getToken();

            if(token.getType().equals("CONST"))
            {
                token = tokens.getToken();

                if(token.getType().equals("PONTO_VIRG"))
                {
                    // Análise semântica aqui.
                }
                else if(token.getType().equals("VIRGULA"))
                {
                    token = tokens.getToken();

                    if(token.getType().equals("ID"))
                    {
                        token = tokens.getToken();

                        if(token.getType().equals("PONTO_VIRG"))
                        {
                            // Análise semântica aqui.
                        }
                        else
                        {
                            WizardSpeller.castError("Esperado ';' após o identificador em writeln.", token.getLine(), token.getColumn());
                        }
                    }
                    else
                    {
                        WizardSpeller.castError("Esperado identificador após ',' em writeln.", token.getLine(), token.getColumn());
                    }
                }
                else
                {
                    WizardSpeller.castError("Esperado ';' ou ',' após a constante em writeln.", token.getLine(), token.getColumn());
                }
            }
            else
            {
                WizardSpeller.castError("Esperado uma constante após ',' em write/writeln.", token.getLine(), token.getColumn());
            }
        }
        else
        {
            WizardSpeller.castError("Esperado ',' após 'write'/'writeln'.", token.getLine(), token.getColumn());
        }
    }

    private static void loop()
    {
        token = tokens.getToken();

        expression();

        if(!token.getType().equals("BEGIN"))
        {
            WizardSpeller.castError("Esperado 'begin' após a condição do while.", token.getLine(), token.getColumn());
        }
        
        block();
    }

    private static void block()
    {
        token = tokens.getToken();

        while(!token.getType().equals("END") && !tokens.isEmpty())
        {
            switch (token.getType())
            {
                case "ID":
                    assignment();
                    break;
                case "FINAL":
                case "INT":
                case "BYTE":
                case "STRING":
                case "BOOLEAN":
                    declaration();
                    break;
                case "READLN":
                    read();
                    break;
                case "WRITE":
                case "WRITELN":
                    write();
                    break;
                case "WHILE":
                    loop();
                    break;
                case "BEGIN":
                    block();
                    break;
                default:
                    WizardSpeller.castError("Comando inválido dentro de bloco.", token.getLine(), token.getColumn());
            }

            token = tokens.getToken();

            if(token == null)
            {
                WizardSpeller.castError("Esperado 'end' para fechar o bloco, mas fim do código foi alcançado.", 1, 1);
            }
        }
    }

    public static void expression()
    {
        // Primeiro operando
        if(isOperate(token))
        {
            token = tokens.getToken(); // consome operando
        }
        else if(token.getType().equals("ABRE_PAR"))
        {
            token = tokens.getToken(); // consome '('

            expression(); // expressão  dentro do parêntese

            if(token.getType().equals("FECHA_PAR"))
            {
                token = tokens.getToken(); // consome ')'
            }
            else
            {
                WizardSpeller.castError("Esperado ')'.", token.getLine(), token.getColumn());
            }
        }
        else if(token.getType().equals("NOT"))
        {
            token = tokens.getToken(); // consome 'not'
            expression(); // negação de expressão
        }
        else
        {
            WizardSpeller.castError("Operando (ID, CONST) inválido na expressão.", token.getLine(), token.getColumn());
        }

        // Enquanto houver operadores, continue processando a expressão
        while (isOperator(token))
        {
            token = tokens.getToken(); // consome operador

            if(isOperate(token))
            {
                token = tokens.getToken(); // consome operando
            }
            else if(token.getType().equals("ABRE_PAR"))
            {
                token = tokens.getToken(); // consome '('
                expression(); // expressão entre parênteses

                if(token.getType().equals("FECHA_PAR"))
                {
                    token = tokens.getToken(); // consome ')'
                }
                else
                {
                    WizardSpeller.castError("Esperado ')'.", token.getLine(), token.getColumn());
                }
            }
            else
            {
                WizardSpeller.castError("Esperado operando (ID, CONST) após operador (+, -, *, /, <, >, etc).", token.getLine(), token.getColumn());
            }
        }
    }

    private static boolean isOperate(Token token)
    {
        return token.getType().equals("ID") ||
            token.getType().equals("CONST") ||
            token.getType().equals("TRUE") ||
            token.getType().equals("FALSE");
    }

    private static boolean isOperator(Token token)
    {
        return token.getType().equals("SOMA") ||
            token.getType().equals("SUB") ||
            token.getType().equals("MULT") ||
            token.getType().equals("DIV") ||
            token.getType().equals("IGUAL") ||
            token.getType().equals("MENOR") ||
            token.getType().equals("MAIOR") ||
            token.getType().equals("MENOR_IGUAL") ||
            token.getType().equals("MAIOR_IGUAL") ||
            token.getType().equals("AND") ||
            token.getType().equals("OR");
    }
}