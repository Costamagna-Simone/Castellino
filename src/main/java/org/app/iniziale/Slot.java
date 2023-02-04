package org.app.iniziale;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import io.github.palexdev.materialfx.controls.*;

public class Slot {
    private int id;

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

        nome = (Text) scene.lookup("textn" + id);
        cognome = (Text) scene.lookup("textc" + id);
        iconUtente = (FontAwesomeIconView) scene.lookup("faiviu" + id);
        arrowApriUtent = (FontAwesomeIconView) scene.lookup("faivau" + id);

        buttonAcquisto = (MFXButton) scene.lookup("mfxba1" + id);
        buttonVendita = (MFXButton) scene.lookup("mfxbv1" + id);
        buttonRaffronto = (MFXButton) scene.lookup("mfxbr1" + id);
        indietro = (FontAwesomeIconView) scene.lookup("faivi1" + id);
    }

    public void apriUtente() {
        nome.setVisible(false);
        cognome.setVisible(false);
        iconUtente.setVisible(false);
        arrowApriUtent.setVisible(false);

        buttonAcquisto.setVisible(true);
        buttonVendita.setVisible(true);
        buttonRaffronto.setVisible(true);
        indietro.setVisible(true);
    }
}
