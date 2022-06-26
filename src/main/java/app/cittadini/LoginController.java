package app.cittadini;

import java.net.URL;
import java.util.ResourceBundle;

import app.ClientConnectionHandler;
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

    private ClientConnectionHandler connectionHandler;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();
    }

    @FXML
    private void login() {
        username = txtUsername.getText();
        password = txtPassword.getText();

        // TODO inserire metodo login

        System.out.println(username + " | " + password);
    }
}
