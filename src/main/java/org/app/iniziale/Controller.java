package org.app.iniziale;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.app.App;

import static org.app.utilities.Constants.ACQUISTO;

public class Controller {
    private static final int NUM_SLOTS = 6;
    private Slot[] slots;

    public void init()   {
        slots = new Slot[NUM_SLOTS];
        inizializzaSlot();
    }

    public void inizializzaSlot()  {
        for(int i=0; i<NUM_SLOTS; i++)  {
            slots[i] = new Slot(App.getScene(), i);
        }
    }

    public void apriUtente(MouseEvent mouseEvent) {
        FontAwesomeIconView icon =(FontAwesomeIconView) mouseEvent.getSource();
        int numSlot = parseInt(icon.getId().substring(icon.getId().length()-1));
        slots[numSlot].apriUtente();
    }

    private static int parseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    /*@FXML
    private void switchToSecondary() throws IOException {
        App.setRoot(ACQUISTO, "acquisto");
    }*/


}
