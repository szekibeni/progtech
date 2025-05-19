package database;

import java.sql.*;

public class TrainRepository {

    public static void addTrain(String name, int capacity) {
        String sql = "INSERT INTO trains (name, capacity) VALUES (?, ?)"; // SQL beszúrási utasítás

        try (Connection conn = Database.connect(); // kapcsolat létrehozása
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // PreparedStatement használata SQL lekérdezéshez
            pstmt.setString(1, name); // az első helyettesítő (?)
            pstmt.setInt(2, capacity); // a második helyettesítő (?)

            pstmt.executeUpdate(); // végrehajtás
            System.out.println("Új vonat sikeresen hozzáadva!");
        } catch (SQLException e) {
            System.out.println("Hiba történt a vonat hozzáadásakor: " + e.getMessage());
        }
    }
    public static void getAllTrains() {
        String sql = "SELECT * FROM trains"; // Lekérdezés a tábla összes rekordjára

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int capacity = rs.getInt("capacity");
                System.out.println("ID: " + id + ", Name: " + name + ", Capacity: " + capacity);
            }

        } catch (SQLException e) {
            System.out.println("Hiba történt az adatok lekérdezésekor: " + e.getMessage());
        }
    }
    public static void updateTrainCapacity(int id, int newCapacity) {
        String sql = "UPDATE trains SET capacity = ? WHERE id = ?"; // SQL frissítési utasítás

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newCapacity); // új kapacitás
            pstmt.setInt(2, id); // frissítendő sor ID-je

            pstmt.executeUpdate();
            System.out.println("Kapacitás sikeresen frissítve!");
        } catch (SQLException e) {
            System.out.println("Hiba történt a kapacitás frissítésekor: " + e.getMessage());
        }
    }
    public static void deleteTrain(int id) {
        String sql = "DELETE FROM trains WHERE id = ?"; // SQL törlési utasítás

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id); // törlendő rekord ID-je

            pstmt.executeUpdate();
            System.out.println("Rekord sikeresen törölve!");
        } catch (SQLException e) {
            System.out.println("Hiba történt a rekord törlésekor: " + e.getMessage());
        }
    }


}
