package strategy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Train;

public class SzilvasvaradStrategy implements DestinationStrategy {

    @Override
    public ObservableList<Train> filter(ObservableList<Train> trains) {
        ObservableList<Train> result = FXCollections.observableArrayList();
        if (trains == null) {
            return result;
        }
        for (Train train : trains) {
            if (train != null && matches(train)) {
                result.add(train);
            }
        }
        return result;
    }

    public boolean matches(Train train) {
        return train.getTrainName() != null && train.getTrainName().startsWith("35");
    }
}