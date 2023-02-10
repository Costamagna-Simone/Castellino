package org.app.iniziale;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.app.App;
import org.app.Controller;
import org.app.model.DataModel;
import org.app.model.Utente;

import java.io.IOException;
import java.util.Objects;

import static org.app.utilities.Constants.*;

public class ControllerIniziale implements Controller {
    private static final int NUM_SLOTS = 6;

    private DataModel dataModel;
    private Slot[] slots;
    private ControllerAggiungiUtente controllerAggiungiUtente;
    private Stage aggiungiUtente;

    private ControllerModificaUtente controllerModificaUtente;
    private Stage modificaUtente;

    public void init(DataModel dataModel)   {
        this.dataModel = dataModel;

        initializeSlot();

        dataModel.getAggiornaUtenti().addListener((observableValue, numberOld, numberNew) -> {
            initializeSlot();
        });
    }

    // --- Initialize ---
    public void initializeSlot()  {
        slots = new Slot[NUM_SLOTS];

        ObservableList<Utente> utenti = dataModel.getUtenti();

        for(int i=0; i<NUM_SLOTS; i++) {
            slots[i] = new Slot(App.getScene(), i);

            if(i<utenti.size()) {
                slots[i].setUtente(utenti.get(i));
            }
        }
    }

    private void initializeDialogAggiungiUtente()  {
        try {
            FXMLLoader loaderReceived = new FXMLLoader(App.class.getResource("dialogAggiungiUtente.fxml"));
            Parent parent = loaderReceived.load();
            controllerAggiungiUtente = loaderReceived.getController();
            controllerAggiungiUtente.init(dataModel);

            Scene scene = new Scene(parent);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/dialogUtente.css")).toExternalForm());

            aggiungiUtente = new Stage();
            aggiungiUtente.setScene(scene);
            aggiungiUtente.setResizable(false);
            aggiungiUtente.setTitle("Aggiungi utente");

            aggiungiUtente.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeDialogModificaUtente()  {
        try {
            FXMLLoader loaderReceived = new FXMLLoader(App.class.getResource("dialogModificaUtente.fxml"));
            Parent parent = loaderReceived.load();
            controllerModificaUtente = loaderReceived.getController();
            controllerModificaUtente.init(dataModel);

            Scene scene = new Scene(parent);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/dialogUtente.css")).toExternalForm());

            modificaUtente = new Stage();
            modificaUtente.setScene(scene);
            modificaUtente.setResizable(false);
            modificaUtente.setTitle("Modifica utente");

            modificaUtente.initModality(Modality.APPLICATION_MODAL);
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
    }

    //Apri dialog modifica utente
    public void fxmlModificaUtente(MouseEvent mouseEvent) {
        if(modificaUtente==null)  {
            initializeDialogModificaUtente();
        }

        modificaUtente.show();
    }

    //Apri finestra acquisto
    public void fxmlApriAcquisto(MouseEvent mouseEvent) {
        try {
            App.setRoot(ACQUISTO, "acquisto");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Apri finestra vendita
    public void fxmlApriVendita(MouseEvent mouseEvent) {
        try {
            App.setRoot(VENDITA, "vendita");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Apri finestra raffronto
    public void fxmlApriRaffronto(MouseEvent mouseEvent) {
        try {
            App.setRoot(RAFFRONTO, "raffronto");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*@FXML
    private void switchToSecondary() throws IOException {
        App.setRoot(ACQUISTO, "acquisto");
    }*/


}
