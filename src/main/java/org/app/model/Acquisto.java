package org.app.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.app.utilities.Constants.*;

public class Acquisto extends Fattura {
    private String numeroRif;
    private Date dataRif;
    private String fornitore;
    public Acquisto()   {
        super(ACQUISTO);
    }

    public Acquisto(ResultSet rs)    {
        super(VENDITA);

        try {
            id = rs.getInt("ID");
            utente = rs.getInt("UTENTE");
            tipo = rs.getInt("TIPO");
            numero = rs.getInt("NUMERO");
            suffisso = rs.getString("SUFFISSO");
            anno = rs.getInt("ANNO");
            data = rs.getDate("DATA_");
            tipoDocumento = rs.getString("TIPO_DOCUMENTO");
            codiceFiscale = rs.getString("CODICE_FISCALE");
            partitaIva = rs.getString("PARTITA_IVA");
            imponibile = rs.getDouble("IMPONIBILE");
            tipoCassaPrevidenza = rs.getString("TIPO_CASSA_PREVIDENZA");
            cassaPrevidenza = rs.getDouble("CASSA_PREVIDENZA");
            imposta = rs.getDouble("IMPOSTA");
            importoArt15 = rs.getDouble("IMPORTO_ART_15");
            bollo = rs.getDouble("BOLLO");
            totale = rs.getDouble("TOTALE");
            ritenuta = rs.getString("RITENUTA");
            nettoAPagare = rs.getDouble("NETTO_A_PAGARE");
            notePiede = rs.getString("NOTE_PIEDE");
            stato = rs.getString("STATO");
            numeroRif = rs.getString("NUMERO_RIF");
            fornitore = rs.getString("FORNITORE");
            dataRif = rs.getDate("DATA_RIF");
        } catch (SQLException e) {
            //TODO ERRORE
            throw new RuntimeException(e);
        }
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

        try {
            if(!colonne[0].equals(""))
                numero = Integer.parseInt(colonne[0]);
        } catch(NumberFormatException ex)   {
            return "la colonna numero contiene un formato non valido a riga: " + num;
        } catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            numero = 0;
        }

        try {
            suffisso = colonne[1];
        } catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            suffisso = "";
        }

        try {
            if(!colonne[2].equals(""))
                anno = Integer.parseInt(colonne[2]);
        } catch(NumberFormatException ex)   {
            return "la colonna anno contiene un formato non valido a riga: " + num;
        }  catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            anno = 0;
        }

        try {
            if(!colonne[3].equals(""))
                data = new java.sql.Date((new SimpleDateFormat("dd/MM/yyyy").parse(colonne[3])).getTime());
        } catch (ParseException e) {
            return "la colonna data contiene un formato diverso da dd/MM/yyyy a riga: " + num;
        }  catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            data = null;
        }

        try {
            numeroRif = colonne[4];
        } catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            numeroRif = "";
        }


        try {
            if(!colonne[5].equals(""))
                dataRif = new java.sql.Date((new SimpleDateFormat("dd/MM/yyyy").parse(colonne[5])).getTime());
        } catch (ParseException e) {
            return "la colonna data contiene un formato diverso da dd/MM/yyyy a riga: " + num;
        }  catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            dataRif = null;
        }

        try {
            tipoDocumento = colonne[6];
        }  catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            tipoDocumento = "";
        }

        try {
            fornitore = colonne[7];
        }  catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            fornitore = "";
        }

        try {
            codiceFiscale = colonne[8];
        } catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            codiceFiscale = "";
        }

        try {
            partitaIva = colonne[9];
        }  catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            partitaIva = "";
        }

        try {
            if(!colonne[10].equals(""))  {
                imponibile = fromStringToDouble(colonne[10]);
            } else {
                imponibile = 0.0;
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna imponibile contiene un formato non valido a riga: " + num;
        }  catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            imponibile = 0.0;
        }

        try {
            tipoCassaPrevidenza = colonne[11];
        } catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            tipoCassaPrevidenza = "";
        }

        try {
            if(!colonne[12].equals(""))  {
                cassaPrevidenza = fromStringToDouble(colonne[12]);
            } else {
                cassaPrevidenza = 0.0;
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna cassa previdenza contiene un formato non valido a riga: " + num;
        }  catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            cassaPrevidenza = 0.0;
        }

        try {
            if(!colonne[13].equals(""))  {
                imposta = fromStringToDouble(colonne[13]);
            } else {
                imposta = 0.0;
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna imposta contiene un formato non valido a riga: " + num;
        }  catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            imposta = 0.0;
        }

        try {
            if(!colonne[14].equals(""))  {
                importoArt15 = fromStringToDouble(colonne[14]);
            } else {
                importoArt15 = 0.0;
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna importo Art. 15 contiene un formato non valido a riga: " + num;
        }  catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            importoArt15 = 0.0;
        }

        try {
            if(!colonne[15].equals(""))  {
                bollo = fromStringToDouble(colonne[15]);
            } else {
                bollo = 0.0;
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna bollo contiene un formato non valido a riga: " + num;
        } catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            bollo = 0.0;
        }

        try {
            if(!colonne[16].equals(""))  {
                totale = fromStringToDouble(colonne[16]);
            } else {
                totale = 0.0;
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna totale contiene un formato non valido a riga: " + num;
        }  catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            totale = 0.0;
        }

        try {
            ritenuta = colonne[17];
        }   catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            ritenuta = "";
        }

        try {
            if(!colonne[18].equals(""))  {
                nettoAPagare = fromStringToDouble(colonne[18]);
            } else {
                nettoAPagare = 0.0;
            }
        } catch (NumberFormatException | ParseException ex) {
            return "la colonna netto a pagare contiene un formato non valido a riga: " + num;
        } catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
            nettoAPagare = 0.0;
        }

        if(colonne.length==19)    {
            String np = "";
            try {
                line = reader.readLine();

                while(line != null && line.split("\\^").length==1) {
                    np = np + "\n" + line;
                    line = reader.readLine();
                }

                if (line != null) {
                    notePiede = np + "\n" + line.split("\\^")[0];
                }
            } catch (IOException e) {
                return "la colonna note a piede contiene un formato non valido a riga: " + num;
            }

            colonne = line.split("\\^");

            if(colonne.length > 2) {
                return "A riga " + num + " numero di colonne diverso da " + NUM_COLONNE_ACQUISTO;
            }

            stato = colonne[1];
        } else {
            try {
                notePiede = colonne[19];
            }  catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
                notePiede = "";
            }

            try {
                stato = colonne[20];
            }  catch(NullPointerException | ArrayIndexOutOfBoundsException ex)    {
                stato = "";
            }
        }

        return "OK";
    }

    public String getNumeroRif()    {
        return numeroRif;
    }

    public Date getDataRif()    {
        return dataRif;
    }

    public String getFornitore()    {
        return fornitore;
    }
}
