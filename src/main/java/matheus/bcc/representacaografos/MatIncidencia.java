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

    public static boolean verificarSimples(String[] inc) {
        boolean simples = true;

        for (int i = 0; i < inc.length && simples; i++)
            if (inc[i].charAt(0) == inc[i].charAt(2))
                simples = false;

        return simples;
    }

    public static boolean verificarRegular(String[][] mat, int linhas) {
        boolean regular = true;
        int ant = 0, atual = 0;

        for (int i = 0; i < linhas; i++)
            if(Integer.parseInt(mat[0][i]) != 0)
                ant++;
        for(int i = 1; i < linhas && regular; i++) {
            for (int j = 0; j < mat[0].length; j++)
                if(Integer.parseInt(mat[i][j]) != 0)
                    atual++;
            if (atual != ant)
                regular = false;
            ant = atual;
            atual = 0;
        }

        return regular;
    }

    private static boolean arestaExiste(char v1, char v2, String[] incidencias) {
        v1 = Character.toLowerCase(v1);
        v2 = Character.toLowerCase(v2);
        for (String aresta : incidencias) {
            String arestaLower = aresta.toLowerCase();
            String[] par = arestaLower.split(",");

            if (par.length == 2) {
                char p1 = par[0].charAt(0);
                char p2 = par[1].charAt(0);
                if ((p1 == v1 && p2 == v2) || (p1 == v2 && p2 == v1)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean verificarCompleto(char[] vertices, String[] incidencias) {
        int numVertices = vertices.length;

        if (numVertices <= 1) {
            return true;
        }
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                char vertice1 = vertices[i];
                char vertice2 = vertices[j];
                if (!arestaExiste(vertice1, vertice2, incidencias))
                    return false;
            }
        }
        return true;
    }
}
