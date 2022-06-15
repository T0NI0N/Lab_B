package app.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.fxml.Initializable;

public class CercaInfoCentriController implements Initializable{

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
    // ci sarà da modificare il tipo <T> in seguito in base al codice
    private Spinner<String> txtType; 

    @FXML
    // ci sarà da modificare il tipo <T> in seguito in base al codice
    private ListView<String> lvResults;
    @FXML
    // ci sarà da modificare il tipo <T> in seguito in base al codice
    private ListView<String> lvInfo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // qui dentro verranno inizializzati i riferimenti ad altre classi
        // nel caso in cui servissero
        // potrebbero essere riferimenti ad oggetti, connessione al db ecc.
    }

    private void search(){
        System.out.println("search pressed");
    }
}
