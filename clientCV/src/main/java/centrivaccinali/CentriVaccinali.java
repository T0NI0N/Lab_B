package centrivaccinali;

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
import java.util.Optional;

/**
 * JavaFX App
 */
public class CentriVaccinali extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    //private static ClientConnectionHandler connectionHandler;

    /**
     * Lancia l'applicazione e l'interfaccia grafica instanziando constestualmente
     * la connessione alla base di dati
     *
     * @param primaryStage schermata di partenza predefinita di JavaFX
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        //connectionHandler = ClientConnectionHandler.getClientConnectionHandler();
        //connectionHandler.connect();

        CentriVaccinali.primaryStage = primaryStage;
        scene = new Scene(loadFXML("Home"));
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Uscire");
                alert.setHeaderText("");
                alert.setContentText("Sei sicuro di voler uscire dal programma?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    System.out.println("Closing...");
                    //connectionHandler.disconnect();
                    System.exit(0);
                } else
                    event.consume();
            }

        });
    }

    /**
     * Cambia la scena visualizzata a schermo
     *
     * @param fxml nome del file di layout della scena
     * @throws IOException
     */
    public static void switchScene(String fxml) throws IOException {
        // primaryStage.hide();
        scene = new Scene(loadFXML(fxml));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Carica il file di layout recuperandolo dalle risorse
     *
     * @param fxml nome del file di layout da caricare
     * @return il layout caricato
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CentriVaccinali.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
