module demo {

    requires javafx.fxml;
    requires javafx.controls;

    opens app.cittadini to javafx.fxml;
    opens app.CentriVaccinali to javafx.fxml;
    opens app to javafx.graphics;
}