package matheus.bcc.representacaografos;

public class No {
    private char info;
    private String peso;
    private No prox;

    public No(char info) {
        this.info = info;
        this.peso = "0";
        this.prox = null;
    }

    public No(char info, String peso) {
        this.info = info;
        this.peso = peso;
        this.prox = null;
    }

    public char getInfo() {
        return info;
    }

    public void setInfo(char info) {
        this.info = info;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }
}
