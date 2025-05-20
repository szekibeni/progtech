package strategy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Train;

public class BudapestStrategy implements DestinationStrategy {

    @Override
    public ObservableList<Train> filter(ObservableList<Train> trains) {
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
        return train.getTrainType() != null && train.getTrainType().equalsIgnoreCase("AGRIA InterRégió");
    }
}