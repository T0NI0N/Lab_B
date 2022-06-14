package app.GUI;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

public class InsEventoCittadiniController {

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
    private TextArea txtNotes;


    @FXML
    private void btnSubmitPressed() throws IOException {
        System.out.println("Button submit pressed");
        System.out.println(tgRbSev.getSelectedToggle().toString());
        System.out.println(txtNotes.getText());
    }
}
