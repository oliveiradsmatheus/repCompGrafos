package matheus.bcc.representacaografos;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Files;
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
    public VBox matrizContainer;
    public VBox listaContainer;
    public VBox lista;
    @FXML
    private GridPane matrizGrid;
    private String[][] matriz;
    private Lista[] listaVertices;
    private int linhas, colunas;
    private char[] vetVertices;
    private String[] vetIncidencias;

    private void exibirMatriz(String tipo) {
        matrizGrid.getChildren().clear();
        lista.getChildren().clear();
        listaContainer.setVisible(false);
        matrizContainer.setVisible(true);

        Label cabecalho = new Label(String.valueOf(tipo));
        cabecalho.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        matrizGrid.add(criarPainel(cabecalho, "#17c3b2"), 0, 0);

        for (int i = 0; i < vetIncidencias.length; i++) {
            cabecalho = new Label(String.valueOf(vetIncidencias[i]));
            cabecalho.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            matrizGrid.add(criarPainel(cabecalho, "#17c3b2"), i + 1, 0);
        }

        for (int i = 0; i < vetVertices.length; i++) {
            cabecalho = new Label(String.valueOf(vetVertices[i]));
            cabecalho.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            matrizGrid.add(criarPainel(cabecalho, "#17c3b2"), 0, i + 1);
        }

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Label valueLabel = new Label(String.valueOf(matriz[i][j]));
                valueLabel.setStyle("-fx-font-weight: bold;");

                String bgColor = (!matriz[i][j].equals("0")) ? "#ffb5a7" : "#f5e6e6";
                String textColor = (!matriz[i][j].equals("0")) ? "#666666" : "#cccccc";
                valueLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: " + textColor + ";");

                matrizGrid.add(criarPainel(valueLabel, bgColor), j + 1, i + 1);
            }
        }
    }

    private void exibirLista() {
        matrizGrid.getChildren().clear();
        lista.getChildren().clear();
        listaContainer.setVisible(true);
        matrizContainer.setVisible(false);
        listaContainer.setMaxWidth(Region.USE_PREF_SIZE);

        for (Lista v : listaVertices) {
            HBox hb = new HBox();
            hb.setAlignment(Pos.CENTER_LEFT);
            hb.setSpacing(10);

            Button b = new Button();
            b.setPrefSize(75, 25);
            b.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 15px;-fx-background-color: #17c3b2;");
            b.setText("" + v.getInicio().getInfo());
            hb.getChildren().add(b);

            No aux = v.getInicio().getProx();
            while(aux != null) {
                b = new Button();
                b.setPrefSize(40, 25);
                b.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;-fx-background-color: #fef9ef;");
                b.setText("→");
                hb.getChildren().add(b);

                b = new Button();
                b.setPrefSize(75, 25);
                b.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;-fx-background-color: #ffb5a7;");
                b.setText(aux.getInfo() + " - " + aux.getPeso());
                hb.getChildren().add(b);
                aux = aux.getProx();
            }
            lista.getChildren().add(hb);
        }
    }

    private StackPane criarPainel(Label label, String bgColor) {
        StackPane pane = new StackPane(label);
        pane.setPrefSize(40, 40);
        pane.setStyle("-fx-background-color: " + bgColor + "; -fx-border-color: #cccccc; -fx-border-width: 0.5;");
        StackPane.setAlignment(label, Pos.CENTER);
        return pane;
    }

    public void onCarregarMA() {
        File arq = Arquivo.carregarArquivo();
        if (arq != null) {
            tipo.setText("Matriz de Adjacência");
            try {
                RandomAccessFile raf = new RandomAccessFile(arq.getAbsolutePath(), "r");

                String linha = raf.readLine();
                String[] vetor = linha.split(" ");

                vetVertices = new char[vetor.length];
                vetIncidencias = new String[vetVertices.length];

                for (int i = 0; i < vetor.length; i++) {
                    vetVertices[i] = vetor[i].charAt(0);
                    vetIncidencias[i] = String.valueOf(vetVertices[i]);
                }

                linhas = colunas = vetor.length;
                matriz = new String[linhas][colunas];

                for (int i = 0; i < vetor.length; i++)
                    vetVertices[i] = vetor[i].charAt(0);

                int i = 0;
                while (raf.getFilePointer() < raf.length()) {
                    linha = raf.readLine();
                    matriz[i] = linha.split(" ");
                    i++;
                }

                raf.close();
                exibirMatriz("MA");

                boolean simp = MatAdjacencia.verificarSimples(matriz, colunas);

                simples.setText(simp ? "Sim" : "Não");
                orientado.setText(MatAdjacencia.verificarOrientado(matriz, linhas, colunas) ? "Sim" : "Não");
                int r = MatAdjacencia.verificarRegular(matriz, linhas);
                regular.setText(r == -1 ? "Não" : r + "-regular");

                hbRecepcao.setVisible(false);
                hbEmissao.setVisible(false);
                completo.setVisible(false);
                labelK.setVisible(false);

                if (simp && r != -1) {
                    int k = MatAdjacencia.verificarCompleto(matriz, linhas);
                    if (k != -1) {
                        completo.setVisible(true);
                        labelK.setVisible(true);
                        labelK.setText("K-" + linhas);
                    }
                }

            } catch (Exception e) {
                matrizGrid.getChildren().clear();
                lista.getChildren().clear();
                tipo.setText("Arquivo inválido");
            }
        }
    }

    public void onCarregarMI() {
        File arq = Arquivo.carregarArquivo();
        if (arq != null) {
            tipo.setText("Matriz de Incidência");
            try {
                RandomAccessFile raf = new RandomAccessFile(arq.getAbsolutePath(), "r");

                String linha = raf.readLine();
                String[] vetorVertices = linha.split(" ");

                vetVertices = new char[vetorVertices.length];
                for (int i = 0; i < vetorVertices.length; i++)
                    vetVertices[i] = vetorVertices[i].charAt(0);
                linhas = vetVertices.length;

                linha = raf.readLine();
                String[] vetorIncidencias = linha.split(" ");

                vetIncidencias = new String[vetorIncidencias.length];
                for (int i = 0; i < vetorIncidencias.length; i++)
                    vetIncidencias[i] = vetorIncidencias[i].replace(",", "");
                colunas = vetIncidencias.length;

                matriz = new String[linhas][colunas];

                int i = 0;
                while (raf.getFilePointer() < raf.length()) {
                    linha = raf.readLine();
                    matriz[i++] = linha.split(" ");
                }

                raf.close();
                exibirMatriz("MI");

                boolean simp = MatIncidencia.verificarSimples(matriz, linhas);

                simples.setText(simp ? "Sim" : "Não");
                completo.setVisible(false);
                labelK.setVisible(false);

                if (MatIncidencia.verificarOrientado(matriz, linhas)) {
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
                            }
                        }
                    }
                }

            } catch (Exception e) {
                matrizGrid.getChildren().clear();
                lista.getChildren().clear();
                tipo.setText("Arquivo inválido");
            }
        }
    }

    public void onCarregarLA() {
        File arq = Arquivo.carregarArquivo();
        if (arq != null) {
            try {
                List<String> linhas = Files.readAllLines(arq.toPath());
                listaVertices = new Lista[linhas.size()];

                for (int i = 0; i < linhas.size(); i++) {
                    char prox;
                    StringBuilder peso = new StringBuilder();
                    listaVertices[i] = new Lista(linhas.get(i).charAt(0));
                    for(int j = 2; j < linhas.get(i).length(); j++) {
                        prox = linhas.get(i).charAt(j);
                        j+=2;
                        while(j < linhas.get(i).length() && linhas.get(i).charAt(j) != ' ')
                            peso.append(linhas.get(i).charAt(j++));
                        listaVertices[i].inserir(prox, peso.toString());
                        peso = new StringBuilder();
                    }
                }
                exibirLista();

                boolean simp = ListaAdjacencia.verificarSimples(listaVertices);

                simples.setText(simp ? "Sim" : "Não");
                completo.setVisible(false);
                labelK.setVisible(false);

                if (ListaAdjacencia.verificarOrientado(listaVertices)) {
                    orientado.setText("Sim");
                    regular.setText("");
                    hbRecepcao.setVisible(true);
                    hbEmissao.setVisible(true);

                    int e = ListaAdjacencia.verificarRegular(listaVertices);
                    int r = ListaAdjacencia.verificarRecepcao(listaVertices);

                    emissao.setText(e == -1 ? "Não" : e + "-regular");
                    recepcao.setText(r == -1 ? "Não" : r + "-regular");
                } else {
                    orientado.setText("Não");
                    hbRecepcao.setVisible(false);
                    hbEmissao.setVisible(false);
                    int n = ListaAdjacencia.verificarRegular(listaVertices);
                    if (n == -1)
                        regular.setText("Não");
                    else {
                        regular.setText(n + "-regular");
                        if (simp) {
                            int k = ListaAdjacencia.verificarCompleto(listaVertices);
                            if (k != -1) {
                                completo.setVisible(true);
                                labelK.setVisible(true);
                                labelK.setText("K-" + listaVertices.length);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                matrizGrid.getChildren().clear();
                lista.getChildren().clear();
                tipo.setText("Arquivo inválido");
            }
        }
    }
}