package controllers;

import centrivaccinali.CentriVaccinali;
import centrivaccinali.CentroVaccinale;
import centrivaccinali.ClientConnectionHandler;
import centrivaccinali.Indirizzo;
import enums.TipoCentroVaccinale;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class RegCentroVaccinaleController implements Initializable {

    @FXML
    private ChoiceBox<String> chb_tipoIndirizzo;
    @FXML
    private ChoiceBox<TipoCentroVaccinale> chb_tipoCentro;
    @FXML
    private TextField tf_nomeCentro;
    @FXML
    private TextField tf_via;
    @FXML
    private TextField tf_numCivico;
    @FXML
    private TextField tf_prov;
    @FXML
    private TextField tf_cap;
    @FXML
    private TextField tf_comune;

    private ClientConnectionHandler connectionHandler;

    @FXML
    private void onEnterPressed() throws IOException {

        System.out.println("Enter button pressed");

        try {
            connectionHandler.registerCenter(new CentroVaccinale(tf_nomeCentro.getText(),
                    new Indirizzo(chb_tipoIndirizzo.getValue(), tf_via.getText(), tf_numCivico.getText(),
                            tf_comune.getText(), tf_prov.getText(), Integer.parseInt(tf_cap.getText())),
                    chb_tipoCentro.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(tf_nomeCentro.getText());
        System.out.println(chb_tipoIndirizzo.getValue() + " " + tf_via.getText() + " " + tf_numCivico.getText());
        System.out.println(tf_comune.getText() + " (" + tf_prov.getText() + ")" + " " + tf_cap.getText());
        System.out.println(chb_tipoCentro.getValue());

    }

    @FXML
    private void onEscapePressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE)
            CentriVaccinali.switchScene("HomeOperatori");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();

        chb_tipoIndirizzo.setItems(FXCollections.observableList(Arrays.asList("Via", "V.le", "P.zza")));
        chb_tipoIndirizzo.setValue("Via");

        chb_tipoCentro.setItems(FXCollections.observableList(Arrays.asList(TipoCentroVaccinale.values())));
        chb_tipoCentro.setValue(TipoCentroVaccinale.OSPEDALIERO);

    }

}

