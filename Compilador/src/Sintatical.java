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
                WizardSpeller.castError("Não foi possível encontrar nenhum token válido.", 1, 1);
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
                WizardSpeller.castError("Esperado ';' após a expressão.", 1 ,1);
            }
        }
        else
        {
            WizardSpeller.castError("Esperado '=' após identificador.", 1 ,1);
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
                    WizardSpeller.castError("Esperado ';' após a expressão de inicialização.", 1 ,1);
                }
            }
            else
            {
                WizardSpeller.castError("Esperado '=' para inicialização ou ';' para fim da declaração.", 1 ,1);
            }
        }
        else
        {
            WizardSpeller.castError("Esperado identificador após o tipo.", 1 ,1);
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
                    WizardSpeller.castError("Esperado ';' após o identificador em readln.", 1 ,1);
                }
            }
            else
            {
                WizardSpeller.castError("Esperado identificador após ',' em readln.", 1 ,1);
            }
        }
        else
        {
            WizardSpeller.castError("Esperado ',' após 'readln'.", 1 ,1);
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
                            WizardSpeller.castError("Esperado ';' após o identificador em writeln.", 1 ,1);
                        }
                    }
                    else
                    {
                        WizardSpeller.castError("Esperado identificador após ',' em writeln.", 1 ,1);
                    }
                }
                else
                {
                    WizardSpeller.castError("Esperado ';' ou ',' após a constante em writeln.", 1 ,1);
                }
            }
            else
            {
                WizardSpeller.castError("Esperado uma constante após ',' em write/writeln.", 1 ,1);
            }
        }
        else
        {
            WizardSpeller.castError("Esperado ',' após 'write'/'writeln'.", 1 ,1);
        }
    }

    private static void loop()
    {
        token = tokens.getToken();

        expression();

        if(!token.getType().equals("BEGIN"))
        {
            WizardSpeller.castError("Esperado 'begin' após a condição do while.", 1, 1);
        }
        
        block();
    }

    private static void block()
    {
        token = tokens.getToken();

        while(!token.getType().equals("END"))
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
                    WizardSpeller.castError("Comando inválido dentro de bloco.", 1, 1);
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
            expression(); // expressão dentro do parêntese

            if(token.getType().equals("FECHA_PAR"))
            {
                token = tokens.getToken(); // consome ')'
            }
            else
            {
                WizardSpeller.castError("Esperado ')'.", 1, 1);
            }
        }
        else if(token.getType().equals("NOT"))
        {
            token = tokens.getToken(); // consome 'not'
            expression(); // negação de expressão
        }
        else
        {
            WizardSpeller.castError("Operando (ID, CONST) inválido na expressão.", 1, 1);
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
                    WizardSpeller.castError("Esperado ')'.", 1, 1);
                }
            }
            else
            {
                WizardSpeller.castError("Esperado operando (ID, CONST) após operador (+, -, *, /, <, >, etc).", 1, 1);
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