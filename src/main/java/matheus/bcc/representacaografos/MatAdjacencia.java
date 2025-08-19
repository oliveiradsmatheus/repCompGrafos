package matheus.bcc.representacaografos;

public class MatAdjacencia {

    public static boolean verificarOrientado(String[][] matriz, int linhas, int colunas) {
        boolean orientado = false;

        for (int l = 0; l < linhas - 1 && !orientado; l++)
            for (int c = l + 1; c < colunas && !orientado; c++)
                //verifica se é digrafo
                if (!matriz[l][c].equals(matriz[c][l]))
                    orientado = true;

        return orientado;
    }

    public static boolean verificarSimples(String[][] matriz, int colunas) {
        boolean simples = true;
        // Verifica se há laços
        for (int i = 0; i < colunas && simples; i++)
            if(!matriz[i][i].equals("0"))
                simples = false;

        return simples;
    }

    public static int verificarRegular(String[][] mat, int linhas) {
        boolean regular = true;
        int cont1 = 0, cont2;

        for(int i = 0; i < linhas - 1 && regular; i++) {
            cont1 = cont2 = 0;
            for (int j = 0; j < mat[0].length; j++) {
                if (!mat[i][j].equals("0"))
                    cont1++;
                if (!mat[i + 1][j].equals("0"))
                    cont2++;
            }
            if (cont1 != cont2)
                regular = false;
        }

        if (regular)
            return cont1;
        return -1;
    }

    public static int verificarCompleto(String[][] mat, int linhas) {
        int k, cont = 0;

        for (int i= 0; i < linhas; i++)
            for(int j = 0; j < mat[0].length; j++)
                if (!mat[i][j].equals("0"))
                    cont++;

        k = (linhas*(linhas-1));

        if (k == cont)
            return k/2;
        return -1;
    }
}
