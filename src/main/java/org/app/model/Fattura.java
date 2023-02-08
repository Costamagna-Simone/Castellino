package org.app.model;

import java.util.Date;

public class Fattura {
    private int numero;
    private String suffisso;
    private int anno;
    private Date data;
    private String tipoDocumento;
    private String codiceFiscale;
    private int partitaIva;
    private double imponibile;
    private String tipoCassaPrevidenza;
    private double cassaPrevidenza;
    private double imposta;
    private double importoArt15;
    private double bollo;
    private double totale;
    private double ritenuta;
    private double nettoAPagare;
    private String notePiede;
    private String stato;

    public Fattura()    {
        
    }
}
