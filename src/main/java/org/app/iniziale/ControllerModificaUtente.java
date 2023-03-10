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


public class ControllerModificaUtente {
    private DataModel dataModel;
    private Utente utente;

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
    //Chiudi dialog modifica utente
    private void stop(MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        textFieldNome.setText("");
        textFieldCognome.setText("");
        textErrore.setText("");
        textErrore.setVisible(false);
    }
    private boolean utilityControllaCampi() {
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
        if(utilityControllaUtenti())    {
            textErrore.setText("Nome e cognome già utilizzati");
            return false;
        }

        return true;
    }

    private boolean utilityControllaUtenti()    {
        ObservableList<Utente> utenti = dataModel.getUtenti();

        String nome = textFieldNome.getText();
        String cognome = textFieldCognome.getText();

        for(Utente u : utenti)  {
            if(u.getId()!=utente.getId() && u.getNome().equals(nome) && u.getCognome().equals(cognome))  {
                return true;
            }
        }

        return false;
    }


    /********************
     FXML
     ********************/

    //Modifica utente
    public void modificaUtente(MouseEvent mouseEvent) {
        if(utilityControllaCampi())    {
            dataModel.modificaUtente(utente.getId(), textFieldNome.getText(), textFieldCognome.getText());
            stop(mouseEvent);
        } else {
            textErrore.setVisible(true);
        }
    }

    //Elimina utente
    public void eliminaUtente(MouseEvent mouseEvent) {
        //TODO open dialog di avviso
        dataModel.eliminaUtente(utente);
        stop(mouseEvent);
    }


    /********************
     Get & Set
     ********************/
    public void setUtente(Utente utente) {
        this.utente = utente;

        textFieldNome.setText(utente.getNome());
        textFieldCognome.setText(utente.getCognome());
    }
}
