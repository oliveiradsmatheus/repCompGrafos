package matheus.bcc.representacaografos;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

public class MainController {
    public Label tipo;
    public Label simples;
    public Label regular;
    public Label orientado;
    public Label completo;
    public HBox hbEmissao;
    public Label emissao;
    public HBox hbRecepcao;
    public Label recepcao;
    public Label labelK;
    public VBox vbMatriz;
    public VBox matrizContainer;
    public VBox listaContainer;
    public VBox lista;
    @FXML
    private GridPane matrizGrid;
    private String[][] matriz;
    private int linhas, colunas;
    private char[] vertices;
    private String[] incidencias;

    private void exibirMatriz() {
        matrizGrid.getChildren().clear();

        for (int i = 0; i < incidencias.length; i++) {
            Label headerLabel = new Label(String.valueOf(incidencias[i]));
            headerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            matrizGrid.add(criarPainel(headerLabel, "#77dd77"), i + 1, 0);
        }

        for (int i = 0; i < vertices.length; i++) {
            Label headerLabel = new Label(String.valueOf(vertices[i]));
            headerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            matrizGrid.add(criarPainel(headerLabel, "#77dd77"), 0, i + 1);
        }

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Label valueLabel = new Label(String.valueOf(matriz[i][j]));
                valueLabel.setStyle("-fx-font-weight: bold;");

                String bgColor = (!matriz[i][j].equals("0")) ? "#e69c9b" : "#f5e6e6";
                String textColor = (!matriz[i][j].equals("0")) ? "#666666" : "#cccccc";
                valueLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: " + textColor + ";");

                matrizGrid.add(criarPainel(valueLabel, bgColor), j + 1, i + 1);
            }
        }
    }

    private void exibirLista() {
    }

    private StackPane criarPainel(Label label, String bgColor) {
        StackPane pane = new StackPane(label);
        pane.setPrefSize(40, 40);
        pane.setStyle("-fx-background-color: " + bgColor + "; -fx-border-color: #cccccc; -fx-border-width: 0.5;");
        StackPane.setAlignment(label, Pos.CENTER);
        return pane;
    }

    public void onCarregarMA() {
        listaContainer.setVisible(false);
        matrizContainer.setVisible(true);
        File arq = Arquivo.carregarArquivo();
        if (arq != null) {
            tipo.setText("Matriz de Adjacência");
            try {
                RandomAccessFile raf = new RandomAccessFile(arq.getAbsolutePath(), "r");

                String linha = raf.readLine();
                String[] vetor = linha.split(" ");

                vertices = new char[vetor.length];
                incidencias = new String[vertices.length];

                for (int i = 0; i < vetor.length; i++) {
                    vertices[i] = vetor[i].charAt(0);
                    incidencias[i] = String.valueOf(vertices[i]);
                }

                linhas = colunas = vetor.length;
                matriz = new String[linhas][colunas];

                for (int i = 0; i < vetor.length; i++)
                    vertices[i] = vetor[i].charAt(0);

                int i = 0;
                while(raf.getFilePointer() < raf.length()) {
                    linha = raf.readLine();
                    matriz[i] = linha.split(" ");
                    i++;
                }

                raf.close();
                exibirMatriz();

                boolean simp = MatAdjacencia.verificarSimples(matriz, colunas);

                //simples
                simples.setText(simp ? "Sim" : "Não");
                //orientado
                orientado.setText(MatAdjacencia.verificarOrientado(matriz, linhas, colunas) ? "Sim" : "Não");
                //regular
                int r = MatAdjacencia.verificarRegular(matriz, linhas);
                regular.setText(r == -1 ? "Não" : r + "-regular");
                hbRecepcao.setVisible(false);
                hbEmissao.setVisible(false);
                //completo
                if (simp && r != -1) {
                    int k = MatAdjacencia.verificarCompleto(matriz, linhas);
                    if (k != -1) {
                        completo.setVisible(true);
                        labelK.setVisible(true);
                        labelK.setText("K-" + linhas);
                    } else {
                        completo.setVisible(false);
                        labelK.setVisible(false);
                    }
                } else {
                    completo.setVisible(false);
                    labelK.setVisible(false);
                }

            } catch (Exception e) {
                matrizGrid.getChildren().clear();
                tipo.setText("Arquivo inválido");
            }
        }
    }

    public void onCarregarMI() {
        listaContainer.setVisible(false);
        matrizContainer.setVisible(true);
        File arq = Arquivo.carregarArquivo();
        if (arq != null) {
            tipo.setText("Matriz de Incidência");
            try {
                RandomAccessFile raf = new RandomAccessFile(arq.getAbsolutePath(), "r");

                String linha = raf.readLine();
                String[] vetorVertices = linha.split(" ");

                vertices = new char[vetorVertices.length];
                for (int i = 0; i < vetorVertices.length; i++)
                    vertices[i] = vetorVertices[i].charAt(0);
                linhas = vertices.length;

                linha = raf.readLine();
                String[] vetorIncidencias = linha.split(" ");

                incidencias = new String[vetorIncidencias.length];
                for (int i = 0; i < vetorIncidencias.length; i++)
                    incidencias[i] = vetorIncidencias[i];
                colunas = incidencias.length;

                matriz = new String[linhas][colunas];

                int i = 0;
                while (raf.getFilePointer() < raf.length()) {
                    linha = raf.readLine();
                    matriz[i++] = linha.split(" ");
                }

                raf.close();
                exibirMatriz();

                boolean simp = MatIncidencia.verificarSimples(matriz, linhas);
                simples.setText(simp ? "Sim" : "Não");
                if(MatIncidencia.verificarOrientado(matriz, linhas)) {
                    orientado.setText("Sim");
                    regular.setText("");
                    hbRecepcao.setVisible(true);
                    hbEmissao.setVisible(true);

                    int e = MatIncidencia.verificarEmissao(matriz, linhas);
                    int r = MatIncidencia.verificarRecepcao(matriz, linhas);

                    emissao.setText(e == -1 ? "Não" : e + "-regular");
                    recepcao.setText(r == -1 ? "Não" : r + "-regular");
                } else {
                    orientado.setText("Não");
                    hbRecepcao.setVisible(false);
                    hbEmissao.setVisible(false);
                    int n = MatIncidencia.verificarRegular(matriz, linhas);
                    if (n == -1)
                        regular.setText("Não");
                    else {
                        regular.setText(n + "-regular");
                        if (simp) {
                            int k = MatIncidencia.verificarCompleto(matriz, linhas);
                            if (k != -1) {
                                completo.setVisible(true);
                                labelK.setVisible(true);
                                labelK.setText("K-" + linhas);
                            } else {
                                completo.setVisible(false);
                                labelK.setVisible(false);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                matrizGrid.getChildren().clear();
                tipo.setText("Arquivo inválido");
            }
        }
    }

    public void onCarregarLA() {
        matrizContainer.setVisible(false);
        listaContainer.setVisible(true);

        // 2. Limpe a lista anterior (caso o botão seja clicado novamente)
        lista.getChildren().clear();

        // 3. Obtenha ou crie os dados da lista de adjacência
        //    (Exemplo com dados fixos)
        List<String> dadosDaLista = Arrays.asList(
                "Vértice 1 -> [2, 3]",
                "Vértice 2 -> [1, 4]",
                "Vértice 3 -> [1]",
                "Vértice 4 -> [2]"
        );

        // 4. Popule o VBox com as strings da lista
        for (String linha : dadosDaLista) {
            Label labelLinha = new Label(linha);
            labelLinha.setStyle("-fx-font-size: 16px; -fx-font-family: 'Consolas';"); // Estilo opcional
            lista.getChildren().add(labelLinha);
        }

        // Atualize o painel esquerdo se necessário
        orientado.setText("Sim");

        /*File arq = Arquivo.carregarArquivo();
        if (arq != null) {
            tipo.setText("Lista de Adjacência");
            try {
                RandomAccessFile raf = new RandomAccessFile(arq.getAbsolutePath(), "r");

                raf.close();
                exibirLista();
            } catch (Exception e) {
                matrizGrid.getChildren().clear();
                tipo.setText("Arquivo inválido");
            }
        }*/
    }
}