import java.util.LinkedList;
import java.util.Queue;

public class TokenList
{
    private static TokenList instance = null;
    Queue<Token> tokens;


    private TokenList()
    {
        this.tokens = new LinkedList<>();
    }

    public static TokenList getInstance()
    {
        if(instance == null)
            instance = new TokenList();
        
        return instance;
    }

    public void addToken(Token token)
    {
        tokens.add(token);
    }

    public Token peekToken()
    {
        return tokens.peek();
    }

    public Token getToken()
    {
        return tokens.poll();
    }

    public boolean isEmpty()
    {
        return tokens.isEmpty();
    }
}