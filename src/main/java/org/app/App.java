package org.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.app.iniziale.ControllerIniziale;

import java.io.IOException;
import java.util.Objects;

import static org.app.utilities.Constants.INIZIALE;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Parent[] parents;
    private static Controller[] controllers;

    @Override
    public void start(Stage stage) throws IOException {
        parents = new Parent[5];
        controllers = new Controller[5];

        FXMLLoader loaderReceived = new FXMLLoader(App.class.getResource("iniziale.fxml"));
        parents[INIZIALE] = loaderReceived.load();
        controllers[INIZIALE] = loaderReceived.getController();
        scene = new Scene(parents[INIZIALE]);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/iniziale.css")).toExternalForm());

        controllers[INIZIALE].init();


        stage.setMinHeight(470);
        stage.setMinWidth(680);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(int parent, String fxml) throws IOException {
        boolean inizializza = (parents[parent]==null);

        if(inizializza)   {
            FXMLLoader loaderReceived = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            parents[parent] = loaderReceived.load();
            controllers[parent] = loaderReceived.getController();
        }

        scene.setRoot(parents[parent]);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/styles/" + fxml + ".css")).toExternalForm());

        if(inizializza)
            controllers[parent].init();
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