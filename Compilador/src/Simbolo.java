class Simbolo 
{
    String nome;
    String token;
    String lexema;
    String classe;
    String tipo;
    String endereco;

    Simbolo(String nome, String token, String lexema) 
    {
        this.nome = nome;
        this.token = token;
        this.lexema = lexema;
        this.classe = "";
        this.tipo = "";
        this.endereco = "";
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}