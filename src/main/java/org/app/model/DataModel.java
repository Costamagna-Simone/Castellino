package org.app.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.app.database.ManagerDB;

import java.util.ArrayList;

public class DataModel {

    private final Stage stage;
    private final ObservableList<Utente> utenti;
    private final SimpleIntegerProperty aggiornaUtenti;
    private final ObservableList<Fattura> fatture;

    public DataModel(Stage stage)  {
        this.stage = stage;

        utenti = FXCollections.observableArrayList(new ArrayList<>());
        aggiornaUtenti = new SimpleIntegerProperty(0);

        fatture = FXCollections.observableArrayList(new ArrayList<>());
    }

    public void aggiungiUtente(String nome, String cognome)    {
        Utente utente = ManagerDB.aggiungiUtente(nome, cognome);

        if(utente==null) {
            //TODO implementare dialog errore
        } else {
            utenti.add(utente);
            setAggiornaUtenti();
        }
    }

    public void modificaUtente(int id, String nome, String cognome) {
        Utente utente = ManagerDB.modificaUtente(id, nome, cognome);

        if(utente==null) {
            //TODO implementare dialog errore
        } else {
            for(Utente u : utenti)  {
                if(u.getId() == utente.getId()) {
                    u.utilityModifica(utente);
                }
            }
            setAggiornaUtenti();
        }
    }


    //GET & SET
    public Stage getStage() {
        return stage;
    }
    public ObservableList<Utente> getUtenti() {
        if(utenti.isEmpty())    {
            utenti.addAll(ManagerDB.getUtenti());
        }

        return utenti;
    }
    public SimpleIntegerProperty getAggiornaUtenti()    {
        return aggiornaUtenti;
    }
    public ObservableList<Fattura> getFatture() {
        return fatture;
    }

    public void setAggiornaUtenti()    {
        if(aggiornaUtenti.get() == 0)
            aggiornaUtenti.set(1);
        else
            aggiornaUtenti.set(0);
    }

}
