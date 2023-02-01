package org.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static org.app.utilities.Constants.INIZIALE;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Parent[] parents;

    @Override
    public void start(Stage stage) throws IOException {
        parents = new Parent[5];

        parents[INIZIALE] = loadFXML("primary");
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(int parent, String fxml) throws IOException {
        if(parents[parent]==null)
            parents[parent] = loadFXML(fxml);

        scene.setRoot(parents[parent]);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}