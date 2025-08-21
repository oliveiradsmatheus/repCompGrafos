package matheus.bcc.representacaografos;

public class Lista {
    char info;
    String peso;
    Lista prox;

    public Lista(char info) {
        this.info = info;
        this.peso = "0";
        this.prox = null;
    }

    public Lista(char info, String peso) {
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

    public Lista getProx() {
        return prox;
    }

    public void setProx(Lista prox) {
        this.prox = prox;
    }
}
