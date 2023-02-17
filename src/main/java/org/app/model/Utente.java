package org.app.model;

public class Utente {
    private Integer id;
    private String nome;
    private String cognome;

    public Utente(Integer id, String nome, String cognome) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
    }

    /********************
     Utility
     ********************/
    public void modifica(Utente utente) {
        nome = utente.getNome();
        cognome = utente.getCognome();
    }


    /********************
     Get & Set
     ********************/
    public String getNome()    {
        return nome;
    }

    public String getCognome()    {
        return cognome;
    }

    public int getId()  {
        return id;
    }



}
