package org.app.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.app.utilities.Constants.*;

public class Acquisto extends Fattura {
    private String numeroRif;

    private Date dataRif;

    private String fornitore;
    public Acquisto()   {
        super(ACQUISTO);
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

        for(int i=0; i<colonne.length; i++) {
            System.out.println(colonne[i]);
        }

        System.out.println(colonne.length);

        if(colonne.length != NUM_COLONNE_ACQUISTO && colonne.length != NUM_COLONNE_ACQUISTO-2)   {
            return "numero di colonne diverso da " + NUM_COLONNE_ACQUISTO;
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
                data = new java.sql.Date((new SimpleDateFormat("dd/MM/yyyy").parse(colonne[3])).getTime());
        } catch (ParseException e) {
            return "la colonna data contiene un formato diverso da dd/MM/yyyy a riga: " + num;
        }

        numeroRif = colonne[4];

        try {
            if(!colonne[5].equals(""))
                dataRif = new java.sql.Date((new SimpleDateFormat("dd/MM/yyyy").parse(colonne[5])).getTime());
        } catch (ParseException e) {
            return "la colonna data contiene un formato diverso da dd/MM/yyyy a riga: " + num;
        }

        tipoDocumento = colonne[6];
        fornitore = colonne[7];
        codiceFiscale = colonne[8];
        partitaIva = colonne[9];

        try {
            if(!colonne[10].equals(""))  {
                imponibile = fromStringToDouble(colonne[10]);
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna imponibile contiene un formato non valido a riga: " + num;
        }

        tipoCassaPrevidenza = colonne[11];

        try {
            if(!colonne[12].equals(""))  {
                cassaPrevidenza = fromStringToDouble(colonne[12]);
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna cassa previdenza contiene un formato non valido a riga: " + num;
        }

        try {
            if(!colonne[13].equals(""))  {
                imposta = fromStringToDouble(colonne[13]);
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna imposta contiene un formato non valido a riga: " + num;
        }

        try {
            if(!colonne[14].equals(""))  {
                importoArt15 = fromStringToDouble(colonne[14]);
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna importo Art. 15 contiene un formato non valido a riga: " + num;
        }

        try {
            if(!colonne[15].equals(""))  {
                bollo = fromStringToDouble(colonne[15]);
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna bollo contiene un formato non valido a riga: " + num;
        }

        try {
            if(!colonne[16].equals(""))  {
                totale = fromStringToDouble(colonne[16]);
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna totale contiene un formato non valido a riga: " + num;
        }

        ritenuta = colonne[17];

        try {
            if(!colonne[18].equals(""))  {
                nettoAPagare = fromStringToDouble(colonne[18]);
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna netto a pagare contiene un formato non valido a riga: " + num;
        }

        if(colonne.length==20)    {
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

            if(colonne.length > 2) {
                System.out.println("entro qui");
                return "numero di colonne diverso da " + NUM_COLONNE_VENDITA;
            }

            stato = colonne[1];
        } else {
            notePiede = colonne[19];
            stato = colonne[20];
        }

        return "OK";
    }
}
