package app.cittadini;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HomeCittadiniController implements Initializable {

    private boolean userLoggedIn;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO va impostato un metodo nella classe cittadino per verificare se è
        // loggato
        userLoggedIn = false;
    }

    @FXML
    private void btnInfoPressed() throws IOException {
        System.out.println("Button info pressed");
        Main.switchScene("CercaInfoCentri");
    }

    @FXML
    private void btnRegisterPressed() throws IOException {
        System.out.println("Button register pressed");
        Main.switchScene("RegCittadini");
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
                Main.switchScene("Login");
            } else if (option.get() == cancel) {
                System.out.println("cancel pressed");
            }

        } else {
            Main.switchScene("InsEventoCittadini");
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
            Main.switchScene("Home");
    }

}
