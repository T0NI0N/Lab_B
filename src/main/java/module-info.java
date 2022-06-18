module demo {

    requires javafx.fxml;
    requires javafx.controls;

    opens app.GUI to javafx.fxml;
    opens app to javafx.graphics;
}