package app.cittadini;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class RegCittadiniController implements Initializable{
    
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSurname;
    @FXML
    private TextField txtCodf;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtIdVacc;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // qui dentro verranno inizializzati i riferimenti ad altre classi
        // nel caso in cui servissero
        // potrebbero essere riferimenti ad oggetti, connessione al db ecc.
    }

    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE)
            Main.switchScene("HomeCittadini");
    }

    @FXML
    private void register(){
        System.out.println("register user");
    }
}
