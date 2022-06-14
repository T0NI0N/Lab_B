package app.GUI;

import java.io.IOException;

import app.Main;
import javafx.fxml.FXML;

public class HomeController {
    
    @FXML
    private void btnOperatorsPressed() throws IOException {
        System.out.println("Entering operators area");
        // Main.setRoot("HomeOperatori");
    }

    @FXML
    private void btnCiviliansPressed() throws IOException {
        System.out.println("Entering civilians area");
        Main.setRoot("HomeCittadini");
    }

    @FXML
    private void btnHelpPressed() throws IOException {
        System.out.println("Showing help box");
    }
}
