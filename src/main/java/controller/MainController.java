package controller;

import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Train;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private TableView<Train> tableView;
    @FXML
    private TableColumn<Train, String> trainNameColumn;
    @FXML
    private TableColumn<Train, String> trainTypeColumn;
    @FXML
    private TableColumn<Train, Integer> capacityColumn;
    @FXML
    private TableColumn<Train, String> departureColumn;
    @FXML
    private TableColumn<Train, String> arrivalColumn;


    public List<Train> getAllTrains() {
        List<Train> trains = new ArrayList<>();
        String query = "SELECT * FROM trains";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Train train = new Train();
                train.setTrainId(rs.getInt("train_id"));
                train.setTrainName(rs.getString("train_name"));
                train.setTrainType(rs.getString("train_type"));
                train.setCapacity(rs.getInt("capacity"));
                train.setDepartureTime(rs.getString("departure_time"));
                train.setArrivalTime(rs.getString("arrival_time"));


                trains.add(train);
            }

        } catch (SQLException e) {
            System.out.println("Hiba történt az adatok lekérdezésekor: " + e.getMessage());
        }

        return trains;
    }

    public boolean addTrain(Train train) {
        String query = "INSERT INTO trains (train_name, train_type, capacity, departure_time, arrival_time) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, train.getTrainName());
            pstmt.setString(2, train.getTrainType());
            pstmt.setInt(3, train.getCapacity());
            pstmt.setString(4, train.getDepartureTime());
            pstmt.setString(5, train.getArrivalTime());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Hiba történt a vonat hozzáadásakor: " + e.getMessage());
            return false;
        }
    }

    public boolean updateTrain(Train train) {
        String query = "UPDATE trains SET train_name = ?, train_type = ?, capacity = ?, departure_time = ?, arrival_time = ? WHERE train_id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, train.getTrainName());
            pstmt.setString(2, train.getTrainType());
            pstmt.setInt(3, train.getCapacity());
            pstmt.setString(4, train.getDepartureTime());
            pstmt.setString(5, train.getArrivalTime());
            pstmt.setInt(6, train.getTrainId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Hiba történt a vonat frissítésekor: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteTrain(int trainId) {
        String query = "DELETE FROM trains WHERE train_id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, trainId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Hiba történt a rekord törlésekor: " + e.getMessage());
            return false;
        }
    }

    @FXML
    public void initialize() {

        trainNameColumn.setCellValueFactory(new PropertyValueFactory<>("trainName"));
        trainTypeColumn.setCellValueFactory(new PropertyValueFactory<>("trainType"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));


        List<Train> trains = getAllTrains();
        ObservableList<Train> observableTrains = FXCollections.observableArrayList(trains);


        tableView.setItems(observableTrains);
    }
}