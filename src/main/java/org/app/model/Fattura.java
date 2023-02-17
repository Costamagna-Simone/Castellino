package org.app.model;


import java.sql.Date;

public class Fattura {
    private Integer id;
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


    /********************
     Get & Set
     ********************/
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public int getTipo() {
        return tipo;
    }

    public int getNumero() {
        return numero;
    }

    public String getSuffisso() {
        return suffisso;
    }

    public int getAnno() {
        return anno;
    }

    public Date getData() {
        return data;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public double getImponibile() {
        return imponibile;
    }

    public String getTipoCassaPrevidenza() {
        return tipoCassaPrevidenza;
    }

    public double getCassaPrevidenza() {
        return cassaPrevidenza;
    }

    public double getImposta() {
        return imposta;
    }

    public double getImportoArt15() {
        return importoArt15;
    }

    public double getBollo() {
        return bollo;
    }

    public double getTotale() {
        return totale;
    }

    public String getRitenuta() {
        return ritenuta;
    }

    public double getNettoAPagare() {
        return nettoAPagare;
    }

    public String getNotePiede() {
        return notePiede;
    }

    public String getStato() {
        return stato;
    }
}
