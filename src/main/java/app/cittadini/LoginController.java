package app.cittadini;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    private String username;
    private String password;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // qui dentro verranno inizializzati i riferimenti ad altre classi
        // nel caso in cui servissero
        // potrebbero essere riferimenti ad oggetti, connessione al db ecc.
    }

    @FXML
    private void login() {
        username = txtUsername.getText();
        password = txtPassword.getText();

        System.out.println(username + " | " + password);
    }
}
