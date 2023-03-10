package org.app.modificaFattura;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import org.app.Controller;
import org.app.model.DataModel;

public class ControllerModificaFattura implements Controller {
    private DataModel dataModel;

    @Override
    public void init(DataModel dataModel) {
        this.dataModel = dataModel;

        choiceBoxTipologie();
    }

    /********************
     Initialize
     ********************/
    private void choiceBoxTipologie()   {
        ObservableList<String> t = FXCollections.observableArrayList();
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");
        t.add("Commessa");

        tipologie.setItems(t);
    }

    @Override
    public void aggiorna() {

    }

    @FXML
    private ChoiceBox<String> tipologie;

    @FXML
    private TextArea testo;

    @FXML
    private MFXButton modifica;
}

