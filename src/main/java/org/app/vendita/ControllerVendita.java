package org.app.vendita;

import java.io.IOException;
import javafx.scene.input.MouseEvent;
import org.app.App;
import org.app.Controller;

import static org.app.utilities.Constants.INIZIALE;

public class ControllerVendita implements Controller {

    @Override
    public void init() {

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