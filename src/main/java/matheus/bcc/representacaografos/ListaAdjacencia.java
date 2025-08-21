package matheus.bcc.representacaografos;

public class ListaAdjacencia {
    public static boolean verificarOrientado(Lista[] listaVertices) {
        boolean orientado = false;

        for (int i = 0; i < listaVertices.length && !orientado; i++)
            orientado = buscaVertice(listaVertices, i);

        return orientado;
    }

    private static boolean buscaVerticeLista(Lista lista, char vertice) {
        No aux = lista.getInicio().getProx();

        while (aux != null && aux.getInfo() != vertice)
            aux = aux.getProx();

        return aux == null;
    }

    private static boolean buscaVertice(Lista[] listaVertices, int pos) {
        No aux = listaVertices[pos].getInicio().getProx();
        char no = listaVertices[pos].getInicio().getInfo();
        boolean orientado = false;

        while (aux != null && !orientado) {
            for(int i = 0; i < listaVertices.length && !orientado; i++){
                if (i != pos && aux.getInfo() == listaVertices[i].getInicio().getInfo())
                    orientado = buscaVerticeLista(listaVertices[i], no);
            }
            aux = aux.getProx();
        }

        return orientado;
    }

    private static boolean buscaLaco(Lista vertice) {
        No aux = vertice.getInicio().getProx();
        char v = vertice.getInicio().getInfo();

        while (aux != null && aux.getInfo() != v)
            aux = aux.getProx();

        return aux == null;
    }

    private static boolean buscaMultiplasArestas(Lista vertices) {
        No aux = vertices.getInicio().getProx();
        boolean simples = true;

        if (aux != null)
            while (aux.getProx() != null && simples) {
                No prox = aux.getProx();
                while(prox != null && prox.getInfo() != aux.getInfo())
                    prox = prox.getProx();
                simples = prox == null;
                aux = aux.getProx();
            }

        return simples;
    }

    public static boolean verificarSimples(Lista[] listaVertices) {
        boolean simples = true;

        for (int i = 0; i < listaVertices.length && simples; i++)
            simples = buscaLaco(listaVertices[i]);

        for (int i = 0; i < listaVertices.length && simples; i++)
            simples = buscaMultiplasArestas(listaVertices[i]);

        return simples;
    }

    public static int verificarRegular(Lista[] listaVertices) {
        boolean regular = true;
        int primeira, proxima;

        primeira = listaVertices[0].getGrau();
        for(int i = 1; i < listaVertices.length && regular; i++) {
            proxima = listaVertices[i].getGrau();
            if (primeira != proxima)
                regular = false;
        }

        if (regular)
            return primeira;
        return -1;
    }

    public static int verificarCompleto(Lista[] listaVertices) {
        int k, cont = 0;

        for (int i = 0;  i < listaVertices.length; i++)
            cont += listaVertices[i].getGrau();

        k = listaVertices.length * (listaVertices.length - 1);

        System.out.println(k);

        if (k == cont)
            return k/2;
        return -1;
    }

    private static int contaVertice(Lista[] listaVertices, int pos) {
        char no = listaVertices[pos].getInicio().getInfo();
        int cont = 0;

        for (int i = 0; i < listaVertices.length; i++)
            if (i != pos) {
                No aux = listaVertices[i].getInicio().getProx();
                while (aux != null) {
                    if (aux.getInfo() == no)
                        cont++;
                    aux = aux.getProx();
                }
            }

        return cont;
    }

    public static int verificarRecepcao(Lista[] listaVertices) {
        int n, comp;

        n = comp = contaVertice(listaVertices, 0);
        for (int i = 1; i < listaVertices.length && n == comp; i++)
            comp = contaVertice(listaVertices, i);

        if (n == comp)
            return n;
        return -1;
    }
}
