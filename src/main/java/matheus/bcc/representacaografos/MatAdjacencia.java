package matheus.bcc.representacaografos;

public class MatAdjacencia {
    /*public static boolean isOrientado(String[][] mat) {
        boolean orientado = true;
        int tam = mat[0].length;

        for(int i = 1; i < tam && orientado; i++){
            for(int j = i; j < tam && orientado; j++)
                if(mat[i][j].equals("0") && !mat[j][i].equals("0") || !mat[i][j].equals("0") && mat[j][i].equals("0"))
                    orientado = false;
        }
        return orientado;
    }*/
    public static boolean isOrientado(String[][] matriz, int linhas, int colunas) {
        boolean orientado = false;

        for (int l = 0; l < linhas && !orientado; l++) {
            for (int c = 0; c < colunas && !orientado; c++) {
                //verifica se é digrafo
                if (!matriz[l][c].equals(matriz[c][l]))
                    orientado = true;
            }
        }
        return orientado;
    }

    public static boolean isSimples(String[][] matriz, int colunas) {
        int valor;
        boolean simples = true;
        // Verifica se há laços
        for (int i = 0; i < colunas && simples; i++) {
            for (int j = 0; j < colunas && simples; j++) {
                valor = Integer.parseInt(matriz[i][j]);

                if (i == j) {
                    // Diagonal principal laços deve ser 0
                    if (valor != 0) {
                        simples = false; // Não é simples, tem laço
                    }
                }
            }
        }
        return simples;
    }

    //verifica regular
    public static boolean isGrafoRegular(String[][] matriz, int linhas, int colunas) {
        //se todos os seus vértices têm o mesmo grau
        int grauAtual, grauRef;
        boolean gRegular = true;
        if (linhas == 0) {
            return gRegular; // grafo vazio considerado regular.
        } else {

            // calcula grau de referência
            grauRef = 0;
            for (int j = 0; j < colunas; j++) {
                grauRef += Integer.parseInt(matriz[0][j]);
            }

            // Verifica se os outros vértices têm o mesmo grau
            for (int i = 1; i < colunas && gRegular; i++) {
                grauAtual = 0;
                for (int j = 0; j < colunas; j++) {
                    grauAtual += Integer.parseInt(matriz[i][j]);
                }
                if (grauAtual != grauRef) {
                    gRegular = false; // Encontrou um vértice com grau diferente.
                }
            }
            return gRegular;
        }
    }

    public static boolean isDigrafoRegular(String[][] matriz, int linhas, int colunas) {
        //se todos os vertices tem o mesmo grau de entrada E o mesmo grau de saida
        boolean dRegular = true;
        int grauSaidaAtual, grauEntradaAtual;
        int grauSaidaRef, grauEntradaRef;
        int i;

        if (linhas == 0) {
            return dRegular; // Dígrafo vazio pode ser considerado regular.
        } else {
            // Calcula os graus de entrada e saída do primeiro vértice (referência)
            grauSaidaRef = 0;
            grauEntradaRef = 0;
            for (i = 0; i < colunas; i++) {
                // CONVERSÃO DE STRING PARA INT AQUI
                grauSaidaRef += Integer.parseInt(matriz[0][i]);
                grauEntradaRef += Integer.parseInt(matriz[i][0]);
            }

            // Compara com os graus dos outros vértices
            for (i = 1; i < colunas && dRegular; i++) {
                grauSaidaAtual = 0;
                grauEntradaAtual = 0;
                for (int j = 0; j < colunas; j++) {
                    // CONVERSÃO DE STRING PARA INT AQUI
                    grauSaidaAtual += Integer.parseInt(matriz[i][j]);
                    grauEntradaAtual += Integer.parseInt(matriz[j][i]);

                }
                // Se o grau de saída ou o de entrada for diferente da referência, não é regular
                if (grauSaidaAtual != grauSaidaRef || grauEntradaAtual != grauEntradaRef) {
                    dRegular = false;
                }
            }
            return dRegular;
        }

    }


    //verifica completo
    public static boolean isGrafoCompleto(String[][] matriz, int linhas, int colunas) {
        //para grafo ser completo tem que ser simples e regular
        if (isSimples(matriz, colunas)) {
            if(isGrafoRegular(matriz,linhas,colunas))
                return true;
            return false;
        }
        return false;
    }

    public static boolean isDigrafoCompleto(String[][] matriz, int colunas) {
        boolean dCompleto = true;
        int valor_ij, valor_ji;
        if (colunas == 0) {
            return dCompleto; // Um dígrafo vazio considerado completo.
        } else {
            //Verifica se o dígrafo é simples.
            if (!isSimples(matriz, colunas)) {
                dCompleto = false;
            } else {
                // Verifica se há uma aresta em uma direção única para cada par de vértices distintos.
                for (int i = 0; i < colunas; i++) {
                    for (int j = i + 1; j < colunas; j++) {
                        // A soma dos valores dos pares (i,j) e (j,i) deve ser 1.
                        valor_ij = Integer.parseInt(matriz[i][j]);
                        valor_ji = Integer.parseInt(matriz[j][i]);

                        // Se a soma não for 1, significa que falta uma aresta ou tem arestas paralelas.
                        if (valor_ij + valor_ji != 1) {
                            dCompleto = false;
                        }
                    }
                }
            }

            // Se o dígrafo é simples e tem arestas únicas entre todos os pares, ele é completo.
            return dCompleto;
        }
    }

}
