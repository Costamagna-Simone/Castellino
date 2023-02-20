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
                            "UTENTE INTEGER NOT NULL, " +
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

    //elimina utente
    public static void eliminaUtente(int id)    {
        //TODO ELIMINA UTENTE
    }

    //aggiungi una lista di fatture nel db
    public static ArrayList<Fattura> setFatture(ArrayList<Fattura> fatture) {
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
                        "INSERT INTO FATTURA(UTENTE, TIPO, NUMERO, SUFFISSO, ANNO, DATA_, TIPO_DOCUMENTO, " +
                                "CODICE_FISCALE, PARTITA_IVA, IMPONIBILE, TIPO_CASSA_PREVIDENZA, " +
                                "CASSA_PREVIDENZA, IMPOSTA, IMPORTO_ART_15, BOLLO, TOTALE, RITENUTA, " +
                                "NETTO_A_PAGARE, NOTE_PIEDE, STATO, CLIENTE, ESITO) " +
                                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ",
                        Statement.RETURN_GENERATED_KEYS);

                pst[row].setInt(1, f.getUtente());
                pst[row].setInt(2, f.getTipo());
                pst[row].setInt(3, f.getNumero());
                pst[row].setString(4, f.getSuffisso());
                pst[row].setInt(5, f.getAnno());
                pst[row].setDate(6, f.getData());
                pst[row].setString(7, f.getTipoDocumento());
                pst[row].setString(8, f.getCodiceFiscale());
                pst[row].setString(9, f.getPartitaIva());
                pst[row].setDouble(10, f.getImponibile());
                pst[row].setString(11, f.getTipoCassaPrevidenza());
                pst[row].setDouble(12, f.getCassaPrevidenza());
                pst[row].setDouble(13, f.getImposta());
                pst[row].setDouble(14, f.getImportoArt15());
                pst[row].setDouble(15, f.getBollo());
                pst[row].setDouble(16, f.getTotale());
                pst[row].setString(17, f.getRitenuta());
                pst[row].setDouble(18, f.getNettoAPagare());
                pst[row].setString(19, f.getNotePiede());
                pst[row].setString(20, f.getStato());

                if(f.getTipo()==VENDITA)    {
                    Vendita v = (Vendita)f;
                    pst[row].setString(21, v.getCliente());
                    pst[row].setString(22, v.getEsito());
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
                } catch (SQLException e) {
                    System.out.println(e);
                }

                if (idFattura != null)   {
                    fatture.get(i).setId(idFattura);
                    nuoveFatture.add(fatture.get(i));
                } else {
                    System.out.println("null");
                    //TODO dialog id null
                }
            }
        } catch (SQLException e) {
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

    //leggi le fatture dal db
    public static ArrayList<Fattura> getFatture(int idUtente, int tipo_) {
        ArrayList<Fattura> fatture = new ArrayList<>();

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    URL, USER, PASSWORD);

            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM FATTURA WHERE UTENTE=? AND TIPO=?;");

            pst.setInt(1, idUtente);
            pst.setInt(2, tipo_);

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                if (tipo_ == VENDITA) {
                    fatture.add(new Vendita(rs));
                }
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

        return fatture;
    }
}
