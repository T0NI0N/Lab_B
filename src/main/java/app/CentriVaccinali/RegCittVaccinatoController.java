package app.CentriVaccinali;

import app.TipoCentroVaccinale;
import app.TipoVaccino;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

public class RegCittVaccinatoController {

    @FXML
    private ChoiceBox<TipoVaccino> chb_vaccino;
    @FXML
    private TextField tf_centroV;
    @FXML
    private TextField tf_nome;
    @FXML
    private TextField tf_cognome;
    @FXML
    private TextField tf_codiceFiscale;
    @FXML
    private DatePicker dp_data;

    @FXML
    private void onEnterPressed() throws IOException {

        System.out.println("Enter button pressed");
        System.out.println(tf_cognome.getText()+" "+tf_nome.getText()+" vaccinato presso il centro "+tf_centroV.getText()+ " in data "+dp_data.getValue());
        System.out.println("CF: "+tf_codiceFiscale.getText());
        System.out.println("Vaccinato con una dose di "+chb_vaccino.getValue());
        System.out.println("16bit univoco: "+ KeyFormatter(tf_centroV.getText(),tf_codiceFiscale.getText(),dp_data,chb_vaccino.getValue().toString()).toString());
        
    }

    /**
     * Formatta e computa un id univoco ricavando le informazioni dai parametri forniti in ingresso
     * @param centro Centro vaccinale ottenuto
     * @param codiceF Codice fiscale del cittadino
     * @param data Istanza del DatePicker da cui si ottiene la data
     * @param vaccino Stringa del vaccino somministrato
     * @return  l'id univoco sottoforma di array di 2 byte
     */
    private byte[] KeyFormatter(String centro, String codiceF, DatePicker data, String vaccino){
        String tmp ="";
        short key=0;

        tmp+=centro.substring(0,3);
        tmp+=codiceF.substring(0,6);
        tmp+=codiceF.substring(6,8);
        tmp+=data.getValue().format(DateTimeFormatter.ofPattern("ddMMYY"));
        tmp+=vaccino.substring(0,2);

        key= (short) tmp.hashCode();
        return new byte[] {
                (byte)(key >>> 16),
                (byte)(key >>> 8),
                (byte)key};
    }

    public void initialize() {
        chb_vaccino.setItems(FXCollections.observableList(Arrays.asList(TipoVaccino.values())));
        dp_data.setValue(LocalDate.now());
    }
}
