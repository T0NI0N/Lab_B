// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package controllers;

import centrivaccinali.CentriVaccinali;
import centrivaccinali.ClientConnectionHandler;
import centrivaccinali.Cittadino;
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
import javafx.scene.layout.Region;
import utils.ValidateData;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * <p>Classe RegCittVaccinatoController.</p>
 */
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

        String codf = tf_codiceFiscale.getText();

        if(tf_centroV.equals("") || tf_nome.equals("") || tf_cognome.equals("") || tf_codiceFiscale.equals("")){
            showErrorBox("Dati necessari non inseriti");
            return;
        }

        if(!ValidateData.validateCodf(codf)){
            showErrorBox("Codice fiscale non valido");
            return;
        }

        System.out.println(tf_cognome.getText() + " " + tf_nome.getText() + " vaccinato presso il centro "
                + tf_centroV.getText() + " in data " + dp_data.getValue());
        System.out.println("CF: " + tf_codiceFiscale.getText());
        System.out.println("Vaccinato con una dose di " + chb_vaccino.getValue());

        String result = connectionHandler.registerVaccination
        (
            new Cittadino(tf_nome.getText(), tf_cognome.getText(), codf, "", "", "", 0, dp_data.getValue().getDayOfMonth()+"/"+dp_data.getValue().getMonth()+"/"+dp_data.getValue().getYear(), chb_vaccino.getValue()),
            tf_centroV.getText()
        );

        try{
            int id_parsed = Integer.parseInt(result);
            System.out.println("Success: Vaccinazione registrata");
            showInfoBox("Vaccinazione cittadino registrata con successo\nID Vaccinazione: "+result);
        }catch (NumberFormatException e){
            System.out.println(result);
            showErrorBox("Errore durante la registrazione della vaccinazione");
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
            CentriVaccinali.switchScene("HomeOperatori", null);
    }

    /**
     * {@inheritDoc}
     *
     * inizializza la connessione alla base di dati e dei campi della schermata
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();

        chb_vaccino.setItems(FXCollections.observableList(Arrays.asList(TipoVaccino.values())));
        chb_vaccino.setValue(TipoVaccino.MODERNA);
        dp_data.setValue(LocalDate.now());

    }

    /**
     * Mostra un messaggio di errore
     *
     * @param error i problemi riscontrati da visualizzare
     */
    @FXML
    private void showErrorBox(String error){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Errore");
        a.setHeaderText("");
        a.setContentText(error);
        a.setResizable(true);
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        a.showAndWait();
    }

    /**
     * Mostra un messaggio informativo
     *
     * @param info le informazioni da visualizzare
     */
    private void showInfoBox(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registrazione avvenuta");
        alert.setHeaderText("");
        alert.setContentText(info);
        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
