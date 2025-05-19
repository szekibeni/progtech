package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Train;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainController {

    @FXML private TableView<Train> tableView;
    @FXML private TableColumn<Train, String> departureColumn;
    @FXML private TableColumn<Train, String> arrivalColumn;
    @FXML private TableColumn<Train, String> timeColumn;

    @FXML
    public void initialize() {
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departure"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        ObservableList<Train> trains = FXCollections.observableArrayList(
                new Train("Budapest", "Debrecen", "08:30"),
                new Train("Győr", "Pécs", "10:15"),
                new Train("Miskolc", "Szeged", "12:00")
        );

        tableView.setItems(trains);
    }
}