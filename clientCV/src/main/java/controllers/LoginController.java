// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import centrivaccinali.CentriVaccinali;
import centrivaccinali.CentroVaccinale;
import centrivaccinali.ClientConnectionHandler;
import centrivaccinali.Cittadino;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.EncryptData;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    private String username;
    private String password;

    private ClientConnectionHandler connectionHandler;

    /**
     * inizializza la connessione alla base di dati
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();
    }

    @FXML
    private void login() throws IOException {
        username = txtUsername.getText();
        password = EncryptData.encrypt(txtPassword.getText());

        Cittadino success = null;

        try {
            success = connectionHandler.login(username, password);
        } catch (RemoteException e) {
            System.out.println(e);
        }

        if (success != null) {
            CentriVaccinali.switchScene("InsEventoCittadini");
            Cittadino ct = connectionHandler.getCitizenByLogin(username, password);
            CentroVaccinale cv = connectionHandler.getCenterByVaccinatedCitizen(ct);
            System.out.println(ct.toString());
            new InsEventoCittadiniController().setCittadino(ct);

            System.out.println(cv.toString());
            new InsEventoCittadiniController().setCenter(cv);
        }else {
            System.out.println("Failure: Login fallita");
            Alert b = new Alert(Alert.AlertType.ERROR);
            b.setTitle("Login fallita");
            b.setHeaderText("Credenziali errate");
            b.setContentText("Il nome utente o la password sono errati");
            b.showAndWait();
        }

        // System.out.println(username + " | " + password);
    }
}
