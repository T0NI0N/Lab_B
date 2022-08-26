// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package controllers;

import centrivaccinali.CentriVaccinali;
import centrivaccinali.CentroVaccinale;
import centrivaccinali.ClientConnectionHandler;
import centrivaccinali.Indirizzo;
import enums.TipoCentroVaccinale;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class RegCentroVaccinaleController implements Initializable {

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

    private ClientConnectionHandler connectionHandler;

    @FXML
    private void onEnterPressed() throws IOException {

        System.out.println("Enter button pressed");

        try {
            String result = connectionHandler.registerCenter(new CentroVaccinale(tf_nomeCentro.getText(),
                    new Indirizzo(chb_tipoIndirizzo.getValue(), tf_via.getText(), tf_numCivico.getText(),
                            tf_comune.getText(), tf_prov.getText(), Integer.parseInt(tf_cap.getText())),
                    chb_tipoCentro.getValue()));
            //Gestione del risultato ottenuto dal metodo di registrazione del server
            switch (result) {
                case "ok":
                    System.out.println("Success: Centro registrato");
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Registrazione avvenuta");
                    a.setHeaderText("Centro Registrato");
                    a.setContentText("Il centro è stato registrato con successo");
                    a.showAndWait();
                    break;
                case "already_in":
                    System.out.println("Failure: Centro già presente");
                    Alert b = new Alert(Alert.AlertType.ERROR);
                    b.setTitle("Registrazione fallita");
                    b.setHeaderText("Centro vaccinale non registrato");
                    b.setContentText("Il centro è già presente nel database");
                    b.showAndWait();
                    break;
                default:
                    System.out.println("Failure: " + result);
                    Alert c = new Alert(Alert.AlertType.ERROR);
                    c.setTitle("Registrazione fallita");
                    c.setHeaderText("Un errore è avvenuto durante la registrazione");
                    c.setContentText(result);
                    c.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
     * inizializza la connessione alla base di dati e dei campi della schermata
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();

        chb_tipoIndirizzo.setItems(FXCollections.observableList(Arrays.asList("Via", "V.le", "P.zza")));
        chb_tipoIndirizzo.setValue("Via");

        chb_tipoCentro.setItems(FXCollections.observableList(Arrays.asList(TipoCentroVaccinale.values())));
        chb_tipoCentro.setValue(TipoCentroVaccinale.OSPEDALIERO);

    }

}

