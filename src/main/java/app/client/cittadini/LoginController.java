package app.client.cittadini;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import app.ClientConnectionHandler;
import app.client.centrivaccinali.CentriVaccinali;
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
    private void login() throws IOException {
        username = txtUsername.getText();
        password = txtPassword.getText();

        boolean success = false;

        try {
            success = connectionHandler.login(username, password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if (success) {
            CentriVaccinali.switchScene("InsEventoCittadini");
        }

        // System.out.println(username + " | " + password);
    }
}
