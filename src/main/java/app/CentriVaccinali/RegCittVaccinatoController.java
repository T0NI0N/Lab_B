package app.CentriVaccinali;

import app.ClientConnectionHandler;
import app.TipoCentroVaccinale;
import app.TipoVaccino;
import app.cittadini.Cittadino;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

public class RegCittVaccinatoController implements Initializable {

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

    private ClientConnectionHandler connectionHandler;

    @FXML
    private void onEnterPressed() throws IOException {

        System.out.println("Enter button pressed");
        System.out.println(tf_cognome.getText() + " " + tf_nome.getText() + " vaccinato presso il centro "
                + tf_centroV.getText() + " in data " + dp_data.getValue());
        System.out.println("CF: " + tf_codiceFiscale.getText());
        System.out.println("Vaccinato con una dose di " + chb_vaccino.getValue());

        connectionHandler.registerVaccination
        (
            new Cittadino(tf_nome.getText(), tf_cognome.getText(), tf_codiceFiscale.getText(), "", "", "", 0, dp_data.getValue().getDayOfMonth()+"/"+dp_data.getValue().getMonth()+"/"+dp_data.getValue().getYear(), chb_vaccino.getValue()), 
            new CentroVaccinale(tf_centroV.getText(), new Indirizzo("", "", "", "", "", 0), TipoCentroVaccinale.OSPEDALIERO)
        );
        // TODO inserire perlomeno userid

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();

        chb_vaccino.setItems(FXCollections.observableList(Arrays.asList(TipoVaccino.values())));
        dp_data.setValue(LocalDate.now());

    }
}
