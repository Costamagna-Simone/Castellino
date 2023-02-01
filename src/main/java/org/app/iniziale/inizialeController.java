package org.app.iniziale;

import java.io.IOException;
import javafx.fxml.FXML;
import org.app.App;

import static org.app.utilities.Constants.ACQUISTO;

public class inizialeController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot(ACQUISTO, "secondary");
    }
}
