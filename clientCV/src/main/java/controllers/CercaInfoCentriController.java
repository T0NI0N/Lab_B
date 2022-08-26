// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import centrivaccinali.CentriVaccinali;
import centrivaccinali.CentroVaccinale;
import centrivaccinali.ClientConnectionHandler;
import centrivaccinali.EventoAvverso;
import enums.TipoCentroVaccinale;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.fxml.Initializable;

public class CercaInfoCentriController implements Initializable {

    @FXML
    private RadioButton rbName;
    @FXML
    private RadioButton rbComType;
    @FXML
    private ToggleGroup tgSearchType;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCom;

    @FXML
    private ChoiceBox<TipoCentroVaccinale> centreTypeBox;

    @FXML
    // ci sarà da modificare il tipo <T> in seguito in base al codice
    private ListView<String> lvResults;
    @FXML
    // ci sarà da modificare il tipo <T> in seguito in base al codice
    private ListView<String> lvInfo;

    private String centreName;
    private String com;
    private TipoCentroVaccinale centreType;

    private ClientConnectionHandler connectionHandler;
    private boolean nameSearch;
    private ArrayList<CentroVaccinale> result;
    private ArrayList<EventoAvverso> eventList;

    /**
     * inizializza la connessione alla base di dati e dei campi della schermata
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();

        centreTypeBox.setItems(FXCollections.observableList(Arrays.asList(TipoCentroVaccinale.values())));
        centreTypeBox.setValue(TipoCentroVaccinale.OSPEDALIERO);

        txtName.setDisable(true);
        txtCom.setDisable(true);
        centreTypeBox.setDisable(true);
    }

    /**
     * gestisce il click che riguarda la ricerca del centro vaccinale per nome
     * disabilitando i campi dell'altro tipo di ricerca
     * e imposta il tipo di ricerca del centro a ricerca per nome
     */
    @FXML
    private void onNameSearchPressed() {
        txtName.setDisable(false);
        txtCom.setDisable(true);
        centreTypeBox.setDisable(true);

        clearData();

        nameSearch = true;
    }

    /**
     * gestisce il click che riguarda la ricerca del centro vaccinale per comune e tipo
     * disabilitando i campi dell'altro tipo di ricerca
     * e imposta il tipo di ricerca del centro a ricerca per comune e tipo
     */
    @FXML
    private void onCentreTypeSearchPressed() {
        txtCom.setDisable(false);
        centreTypeBox.setDisable(false);
        txtName.setDisable(true);

        clearData();

        nameSearch = false;
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
     * Ricerca e mostra all'utente la lista dei centri vaccinali,
     * in base alle preferenze di ricerca (nome oppure comune e tipo)
     * oppure mostra gli errori riscontrati durante la procedura
     */
    @FXML
    private void search() {

        // TODO testare la funzione di ricerca

        centreName = txtName.getText();
        com = txtCom.getText();
        centreType = centreTypeBox.getValue();

        if (tgSearchType.getSelectedToggle() != null) {

            try {
                if (!nameSearch) {
                    result = connectionHandler.getCenterByPlaceAndType(com, centreType);
                } else {
                    result = connectionHandler.getCentersByName(centreName);
                }

                if(!result.isEmpty()){
                    for (CentroVaccinale centro : result) {
                        lvResults.getItems().add(centro.toString());
                    }
                }else{
                    showInfoBox("Nessun centro vaccinale trovato");
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }

        } else {
            showErrorBox("Seleziona un tipo di ricerca");
        }

    }

    /**
     * Ricerca e mostra all'utente un prospetto informativo degli eventi avversi
     * registrati in un centro vaccinale, se ne sono stati registrati
     */
    @FXML
    private void showEvent() {

        // TODO testare la visualizzazione degli eventi avversi

        centreName
        
        try {
            String centre = lvResults.getSelectionModel().getSelectedItem();
            centreName=lvResults.getSelectionModel().getSelectedItem().split(",")[0].split("Centro ")[1];
            int i = centre.indexOf(",");
            String centreName = centre.substring(6, i);
        } catch (Exception e) {
            System.out.println(e);
        }

        
        try{
            eventList = connectionHandler.getAdverseEvents(centreName);
        }
        catch(Exception ex){}

        if (!eventList.isEmpty()) {
            for (EventoAvverso evento : eventList) {
                lvInfo.getItems().add(evento.toString());
            }
        } else {
            showInfoBox("Nessun evento avverso registrato");
        }

    }

    /**
     * Azzera i campi mostrati a schermo
     */
    private void clearData() {
        txtName.clear();
        txtCom.clear();
        lvInfo.getItems().clear();
        lvResults.getItems().clear();
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
        a.showAndWait();
    }

    /**
     * Mostra un messaggio informativo
     *
     * @param info le informazioni da visualizzare
     */
    private void showInfoBox(String info) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Attenzione");
        alert.setHeaderText("");
        alert.setContentText(info);
        alert.showAndWait();
    }
}
