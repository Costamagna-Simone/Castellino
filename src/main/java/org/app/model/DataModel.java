package org.app.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.app.database.MangerDB;

import java.util.ArrayList;

public class DataModel {

    //lista delle fatture
    private final ObservableList<Fattura> fatture;
    private final ObservableList<Utente> utenti;
    public DataModel()  {
        fatture = FXCollections.observableArrayList(new ArrayList<>());
        utenti = FXCollections.observableArrayList(new ArrayList<>());
    }

    public ObservableList<Fattura> getFatture() {
        return fatture;
    }
    public ObservableList<Utente> getUtenti() {
        if(utenti.isEmpty())    {
            utenti.addAll(MangerDB.getUtenti());
        }

        return utenti;
    }
}
