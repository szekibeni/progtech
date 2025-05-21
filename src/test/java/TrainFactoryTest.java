

import model.Train;
import org.junit.jupiter.api.Test;
import factory.TrainFactory;
import static org.junit.jupiter.api.Assertions.*;

class TrainFactoryTest {

    @Test
    void testCreateTrain() {
        Train train = TrainFactory.createTrain("IC 789", "InterCity", 300, "10:30", "13:00");

        assertNotNull(train);
        assertEquals("IC 789", train.getTrainName());
        assertEquals("InterCity", train.getTrainType());
        assertEquals(300, train.getCapacity());
        assertEquals("10:30", train.getDepartureTime());
        assertEquals("13:00", train.getArrivalTime());
    }
}

