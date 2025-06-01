public class Sintatical 
{
    static SymbolTable symbolsTable = SymbolTable.getInstance();
    static TokenList tokens = TokenList.getInstance();
    static Simbolo symbol;
    static Token token;

    public static void analysis()
    {
        boolean isDeclaration;

        while(!tokens.isEmpty())
        {
            token = tokens.nextToken();

            isDeclaration = token.getTipo().equals("FINAL") || token.getTipo().equals("INT") || token.getTipo().equals("BYTE") || token.getTipo().equals("STRING") || token.getTipo().equals("BOOLEAN");

            if(token.getTipo().equals("ID"))
            {
                assignment();
            }
            else if(isDeclaration)
            {
                declaration();
            }
            else if(token.getTipo().equals("READLN"))
            {
                read();
            }
            else if(token.getTipo().equals("WRITE") || token.getTipo().equals("WRITELN"))
            {
                write();
            }
            else if(token.getTipo().equals("WHILE"))
            {
                loop();
            }
            else if(token.getTipo().equals("BEGIN"))
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
        token = tokens.nextToken();

        if(token.getTipo().equals("ATRIB"))
        {
            token = tokens.nextToken();

            expression();

            if(token.getTipo().equals("PONTO_VIRG"))
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
        token = tokens.nextToken();

        if(token.getTipo().equals("ID"))
        {
            token = tokens.nextToken();

            if(token.getTipo().equals("PONTO_VIRG"))
            {
                // Análise semântica aqui.
            }
            else if(token.getTipo().equals("ATRIB"))
            {
                token = tokens.nextToken();

                expression();

                if(token.getTipo().equals("PONTO_VIRG"))
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
        token = tokens.nextToken();

        if(token.getTipo().equals("VIRGULA"))
        {
            token = tokens.nextToken();

            if(token.getTipo().equals("ID"))
            {
                token = tokens.nextToken();

                if(token.getTipo().equals("PONTO_VIRG"))
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
        token = tokens.nextToken();

        if(token.getTipo().equals("VIRGULA"))
        {
            token = tokens.nextToken();

            if(token.getTipo().equals("CONST"))
            {
                token = tokens.nextToken();

                if(token.getTipo().equals("PONTO_VIRG"))
                {
                    // Análise semântica aqui.
                }
                else if(token.getTipo().equals("VIRGULA"))
                {
                    token = tokens.nextToken();

                    if(token.getTipo().equals("ID"))
                    {
                        token = tokens.nextToken();

                        if(token.getTipo().equals("PONTO_VIRG"))
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
        token = tokens.nextToken();

        expression();

        if(!token.getTipo().equals("BEGIN"))
        {
            WizardSpeller.castError("Esperado 'begin' após a condição do while.", 1, 1);
        }
        
        block();
    }

    private static void block()
    {
        token = tokens.nextToken();

        while(!token.getTipo().equals("END"))
        {
            switch (token.getTipo())
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

            token = tokens.nextToken();

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
            token = tokens.nextToken(); // consome operando
        } 
        else if(token.getTipo().equals("ABRE_PAR"))
        {
            token = tokens.nextToken(); // consome '('
            expression(); // expressão dentro do parêntese

            if(token.getTipo().equals("FECHA_PAR"))
            {
                token = tokens.nextToken(); // consome ')'
            }
            else
            {
                WizardSpeller.castError("Esperado ')'.", 1, 1);
            }
        }
        else if(token.getTipo().equals("NOT"))
        {
            token = tokens.nextToken(); // consome 'not'
            expression(); // negação de expressão
        }
        else
        {
            WizardSpeller.castError("Operando (ID, CONST) inválido na expressão.", 1, 1);
        }

        // Enquanto houver operadores, continue processando a expressão
        while (isOperator(token))
        {
            token = tokens.nextToken(); // consome operador

            if(isOperate(token))
            {
                token = tokens.nextToken(); // consome operando
            }
            else if(token.getTipo().equals("ABRE_PAR"))
            {
                token = tokens.nextToken(); // consome '('
                expression(); // expressão entre parênteses

                if(token.getTipo().equals("FECHA_PAR"))
                {
                    token = tokens.nextToken(); // consome ')'
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
        return token.getTipo().equals("ID") ||
            token.getTipo().equals("CONST") ||
            token.getTipo().equals("TRUE") ||
            token.getTipo().equals("FALSE");
    }

    private static boolean isOperator(Token token)
    {
        return token.getTipo().equals("SOMA") ||
            token.getTipo().equals("SUB") ||
            token.getTipo().equals("MULT") ||
            token.getTipo().equals("DIV") ||
            token.getTipo().equals("IGUAL") ||
            token.getTipo().equals("MENOR") ||
            token.getTipo().equals("MAIOR") ||
            token.getTipo().equals("MENOR_IGUAL") ||
            token.getTipo().equals("MAIOR_IGUAL") ||
            token.getTipo().equals("AND") ||
            token.getTipo().equals("OR");
    }
}