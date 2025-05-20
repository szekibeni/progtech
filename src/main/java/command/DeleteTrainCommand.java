package command;

import database.TrainRepository;
import javafx.collections.ObservableList;
import model.Train;

public class DeleteTrainCommand implements Command {
    private final Train train;
    private final ObservableList<Train> data;

    public DeleteTrainCommand(Train train, ObservableList<Train> data) {
        this.train = train;
        this.data = data;
    }

    @Override
    public void execute() {
        TrainRepository.deleteTrain(train.getTrainId());
        data.remove(train);
    }
}
