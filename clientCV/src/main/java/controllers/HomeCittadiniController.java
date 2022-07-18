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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();
        loggedUser = connectionHandler.getLoggedUser();
        userLoggedIn = (loggedUser != null);
    }

    @FXML
    private void btnInfoPressed() throws IOException {
        System.out.println("Button info pressed");
        CentriVaccinali.switchScene("CercaInfoCentri");
    }

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
            // TODO sostituire / modificare if per il controllo dell'id
            //  in base al metodo del db che verrà implementato in seguito
            if (id == 0000) {
                // se l'id è riconosciuto si passa alla schermata di registrazione
                // va implementato un modo per passare alla schermata di registrazione l'id
                // in modo da autocompilare alcuni campi
                CentriVaccinali.switchScene("RegCittadini");
            }else{
                // errore id non presente
                System.out.println("id non presente in db");
            }
        }
    }

    @FXML
    private void btnAddEventPressed() throws IOException {
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

    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE)
            CentriVaccinali.switchScene("Home");
    }

}
