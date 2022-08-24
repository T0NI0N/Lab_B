// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package controllers;

import centrivaccinali.CentriVaccinali;
import centrivaccinali.CentroVaccinale;
import centrivaccinali.ClientConnectionHandler;
import centrivaccinali.Indirizzo;
import cittadini.Cittadino;
import enums.TipoCentroVaccinale;
import enums.TipoVaccino;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

        // TODO testare gli inserimenti

        System.out.println("Enter button pressed");
        System.out.println(tf_cognome.getText() + " " + tf_nome.getText() + " vaccinato presso il centro "
                + tf_centroV.getText() + " in data " + dp_data.getValue());
        System.out.println("CF: " + tf_codiceFiscale.getText());
        System.out.println("Vaccinato con una dose di " + chb_vaccino.getValue());

        String result = connectionHandler.registerVaccination
        (
            new Cittadino(tf_nome.getText(), tf_cognome.getText(), tf_codiceFiscale.getText(), "", "", "", 0, dp_data.getValue().getDayOfMonth()+"/"+dp_data.getValue().getMonth()+"/"+dp_data.getValue().getYear(), chb_vaccino.getValue()),
            tf_centroV.getText()
        );
        switch (result){
            case "ok" -> {
                System.out.println("Success: Vaccinazione registrata");
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Registrazione avvenuta");
                a.setHeaderText("Vaccinazione cittadino avvenuta con successo");
                a.setContentText("Il centro è stato registrato con successo");
                a.showAndWait();
            }
            default -> {
                System.out.println("Success: Centro registrato");
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Registrazione fallita");
                a.setHeaderText("Vaccinazione cittadino non avvenuta");
                a.setContentText(result);
                a.showAndWait();
            }
        }

    }

    /**
     * Gestisce la pressione del tasto escape (esc) da tastiera
     * ritornando alla schermata precedente
     *
     * @param event evento che indica la pressione di un tasto
     * @throws IOException
     */
    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE)
            CentriVaccinali.switchScene("HomeOperatori");
    }

    /**
     * inizializza la connessione alla base di dati e dei campi della schermata
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();

        chb_vaccino.setItems(FXCollections.observableList(Arrays.asList(TipoVaccino.values())));
        dp_data.setValue(LocalDate.now());

    }
}
