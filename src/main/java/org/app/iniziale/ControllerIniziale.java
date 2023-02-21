package org.app.iniziale;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
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

        slot();

        dataModel.getAggiornaUtenti().addListener((observableValue, numberOld, numberNew) -> {
            slot();
        });
    }


    /********************
     Initialize
     ********************/
    //inizializza gli slot
    public void slot()  {
        slots = new Slot[NUM_SLOTS];

        ObservableList<Utente> utenti = dataModel.getUtenti();

        for(int i=0; i<NUM_SLOTS; i++) {
            slots[i] = new Slot(App.getScene(), i);

            if(i<utenti.size()) {
                slots[i].setUtente(utenti.get(i));
            } else {
                slots[i].setUtente(null);
            }
        }
    }

    //inizializza il dialog aggiungi utente
    private void dialogAggiungiUtente()  {
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

    //inizializza il dialog modifica utente
    private void dialogModificaUtente()  {
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


    /********************
     Utility
     ********************/
    private static int parseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    /********************
     FXML
     ********************/

    //Visualizza slot acquisto/vendita/raffronto dopo click su freccia
    public void apriUtente(MouseEvent mouseEvent) {
        for(Slot slot : slots) {
            if(slot.getAperto())    {
                slot.apriUtente(false);
            }
        }

        FontAwesomeIconView icon = (FontAwesomeIconView) mouseEvent.getSource();
        int numSlot = parseInt(icon.getId().substring(icon.getId().length()-1));
        slots[numSlot].apriUtente(true);
        dataModel.setUtenteCorrente(slots[numSlot].getUtente());
    }

    //Chiudi slot acquisto/vendita/raffonto e apri nome e cognome utente
    public void chiudiUtente(MouseEvent mouseEvent) {
        FontAwesomeIconView icon = (FontAwesomeIconView) mouseEvent.getSource();
        int numSlot = parseInt(icon.getId().substring(icon.getId().length()-1));
        slots[numSlot].apriUtente(false);
    }

    //Apri dialog aggiungi utente
    public void aggiungiUtente(MouseEvent mouseEvent)    {
        if(aggiungiUtente==null)  {
            dialogAggiungiUtente();
        }

        aggiungiUtente.show();
    }

    //Modifica dialog modifica utente, cliccando da text
    public void modificaUtente(MouseEvent mouseEvent) {
        Text text = (Text) mouseEvent.getSource();
        int numSlot = parseInt(text.getId().substring(text.getId().length()-1));

        if(modificaUtente==null)  {
            dialogModificaUtente();
        }

        controllerModificaUtente.setUtente(slots[numSlot].getUtente());
        modificaUtente.show();
    }

    //Modifica dialog modifica utente, cliccando da icon
    public void modificaUtenteIcon(MouseEvent mouseEvent) {
        FontAwesomeIconView icon = (FontAwesomeIconView) mouseEvent.getSource();
        int numSlot = parseInt(icon.getId().substring(icon.getId().length()-1));

        if(modificaUtente==null)  {
            dialogModificaUtente();
        }

        controllerModificaUtente.setUtente(slots[numSlot].getUtente());
        modificaUtente.show();
    }

    //Apri finestra acquisto
    public void apriAcquisto(MouseEvent mouseEvent) {
        dataModel.setFatture(dataModel.getUtenteCorrente(), ACQUISTO);
        try {
            App.setRoot(ACQUISTO, "acquisto");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Apri finestra vendita
    public void apriVendita(MouseEvent mouseEvent) {
        dataModel.setFatture(dataModel.getUtenteCorrente(), VENDITA);
        try {
            App.setRoot(VENDITA, "vendita");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Apri finestra raffronto
    public void apriRaffronto(MouseEvent mouseEvent) {
        try {
            App.setRoot(RAFFRONTO, "raffronto");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
