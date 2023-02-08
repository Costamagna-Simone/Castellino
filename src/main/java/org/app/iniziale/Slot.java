package org.app.iniziale;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import io.github.palexdev.materialfx.controls.*;
import org.app.model.Utente;

public class Slot {
    private int id;

    private Utente utente;
    private boolean aperto;

    private AnchorPane anchorPane;
    private Text nome;
    private Text cognome;
    private FontAwesomeIconView iconUtente;
    private FontAwesomeIconView arrowApriUtent;

    private MFXButton buttonAcquisto;
    private MFXButton buttonVendita;
    private MFXButton buttonRaffronto;
    private FontAwesomeIconView indietro;

    public Slot(Scene scene, int id)    {
        this.id = id;
        utente = null;
        aperto = false;

        anchorPane = (AnchorPane) scene.lookup("#anchorpanes" + id);

        nome = (Text) scene.lookup("#textn" + id);
        cognome = (Text) scene.lookup("#textc" + id);
        iconUtente = (FontAwesomeIconView) scene.lookup("#faiviu" + id);
        arrowApriUtent = (FontAwesomeIconView) scene.lookup("#faivau" + id);

        buttonAcquisto = (MFXButton) scene.lookup("#mfxba" + id);
        buttonVendita = (MFXButton) scene.lookup("#mfxbv" + id);
        buttonRaffronto = (MFXButton) scene.lookup("#mfxbr" + id);
        indietro = (FontAwesomeIconView) scene.lookup("#faivi" + id);
    }

    /* mostra o meno i bottoni acuqisto, vendita, raffonto */
    public void apriUtente(boolean aperto) {
        this.aperto = aperto;

        nome.setVisible(!aperto);
        cognome.setVisible(!aperto);
        iconUtente.setVisible(!aperto);
        arrowApriUtent.setVisible(!aperto);

        buttonAcquisto.setVisible(aperto);
        buttonVendita.setVisible(aperto);
        buttonRaffronto.setVisible(aperto);
        indietro.setVisible(aperto);
    }

    /* oscura o meno lo slot a seconda sia presente un utente*/
    public void oscura(boolean presente) {
        if(presente)    {
            anchorPane.setOpacity(1);
            anchorPane.setDisable(false);
            nome.setText(utente.getNome());
            cognome.setText(utente.getCognome());
        } else {
            anchorPane.setOpacity(0.25);
            anchorPane.setDisable(true);
            nome.setText("Slot");
            cognome.setText("Libero");
        }
    }

    public void setUtente(Utente utente)    {
        this.utente = utente;

        oscura(utente!=null);
    }
    public boolean getAperto()  {
        return aperto;
    }
}
