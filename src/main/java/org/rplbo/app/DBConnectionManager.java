package org.rplbo.app;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionManager {
    // TODO 1 : CONNECT KE DATABASE (Masukan Path DB)
    private static final String DB_URL = "jdbc:sqlite:Asylum.db";
    private static Connection connection;

    private DBConnectionManager() {
        // Private constructor to prevent instantiation
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // TODO 2 : getConnection jika connection null/closed (gunakan DriverManager.getConnection)
                connection = DriverManager.getConnection(DB_URL);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return connection
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    }