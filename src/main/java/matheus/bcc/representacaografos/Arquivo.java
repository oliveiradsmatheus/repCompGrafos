package matheus.bcc.representacaografos;

import javafx.stage.FileChooser;

import java.io.File;

public class Arquivo {
    public static File carregarArquivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("..\\"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo texto", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        return fileChooser.showOpenDialog(null);
    }
}
