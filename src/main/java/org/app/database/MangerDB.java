package org.app.database;

import org.app.model.Utente;
import org.hsqldb.server.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class MangerDB {

    public static void startDatabase()  {
        Server hsqlServer = null;

        try {
            hsqlServer = new Server();

            // HSQLDB prints out a lot of informations when
            // starting and closing, which we don't need now.
            // Normally you should point the setLogWriter
            // to some Writer object that could store the logs.
            hsqlServer.setLogWriter(null);
            hsqlServer.setSilent(true);

            // The actual database will be named 'xdb' and its
            // settings and data will be stored in files
            // testdb.properties and testdb.script
            hsqlServer.setDatabaseName(0, "castellinoDB");
            hsqlServer.setDatabasePath(0, "file:castellinoDB");

            // Start the database!
            hsqlServer.start();

            Connection connection = null;
            // We have here two 'try' blocks and two 'finally'
            // blocks because we have two things to close
            // after all - HSQLDB server and connection
            try {
                // Getting a connection to the newly started database
                Class.forName("org.hsqldb.jdbcDriver");
                // Default user of the HSQLDB is 'sa'
                // with an empty password
                connection = DriverManager.getConnection(
                        "jdbc:hsqldb:hsql://localhost/xdb", "sa", "");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                // Closing the connection
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        } finally {
            // Closing the server
            if (hsqlServer != null) {
                hsqlServer.stop();
            }
        }
    }

    public static ArrayList<Utente> getUtenti()   {
        ArrayList<Utente> utenti = new ArrayList<Utente>();

        utenti.add(new Utente("Alessio", "Silivestro"));
        utenti.add(new Utente("Simone", "Costamagna"));
        utenti.add(new Utente("Luca", "Beccaria"));
        utenti.add(new Utente("Samuele", "Mandaglio"));

        return utenti;
    }
}
