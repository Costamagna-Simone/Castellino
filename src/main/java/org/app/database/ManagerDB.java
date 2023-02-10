package org.app.database;

import org.app.model.Utente;
import org.hsqldb.server.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.app.database.Constants.*;

public class ManagerDB {
    private static Server hsqlServer = null;


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
                                    "ID INTEGER not null IDENTITY, " +
                                    "NOME varchar(50) not null, " +
                                    "COGNOME varchar(50) not null," +
                                    "PRIMARY KEY (ID)" +
                                    ");").execute();
        }finally {
            if(connection != null) {
                connection.close();
            }
        }
    }

    public static ArrayList<Utente> getUtenti() {
        ArrayList<Utente> utenti = new ArrayList<Utente>();

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    URL, USER, PASSWORD);

            ResultSet rs = connection.prepareStatement(
                    "select * from UTENTE;").executeQuery();

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
