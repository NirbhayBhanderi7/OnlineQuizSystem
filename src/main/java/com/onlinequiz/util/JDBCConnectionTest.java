package com.onlinequiz.util;

import java.sql.Connection;

/** Simple console test for the Advance Java practical submission. */
public class JDBCConnectionTest {
    public static void main(String[] args) {
        System.out.println("Online Quiz System - JDBC Test");
        try (Connection connection = DBConnection.getConnection()) {
            System.out.println("MySQL JDBC Driver Loaded");
            System.out.println("Database Connected Successfully");
            System.out.println("Database: online_quiz_db");
            System.out.println("Connection Status: SUCCESS");
        } catch (Exception e) {
            System.out.println("Connection Status: FAILED");
            System.out.println("Reason: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
