package app.cittadini;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import app.ClientConnectionHandler;
import app.centrivaccinali.CentriVaccinali;
import app.TipoCentroVaccinale;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    }

    @FXML
    private void onCentreTypeSearchPressed() {
        txtCom.setDisable(false);
        centreTypeBox.setDisable(false);
        txtName.setDisable(true);
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
        try
        {
            connectionHandler.getCenterByPlaceAndType(txtCom.getText(), centreTypeBox.getValue());
        }
        catch(Exception ex)
        {}

        System.out.println(centreName + " | " + com + " | " + centreType);
    }
}
