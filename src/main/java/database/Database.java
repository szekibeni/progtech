package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection conn;

    public static Connection connect() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:sqlite:trains");
        }
        return conn;
    }
}
