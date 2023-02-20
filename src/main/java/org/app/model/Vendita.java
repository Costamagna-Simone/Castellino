package org.app.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.app.utilities.Constants.NUM_COLONNE_VENDITA;
import static org.app.utilities.Constants.VENDITA;

public class Vendita extends Fattura {
    private String cliente;
    private String esito;

    public Vendita()    {
        super(VENDITA);
    }


    /********************
     Utility
     ********************/
    private double fromStringToDouble(String string) throws ParseException {
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(symbols);
        return (df.parse(string)).doubleValue();
    }



    /********************
     Get & Set
     ********************/
    public String setCampi(BufferedReader reader, String line, int num)   {
        String[] colonne = line.split("\\^");

        if(colonne.length != NUM_COLONNE_VENDITA && colonne.length != NUM_COLONNE_VENDITA-2)   {
            return "numero di colonne diverso da " + NUM_COLONNE_VENDITA;
        }

        try {
            if(!colonne[0].equals(""))
                numero = Integer.parseInt(colonne[0]);
        } catch(NumberFormatException ex)   {
            return "la colonna numero contiene un formato non valido a riga: " + num;
        }

        suffisso = colonne[1];

        try {
            if(!colonne[0].equals(""))
                anno = Integer.parseInt(colonne[2]);
        } catch(NumberFormatException ex)   {
            return "la colonna anno contiene un formato non valido a riga: " + num;
        }

        try {
            if(!colonne[3].equals(""))
                data = new Date((new SimpleDateFormat("dd/MM/yyyy").parse(colonne[3])).getTime());
        } catch (ParseException e) {
            return "la colonna data contiene un formato diverso da dd/MM/yyyy a riga: " + num;
        }

        tipoDocumento = colonne[4];
        cliente = colonne[5];
        codiceFiscale = colonne[6];
        partitaIva = colonne[7];

        try {
            if(!colonne[8].equals(""))  {
                imponibile = fromStringToDouble(colonne[8]);
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna imponibile contiene un formato non valido a riga: " + num;
        }

        tipoCassaPrevidenza = colonne[9];

        try {
            if(!colonne[10].equals(""))  {
                cassaPrevidenza = fromStringToDouble(colonne[10]);
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna cassa previdenza contiene un formato non valido a riga: " + num;
        }

        try {
            if(!colonne[11].equals(""))  {
                imposta = fromStringToDouble(colonne[11]);
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna imposta contiene un formato non valido a riga: " + num;
        }

        try {
            if(!colonne[12].equals(""))  {
                importoArt15 = fromStringToDouble(colonne[12]);
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna importo Art. 15 contiene un formato non valido a riga: " + num;
        }

        try {
            if(!colonne[13].equals(""))  {
                bollo = fromStringToDouble(colonne[13]);
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna bollo contiene un formato non valido a riga: " + num;
        }

        try {
            if(!colonne[14].equals(""))  {
                totale = fromStringToDouble(colonne[14]);
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna totale contiene un formato non valido a riga: " + num;
        }

        ritenuta = colonne[15];

        try {
            if(!colonne[16].equals(""))  {
                nettoAPagare = fromStringToDouble(colonne[16]);
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna netto a pagare contiene un formato non valido a riga: " + num;
        }

        if(colonne.length==18)    {
            String np = "";
            try {
                line = reader.readLine();

                while(line != null && line.split("\\^").length==1) {
                    np = np + "\n" + line;
                    line = reader.readLine();
                }

                notePiede = np + "\n" + line.split("\\^")[0];
            } catch (IOException e) {
                return "la colonna note a piede contiene un formato non valido a riga: " + num;
            }

            colonne = line.split("\\^");

            if(colonne.length > 3) {
                return "numero di colonne diverso da " + NUM_COLONNE_VENDITA;
            }

            stato = colonne[1];
            esito = colonne[2];
        } else {
            notePiede = colonne[17];
            stato = colonne[18];
            esito = colonne[19];
        }

        return "OK";
    }

    public String getCliente() {
        return cliente;
    }

    public String getEsito() {
        return esito;
    }

    /********************
     Override
     ********************/
    @Override
    public String toString() {
        return "Vendita{" +
                ", cliente='" + cliente + '\'' +
                ", esito='" + esito + '\'' +
                ", numero=" + numero +
                ", suffisso='" + suffisso + '\'' +
                ", anno=" + anno +
                ", data=" + data +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", codiceFiscale='" + codiceFiscale + '\'' +
                ", partitaIva='" + partitaIva + '\'' +
                ", imponibile=" + imponibile +
                ", tipoCassaPrevidenza='" + tipoCassaPrevidenza + '\'' +
                ", cassaPrevidenza=" + cassaPrevidenza +
                ", imposta=" + imposta +
                ", importoArt15=" + importoArt15 +
                ", bollo=" + bollo +
                ", totale=" + totale +
                ", ritenuta=" + ritenuta +
                ", nettoAPagare=" + nettoAPagare +
                ", notePiede='" + notePiede + '\'' +
                ", stato='" + stato + '\'' +
                '}';
    }
}
