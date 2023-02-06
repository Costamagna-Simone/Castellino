package org.app.iniziale;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.input.MouseEvent;
import org.app.App;

public class Controller {
    private static final int NUM_SLOTS = 6;
    private Slot[] slots;

    public void init()   {
        slots = new Slot[NUM_SLOTS];
        initializeSlot();
    }

    /* --- Initialize --- */
    public void initializeSlot()  {
        for(int i=0; i<NUM_SLOTS; i++)  {
            slots[i] = new Slot(App.getScene(), i);
        }
    }

    /* --- Utility --- */
    private static int utilityParseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /* --- FXML --- */

    //Visualizza slot acquisto/vendita/raffronton dopo click su freccia
    public void fxmlApriUtente(MouseEvent mouseEvent) {
        //TODO controllare non ci siano altri slot aperti. In tal caso chiuderli

        FontAwesomeIconView icon = (FontAwesomeIconView) mouseEvent.getSource();
        int numSlot = utilityParseInt(icon.getId().substring(icon.getId().length()-1));
        slots[numSlot].apriUtente(true);
    }

    //FXML: chiudi slot acquisto/vendita/raffonto e apri nome e cognome utente
    public void fxmlChiudiUtente(MouseEvent mouseEvent) {
        FontAwesomeIconView icon = (FontAwesomeIconView) mouseEvent.getSource();
        int numSlot = utilityParseInt(icon.getId().substring(icon.getId().length()-1));
        slots[numSlot].apriUtente(false);
    }




    /*@FXML
    private void switchToSecondary() throws IOException {
        App.setRoot(ACQUISTO, "acquisto");
    }*/


}
