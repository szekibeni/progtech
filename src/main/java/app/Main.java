package app;

import database.Database;
import database.TrainRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = Database.connect();
            if (conn != null) {
                System.out.println("Kapcsolat sikeresen létrejött az adatbázissal!");

                // Add Intercity vonat
                if (TrainRepository.addTrain("Intercity", "IC", 300, "08:00", "12:00")) {
                    System.out.println("Intercity vonat sikeresen hozzáadva.");
                } else {
                    System.out.println("Intercity vonat hozzáadása sikertelen.");
                }

                // Add Express vonat
                if (TrainRepository.addTrain("Express", "Expressz", 200, "10:00", "13:30")) {
                    System.out.println("Express vonat sikeresen hozzáadva.");
                } else {
                    System.out.println("Express vonat hozzáadása sikertelen.");
                }

                // Kapacitás frissítése az 1-es ID-jű vonatnál
                if (TrainRepository.updateTrainCapacity(1, 350)) {
                    System.out.println("Kapacitás frissítése sikeres az 1-es ID-jű vonatnál.");
                } else {
                    System.out.println("Kapacitás frissítése sikertelen az 1-es ID-jű vonatnál.");
                }

                // Törlés a 2-es ID-jű vonatnál
                if (TrainRepository.deleteTrain(2)) {
                    System.out.println("A 2-es ID-jű vonat sikeresen törölve.");
                } else {
                    System.out.println("A 2-es ID-jű vonat törlése sikertelen.");
                }

                System.out.println("Az összes vonat:");
                TrainRepository.getAllTrainsPrint();
            }
        } catch (SQLException e) {
            System.out.println("Hiba történt az adatbázis csatlakozás közben: " + e.getMessage());
        }
    }
}