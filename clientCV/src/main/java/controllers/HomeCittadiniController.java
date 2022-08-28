// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import centrivaccinali.CentriVaccinali;
import centrivaccinali.CentroVaccinale;
import centrivaccinali.ClientConnectionHandler;
import centrivaccinali.Cittadino;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * <p>Classe HomeCittadiniController.</p>
 */
public class HomeCittadiniController implements Initializable {

    private ClientConnectionHandler connectionHandler;
    private boolean userLoggedIn = false;
    private Cittadino loggedUser;

    /**
     * Imposta il cittadino che ha eseguito l'accesso
     *
     * @param cittadino il cittadino che ha fatto il login
     */
    public void setLoggedUser(Cittadino cittadino){
        this.loggedUser = cittadino;
    }

    /**
     * Imposta lo stato del login dell'utente
     *
     * @param clause true se l'utente ha eseguito l'accesso, false altrimenti
     */
    public void setUserLoggedIn(Boolean clause){
        this.userLoggedIn = clause;
    }

    /**
     * inizializza la connessione alla base di dati
     * e recupera il cittadino che ha fatto il login, se presente
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();
        loggedUser = connectionHandler.getLoggedUser();
        userLoggedIn = (loggedUser != null);
    }

    /**
     * gestisce la pressione del bottone di ricerca delle informazioni di un centro vaccinale
     * cambiando la schermata visualizzata
     * @throws IOException
     */
    @FXML
    private void btnInfoPressed() throws IOException {
        System.out.println("Button info pressed");
        CentriVaccinali.switchScene("CercaInfoCentri", null);
    }

    /**
     * gestisce la pressione del bottone di registrazione di un cittadino
     * richiedendo contestualmente l'id di vaccinazione
     * se presente, cambia la schermata visualizzata a quella dell'effettiva registrazione
     * @throws IOException
     */
    @FXML
    private void btnRegisterPressed() throws IOException {
        System.out.println("Button register pressed");

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("id richiesto");
        dialog.setHeaderText("inserire id vaccinazione:");
        dialog.setContentText("id:");

        Optional<String> result = dialog.showAndWait();

        if (result.isEmpty() || result.get().trim().isBlank()) {
            showErrorBox("id non inserito");

        }else{
            int id = Integer.parseInt(result.get());

            Cittadino c = connectionHandler.getCitizenByVaccinationID(id);

            if(c != null) {
                ArrayList<Object> sendingDatas = new ArrayList<>();
                sendingDatas.add(c);
                CentriVaccinali.switchScene("RegCittadini", sendingDatas);

            }else{
                System.out.println("Failure: ID vaccinazione errato");
                showErrorBox("Non è stata trovata nessuna vaccinazione sotto questo id");
            }
        }
    }

    /**
     * gestisce la pressione del bottone di registrazione di un evento avverso
     * richiedendo contestualmente il login alla piattaforma
     * se ciò avviene, cambia la schermata visualizzata a quella dell'effettivo inserimento degli eventi avversi
     * @throws IOException
     */
    @FXML
    private void btnAddEventPressed() throws IOException {

        System.out.println("Button add event pressed");

        if (!userLoggedIn) {

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Login richiesto");

            alert.setHeaderText(null);
            alert.setContentText("Per usare questa funzionalità è richiesto il login");
            ButtonType login = new ButtonType("Login");
            ButtonType cancel = new ButtonType("Annulla");

            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(login, cancel);

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == login) {
                CentriVaccinali.switchScene("Login", null);
            } else if (option.get() == cancel) {
                System.out.println("cancel pressed");
            }

        } else {
            CentroVaccinale cv = connectionHandler.getCenterByVaccinatedCitizen(loggedUser);

            ArrayList<Object> sendingDatas = new ArrayList<>();
            sendingDatas.add(loggedUser);
            sendingDatas.add(cv);

            CentriVaccinali.switchScene("InsEventoCittadini", sendingDatas);
        }

    }

    /**
     * visualizza una schermata di aiuto
     * @throws IOException
     */
    @FXML
    private void btnHelpPressed() throws IOException {

        Alert a = new Alert(AlertType.NONE, "", ButtonType.OK);

        String helpString = "Elenco delle funzioni: " +
                "\n - Consultare le informazioni di ogni centro vaccinale fornendo o il nome del centro o il comune e il tipo di centro;"
                +
                "\n - Registrarsi al centro vaccinale desiderato inserendo i propri dati anagrafici, email, nome utente e password;"
                +
                "\n - Inserire eventuali effetti collaterali post vaccinazione, previo login alla piattaforma.";

        a.setTitle("Informazioni");
        a.setHeaderText("Informazioni");
        a.setContentText(helpString);
        a.setResizable(true);
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        a.show();
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
            CentriVaccinali.switchScene("Home", null);
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
        a.showAndWait();
    }

}
