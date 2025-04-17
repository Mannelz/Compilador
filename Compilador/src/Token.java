public class Token {
    String tipo;
    String valor;

    Token(String tipo, String valor){
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

}
