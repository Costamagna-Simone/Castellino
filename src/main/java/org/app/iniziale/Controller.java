package org.app.iniziale;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.app.App;
import java.io.IOException;

import static org.app.utilities.Constants.ACQUISTO;

public class Controller {
    private static final int NUM_SLOTS = 6;
    private Slot[] slots;
    private ControllerAggiungiUtente controllerAggiungiUtente;
    private Stage aggiungiUtente;

    public void init()   {
        slots = new Slot[NUM_SLOTS];
        initializeSlot();
    }

    // --- Initialize ---
    public void initializeSlot()  {
        for(int i=0; i<NUM_SLOTS; i++)  {
            slots[i] = new Slot(App.getScene(), i);
        }
    }

    private void initializeDialogAggiungiUtente()  {
        try {
            FXMLLoader loaderReceived = new FXMLLoader(App.class.getResource("dialogAggiungiUtente.fxml"));
            Parent parent = loaderReceived.load();
            controllerAggiungiUtente = loaderReceived.getController();
            controllerAggiungiUtente.init();

            Scene scene = new Scene(parent);
            aggiungiUtente = new Stage();
            aggiungiUtente.setScene(scene);
            aggiungiUtente.setResizable(false);
            aggiungiUtente.setTitle("Aggiungi utente");

            aggiungiUtente.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // --- Utility ---
    private static int utilityParseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    // --- FXML ---

    //Visualizza slot acquisto/vendita/raffronto dopo click su freccia
    public void fxmlApriUtente(MouseEvent mouseEvent) {
        for(Slot slot : slots) {
            if(slot.getAperto())    {
                slot.apriUtente(false);
            }
        }

        FontAwesomeIconView icon = (FontAwesomeIconView) mouseEvent.getSource();
        int numSlot = utilityParseInt(icon.getId().substring(icon.getId().length()-1));
        slots[numSlot].apriUtente(true);
    }

    //Chiudi slot acquisto/vendita/raffonto e apri nome e cognome utente
    public void fxmlChiudiUtente(MouseEvent mouseEvent) {
        FontAwesomeIconView icon = (FontAwesomeIconView) mouseEvent.getSource();
        int numSlot = utilityParseInt(icon.getId().substring(icon.getId().length()-1));
        slots[numSlot].apriUtente(false);
    }

    //Apri dialog aggiungi utente
    public void fxmlAggiungiUtente(MouseEvent mouseEvent)    {
        if(aggiungiUtente==null)  {
            initializeDialogAggiungiUtente();
        }

        aggiungiUtente.show();
        //TODO: FXML dialog aggiungi utente
    }

    //Apri finestra acquisto
    public void fxmlApriAcquisto(MouseEvent mouseEvent) {
        try {
            App.setRoot(ACQUISTO, "acquisto");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*@FXML
    private void switchToSecondary() throws IOException {
        App.setRoot(ACQUISTO, "acquisto");
    }*/


}
