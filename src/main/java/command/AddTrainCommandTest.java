/*
package command;

import model.Train;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddTrainCommandTest {

    private ObservableList<Train> trainList;
    private Train testTrain;

    @BeforeEach
    void setUp() {
        trainList = FXCollections.observableArrayList();
        testTrain = new Train();
        testTrain.setTrainId(1);
        testTrain.setTrainName("IC 456");
        testTrain.setTrainType("InterCity");
        testTrain.setCapacity(250);
        testTrain.setDepartureTime("12:00");
        testTrain.setArrivalTime("14:30");
    }

    @Test
    void testExecuteShouldAddTrainToList() {
        AddTrainCommand command = new AddTrainCommand(testTrain, trainList);

        assertEquals(0, trainList.size());
        command.execute();
        assertEquals(1, trainList.size());
        assertEquals(testTrain, trainList.get(0));
    }
}
*/