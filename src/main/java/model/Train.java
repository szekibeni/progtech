package model;

public class Train {
    private final String departure;
    private final String arrival;
    private final String time;

    public Train(String departure, String arrival, String time) {
        this.departure = departure;
        this.arrival = arrival;
        this.time = time;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public String getTime() {
        return time;
    }
}