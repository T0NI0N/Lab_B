// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package centrivaccinali;

import controllers.HomeCittadiniController;
import controllers.InsEventoCittadiniController;
import controllers.RegCittadiniController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Main dell'applicazione
 */
public class CentriVaccinali extends Application {

    private static Scene scene;
    public static Stage primaryStage;
    private static FXMLLoader fxmlLoader;

    private static ClientConnectionHandler connectionHandler;

    /**
     * Lancia l'applicazione e l'interfaccia grafica instanziando contestualmente
     * la connessione alla base di dati
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        connectionHandler = ClientConnectionHandler.getClientConnectionHandler();

        if (!connectionHandler.connect()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Errore");
            a.setHeaderText("Errore di connessione");
            a.setContentText("Assicurarsi che serverCV sia in esecuzione e che \nla connessione ad internet sia attiva.");
            a.showAndWait();
        } else {

            CentriVaccinali.primaryStage = primaryStage;
            scene = new Scene(loadFXML("Home", null));
            primaryStage.setScene(scene);
            primaryStage.setTitle("CentriVaccinali-Client");
            primaryStage.show();

            primaryStage.setOnCloseRequest(
                    new EventHandler<WindowEvent>() {

                        @Override
                        public void handle(WindowEvent event) {
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Uscire");
                            alert.setHeaderText("");
                            alert.setContentText("Sei sicuro di voler uscire dal programma?");
                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.get() == ButtonType.OK) {
                                System.out.println("Closing...");
                                connectionHandler.disconnect();
                                System.exit(0);
                            } else event.consume();
                        }
                    });
        }
    }

    /**
     * Cambia la scena visualizzata a schermo
     *
     * @param fxml nome del file di layout della scena
     * @throws java.io.IOException
     * @param o oggetti da passare alla scena
     */
    public static void switchScene(String fxml, ArrayList<Object> o) throws IOException {
        //primaryStage.hide();

        //scene.getStylesheets().add("style.css");

        switch (fxml) {
            case "InsEventoCittadini" -> {
                InsEventoCittadiniController insEventoCittadiniController = new InsEventoCittadiniController();
                insEventoCittadiniController.setCittadino((Cittadino) o.get(0));
                insEventoCittadiniController.setCentroVaccinale((CentroVaccinale) o.get(1));
                scene = new Scene(loadFXML(fxml, insEventoCittadiniController));
            }
            case "RegCittadini" ->{
                RegCittadiniController regCittadiniController = new RegCittadiniController();
                regCittadiniController.setCittadino((Cittadino) o.get((0)));
                scene = new Scene(loadFXML(fxml, regCittadiniController));
            }
            default -> {
                scene = new Scene(loadFXML(fxml, null));
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.centerOnScreen();
                primaryStage.show();
            }
        }
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**
     * Carica il file di layout recuperandolo dalle risorse
     *
     * @param fxml nome del file di layout da caricare
     * @return il layout caricato
     * @throws IOException
     */
    private static Parent loadFXML(String fxml, Object o) throws IOException{
        fxmlLoader = new FXMLLoader(CentriVaccinali.class.getResource(fxml + ".fxml"));
        if(o !=null)
            fxmlLoader.setController(o);
        return fxmlLoader.load();
    }

    /**
     * <p>main.</p>
     *
     * @param args array di {@link java.lang.String}
     */
    public static void main(String[] args) {
        launch();
    }

}
