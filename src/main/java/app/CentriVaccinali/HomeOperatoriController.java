package app.CentriVaccinali;

import java.io.IOException;

import app.Main;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HomeOperatoriController {

    @FXML
    private void btnRgstCV() throws IOException{
        System.out.println("Button register centro vaccinale pressed");
        Main.switchScene("RegCentroVaccinale");
    }

    @FXML
    private void btnRgstVacc() throws IOException{
        System.out.println("Button register cittadino vaccinato pressed");
        Main.switchScene("RegCittVaccinato");
    }

    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException{
        if(event.getCode() == KeyCode.ESCAPE)
            Main.switchScene("Home");
    }

}