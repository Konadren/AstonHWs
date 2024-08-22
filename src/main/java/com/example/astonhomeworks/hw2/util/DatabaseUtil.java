package com.example.astonhomeworks.hw2.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String url = "jdbc:postgresql://localhost:5432/AstonHW2";
    private static final String user = "postgres";
    private static final String password = "postgres";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(url, user, password);
    }
}
