package org.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

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

        parents[INIZIALE] = loadFXML("iniziales");
        scene = new Scene(parents[INIZIALE]);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Styles/iniziale.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(int parent, String fxml) throws IOException {
        if(parents[parent]==null)
            parents[parent] = loadFXML(fxml);

        scene.setRoot(parents[parent]);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/Styles/" + fxml + ".css")).toExternalForm());
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}