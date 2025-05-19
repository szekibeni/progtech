package app;

import database.TrainRepository;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Train;

import java.util.ArrayList;
import java.util.List;

public class TrainApp extends Application {

    private static boolean isAdmin;

    public TrainApp() {
    }

    public TrainApp(boolean isAdmin) {
        TrainApp.isAdmin = isAdmin;
    }

    @Override
    public void start(Stage primaryStage) {
        TableView<Train> tableView = new TableView<>();

        TableColumn<Train, String> nameColumn = new TableColumn<>("Vonatszám");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("trainName"));

        TableColumn<Train, String> typeColumn = new TableColumn<>("Típus");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("trainType"));

        TableColumn<Train, Integer> capacityColumn = new TableColumn<>("Kapacitás");
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        TableColumn<Train, String> departureColumn = new TableColumn<>("Érkezés");
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));

        TableColumn<Train, String> arrivalColumn = new TableColumn<>("Indulás");
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        tableView.getColumns().addAll(nameColumn, typeColumn, capacityColumn, departureColumn, arrivalColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<Train> trainList = TrainRepository.getAllTrains();
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
        filterField.getStyleClass().add("text-field-filter");
        filterField.setPrefWidth(300);
        filterField.setPrefHeight(35);
        filterField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(train ->
                    train.getTrainName().toLowerCase().contains(newVal.toLowerCase()) ||
                            train.getTrainType().toLowerCase().contains(newVal.toLowerCase())
            );
        });

        // Top bar
        HBox topBar = new HBox(10);
        topBar.setStyle("-fx-padding: 10;");
        Button backButton = new Button("Vissza");
        backButton.setOnAction(e -> {
            new LoginWindow().start(new Stage());
            primaryStage.close();
        });

        topBar.getChildren().addAll(backButton, filterField);

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(tableView);

        if (isAdmin) {
            Button addButton = new Button("➕ Hozzáadás");
            Button deleteButton = new Button("🗑️ Törlés");

            TextField nameField = new TextField();
            nameField.setPromptText("Név");

            TextField typeField = new TextField();
            typeField.setPromptText("Típus");

            TextField capacityField = new TextField();
            capacityField.setPromptText("Kapacitás");

            TextField departureField = new TextField();
            departureField.setPromptText("Érkezés (HH:mm)");

            TextField arrivalField = new TextField();
            arrivalField.setPromptText("Indulás (HH:mm)");

            addButton.setOnAction(e -> {
                try {
                    Train newTrain = new Train();
                    newTrain.setTrainName(nameField.getText());
                    newTrain.setTrainType(typeField.getText());
                    newTrain.setCapacity(Integer.parseInt(capacityField.getText()));
                    newTrain.setDepartureTime(departureField.getText());
                    newTrain.setArrivalTime(arrivalField.getText());

                    TrainRepository.addTrain(newTrain);
                    trainData.add(newTrain);

                    nameField.clear();
                    typeField.clear();
                    capacityField.clear();
                    departureField.clear();
                    arrivalField.clear();
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Hibás adatbevitel! Ellenőrizd az értékeket.");
                    alert.showAndWait();
                }
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
                        System.out.println("Szerkesztés (opcionális): " + train.getTrainName());
                    }
                });
                return row;
            });

            VBox rightForm = new VBox(8, nameField, typeField, capacityField, departureField, arrivalField, addButton, deleteButton);
            rightForm.setStyle("-fx-padding: 10;");
            root.setRight(rightForm);

        } else {
            // Ha utas vagy, akkor legyen egy "Foglalás megerősítése" gomb a jobb oldalon
            Button confirmButton = new Button("Foglalás megerősítése");
            confirmButton.setDisable(true);

            // A gomb csak akkor aktív, ha kiválasztottunk egy vonatot
            tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                confirmButton.setDisable(newSel == null);
            });

            confirmButton.setOnAction(e -> {
                Train selectedTrain = tableView.getSelectionModel().getSelectedItem();
                if (selectedTrain != null) {
                    showConfirmationWindow(selectedTrain);
                }
            });

            VBox rightBox = new VBox(10, confirmButton);
            rightBox.setStyle("-fx-padding: 10;");
            root.setRight(rightBox);
        }

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // Ikon beállítása
        Image icon = new Image(getClass().getResourceAsStream("/prog.png"));
        primaryStage.getIcons().add(icon);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Vonatok listája - " + (isAdmin ? "Állomásfőnök" : "Utas"));
        primaryStage.show();
    }

    // Új metódus a foglalás megerősítő ablak megjelenítéséhez
    private void showConfirmationWindow(Train train) {
        Stage confirmStage = new Stage();
        confirmStage.setTitle("Foglalás megerősítése");

        Image icon = new Image(getClass().getResourceAsStream("/prog.png"));
        confirmStage.getIcons().add(icon);


        Label infoLabel = new Label("Foglalás megerősítve!");
        infoLabel.getStyleClass().add("header-label");

        Label trainLabel = new Label("Vonat: " + train.getTrainName());
        trainLabel.getStyleClass().add("content-label");

        Label departureLabel = new Label("Indulás időpontja: " + train.getDepartureTime());
        departureLabel.getStyleClass().add("content-label");

        Button closeButton = new Button("Bezárás");
        closeButton.getStyleClass().add("btn-primary");
        closeButton.setOnAction(e -> confirmStage.close());

        VBox layout = new VBox(20, infoLabel, trainLabel, departureLabel, closeButton);
        layout.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-background-color: white;");
        layout.setPrefWidth(350);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        confirmStage.setScene(scene);
        confirmStage.setResizable(false);
        confirmStage.show();
    }
}