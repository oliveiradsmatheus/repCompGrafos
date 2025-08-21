package matheus.bcc.representacaografos;

public class MatIncidencia {
    public static boolean verificarOrientado(String[][] mat, int linhas) {
        boolean orientado = false;

        for(int i = 0; i < linhas && !orientado; i++)
            for (int j = 0; j < mat[0].length && !orientado; j++)
                if(Integer.parseInt(mat[i][j]) < 0)
                    orientado = true;

        return orientado;
    }

    public static boolean verificarSimples(String[][] mat, int linhas) {
        boolean simples = true;
        int cont;

        for (int c = 0; c < mat[0].length && simples; c++) {
            cont = 0;
            for (int l = 0; l < linhas; l++)
                if(!mat[l][c].equals("0"))
                    cont++;
            if (cont <= 1)
                simples = false;
        }

        for (int c1 = 0; c1 < mat[0].length - 1 && simples; c1++) {
            for (int c2 = c1 + 1; c2 < mat[0].length && simples; c2++) {
                boolean flag = true;
                for (int l = 0; l < linhas && flag; l++)
                    if (!mat[l][c1].equals(mat[l][c2]))
                        if (mat[l][c1].equals("0") || mat[l][c2].equals("0"))
                            flag = false;
                        else if (Integer.parseInt(mat[l][c1]) > 0 && Integer.parseInt(mat[l][c2]) < 0 || Integer.parseInt(mat[l][c1]) < 0 && Integer.parseInt(mat[l][c2]) > 0)
                            flag = false;
                if (flag)
                    simples = false;
            }
        }
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

    public static int verificarEmissao(String[][] mat, int linhas) {
        boolean regular = true;
        int ant = 0, atual = 0;

        for (int i = 0; i < mat[0].length; i++)
            if(Integer.parseInt(mat[0][i]) < 0)
                ant++;
        for(int i = 1; i < linhas && regular; i++) {
            for (int j = 0; j < mat[0].length; j++)
                if(Integer.parseInt(mat[i][j]) < 0)
                    atual++;
            if (atual != ant)
                regular = false;
            ant = atual;
            atual = 0;
        }

        if (regular)
            return ant;
        return -1;
    }

    public static int verificarRecepcao(String[][] mat, int linhas) {
        boolean regular = true;
        int ant = 0, atual = 0;

        for (int i = 0; i < mat[0].length; i++)
            if(Integer.parseInt(mat[0][i]) > 0)
                ant++;
        for(int i = 1; i < linhas && regular; i++) {
            for (int j = 0; j < mat[0].length; j++)
                if(Integer.parseInt(mat[i][j]) > 0)
                    atual++;
            if (atual != ant)
                regular = false;
            ant = atual;
            atual = 0;
        }

        if (regular)
            return ant;
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
