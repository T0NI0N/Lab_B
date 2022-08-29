module clientapp.clientcv {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.rmi;
    opens controllers to javafx.fxml;
    exports centrivaccinali;
    exports controllers;
}