package org.app.acquisto;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import org.app.App;
import org.app.Controller;
import org.app.model.DataModel;
import org.app.model.Fattura;

import java.io.IOException;

import static org.app.utilities.Constants.INIZIALE;

public class ControllerAcquisto implements Controller {

    @FXML
    private TableView<Fattura> tableViewFatture;

    public void init(DataModel dataModel)  {

    }



    /********************
     FXML
     ********************/

    //Torna alla home
    public void fxmlRitornaHome(MouseEvent mouseEvent) {
        try {
            App.setRoot(INIZIALE, "iniziale");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}