package strategy;

import javafx.collections.ObservableList;
import model.Train;

@FunctionalInterface
public interface DestinationStrategy {
    ObservableList<Train> filter(ObservableList<Train> trains);

    // Új metódus: egy adott vonatra megmondja, hogy illeszkedik-e a szűrésre
    default boolean matches(Train train) {
        // Alapértelmezetten a filter metódust hívjuk meg a train egy elemű listájára, és megnézzük, visszaadja-e
        return filter(javafx.collections.FXCollections.observableArrayList(train)).size() > 0;
    }
}