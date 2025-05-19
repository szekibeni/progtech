package app;

import database.Database;
import database.TrainRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        TrainRepository.addTrain("Intercity", 300);
        TrainRepository.addTrain("Express", 200);
        TrainRepository.updateTrainCapacity(1, 350); // Az 1-es ID-jű vonat kapacitásának frissítése
        TrainRepository.deleteTrain(2); // Az ID: 2-es rekord törlése


        try {
            Connection conn = Database.connect();
            if (conn != null) {
                System.out.println("Kapcsolat sikeresen létrejött az adatbázissal!");
            }
        } catch (SQLException e) {
            System.out.println("Hiba történt az adatbázis csatlakozás közben: " + e.getMessage());
        }
        TrainRepository.getAllTrains();

    }
}
