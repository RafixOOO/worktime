package org.example.worktime;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public void finishtime() {

        Timestamp currentTimestamp = Timestamp.valueOf(LocalDateTime.now());

        String query1 = "Select czasod from worktime where czasdo is NULL";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query1)) {

            // Wykonanie zapytania i pobranie wyników
            ResultSet rs = stmt.executeQuery();

            // Iterowanie przez wyniki i wypisywanie wartości 'czasod'
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDate currentDate = currentDateTime.toLocalDate();  // Tylko data

            while (rs.next()) {
                Timestamp czasodTimestamp = rs.getTimestamp("czasod");
                if (czasodTimestamp != null) {
                    LocalDateTime czasod = czasodTimestamp.toLocalDateTime();
                    LocalDate czasodDate = czasod.toLocalDate();  // Tylko data

                    // Sprawdzenie, czy dni są różne
                    if (!currentDate.equals(czasodDate)) {
                        // Wypisz datę z mniejszym dniem i ustaw godzinę na 22:45
                        LocalDateTime adjustedDateTime;
                        if (czasodDate.isBefore(currentDate)) {
                            adjustedDateTime = czasodDate.atTime(22, 45);
                        } else {
                            adjustedDateTime = currentDate.atTime(22, 45);
                        }

                        // Konwersja na Timestamp
                        currentTimestamp = Timestamp.valueOf(adjustedDateTime);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query = "Update worktime Set czasdo=? where czasdo is NULL";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Ustawienie aktualnej daty i godziny jako parametr
            stmt.setTimestamp(1, currentTimestamp);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
