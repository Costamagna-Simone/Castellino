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

    private Utente utenteCorrente;
    private final SimpleIntegerProperty aggiornaUtenti;
    private final ObservableList<Fattura> fatture;

    public DataModel(Stage stage)  {
        this.stage = stage;

        utenti = FXCollections.observableArrayList(new ArrayList<>());
        aggiornaUtenti = new SimpleIntegerProperty(0);

        fatture = FXCollections.observableArrayList(new ArrayList<>());
    }


    /********************
     Utility
     ********************/

    //aggiungi un utente
    public void aggiungiUtente(String nome, String cognome)    {
        Utente utente = ManagerDB.aggiungiUtente(nome, cognome);

        if(utente==null) {
            //TODO implementare dialog errore
        } else {
            utenti.add(utente);
            setAggiornaUtenti();
        }
    }

    //modifica un utente
    public void modificaUtente(int id, String nome, String cognome) {
        Utente utente = ManagerDB.modificaUtente(id, nome, cognome);

        if(utente==null) {
            //TODO implementare dialog errore
        } else {
            for(Utente u : utenti)  {
                if(u.getId() == utente.getId()) {
                    u.modifica(utente);
                }
            }
            setAggiornaUtenti();
        }
    }

    //elimina un utente
    public void eliminaUtente(Utente utente)    {
        ManagerDB.eliminaUtente(utente.getId());
        utenti.remove(utente);
        setAggiornaUtenti();
    }

    //aggiungi fatture
    public void aggiungiFatture(ArrayList<Fattura> fatture) {
        ArrayList<Fattura> nuoveFatture = ManagerDB.setFatture(fatture);
        this.fatture.addAll(nuoveFatture);
    }


    /********************
     Get & Set
     ********************/
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

    public Utente getUtenteCorrente()   {
        return utenteCorrente;
    }

    public void setAggiornaUtenti()    {
        if(aggiornaUtenti.get() == 0)
            aggiornaUtenti.set(1);
        else
            aggiornaUtenti.set(0);
    }

    public void setUtenteCorrente(Utente utente) {
        utenteCorrente = utente;
    }

    public void setFatture(Utente utente, int tipo)    {
        fatture.clear();
        fatture.addAll(ManagerDB.getFatture(utente.getId(), tipo));
    }
}
