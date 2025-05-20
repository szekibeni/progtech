package command;

import database.TrainRepository;
import javafx.collections.ObservableList;
import model.Train;

public class AddTrainCommand implements Command {
    private final Train train;
    private final ObservableList<Train> data;

    public AddTrainCommand(Train train, ObservableList<Train> data) {
        this.train = train;
        this.data = data;
    }

    @Override
    public void execute() {
        TrainRepository.addTrain(train);
        data.add(train);
    }
}
