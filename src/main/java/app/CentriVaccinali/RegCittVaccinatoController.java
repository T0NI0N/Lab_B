package app.CentriVaccinali;

import app.TipoVaccino;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public class RegCittVaccinatoController {

    @FXML
    private ChoiceBox<TipoVaccino> chb_vaccino;
    @FXML
    private TextField tf_centroV;
    @FXML
    private TextField tf_nome;
    @FXML
    private TextField tf_cognome;
    @FXML
    private TextField tf_codiceFiscale;
    @FXML
    private DatePicker dp_data;

    @FXML
    private void onEnterPressed() throws IOException {

        System.out.println("Enter button pressed");
        System.out.println(tf_cognome.getText() + " " + tf_nome.getText() + " vaccinato presso il centro "
                + tf_centroV.getText() + " in data " + dp_data.getValue());
        System.out.println("CF: " + tf_codiceFiscale.getText());
        System.out.println("Vaccinato con una dose di " + chb_vaccino.getValue());

    }

    public void initialize() {
        chb_vaccino.setItems(FXCollections.observableList(Arrays.asList(TipoVaccino.values())));
        dp_data.setValue(LocalDate.now());
    }
}
