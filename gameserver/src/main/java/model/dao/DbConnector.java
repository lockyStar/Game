package model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Alex on 07.11.2016.
 */
public class DbConnector {
    private static final Logger log = LogManager.getLogger(DbConnector.class);

    private static final String URL_TEMPLATE = "jdbc:postgresql://%s:%d/%s";
    private static final String URL;
    private static final String HOST = "54.224.37.210";
    private static final int PORT = 5432;
    private static final String DB_NAME = "atom16_tinderdb";
    private static final String USER = "atom16";
    private static final String PASSWORD = "atom16";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            log.error("Failed to load jdbc driver.", e);
            System.exit(-1);
        }

        URL = String.format(URL_TEMPLATE, HOST, PORT, DB_NAME);
        log.info("Success. DbConnector init.");
    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private DbConnector() { }
}

