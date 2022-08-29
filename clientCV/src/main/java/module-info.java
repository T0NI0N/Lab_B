module clientapp.clientcv {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.rmi;


    opens centrivaccinali to javafx.fxml;
    opens controllers;
    exports centrivaccinali;
    exports controllers;
}