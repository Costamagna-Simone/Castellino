package org.app.vendita;

import java.io.IOException;
import javafx.scene.input.MouseEvent;
import org.app.App;
import static org.app.utilities.Constants.INIZIALE;

public class Controller {

    public void fxmlRitornaHome(MouseEvent mouseEvent) {
        try {
            App.setRoot(INIZIALE, "iniziale");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}