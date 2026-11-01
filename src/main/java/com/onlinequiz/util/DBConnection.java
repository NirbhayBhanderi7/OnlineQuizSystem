package com.onlinequiz.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://mysql-3f2db0a-nirbhaybhanderi77-4ba0.g.aivencloud.com:28318/defaultdb?useSSL=true&requireSSL=true&autoReconnect=true&connectTimeout=30000";
    private static final String USER = "avnadmin";
    // Replace YOUR_ACTUAL_PASSWORD with your real Aiven password
    private static final String PASSWORD = "AVNS_f6vUjzNC1MrAPh5CjSM";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
}
