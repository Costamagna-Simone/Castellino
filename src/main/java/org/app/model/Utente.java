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

    public String getNome()    {
        return nome;
    }

    public String getCognome()    {
        return cognome;
    }
}
