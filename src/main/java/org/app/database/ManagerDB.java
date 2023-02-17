package org.app.database;

import org.app.model.Utente;
import org.hsqldb.server.Server;
import java.sql.*;
import java.util.ArrayList;
import static org.app.database.Constants.*;

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
