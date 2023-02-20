package org.app.database;

import org.app.model.Fattura;
import org.app.model.Utente;
import org.app.model.Vendita;
import org.hsqldb.server.Server;
import java.sql.*;
import java.util.ArrayList;
import static org.app.database.Constants.*;
import static org.app.utilities.Constants.VENDITA;

public class ManagerDB {
    private static Server hsqlServer = null;


    /********************
     Utility
     ********************/

    //avvia database
    public static void avviaDatabase() {
        hsqlServer = new Server();

        hsqlServer.setLogWriter(null);

        hsqlServer.setDatabaseName(0, NAMEDB);
        hsqlServer.setDatabasePath(0, PATHDB);

        hsqlServer.start();

        try {
            inizializzaDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //ferma database
    public static void stopDatabase()   {
        if(hsqlServer!=null)
            hsqlServer.stop();
    }


    //inizializza le tabelle del database se non esistono
    private static void inizializzaDatabase() throws SQLException {
        Connection connection = null;

        try{
            connection = DriverManager.getConnection(
                    URL, USER, PASSWORD);

            //inizializza tabella utente
            connection.prepareStatement(
                            "CREATE TABLE IF NOT EXISTS UTENTE(" +
                                    "ID INTEGER NOT NULL IDENTITY, " +
                                    "NOME VARCHAR(50) NOT NULL, " +
                                    "COGNOME VARCHAR(50) NOT NULL," +
                                    "PRIMARY KEY (ID)" +
                                    ");").execute();

            //inizializza tabella fatture
            connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS FATTURA(" +
                            "ID INTEGER NOT NULL IDENTITY, " +
                            "TIPO INTEGER NOT NULL, " +
                            "NUMERO INTEGER, " +
                            "SUFFISSO VARCHAR(30), " +
                            "ANNO INTEGER, " +
                            "DATA_ DATE, " +
                            "TIPO_DOCUMENTO VARCHAR(30), " +
                            "CODICE_FISCALE VARCHAR(20), " +
                            "PARTITA_IVA VARCHAR(20), " +
                            "IMPONIBILE DOUBLE, " +
                            "TIPO_CASSA_PREVIDENZA VARCHAR(50), " +
                            "CASSA_PREVIDENZA DOUBLE, " +
                            "IMPOSTA DOUBLE, " +
                            "IMPORTO_ART_15 DOUBLE, " +
                            "BOLLO DOUBLE, " +
                            "TOTALE DOUBLE, " +
                            "RITENUTA VARCHAR(20), " +
                            "NETTO_A_PAGARE DOUBLE, " +
                            "NOTE_PIEDE VARCHAR(1000), " +
                            "STATO VARCHAR(20), " +

                            /*VENDITA*/
                            "CLIENTE VARCHAR(50)," +
                            "ESITO VARCHAR(50)," +

                            /*ACQUISTO*/
                            "NUMERO_RIF VARCHAR(50)," +
                            "DATA_RIF DATE," +
                            "FORNITORE VARCHAR(50)," +

                            "PRIMARY KEY (ID)" +
                            ");").execute();
        }finally {
            if(connection != null) {
                connection.close();
            }
        }
    }

    //aggiungi un utente nel db
    public static Utente aggiungiUtente(String nome, String cognome) {
        Connection connection = null;
        PreparedStatement pst = null;
        Integer idUtente = null;

        try {
            connection = DriverManager.getConnection(
                    URL, USER, PASSWORD);

            pst = connection.prepareStatement(
                    "INSERT INTO UTENTE(NOME, COGNOME) " +
                            "values (?, ?);", Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, nome);
            pst.setString(2, cognome);
            pst.executeUpdate();

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idUtente = generatedKeys.getInt(1);
                }
            }

            if (idUtente != null)
                return new Utente(idUtente, nome, cognome);
            else
                return null;
        } catch (SQLException e) {
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //modifica un utente
    public static Utente modificaUtente(int id, String nome, String cognome) {
        Connection connection = null;
        PreparedStatement pst = null;
        Integer idUtente = null;

        try {
            connection = DriverManager.getConnection(
                    URL, USER, PASSWORD);

            pst = connection.prepareStatement(
                    "UPDATE UTENTE " +
                            "SET nome = ?," +
                            "cognome = ?" +
                            "WHERE id = ?", Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, nome);
            pst.setString(2, cognome);
            pst.setInt(3, id);
            pst.executeUpdate();

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idUtente = generatedKeys.getInt(1);
                }
            }

            if (idUtente != null)
                return new Utente(idUtente, nome, cognome);
            else
                return null;
        } catch (SQLException e) {
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //aggiungi una lista di fatture nel db
    public static ArrayList<Fattura> aggiungiFatture(ArrayList<Fattura> fatture) {
        ArrayList<Fattura> nuoveFatture = new ArrayList<>();

        Connection connection = null;
        PreparedStatement[] pst = new PreparedStatement[fatture.size()];
        Integer idFattura = null;
        int row = 0;

        try {
            connection = DriverManager.getConnection(
                    URL, USER, PASSWORD);

            connection.setAutoCommit(false);

            for(Fattura f : fatture)    {
                pst[row] = connection.prepareStatement(
                        "INSERT INTO FATTURA(TIPO, NUMERO, SUFFISSO, ANNO, DATA_, TIPO_DOCUMENTO, " +
                                "CODICE_FISCALE, PARTITA_IVA, IMPONIBILE, TIPO_CASSA_PREVIDENZA, " +
                                "CASSA_PREVIDENZA, IMPOSTA, IMPORTO_ART_15, BOLLO, TOTALE, RITENUTA, " +
                                "NETTO_A_PAGARE, NOTE_PIEDE, STATO, CLIENTE, ESITO) " +
                                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ",
                        Statement.RETURN_GENERATED_KEYS);

                pst[row].setInt(1, f.getTipo());
                pst[row].setInt(2, f.getNumero());
                pst[row].setString(3, f.getSuffisso());
                pst[row].setInt(4, f.getAnno());
                pst[row].setDate(5, f.getData());
                pst[row].setString(6, f.getTipoDocumento());
                pst[row].setString(7, f.getCodiceFiscale());
                pst[row].setString(8, f.getPartitaIva());
                pst[row].setDouble(9, f.getImponibile());
                pst[row].setString(10, f.getTipoCassaPrevidenza());
                pst[row].setDouble(11, f.getCassaPrevidenza());
                pst[row].setDouble(12, f.getImposta());
                pst[row].setDouble(13, f.getImportoArt15());
                pst[row].setDouble(14, f.getBollo());
                pst[row].setDouble(15, f.getTotale());
                pst[row].setString(16, f.getRitenuta());
                pst[row].setDouble(17, f.getNettoAPagare());
                pst[row].setString(18, f.getNotePiede());
                pst[row].setString(19, f.getStato());

                if(f.getTipo()==VENDITA)    {
                    Vendita v = (Vendita)f;
                    pst[row].setString(20, v.getCliente());
                    pst[row].setString(21, v.getEsito());
                } else {
                    //TODO PER ACQUISTO
                }

                pst[row].executeUpdate();

                row++;
            }

            connection.commit();

            for(int i=0; i<row; i++)    {
                try(ResultSet generatedKeys = pst[i].getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idFattura = generatedKeys.getInt(1);
                    }
                }

                if (idFattura != null)   {
                    fatture.get(i).setId(idFattura);
                    nuoveFatture.add(fatture.get(i));
                } else {
                    System.out.println("Id null");
                }

                nuoveFatture.add(fatture.get(i));
            }
        } catch (SQLException e) {
            System.out.println("Errore riga " + row + " - " + e);
            if(connection != null)  {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            nuoveFatture.clear();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return nuoveFatture;
    }

    //leggi gli utenti dal db
    public static ArrayList<Utente> getUtenti() {
        ArrayList<Utente> utenti = new ArrayList<Utente>();

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    URL, USER, PASSWORD);

            ResultSet rs = connection.prepareStatement(
                    "SELECT * FROM UTENTE;").executeQuery();

            while(rs.next())    {
                utenti.add(new Utente(rs.getInt("ID"), rs.getString("NOME"), rs.getString("COGNOME")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return utenti;
    }
}
