package strategy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Train;

public class NoMatchStrategy implements DestinationStrategy {

    @Override
    public ObservableList<Train> filter(ObservableList<Train> trains) {
        if (trains == null) {
            return FXCollections.observableArrayList();
        }
        ObservableList<Train> result = FXCollections.observableArrayList();
        for (Train train : trains) {
            if (matches(train)) {
                result.add(train);
            }
        }
        return result;
    }

    @Override
    public boolean matches(Train train) {
        return true; // mindig igaz, azaz nem szűr semmit
    }
}