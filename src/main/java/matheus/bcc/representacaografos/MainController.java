package matheus.bcc.representacaografos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class MainController {
    public Label tipo;
    @FXML
    private GridPane matrixGrid;
    private String[][] matriz;
    private int linhas, colunas;
    private char[] vertices;
    private String[] incidencias;

    private void exibirMatriz() {
        matrixGrid.getChildren().clear();

        for (int i = 0; i < linhas; i++) {
            Label headerLabel = new Label(String.valueOf(incidencias[i]));
            headerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            matrixGrid.add(criarPainel(headerLabel, "#77dd77"), i + 1, 0);
        }
        for (int i = 0; i < colunas; i++) {
            Label headerLabel = new Label(String.valueOf(vertices[i]));
            headerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            matrixGrid.add(criarPainel(headerLabel, "#77dd77"), 0, i + 1);
        }
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Label valueLabel = new Label(String.valueOf(matriz[i][j]));
                valueLabel.setStyle("-fx-font-weight: bold;");

                String bgColor = (!matriz[i][j].equals("0")) ? "#e69c9b" : "#f5e6e6";
                String textColor = (!matriz[i][j].equals("0")) ? "#666666" : "#cccccc";
                valueLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: " + textColor + ";");

                matrixGrid.add(criarPainel(valueLabel, bgColor), j + 1, i + 1);
            }
        }
    }

    private StackPane criarPainel(Label label, String bgColor) {
        StackPane pane = new StackPane(label);
        pane.setPrefSize(40, 40);
        pane.setStyle("-fx-background-color: " + bgColor + "; -fx-border-color: #cccccc; -fx-border-width: 0.5;");
        StackPane.setAlignment(label, Pos.CENTER);
        return pane;
    }

    public void onCarregarMA(ActionEvent actionEvent) {
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
                    vetor = linha.split(" ");
                    for (int j = 0; j < vetor.length; j++)
                        matriz[i][j] = String.valueOf(vetor[j]);
                    i++;
                }

                raf.close();
            } catch (Exception e) {
                tipo.setText("Arquivo inválido");
            }
            exibirMatriz();
        }
    }

    public void onCarregarMI(ActionEvent actionEvent) {
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
                while(raf.getFilePointer() < raf.length()) {
                    linha = raf.readLine();
                    String[] vetorLinhaMatriz = linha.split(" ");
                    for (int j = 0; j < vetorLinhaMatriz.length; j++)
                        matriz[i][j] = vetorLinhaMatriz[j];
                    i++;
                }

                raf.close();

            } catch (Exception e) {
                tipo.setText("Arquivo inválido");
                System.err.println(e.getMessage());
            }
            exibirMatriz();
        }
    }

    public void onCarregarLA(ActionEvent actionEvent) {
        File arq = Arquivo.carregarArquivo();
        if (arq != null) {
            tipo.setText("Lista de Adjacência");
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

                System.out.println(Arrays.toString(incidencias));

                linhas = colunas = vetor.length;
                matriz = new String[linhas][colunas];

                for (int i = 0; i < vetor.length; i++)
                    vertices[i] = vetor[i].charAt(0);

                int i = 0;
                while(raf.getFilePointer() < raf.length()) {
                    linha = raf.readLine();
                    vetor = linha.split(" ");
                    for (int j = 0; j < vetor.length; j++)
                        matriz[i][j] = String.valueOf(vetor[j].charAt(0));
                    i++;
                }

                raf.close();
            } catch (Exception e) {
                tipo.setText("Arquivo inválido");
            }
            exibirMatriz();
        }
    }
}