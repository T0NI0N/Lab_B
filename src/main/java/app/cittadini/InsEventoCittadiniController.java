package app.cittadini;

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
    private ChoiceBox<TipoEventoAvverso> eventTypeBox;

    @FXML
    private TextArea txtNotes;

    private TipoEventoAvverso eventType;
    private int sev;
    private String notes;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // qui dentro verranno inizializzati i riferimenti ad altre classi
        // nel caso in cui servissero
        // potrebbero essere riferimenti ad oggetti, connessione al db ecc.

        eventTypeBox.setItems(FXCollections.observableList(Arrays.asList(TipoEventoAvverso.values())));
        eventTypeBox.setValue(TipoEventoAvverso.Emicrania);
    }

    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE)
            Main.switchScene("HomeCittadini");
    }

    @FXML
    private void btnSubmitPressed() throws IOException {
        System.out.println("Button submit pressed");
        eventType = eventTypeBox.getValue();
        sev = Integer.parseInt(tgRbSev.getSelectedToggle().toString());
        notes = txtNotes.getText();
        System.out.println(eventType + " | " + sev + " | " + notes);
    }
}
