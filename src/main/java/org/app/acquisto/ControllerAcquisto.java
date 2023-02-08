package org.app.acquisto;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import org.app.App;
import org.app.Controller;
import org.app.model.Fattura;

import static org.app.utilities.Constants.INIZIALE;

public class ControllerAcquisto implements Controller {

    @FXML
    private TableView<Fattura> tableViewFatture;

    public void init()  {

    }



    // --- FXML ---

    //Torna alla home
    public void fxmlRitornaHome(MouseEvent mouseEvent) {
        try {
            App.setRoot(INIZIALE, "iniziale");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}