package org.app.database;

import org.app.model.Utente;

import java.util.ArrayList;

public class MangerDB {

    public static ArrayList<Utente> getUtenti()   {
        ArrayList<Utente> utenti = new ArrayList<Utente>();

        utenti.add(new Utente("Alessio", "Silivestro"));
        utenti.add(new Utente("Simone", "Costamagna"));
        utenti.add(new Utente("Luca", "Beccaria"));
        utenti.add(new Utente("Samuele", "Mandaglio"));

        return utenti;
    }
}
