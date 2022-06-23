module demo {

    requires javafx.fxml;
    requires javafx.controls;
    requires java.rmi;

    opens app.cittadini to javafx.fxml;
    opens app.CentriVaccinali to javafx.fxml;
    opens app to javafx.graphics;

    exports app.server;
    exports app.cittadini;
}