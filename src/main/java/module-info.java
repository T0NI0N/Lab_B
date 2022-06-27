module demo {

    requires javafx.fxml;
    requires javafx.controls;
    requires java.rmi;
    requires java.sql;

    opens app.client.cittadini to javafx.fxml;
    opens app to javafx.graphics;

    exports app.server;
    exports app.client.cittadini;
    opens app.client.centrivaccinali to javafx.fxml, javafx.graphics;
    opens app.server;
}