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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();

        centreTypeBox.setItems(FXCollections.observableList(Arrays.asList(TipoCentroVaccinale.values())));
        centreTypeBox.setValue(TipoCentroVaccinale.OSPEDALIERO);

        txtName.setDisable(true);
        txtCom.setDisable(true);
        centreTypeBox.setDisable(true);
    }

    @FXML
    private void onNameSearchPressed() {
        txtName.setDisable(false);
        txtCom.setDisable(true);
        centreTypeBox.setDisable(true);

        clearData();

        nameSearch = true;
    }

    @FXML
    private void onCentreTypeSearchPressed() {
        txtCom.setDisable(false);
        centreTypeBox.setDisable(false);
        txtName.setDisable(true);

        clearData();

        nameSearch = false;
    }

    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE)
            CentriVaccinali.switchScene("HomeCittadini");
    }

    @FXML
    private void search() {
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

            } catch (Exception ex) {
            }

            for (CentroVaccinale centro : result) {
                lvResults.getItems().add(centro.toString());
            }
        } else {
            Alert a = new Alert(AlertType.NONE, "Seleziona un tipo di ricerca", ButtonType.OK);

            a.setTitle("Errore");
            a.setHeaderText("Dati mancanti");

            a.show();
        }

    }

    @FXML
    private void showEvent() {

        String centre = lvResults.getSelectionModel().getSelectedItem();
        int i = centre.indexOf(",");
        String centreName = centre.substring(6, i);

        try {
            eventList = connectionHandler.getAdverseEvents(centreName);
        } catch (RemoteException e) {

            e.printStackTrace();
        }

        if (eventList.size() == 0) {
            lvInfo.getItems().add("Nessun evento avverso registrato");
        } else {
            for (EventoAvverso evento : eventList) {
                lvInfo.getItems().add(evento.toString());
            }
        }

    }

    private void clearData() {
        txtName.clear();
        txtCom.clear();
        lvInfo.getItems().clear();
        lvResults.getItems().clear();
    }
}