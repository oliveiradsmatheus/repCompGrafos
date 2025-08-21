package matheus.bcc.representacaografos;

public class Lista {
    private No inicio;

    public Lista(char vertice) {
        this.inicio = new No(vertice);
    }

    public No getInicio() {
        return inicio;
    }

    public int getGrau() {
        int cont = 0;

        if (inicio != null) {
            No aux = inicio.getProx();

            while (aux != null) {
                cont++;
                aux = aux.getProx();
            }
        }

        return cont;
    }

    public void inserir(char info, String peso) {
        No aux = inicio, novo = new No(info, peso);
        while(aux.getProx() != null)
            aux = aux.getProx();
        aux.setProx(novo);
    }
}
