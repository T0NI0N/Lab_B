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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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

    public void setCittadino(Cittadino c){this.cittadino = c;}
    public void setCentroVaccinale(CentroVaccinale cv){this.centroVaccinale = cv;}

    private ClientConnectionHandler connectionHandler;

    /**
     * inizializza la connessione alla base di dati e dei campi della schermata
     *
     * @param location
     * @param resources
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

    @FXML
    private void btnSubmitPressed() throws IOException {

        //TODO testare l'inserimento

        eventType = eventTypeBox.getValue();
        nomeCentro  = centerTypeBox.getValue();
        sev = Integer.parseInt(tgRbSev.getSelectedToggle().getUserData().toString());
        notes = txtNotes.getText();

        System.out.println(cittadino.toString());
        System.out.println(nomeCentro.toString());
        System.out.println(new EventoAvverso(eventType, sev, notes));

		connectionHandler.insertAdverseEvent(cittadino.getUserid(), nomeCentro, new EventoAvverso(eventType, sev, notes));

        System.out.println(eventType + " | " + sev + " | " + notes);
    }

}
