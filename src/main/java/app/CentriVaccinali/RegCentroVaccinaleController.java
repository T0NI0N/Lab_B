package app.CentriVaccinali;

import app.TipoCentroVaccinale;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.Arrays;

public class RegCentroVaccinaleController {

    @FXML
    private ChoiceBox<String> chb_tipoIndirizzo;
    @FXML
    private ChoiceBox<TipoCentroVaccinale> chb_tipoCentro;
    public RegCentroVaccinaleController(){

        chb_tipoIndirizzo.setItems(FXCollections.observableList(Arrays.asList("Via","V.le","P.zza")));

        chb_tipoCentro.setItems(FXCollections.observableList(Arrays.asList(TipoCentroVaccinale.values())));


    }

}
