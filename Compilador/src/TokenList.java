import java.util.ArrayList;
import java.util.List;

public class TokenList
{
    private static TokenList instance = null;
    List<Token> tokens;

    private TokenList()
    {
        this.tokens = new ArrayList<>();
    }

    public static TokenList getInstance()
    {
        if (instance == null)
            instance = new TokenList();
        
        return instance;
    }

    public void addToken(Token token)
    {
        tokens.add(token);
    }
}