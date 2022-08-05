// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import centrivaccinali.CentriVaccinali;
import centrivaccinali.ClientConnectionHandler;
import cittadini.Cittadino;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HomeCittadiniController implements Initializable {

    private ClientConnectionHandler connectionHandler;
    private boolean userLoggedIn = false;
    private Cittadino loggedUser;

    /**
     * inizializza la connessione alla base di dati
     * e recupera il cittadino che ha fatto il login, se c'è
     *
     * @param location
     * @param resources
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
        CentriVaccinali.switchScene("CercaInfoCentri");
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
            // errore no risultato
            System.out.println("id non inserito");
        }else{
            // valore inserito
            int id = Integer.parseInt(result.get());

            if (connectionHandler.getCitizenByVaccinationID(id)) {
                // TODO se l'id è riconosciuto si passa alla schermata di registrazione
                //  va implementato un modo per passare alla schermata di registrazione l'id
                //  in modo da autocompilare alcuni campi

                CentriVaccinali.switchScene("RegCittadini");
            }else{
                // errore id non presente
                System.out.println("id non presente in db");
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

        // TODO testare il login

        System.out.println("Button add event pressed");

        if (!userLoggedIn) {

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Login richiesto");

            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText("Per usare questa funzionalità è richiesto il login");
            ButtonType login = new ButtonType("Login");
            ButtonType cancel = new ButtonType("Annulla");

            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(login, cancel);

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == login) {
                CentriVaccinali.switchScene("Login");
            } else if (option.get() == cancel) {
                System.out.println("cancel pressed");
            }

        } else {
            CentriVaccinali.switchScene("InsEventoCittadini");
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
                "\n - Inserire eventuali effetti collaterali post vaccinazione, previo login alla piattaforma. \n\n\n\n\n  ";

        a.setTitle("Informazioni");
        a.setHeaderText("Informazioni");
        a.setContentText(helpString);

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
            CentriVaccinali.switchScene("Home");
    }

}
