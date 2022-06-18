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
        System.out.println("16bit univoco: "+ stringKeyFormatter().hashCode());
        
    }

    private String stringKeyFormatter(){
        String tmp ="";
        //due del nome del centro, due del nome, due del cognome, datanascita del cf, data somministrazione ddMMaa, due del vaccino
        tmp+=tf_centroV.getText().substring(0,2);
        tmp+=tf_nome.getText().substring(0,2);
        tmp+=tf_cognome.getText().substring(0,2);
        tmp+=tf_codiceFiscale.getText().substring(6,8);
        tmp+=dp_data.getValue().format(DateTimeFormatter.ofPattern("ddMMYY"));
        tmp+=chb_vaccino.getValue().toString().substring(0,2);
        return tmp;
    }

    public void initialize() {
        chb_vaccino.setItems(FXCollections.observableList(Arrays.asList(TipoVaccino.values())));
        dp_data.setValue(LocalDate.now());
    }
}
