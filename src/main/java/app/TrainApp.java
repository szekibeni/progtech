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

import java.util.ArrayList;
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
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // 🔍 Tesztelés
        List<Train> trainList = TrainRepository.getAllTrains();
        System.out.println("Lekért vonatok száma: " + trainList.size());
        for (Train t : trainList) {
            System.out.println(t);
        }

        // ➕ Tesztadatok, ha nincs adatbázis
        if (trainList.isEmpty()) {
            trainList = new ArrayList<>();
            Train dummy = new Train();
            dummy.setTrainId(1);
            dummy.setTrainName("IC 123");
            dummy.setTrainType("InterCity");
            dummy.setCapacity(300);
            dummy.setDepartureTime("10:30");
            dummy.setArrivalTime("13:00");
            trainList.add(dummy);
        }

        ObservableList<Train> trainData = FXCollections.observableArrayList(trainList);
        FilteredList<Train> filteredData = new FilteredList<>(trainData, p -> true);
        tableView.setItems(filteredData);

        // Keresőmező
        TextField filterField = new TextField();
        filterField.setPromptText("Keresés név vagy típus szerint...");
        filterField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(train ->
                    train.getTrainName().toLowerCase().contains(newVal.toLowerCase()) ||
                            train.getTrainType().toLowerCase().contains(newVal.toLowerCase())
            );
        });

        // Admin funkciók
        HBox topBar = new HBox(10);
        topBar.setStyle("-fx-padding: 10;");
        Button backButton = new Button("Vissza");
        backButton.setOnAction(e -> {
            new LoginWindow().start(new Stage());
            primaryStage.close();
        });

        topBar.getChildren().addAll(backButton, filterField);

        if (isAdmin) {
            Button addButton = new Button("➕ Hozzáadás");
            Button deleteButton = new Button("🗑️ Törlés");

            addButton.setOnAction(e -> {
                System.out.println("Hozzáadás");
            });

            deleteButton.setOnAction(e -> {
                Train selected = tableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    TrainRepository.deleteTrain(selected.getTrainId());
                    trainData.remove(selected);
                }
            });

            topBar.getChildren().addAll(addButton, deleteButton);

            tableView.setRowFactory(tv -> {
                TableRow<Train> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && !row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                        Train train = row.getItem();
                        System.out.println("Szerkesztés: " + train.getTrainName());
                    }
                });
                return row;
            });
        }

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(tableView);

        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Vonatok listája - " + (isAdmin ? "Állomásfőnök" : "Utas"));
        primaryStage.show();
    }
}