// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import centrivaccinali.CentriVaccinali;
import centrivaccinali.CentroVaccinale;
import centrivaccinali.ClientConnectionHandler;
import centrivaccinali.EventoAvverso;
import centrivaccinali.Cittadino;
import enums.TipoEventoAvverso;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

/**
 * <p>Classe InsEventoCittadiniController.</p>
 */
public class InsEventoCittadiniController implements Initializable {

    @FXML
    private RadioButton rbSevValue1;
    @FXML
    private RadioButton rbSevValue2;
    @FXML
    private RadioButton rbSevValue3;
    @FXML
    private RadioButton rbSevValue4;
    @FXML
    private RadioButton rbSevValue5;
    @FXML
    private ToggleGroup tgRbSev;

    @FXML
    private ChoiceBox<TipoEventoAvverso> eventTypeBox;
    @FXML
    private ChoiceBox<String> centerTypeBox;

    @FXML
    private TextArea txtNotes;

    private TipoEventoAvverso eventType;
    private String nomeCentro;
    private int sev;
    private String notes;
    private Cittadino cittadino;
    private CentroVaccinale centroVaccinale;

    /**
     * <p>Setter for the field <code>cittadino</code>.</p>
     *
     * @param c a {@link centrivaccinali.Cittadino} object
     */
    public void setCittadino(Cittadino c){this.cittadino = c;}
    /**
     * <p>Setter for the field <code>centroVaccinale</code>.</p>
     *
     * @param cv a {@link centrivaccinali.CentroVaccinale} object
     */
    public void setCentroVaccinale(CentroVaccinale cv){this.centroVaccinale = cv;}

    private ClientConnectionHandler connectionHandler;

    /**
     * {@inheritDoc}
     *
     * inizializza la connessione alla base di dati e dei campi della schermata
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();

        centerTypeBox.setValue(centroVaccinale.getNomeCentro());
        centerTypeBox.setDisable(true);

        eventTypeBox.setItems(FXCollections.observableList(Arrays.asList(TipoEventoAvverso.values())));
        eventTypeBox.setValue(TipoEventoAvverso.Emicrania);
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
            CentriVaccinali.switchScene("HomeCittadini", null);
    }

    /**
     * Aggiunge la segnalazione dell'evento avverso alla base di dati
     * @throws IOException
     */
    @FXML
    private void btnSubmitPressed() throws IOException {

        if(tgRbSev.getSelectedToggle() == null){
            showErrorBox("Inserire la severità dell'evento");
            return;
        }

        eventType = eventTypeBox.getValue();
        nomeCentro  = centerTypeBox.getValue();
        sev = Integer.parseInt(tgRbSev.getSelectedToggle().getUserData().toString());
        notes = txtNotes.getText();

        System.out.println(cittadino.toString());
        System.out.println(nomeCentro);
        System.out.println(eventType + " | " + sev + " | " + notes);


		String result = connectionHandler.insertAdverseEvent(cittadino.getUserid(), nomeCentro, new EventoAvverso(eventType, sev, notes));

        switch (result){
            case "ok" -> {
                System.out.println("Success: registrazione evento ok");
                showInfoBox("Segnalazione evento avvenuta con successo");
                break;
            }
            default -> {
                System.out.println(result);
                showErrorBox("Errore durante la registrazione");
            }
        }

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
