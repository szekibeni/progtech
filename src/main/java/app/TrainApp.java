package app;

import database.TrainRepository;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Train;

import java.util.List;

public class TrainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        TableView<Train> tableView = new TableView<>();

        // Oszlopok definiálása
        TableColumn<Train, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("trainId"));
        idColumn.setPrefWidth(50);

        TableColumn<Train, String> nameColumn = new TableColumn<>("Név");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("trainName"));
        nameColumn.setPrefWidth(150);

        TableColumn<Train, String> typeColumn = new TableColumn<>("Típus");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("trainType"));
        typeColumn.setPrefWidth(100);

        TableColumn<Train, Integer> capacityColumn = new TableColumn<>("Kapacitás");
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        capacityColumn.setPrefWidth(100);

        TableColumn<Train, String> departureColumn = new TableColumn<>("Indulás");
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        departureColumn.setPrefWidth(120);

        TableColumn<Train, String> arrivalColumn = new TableColumn<>("Érkezés");
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        arrivalColumn.setPrefWidth(120);

        tableView.getColumns().addAll(idColumn, nameColumn, typeColumn, capacityColumn, departureColumn, arrivalColumn);

        // Adatok betöltése az adatbázisból
        List<Train> trainList = TrainRepository.getAllTrains();
        ObservableList<Train> trains = FXCollections.observableArrayList(trainList);
        tableView.setItems(trains);

        VBox vbox = new VBox(tableView);

        Scene scene = new Scene(vbox, 650, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Vonatok listája");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
