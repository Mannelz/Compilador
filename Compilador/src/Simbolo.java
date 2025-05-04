class Simbolo 
{
    private String nome;
    private String token;
    private String lexema;
    private String classe;
    private String tipo;
    private String endereco;

    Simbolo(String nome, String token, String lexema) 
    {
        this.nome = nome;
        this.token = token;
        this.lexema = lexema;
        this.classe = "";
        this.tipo = "";
        this.endereco = "";
    }

    public String getNome()
    {
        return nome;
    }

    public String getToken()
    {
        return token;
    }

    public String getLexema()
    {
        return lexema;
    }

    public String getClasse()
    {
        return classe;
    }

    public String getTipo()
    {
        return tipo;
    }

    public String getEndereco()
    {
        return endereco;
    }

    public void setClasse(String classe)
    {
        this.classe = classe;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public void setEndereco(String endereco)
    {
        this.endereco = endereco;
    }
}