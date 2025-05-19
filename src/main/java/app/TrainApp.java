package app;

import database.TrainRepository;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Train;

import java.util.List;

public class TrainApp extends Application {

    private static boolean isAdmin;

    public TrainApp() {}

    public TrainApp(boolean isAdmin) {
        TrainApp.isAdmin = isAdmin;
    }

    @Override
    public void start(Stage primaryStage) {
        TableView<Train> tableView = new TableView<>();

        TableColumn<Train, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("trainId"));

        TableColumn<Train, String> nameColumn = new TableColumn<>("Név");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("trainName"));

        TableColumn<Train, String> typeColumn = new TableColumn<>("Típus");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("trainType"));

        TableColumn<Train, Integer> capacityColumn = new TableColumn<>("Kapacitás");
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        TableColumn<Train, String> departureColumn = new TableColumn<>("Indulás");
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));

        TableColumn<Train, String> arrivalColumn = new TableColumn<>("Érkezés");
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        tableView.getColumns().addAll(idColumn, nameColumn, typeColumn, capacityColumn, departureColumn, arrivalColumn);

        // Betöltjük az adatokat
        List<Train> trainList = TrainRepository.getAllTrains();
        ObservableList<Train> trainData = FXCollections.observableArrayList(trainList);
        FilteredList<Train> filteredData = new FilteredList<>(trainData, p -> true);
        tableView.setItems(filteredData);

        // Vissza gomb
        Button backButton = new Button("Vissza");
        backButton.setOnAction(e -> {
            new LoginWindow().start(new Stage());
            primaryStage.close();
        });

        // Szűrőmező
        TextField filterField = new TextField();
        filterField.setPromptText("Keresés név vagy típus szerint...");
        filterField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(train ->
                    train.getTrainName().toLowerCase().contains(newVal.toLowerCase()) ||
                            train.getTrainType().toLowerCase().contains(newVal.toLowerCase())
            );
        });

        HBox topBar = new HBox(10, backButton, filterField);

        // Admin gombok
        if (isAdmin) {
            Button addButton = new Button("➕ Hozzáadás");
            Button deleteButton = new Button("🗑️ Törlés");

            addButton.setOnAction(e -> {
                // Ide jöhet vonat hozzáadás UI
                System.out.println("Vonat hozzáadása (később megvalósítható)");
            });

            deleteButton.setOnAction(e -> {
                Train selected = tableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    TrainRepository.deleteTrain(selected.getTrainId());
                    trainData.remove(selected);
                }
            });

            tableView.setRowFactory(tv -> {
                TableRow<Train> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && !row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                        Train train = row.getItem();
                        System.out.println("Szerkesztés (dupla katt): " + train.getTrainName());
                        // Ide jöhet vonat szerkesztés UI
                    }
                });
                return row;
            });

            topBar.getChildren().addAll(addButton, deleteButton);
        }

        topBar.setStyle("-fx-padding: 10;");

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(tableView);

        Scene scene = new Scene(root, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Vonatok listája - " + (isAdmin ? "Állomásfőnök" : "Utas"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(LoginWindow.class);
    }
}