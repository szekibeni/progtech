package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection conn;

    public static Connection connect() throws SQLException {
        if (conn == null || conn.isClosed()) {
            String url = "jdbc:mysql://localhost:3306/trains?useSSL=false&serverTimezone=UTC";
            String user = "root";           // Állítsd be a helyes MySQL felhasználónevet
            String password = "";   // Állítsd be a jelszót, vagy hagyd ""-nek, ha nincs

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Sikeres adatbázis csatlakozás.");
            } catch (ClassNotFoundException e) {
                System.out.println("Nem található a MySQL JDBC driver.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Hiba történt az adatbázis csatlakozás közben: " + e.getMessage());
                throw e;
            }
        }
        return conn;
    }
}
