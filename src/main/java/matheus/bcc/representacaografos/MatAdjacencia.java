package matheus.bcc.representacaografos;

public class MatAdjacencia {
    public static boolean isOrientado(String[][] mat) {
        boolean orientado = true;
        int tam = mat[0].length;

        for(int i = 1; i < tam && orientado; i++){
            for(int j = i; j < tam && orientado; j++)
                if(mat[i][j].equals("0") && !mat[j][i].equals("0") || !mat[i][j].equals("0") && mat[j][i].equals("0"))
                    orientado = false;
        }
        return orientado;
    }

    public static boolean isSimples(String[][] mat) {
        return true;
    }

    public static boolean isRegular(String[][] mat) {
        return true;
    }

    public static boolean isCompleto(String[][] mat) {
        return true;
    }

}
