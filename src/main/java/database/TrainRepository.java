package database;

import model.Train;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainRepository {

    public static boolean addTrain(String trainName, int capacity) {
        String sql = "INSERT INTO trains (train_name, capacity) VALUES (?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trainName);
            pstmt.setInt(2, capacity);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Hiba történt a vonat hozzáadásakor: " + e.getMessage());
            return false;
        }
    }

    public static void getAllTrainsPrint() {
        String sql = "SELECT * FROM trains";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("train_id");
                String name = rs.getString("train_name");
                String type = rs.getString("train_type");
                int capacity = rs.getInt("capacity");
                String departure = rs.getString("departure_time");
                String arrival = rs.getString("arrival_time");

                System.out.println("ID: " + id + ", Név: " + name + ", Típus: " + type + ", Kapacitás: " + capacity +
                        ", Indulás: " + departure + ", Érkezés: " + arrival);
            }

        } catch (SQLException e) {
            System.out.println("Hiba történt az adatok lekérdezésekor: " + e.getMessage());
        }
    }

    public static boolean updateTrainCapacity(int trainId, int newCapacity) {
        String sql = "UPDATE trains SET capacity = ? WHERE train_id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newCapacity);
            pstmt.setInt(2, trainId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Kapacitás sikeresen frissítve!");
                return true;
            } else {
                System.out.println("Nem található vonat a megadott ID-val: " + trainId);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Hiba történt a kapacitás frissítésekor: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteTrain(int trainId) {
        String sql = "DELETE FROM trains WHERE train_id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Rekord sikeresen törölve!");
                return true;
            } else {
                System.out.println("Nem található rekord a megadott ID-val: " + trainId);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Hiba történt a rekord törlésekor: " + e.getMessage());
            return false;
        }
    }

    public static List<Train> getAllTrains() {
        List<Train> trains = new ArrayList<>();
        String sql = "SELECT * FROM trains";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Train train = new Train();
                train.setTrainId(rs.getInt("train_id"));
                train.setTrainName(rs.getString("train_name"));
                train.setTrainType(rs.getString("train_type"));
                train.setCapacity(rs.getInt("capacity"));
                train.setDepartureTime(rs.getString("departure_time"));
                train.setArrivalTime(rs.getString("arrival_time"));
                // Ezt kezeljük try-catch-ben, ha nincs created_at mező
                try {
                    train.setCreatedAt(rs.getString("created_at"));
                } catch (SQLException e) {
                    train.setCreatedAt(null);
                }

                trains.add(train);
            }

        } catch (SQLException e) {
            System.out.println("Hiba történt az adatok lekérdezésekor: " + e.getMessage());
        }

        return trains;
    }

    public static Train addDummyTrain() {
        boolean success = addTrain("Új Vonat", 100);
        if (success) {
            List<Train> list = getAllTrains();
            return list.get(list.size() - 1); // legutóbbi
        }
        return null;
    }


    // --- EZ EGY SEGÉD METÓDUS TESZTELÉSHEZ, KIÍRJA A KONZOLRA AZ ÖSSZES VONATOT ---
    public static void testPrintAllTrains() {
        List<Train> trains = getAllTrains();
        if (trains.isEmpty()) {
            System.out.println("Nincs vonat az adatbázisban.");
        } else {
            for (Train t : trains) {
                System.out.println(t);
            }
        }
    }
}