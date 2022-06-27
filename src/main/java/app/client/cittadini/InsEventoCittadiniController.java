package app.client.cittadini;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import app.client.centrivaccinali.CentroVaccinale;
import app.client.centrivaccinali.EventoAvverso;
import app.ClientConnectionHandler;
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
import app.client.centrivaccinali.CentriVaccinali;

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
    private Cittadino cittadino;
    private CentroVaccinale centroVaccinale;

    private ClientConnectionHandler connectionHandler;

    public void setCittadino(Cittadino cittadino) {
        this.cittadino=cittadino;
    }
    public void setCenter(CentroVaccinale centroVaccinale){
        this.centroVaccinale = centroVaccinale;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();

        eventTypeBox.setItems(FXCollections.observableList(Arrays.asList(TipoEventoAvverso.values())));
        eventTypeBox.setValue(TipoEventoAvverso.Emicrania);
    }

    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE)
            CentriVaccinali.switchScene("HomeCittadini");
    }

    @FXML
    private void btnSubmitPressed() throws IOException {
        System.out.println("Button submit pressed");
        eventType = eventTypeBox.getValue();
        sev = Integer.parseInt(tgRbSev.getSelectedToggle().getUserData().toString());
        notes = txtNotes.getText();
		
		//TODO inserire cittadino e nomecentro e controllare se funziona
        System.out.println(cittadino.toString());
        System.out.println(centroVaccinale.toString());
        System.out.println(new EventoAvverso(eventType, sev, notes));
		connectionHandler.insertAdverseEvent(cittadino, centroVaccinale, new EventoAvverso(eventType, sev, notes));

        System.out.println(eventType + " | " + sev + " | " + notes);
    }

}
