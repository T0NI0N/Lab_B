package app.cittadini;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class HomeController implements Initializable {

    @FXML
    private void btnOperatorsPressed() throws IOException {
        System.out.println("Entering operators area");
        Main.switchScene("HomeOperatori");
    }

    @FXML
    private void btnCiviliansPressed() throws IOException {
        System.out.println("Entering civilians area");
        Main.switchScene("HomeCittadini");
    }

    @FXML
    private void btnHelpPressed() throws IOException {
        Alert a = new Alert(AlertType.NONE, "", ButtonType.OK);

        String helpCiviliString = "Nell'area Cittadino è possibile: " +
                "\n - Consultare le informazioni di ogni centro vaccinale;" +
                "\n - Registrarsi al centro vaccinale desiderato;" +
                "\n - Inserire eventuali effetti collaterali post vaccinazione.\n\n";

        String helpOperatoriString = "Nell'area Operatori Sanitari è possibile: " +
                "\n - Registrare un nuovo centro vaccinale;" +
                "\n - Registrare un nuovo vaccinato.";

        a.setTitle("Informazioni");
        a.setHeaderText("Informazioni");
        a.setContentText(helpCiviliString + helpOperatoriString);

        a.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

    }
}
