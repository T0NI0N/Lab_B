package app.CentriVaccinali;

import java.io.IOException;

import app.Main;
import javafx.fxml.FXML;

public class HomeOperatoriController {

    @FXML
    private void btnRgstCV() throws IOException{
        System.out.println("Button register centro vaccinale pressed");
        Main.setRoot("RegCentroVaccinale");
    }

    @FXML
    private void btnRgstVacc() throws IOException{
        System.out.println("Button register cittadino vaccinato pressed");
        Main.setRoot("RegCittVaccinato");
    }

}