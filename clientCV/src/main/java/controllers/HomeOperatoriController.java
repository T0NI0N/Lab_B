// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package controllers;

import java.io.IOException;
import centrivaccinali.CentriVaccinali;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HomeOperatoriController {

    @FXML
    private void btnRgstCV() throws IOException {
        System.out.println("Button register centro vaccinale pressed");
        CentriVaccinali.switchScene("RegCentroVaccinale");
    }

    @FXML
    private void btnRgstVacc() throws IOException {
        System.out.println("Button register cittadino vaccinato pressed");
        CentriVaccinali.switchScene("RegCittVaccinato");
    }

    @FXML
    private void btnHelpPressed() throws IOException {

        Alert a = new Alert(AlertType.NONE, "", ButtonType.OK);

        String helpString = "Elenco delle funzioni: " +
                "\n - Registrare a sistema un nuovo centro vaccinale;"
                +
                "\n - Registrare a sistema un cittadino vaccinato";

        a.setTitle("Informazioni");
        a.setHeaderText("Informazioni");
        a.setContentText(helpString);

        a.show();
    }

    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE)
            CentriVaccinali.switchScene("Home");
    }

}