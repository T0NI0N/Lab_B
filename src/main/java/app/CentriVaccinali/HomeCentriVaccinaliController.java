package app.CentriVaccinali;

import java.io.IOException;

import app.Main;
import javafx.fxml.FXML;

public class HomeCentriVaccinaliController{

    @FXML
    private void btnRgstCV() throws IOException{
        System.out.println("Button register centro vaccinale pressed");
        //Main.setRoot(fxml:"RegistraCentroVaccinale");
    }

    @FXML
    private void btnRgstVacc() throws IOException{
        System.out.println("Button register cittadino vaccinato pressed");
        //Main.setRoot(fxml:"RegistraCittadinoVaccinato");
    }

}