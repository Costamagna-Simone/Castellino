package org.app.model;


import java.sql.Date;
import java.sql.Timestamp;

public class Fattura {
    Integer id;

    Integer utente;
    Integer tipo;

    Timestamp dataCaricamento;
    Integer numero;
    String suffisso;
    Integer anno;
    Date data;
    String tipoDocumento;
    String codiceFiscale;
    String partitaIva;
    Double imponibile;
    String tipoCassaPrevidenza;
    Double cassaPrevidenza;
    Double imposta;
    Double importoArt15;
    Double bollo;
    Double totale;
    String ritenuta;
    Double nettoAPagare;
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

    public void setUtente(int utente)   {
        this.utente = utente;
    }

    public void setDataCaricamento(Timestamp dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUtente()  {
        return utente;
    }

    public int getTipo() {
        return tipo;
    }

    public Timestamp getDataCaricamento()    {
        return dataCaricamento;
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
