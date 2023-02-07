package org.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.app.iniziale.Controller;

import java.io.IOException;
import java.util.Objects;

import static org.app.utilities.Constants.INIZIALE;

/**
 * JavaFX App
 * Author: Simone Costamagna
 */
public class App extends Application {

    private static Scene scene;
    private static Parent[] parents;

    @Override
    public void start(Stage stage) throws IOException {
        parents = new Parent[5];

        FXMLLoader loaderReceived = new FXMLLoader(App.class.getResource("iniziale.fxml"));
        parents[INIZIALE] = loaderReceived.load();
        Controller controller = loaderReceived.getController();
        scene = new Scene(parents[INIZIALE]);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/iniziale.css")).toExternalForm());

        stage.setMinHeight(470);
        stage.setMinWidth(680);
        stage.setScene(scene);
        stage.show();

        controller.init();
    }

    public static void setRoot(int parent, String fxml) throws IOException {
        if(parents[parent]==null)
            parents[parent] = loadFXML(fxml);

        scene.setRoot(parents[parent]);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/styles/" + fxml + ".css")).toExternalForm());
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Scene getScene()    {
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }

}