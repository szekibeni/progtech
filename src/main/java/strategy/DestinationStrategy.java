package strategy;

import javafx.collections.ObservableList;
import model.Train;

@FunctionalInterface
public interface DestinationStrategy {
    ObservableList<Train> filter(ObservableList<Train> trains);


    default boolean matches(Train train) {

        return filter(javafx.collections.FXCollections.observableArrayList(train)).size() > 0;
    }
}