package app;

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
public class Main extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
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
                
                if(result.get() == ButtonType.OK) {
                    System.out.println("Closing...");
                    System.exit(0);
                }
                else
                    event.consume();
            }
            
        });
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}