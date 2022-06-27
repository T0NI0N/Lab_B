module demo {

    requires javafx.fxml;
    requires javafx.controls;
    requires java.rmi;
    requires java.sql;

    opens app.cittadini to javafx.fxml;
    opens app to javafx.graphics;

    exports app.server;
    exports app.cittadini;
    opens app.centrivaccinali to javafx.fxml, javafx.graphics;
    opens app.server;
}