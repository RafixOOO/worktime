package org.example.worktime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String DB_URL = "jdbc:sqlserver://10.100.100.48:49827;databaseName=PartCheck";;
    private static final String USER = "Sa";
    private static final String PASSWORD = "Shark1445NE$T";

    public void saveNumber(String number) {
        String query = "INSERT INTO worktime (RFID) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, number);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
