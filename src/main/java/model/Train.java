package model;

public class Train {

    private int trainId;
    private String trainName;
    private String trainType;
    private int capacity;
    private String departureTime;
    private String arrivalTime;
    private String createdAt; // opcionális, ha szükséged van rá

    public Train() {
        // üres konstruktor az FXML és adatbázis betöltéshez
    }

    // Getterek és setterek
    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Train{id=" + trainId + ", name=" + trainName + ", type=" + trainType + ", capacity=" + capacity +
                ", departure=" + departureTime + ", arrival=" + arrivalTime + "}";
    }
}