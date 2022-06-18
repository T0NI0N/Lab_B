package app.CentriVaccinali;

import app.Main;
import app.TipoCentroVaccinale;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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
    private TextField tf_nomeCentro;
    @FXML
    private TextField tf_via;
    @FXML
    private TextField tf_numCivico;
    @FXML
    private TextField tf_prov;
    @FXML
    private TextField tf_cap;
    @FXML
    private TextField tf_comune;

    @FXML
    private void onEnterPressed() throws IOException {

        System.out.println("Enter button pressed");
        System.out.println(tf_nomeCentro.getText());
        System.out.println(chb_tipoIndirizzo.getValue() + " " + tf_via.getText() + " " + tf_numCivico.getText());
        System.out.println(tf_comune.getText() + " (" + tf_prov.getText() + ")" + " " + tf_cap.getText());
        System.out.println(chb_tipoCentro.getValue());

        //TODO formattazione centro vaccinale per pronta memorizzazione in db
    }

    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE)
            Main.setRoot("HomeOperatori");
    }

    public void initialize() {

        chb_tipoIndirizzo.setItems(FXCollections.observableList(Arrays.asList("Via", "V.le", "P.zza")));
        chb_tipoIndirizzo.setValue("Via");

        chb_tipoCentro.setItems(FXCollections.observableList(Arrays.asList(TipoCentroVaccinale.values())));
        chb_tipoCentro.setValue(TipoCentroVaccinale.OSPEDALIERO);
    }

}
