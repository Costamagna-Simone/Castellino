package org.app.caricamentoFile;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.app.Controller;
import org.app.model.DataModel;
import org.app.model.Fattura;
import java.util.ArrayList;

public class ControllerCaricamento implements Controller {

    private DataModel dataModel;

    private ArrayList<Fattura> fatture;

    @FXML
    private MFXProgressSpinner progressSpinner;

    @FXML
    private MFXButton importa;

    @FXML
    private Text nomeFile;

    @FXML
    private Text numeroFatture;

    public void init(DataModel dataModel)  {
        this.dataModel = dataModel;
        importa.setDisable(true);
        nomeFile.setText("");
        numeroFatture.setText("Fatture caricate: 0");
    }

    /********************
     Initialize
     ********************/
    private void stop(MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        importa.setDisable(true);
        importa.setVisible(true);
        nomeFile.setText("");
        numeroFatture.setText("Fatture individuate: 0");
        progressSpinner.setVisible(true);
    }

    /********************
     Utility
     ********************/
    //aggiorna i dati all'apertura della finestra
    public void aggiorna()  {}

    /********************
     FXML
     ********************/
    public void importa(MouseEvent mouseEvent)   {
        dataModel.aggiungiFatture(fatture);
        stop(mouseEvent);
    }

    /********************
     Get&Set
     ********************/
    public void setProgressSpinner(int num, int tot)    {
        double p = ((double)num)/tot;
        progressSpinner.setProgress(p);
        numeroFatture.setText("Fatture individuate: " + num);
    }

    public void setFatture(ArrayList<Fattura> fatture)    {
        this.fatture = fatture;
    }

    public void setDisableImporta(boolean d) {
        importa.setDisable(d);
    }

    public void setNomeFile(String nome)    {
        nomeFile.setText(nome);
    }

    public void setErrore(String errore) {
        progressSpinner.setVisible(false);
        numeroFatture.setText(errore + "\n" + "Chiudere e riprovare");
        numeroFatture.getStyleClass().add("text-er");
        importa.setVisible(false);
    }
}