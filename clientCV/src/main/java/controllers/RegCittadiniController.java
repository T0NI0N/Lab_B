// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import centrivaccinali.CentriVaccinali;
import centrivaccinali.ClientConnectionHandler;
import centrivaccinali.Cittadino;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utils.EncryptData;
import utils.ValidateData;

public class RegCittadiniController implements Initializable {

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

    private Cittadino cittadino;

    private String name;
    private String surname;
    private String codf;
    private String email;
    private String username;
    private String password;
    private long idVacc;

    private ClientConnectionHandler connectionHandler;

    public void setCittadino(Cittadino cittadino) {
        this.cittadino = cittadino;
    }

    /**
     * inizializza la connessione alla base di dati
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();

        txtName.setText(cittadino.getNome());
        txtSurname.setText(cittadino.getCognome());
        txtCodf.setText(cittadino.getCodiceFiscale());
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
            CentriVaccinali.switchScene("HomeCittadini");
    }

    /**
     * Controlla la correttezza dei dati forniti dall'utente
     * e in caso di problemi mostra una finestra di errore
     *
     * @return true se il controllo va a buon fine, false altrimenti
     */
    private boolean checkData(){

        String error = "";
        boolean success = true;

        name = txtName.getText();
        surname = txtSurname.getText();
        codf = txtCodf.getText();
        email = txtEmail.getText();
        username = txtUsername.getText();
        password = txtPassword.getText();


        if(name.equals("") || surname.equals("") || codf.equals("") || email.equals("") || username.equals("") || password.equals("")){
            showErrorBox("- Dati necessari non inseriti");
            return false;
        }

        if(ValidateData.validateCodf(codf)){
            error += "- Codice fiscale inserito non valido \n";
            success = false;
        }

        if(!ValidateData.validateMail(email)){
            error += "- Email inserita non valida \n";
            success = false;
        }

        if(!ValidateData.validatePassword(password)){
            error += "- La password inserita non rispetta la complessità minima richiesta \n";
            success = false;
        }

        boolean idAvailable = connectionHandler.checkUserIDPresence(username);
        if(!idAvailable) {
            error += "- L'userID scelto è già in uso" + "\n";
            success = false;
        }

        boolean emailAvailable = connectionHandler.checkEmailPresence(email);
        if(!emailAvailable) {
            error += "- L'indirizzo email scelto è già in uso";
            success = false;
        }


        if(!success) {
            showErrorBox(error);
        }

        return success;
    }

    /**
     * Registra a sistema l'utente tramite le credenziali fornite,
     * oppure mostra gli errori riscontrati durante la registrazione
     */
    @FXML
    private void register() {

        boolean correctData = checkData();
        if(!correctData){ return; }

        // TODO testare gli inserimenti

        Cittadino user = new Cittadino(name, surname, codf, email, username, EncryptData.encrypt(password), idVacc);
        try {
            connectionHandler.registerCitizen(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println(name + " | " + surname + " | " + codf + " | " + email + " | " + username + " | " + password
                + " | " + idVacc);
    }

    /**
     * Mostra un messaggio di errore
     *
     * @param error i problemi riscontrati da visualizzare
     */
    private void showErrorBox(String error) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Errore");
        a.setHeaderText("");
        a.setContentText("Problemi riscontrati durante la registrazione: " + "\n" + error);
        a.showAndWait();
    }
}
