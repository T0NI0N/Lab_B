package app.CentriVaccinali;

import app.Main;
import app.TipoCentroVaccinale;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.Arrays;

public class RegCentroVaccinaleController {

    @FXML
    private ChoiceBox<String> chb_tipoIndirizzo;
    @FXML
    private ChoiceBox<TipoCentroVaccinale> chb_tipoCentro;
    @FXML
    private void onEnterPressed() throws IOException {
        System.out.println("Enter button pressed");
        //TODO raccolta dati dai campi compilabili e formattazione centro vaccinale per pronta memorizzazione in db
    }
    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException{
        if(event.getCode() == KeyCode.ESCAPE)
            Main.setRoot("HomeOperatori");
    }
    public void initialize(){

        chb_tipoIndirizzo.setItems(FXCollections.observableList(Arrays.asList("Via","V.le","P.zza")));
        chb_tipoIndirizzo.setValue("Via");

        chb_tipoCentro.setItems(FXCollections.observableList(Arrays.asList(TipoCentroVaccinale.values())));
        chb_tipoCentro.setValue(TipoCentroVaccinale.OSPEDALIERO);
    }

}
