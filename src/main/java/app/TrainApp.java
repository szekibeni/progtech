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
import strategy.*;
import factory.TrainFactory; // felülre
import command.Command;
import command.AddTrainCommand;
import command.DeleteTrainCommand;
import java.util.ArrayList;
import java.util.List;

public class TrainApp extends Application {

    private static boolean isAdmin;

    private ObservableList<Train> masterData;
    private FilteredList<Train> filteredData;
    private DestinationStrategy currentStrategy;

    public TrainApp() {
    }

    public TrainApp(boolean isAdmin) {
        TrainApp.isAdmin = isAdmin;
    }

    @Override
    public void start(Stage primaryStage) {
        TableView<Train> tableView = createTableView();

        List<Train> trainList = TrainRepository.getAllTrains();
        if (trainList.isEmpty()) {
            trainList = createDummyData();
        }

        masterData = FXCollections.observableArrayList(trainList);
        filteredData = new FilteredList<>(masterData, p -> true);
        tableView.setItems(filteredData);

        TextField filterField = createSearchField();

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
            VBox rightForm = createAdminForm(tableView);
            root.setRight(rightForm);
        } else {
            Button confirmButton = createConfirmButton(tableView);
            topBar.getChildren().add(1, confirmButton);

            VBox passengerForm = createPassengerForm();
            root.setRight(passengerForm);
        }

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        Image icon = new Image(getClass().getResourceAsStream("/prog.png"));
        primaryStage.getIcons().add(icon);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Vonatok listája - " + (isAdmin ? "Állomásfőnök" : "Utas"));
        primaryStage.show();
    }

    private TableView<Train> createTableView() {
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

        return tableView;
    }

    private List<Train> createDummyData() {
        List<Train> dummyList = new ArrayList<>();
        Train dummy = new Train();
        dummy.setTrainId(1);
        dummy.setTrainName("IC 123");
        dummy.setTrainType("InterCity");
        dummy.setCapacity(300);
        dummy.setDepartureTime("10:30");
        dummy.setArrivalTime("13:00");
        dummyList.add(dummy);
        return dummyList;
    }

    private TextField createSearchField() {
        TextField filterField = new TextField();
        filterField.setPromptText("Keresés név vagy típus szerint...");
        filterField.getStyleClass().add("text-field-filter");
        filterField.setPrefWidth(300);
        filterField.setPrefHeight(35);
        filterField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isEmpty()) {
                filteredData.setPredicate(train -> true);
            } else {
                String lowerCaseFilter = newVal.toLowerCase();
                filteredData.setPredicate(train ->
                        (train.getTrainName() != null && train.getTrainName().toLowerCase().contains(lowerCaseFilter)) ||
                                (train.getTrainType() != null && train.getTrainType().toLowerCase().contains(lowerCaseFilter))
                );
            }
        });
        return filterField;
    }

    private VBox createAdminForm(TableView<Train> tableView) {
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

        Button addButton = new Button("➕ Hozzáadás");
        addButton.setOnAction(e -> {
            try {
                Train newTrain = TrainFactory.createTrain(
                        nameField.getText(),
                        typeField.getText(),
                        Integer.parseInt(capacityField.getText()),
                        departureField.getText(),
                        arrivalField.getText()
                );

                Command addCommand = new AddTrainCommand(newTrain, masterData);
                addCommand.execute();

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

        Button deleteButton = new Button("🗑️ Törlés");
        deleteButton.setOnAction(e -> {
            Train selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Command deleteCommand = new DeleteTrainCommand(selected, masterData);
                deleteCommand.execute();
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
        return rightForm;
    }

    private Button createConfirmButton(TableView<Train> tableView) {
        Button confirmButton = new Button("🎟️ Foglalás megerősítése");
        confirmButton.setDisable(true);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            confirmButton.setDisable(newSel == null);
        });

        confirmButton.setOnAction(e -> {
            Train selectedTrain = tableView.getSelectionModel().getSelectedItem();
            if (selectedTrain != null) {
                showConfirmationWindow(selectedTrain);
            }
        });

        return confirmButton;
    }

    private VBox createPassengerForm() {
        Label destinationLabel = new Label("Hova utazik?");
        destinationLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField destinationField = new TextField();
        destinationField.setPromptText("Írja be az úticélt (pl. Budapest, Szilvásvárad, Füzesabony)");
        destinationField.setPrefWidth(300);
        destinationField.setPrefHeight(35);

        currentStrategy = new NoMatchStrategy();

        destinationField.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                if (newVal == null || masterData == null) {
                    filteredData.setPredicate(train -> true);
                    currentStrategy = new NoMatchStrategy();
                    return;
                }

                String destination = newVal.trim().toLowerCase();

                switch (destination) {
                    case "budapest":
                        currentStrategy = new BudapestStrategy();
                        break;
                    case "szilvásvárad":
                        currentStrategy = new SzilvasvaradStrategy();
                        break;
                    case "füzesabony":
                        currentStrategy = new FuzesabonyStrategy();
                        break;
                    default:
                        currentStrategy = new NoMatchStrategy();
                        break;
                }

                filteredData.setPredicate(train -> currentStrategy.matches(train));
            } catch (Exception e) {
                e.printStackTrace();
                filteredData.setPredicate(train -> true);
            }
        });

        VBox passengerForm = new VBox(10, destinationLabel, destinationField);
        passengerForm.setStyle("-fx-padding: 10;");
        return passengerForm;
    }

    private void showConfirmationWindow(Train train) {
        Stage confirmStage = new Stage();
        confirmStage.setTitle("Foglalás megerősítése");

        Image icon = new Image(getClass().getResourceAsStream("/prog.png"));
        confirmStage.getIcons().add(icon);

        Label infoLabel = new Label("Foglalás megerősítve!");
        infoLabel.getStyleClass().add("header-label");

        Label trainLabel = new Label("Vonat: " + train.getTrainName());
        trainLabel.getStyleClass().add("content-label");

        Label departureLabel = new Label("Indulás időpontja: " + train.getArrivalTime());
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