package org.app.acquisto;

import java.io.IOException;
import javafx.fxml.FXML;
import org.app.App;

import static org.app.utilities.Constants.INIZIALE;

public class acquistoController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot(INIZIALE, "iniziale");
    }
}