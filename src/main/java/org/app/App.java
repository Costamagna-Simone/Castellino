package org.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.app.database.ManagerDB;
import org.app.model.DataModel;

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

    private static DataModel datamodel;

    @Override
    public void start(Stage stage) throws IOException {
        ManagerDB.avviaDatabase();

        datamodel = new DataModel();

        parents = new Parent[5];
        controllers = new Controller[5];

        FXMLLoader loaderReceived = new FXMLLoader(App.class.getResource("iniziale.fxml"));
        parents[INIZIALE] = loaderReceived.load();
        controllers[INIZIALE] = loaderReceived.getController();
        scene = new Scene(parents[INIZIALE]);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/iniziale.css")).toExternalForm());

        controllers[INIZIALE].init(datamodel);

        stage.setMinHeight(470);
        stage.setMinWidth(680);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop(){
        ManagerDB.stopDatabase();
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
            controllers[parent].init(datamodel);
    }

    public static Scene getScene()    {
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }

}