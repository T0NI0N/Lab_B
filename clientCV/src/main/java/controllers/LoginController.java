// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import utils.EncryptData;

/**
 * <p>Classe LoginController.</p>
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    private String username;
    private String password;

    private ClientConnectionHandler connectionHandler;

    /**
     * {@inheritDoc}
     *
     * inizializza la connessione alla base di dati
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();
    }

    /**
     * Esegue l'accesso alla piattaforma
     * o mostra gli errori avvenuti durante il processo
     *
     * @throws IOException
     */
    @FXML
    private void login() throws IOException {
        username = txtUsername.getText();
        password = EncryptData.encrypt(txtPassword.getText());

        Cittadino success = null;

        try {
            success = connectionHandler.login(username, password);

        } catch (RemoteException e) {
            System.out.println(e);

            showErrorBox("Errore di connessione durante il recupero delle credenziali");
        }

        if (success != null) {
            Cittadino ct = connectionHandler.getCitizenByLogin(username, password);
            CentroVaccinale cv = connectionHandler.getCenterByVaccinatedCitizen(ct);

            ArrayList<Object> sendingDatas = new ArrayList<>();
            sendingDatas.add(ct);
            sendingDatas.add(cv);

            CentriVaccinali.switchScene("InsEventoCittadini", sendingDatas);

        } else {
            System.out.println("Failure: Login fallita");

            showErrorBox("Credenziali errate, il nome utente o la password sono errati");
        }

        // System.out.println(username + " | " + password);
    }

    /**
     * Mostra un messaggio di errore
     *
     * @param error i problemi riscontrati da visualizzare
     */
    @FXML
    private void showErrorBox(String error){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Errore");
        a.setHeaderText("");
        a.setContentText(error);
        a.setResizable(true);
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        a.showAndWait();
    }

    /**
     * Gestisce la pressione del tasto escape (esc) da tastiera
     * ritornando alla schermata precedente
     *
     * @param event evento che indica la pressione di un tasto
     * @throws IOException
     */
    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE)
            CentriVaccinali.switchScene("HomeCittadini", null);
    }
}
