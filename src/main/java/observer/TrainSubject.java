package observer;

import model.Train;

import java.util.ArrayList;
import java.util.List;

public class TrainSubject {
    private static final List<TrainObserver> observers = new ArrayList<>();

    public static void addObserver(TrainObserver observer) {
        observers.add(observer);
    }

    public static void removeObserver(TrainObserver observer) {
        observers.remove(observer);
    }

    public static void notifyObservers(Train train) {
        for (TrainObserver observer : observers) {
            observer.update(train);
        }
    }
}