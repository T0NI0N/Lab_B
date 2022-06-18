package app.GUI;

import java.io.IOException;

import app.Main;
import javafx.fxml.FXML;

public class HomeCittadiniController {
    
    @FXML
    private void btnInfoPressed() throws IOException {
        System.out.println("Button info pressed");
        Main.switchScene("CercaInfoCentri");
    }

    @FXML
    private void btnRegisterPressed() throws IOException {
        System.out.println("Button register pressed");
        Main.switchScene("RegCittadini");
    }

    @FXML
    private void btnAddEventPressed() throws IOException {
        System.out.println("Button add event pressed");
        Main.switchScene("InsEventoCittadini");
    }

    @FXML
    private void btnHelpPressed() throws IOException {
        System.out.println("Button help pressed");
    }
}
