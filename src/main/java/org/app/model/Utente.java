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

    //Utility
    public void utilityModifica(Utente utente) {
        nome = utente.getNome();
        cognome = utente.getCognome();
    }

    //GET e SET

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
