
package com.onlinequiz.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "MySQL JDBC Driver not found.", e
            );
        }
    }

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {

        String host = System.getenv("DB_HOST");
        String port = System.getenv("DB_PORT");
        String database = System.getenv("DB_NAME");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        String url = "jdbc:mysql://" + host + ":" + port
                + "/" + database
                + "?sslMode=REQUIRED&serverTimezone=UTC";

        return DriverManager.getConnection(url, user, password);
    }
}
