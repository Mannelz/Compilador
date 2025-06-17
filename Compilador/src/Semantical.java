public class Semantical
{
    private SymbolTable symbolsTable;

    public Semantical(SymbolTable symbolsTable)
    {
        this.symbolsTable = symbolsTable;
    }

    public String getLiteralType(Symbol symbol)
    {
        switch(symbol.getSymbolType())
        {
            case SymbolType.INT:
                return "INT";
            case SymbolType.BOOLEAN: 
                return "BOOLEAN";
            case SymbolType.BYTE:
                return "BYTE";
            case SymbolType.STRING: 
                return "STRING";
            default:
                return "UNKNOWN"; // Tipo desconhecido
        }
    }

    public String getLiteralType(Token token)
    {
        Symbol literalToken = symbolsTable.getSymbol(token.getValue());

        switch(literalToken.getSymbolType())
        {
            case SymbolType.INT:
                return "INT";
            case SymbolType.BOOLEAN: 
                return "BOOLEAN";
            case SymbolType.BYTE:
                return "BYTE";
            case SymbolType.STRING: 
                return "STRING";
            default:
                return "UNKNOWN"; // Tipo desconhecido
        }
    }

    public boolean checkTypeCompatibility(String expectedType, String actualType)
    {
        if(expectedType.equals("UNKNOWN") || actualType.equals("UNKNOWN"))
        {
            return false;
        }

        if(expectedType.equals(actualType))
        {
            return true;
        }

        if(expectedType.equals("INT") && actualType.equals("BYTE"))
        {
            return true;
        }

        return false;
    }

    public String checkTypeOperator(String operatorType, String currentType, String rightOperandType, Token tokenOperator)
    {
        String resultType = "UNKNOWN";

        switch(operatorType)
        {
            // SOMA pode ser para INT/BYTE ou concatenação de STRING
            case "SOMA":
                if(currentType.equals("STRING") || rightOperandType.equals("STRING"))
                {
                    resultType = "STRING";
                }
                else if((currentType.equals("INT") || currentType.equals("BYTE")) && (rightOperandType.equals("INT") || rightOperandType.equals("BYTE")))
                {
                    if (currentType.equals("INT") || rightOperandType.equals("INT"))
                    {
                        resultType = "INT";
                    }
                    else
                    {
                        resultType = "BYTE";
                    }
                }
                else if(!currentType.equals("UNKNOWN") && !rightOperandType.equals("UNKNOWN"))
                {
                    WizardSpeller.castError("Tipos incompatíveis para o operador '+': " + currentType + " e " + rightOperandType + ".", tokenOperator.getLine(), tokenOperator.getColumn());
                    resultType = "UNKNOWN";
                } else {
                    resultType = "UNKNOWN"; // Se algum tipo é desconhecido, o resultado é desconhecido
                }
                break;

            // Operadores aritméticos (apenas para numéricos)
            case "SUB":
            case "MULT":
            case "DIV":
                if(!currentType.equals("INT") && !currentType.equals("BYTE") && !currentType.equals("UNKNOWN"))
                {
                    WizardSpeller.castError("Operando esquerdo de '" + operatorType + "' não é numérico. Encontrado: " + currentType + ".", tokenOperator.getLine(), tokenOperator.getColumn());
                    resultType = "UNKNOWN";
                }

                if(!rightOperandType.equals("INT") && !rightOperandType.equals("BYTE") && !rightOperandType.equals("UNKNOWN"))
                {
                    WizardSpeller.castError("Operando direito de '" + operatorType + "' não é numérico. Encontrado: " + rightOperandType + ".", tokenOperator.getLine(), tokenOperator.getColumn());
                    resultType = "UNKNOWN";
                }

                // Promoção de tipo: se um for INT, resultado é INT, caso contrário BYTE.
                if(currentType.equals("INT") || rightOperandType.equals("INT"))
                {
                    resultType = "INT";
                }
                else if(currentType.equals("BYTE") && rightOperandType.equals("BYTE"))
                {
                    resultType = "BYTE";
                }
                else
                {
                    resultType = "UNKNOWN";
                }
                break;

            // Operadores relacionais: esperam tipos compatíveis, resultado é BOOLEAN
            case "IGUAL":
            case "MENOR":
            case "MAIOR":
            case "MENOR_IGUAL":
            case "MAIOR_IGUAL":
                if(!checkTypeCompatibility(currentType, rightOperandType) && !checkTypeCompatibility(rightOperandType, currentType) && !currentType.equals("UNKNOWN") && !rightOperandType.equals("UNKNOWN"))
                {
                    WizardSpeller.castError("Incompatibilidade de tipos para o operador '" + operatorType + "'. Tipos: " + currentType + " e " + rightOperandType + ".", tokenOperator.getLine(), tokenOperator.getColumn());
                }
                resultType = "BOOLEAN";
                break;
            
            // Operadores lógicos: esperam BOOLEAN, resultado é BOOLEAN
            case "AND":
            case "OR":
                if(!currentType.equals("BOOLEAN") && !currentType.equals("UNKNOWN"))
                {
                    WizardSpeller.castError("Operando esquerdo de '" + operatorType + "' não é booleano. Encontrado: " + currentType + ".", tokenOperator.getLine(), tokenOperator.getColumn());
                    resultType = "UNKNOWN";
                }

                if(!rightOperandType.equals("BOOLEAN") && !rightOperandType.equals("UNKNOWN"))
                {
                    WizardSpeller.castError("Operando direito de '" + operatorType + "' não é booleano. Encontrado: " + rightOperandType + ".", tokenOperator.getLine(), tokenOperator.getColumn());
                    resultType = "UNKNOWN";
                }
                
                resultType = "BOOLEAN";
                break;

            default:
                WizardSpeller.castError("Operador desconhecido na expressão: " + operatorType + ".", tokenOperator.getLine(), tokenOperator.getColumn());
                resultType = "UNKNOWN";
                break;
        }

        return resultType;
    }

    public void setType(Token token, String type)
    {
        Symbol symbol = symbolsTable.getSymbol(token.getValue());

        SymbolType symbolType = SymbolType.fromString(type);
        
        symbol.setSymbolType(symbolType);
    }

    public boolean setType(Token token, String type, String lastExpressionType)
    {
        Symbol symbol = symbolsTable.getSymbol(token.getValue());

        if(!checkTypeCompatibility(type, lastExpressionType))
        {
            return false;
        }

        SymbolType symbolType = SymbolType.fromString(type);
        
        symbol.setSymbolType(symbolType);

        return true;
    }
}