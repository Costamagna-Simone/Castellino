package org.app.iniziale;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.app.model.DataModel;
import org.app.model.Utente;


public class ControllerAggiungiUtente {
    private DataModel dataModel;

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldCognome;

    @FXML
    private Text textErrore;

    public void init(DataModel dataModel)   {
        this.dataModel = dataModel;
    }


    /********************
     Utility
     ********************/
    private void stop(MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    //controlla che i campi del dialog siano completi e corretti
    private boolean controllaCampi() {
        textErrore.setVisible(false);
        textErrore.setText("");

        if(textFieldNome.getText().equals(""))  {
            textErrore.setText("Inserisci un nome valido");
            return false;
        }
        if(textFieldCognome.getText().equals(""))    {
            textErrore.setText("Inserisci un cognome valido");
            return false;
        }
        if(controllaUtenti())    {
            textErrore.setText("Nome e cognome già utilizzati");
            return false;
        }

        return true;
    }

    //controlla che non ci sia un utente con stessi dati di quello che si vuole aggiungere
    private boolean controllaUtenti()    {
        ObservableList<Utente> utenti = dataModel.getUtenti();

        String nome = textFieldNome.getText();
        String cognome = textFieldCognome.getText();

        for(Utente u : utenti)  {
            if(u.getNome().equals(nome) && u.getCognome().equals(cognome))  {
                return true;
            }
        }

        return false;
    }


    /********************
     FXML
     ********************/

    //Aggiungi utente
    public void aggiungiUtente(MouseEvent mouseEvent) {
        if(controllaCampi())    {
            dataModel.aggiungiUtente(textFieldNome.getText(), textFieldCognome.getText());
            stop(mouseEvent);
        } else {
            textErrore.setVisible(true);
        }
    }

}
