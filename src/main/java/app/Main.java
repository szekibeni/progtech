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

                if (TrainRepository.addTrain("Intercity", 300)) {
                    System.out.println("Intercity vonat sikeresen hozzáadva.");
                } else {
                    System.out.println("Intercity vonat hozzáadása sikertelen.");
                }

                if (TrainRepository.addTrain("Express", 200)) {
                    System.out.println("Express vonat sikeresen hozzáadva.");
                } else {
                    System.out.println("Express vonat hozzáadása sikertelen.");
                }

                if (TrainRepository.updateTrainCapacity(1, 350)) {
                    System.out.println("Kapacitás frissítése sikeres az 1-es ID-jű vonatnál.");
                } else {
                    System.out.println("Kapacitás frissítése sikertelen az 1-es ID-jű vonatnál.");
                }

                if (TrainRepository.deleteTrain(2)) {
                    System.out.println("A 2-es ID-jű vonat sikeresen törölve.");
                } else {
                    System.out.println("A 2-es ID-jű vonat törlése sikertelen.");
                }

                System.out.println("Az összes vonat:");
                TrainRepository.getAllTrains();
            }
        } catch (SQLException e) {
            System.out.println("Hiba történt az adatbázis csatlakozás közben: " + e.getMessage());
        }
    }
}