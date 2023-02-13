package org.app.model;

import java.util.Date;

public class Fattura {
    private int tipo;
    int numero;
    String suffisso;
    int anno;
    Date data;
    String tipoDocumento;
    String codiceFiscale;
    String partitaIva;
    double imponibile;
    String tipoCassaPrevidenza;
    double cassaPrevidenza;
    double imposta;
    double importoArt15;
    double bollo;
    double totale;
    String ritenuta;
    double nettoAPagare;
    String notePiede;
    String stato;

    public Fattura(int tipo)    {
        this.tipo = tipo;
    }
}
