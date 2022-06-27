package app.client.cittadini;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import app.ClientConnectionHandler;
import app.client.centrivaccinali.CentriVaccinali;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
    @FXML
    private TextField txtIdVacc;

    private String name;
    private String surname;
    private String codf;
    private String email;
    private String username;
    private String password;
    private long idVacc;

    private ClientConnectionHandler connectionHandler;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();
    }

    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE)
            CentriVaccinali.switchScene("HomeCittadini");
    }

    @FXML
    private void register() {
        name = txtName.getText();
        surname = txtSurname.getText();
        codf = txtCodf.getText();
        email = txtEmail.getText();
        username = txtUsername.getText();
        password = txtPassword.getText();
        idVacc = Long.parseLong(txtIdVacc.getText());

        Cittadino user = new Cittadino(name, surname, codf, email, username, password, idVacc);
        try {
            connectionHandler.registerCitizen(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println(name + " | " + surname + " | " + codf + " | " + email + " | " + username + " | " + password
                + " | " + idVacc);
    }
}
