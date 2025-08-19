package matheus.bcc.representacaografos;

public class Lista {
    char info;
    char peso;
    Lista prox;

    public Lista(char info, char peso) {
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

    public char getPeso() {
        return peso;
    }

    public void setPeso(char peso) {
        this.peso = peso;
    }

    public Lista getProx() {
        return prox;
    }

    public void setProx(Lista prox) {
        this.prox = prox;
    }
}
