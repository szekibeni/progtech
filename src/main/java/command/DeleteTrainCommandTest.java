/*
package command;

import model.Train;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteTrainCommandTest {

    private ObservableList<Train> trainList;
    private Train testTrain;

    @BeforeEach
    void setUp() {
        testTrain = new Train();
        testTrain.setTrainId(1);
        testTrain.setTrainName("IC 456");
        testTrain.setTrainType("InterCity");
        testTrain.setCapacity(250);
        testTrain.setDepartureTime("12:00");
        testTrain.setArrivalTime("14:30");

        trainList = FXCollections.observableArrayList(testTrain);
    }

    @Test
    void testExecuteShouldRemoveTrainFromList() {
        DeleteTrainCommand command = new DeleteTrainCommand(testTrain, trainList);

        assertEquals(1, trainList.size());
        command.execute();
        assertEquals(0, trainList.size());
    }
}
*/
