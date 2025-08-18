module matheus.bcc.representacaografos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;


    opens matheus.bcc.representacaografos to javafx.fxml;
    exports matheus.bcc.representacaografos;
}