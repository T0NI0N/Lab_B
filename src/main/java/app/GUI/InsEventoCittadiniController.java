package app.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import app.Main;
import app.TipoEventoAvverso;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private ChoiceBox<TipoEventoAvverso> eventType; 

    @FXML
    private TextArea txtNotes;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // qui dentro verranno inizializzati i riferimenti ad altre classi
        // nel caso in cui servissero
        // potrebbero essere riferimenti ad oggetti, connessione al db ecc.

        eventType.setItems(FXCollections.observableList(Arrays.asList(TipoEventoAvverso.values())));
        eventType.setValue(TipoEventoAvverso.Emicrania);
    }

    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE)
            Main.switchScene("HomeCittadini");
    }

    @FXML
    private void btnSubmitPressed() throws IOException {
        System.out.println("Button submit pressed");
        System.out.println(tgRbSev.getSelectedToggle().toString());
        System.out.println(txtNotes.getText());
    }
}
