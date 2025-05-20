package factory;

import model.Train;

public class TrainFactory {
    public static Train createTrain(String name, String type, int capacity, String departureTime, String arrivalTime) {
        Train train = new Train();
        train.setTrainName(name);
        train.setTrainType(type);
        train.setCapacity(capacity);
        train.setDepartureTime(departureTime);
        train.setArrivalTime(arrivalTime);
        return train;
    }
}
