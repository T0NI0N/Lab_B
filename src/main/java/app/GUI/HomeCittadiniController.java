package app.GUI;

import java.io.IOException;

import app.Main;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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

    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException{
        if(event.getCode() == KeyCode.ESCAPE)
            Main.switchScene("Home");
    }
}
