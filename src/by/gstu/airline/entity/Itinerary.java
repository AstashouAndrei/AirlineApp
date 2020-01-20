package by.gstu.airline.entity;

/**
 * Class with description entity of itinerary objects
 */
public class Itinerary {

    private int id;
    private String flightCode;
    private String departure;
    private String arrival;

    public Itinerary(String flightCode, String departure, String arrival) {
        this.flightCode = flightCode;
        this.departure = departure;
        this.arrival = arrival;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Itinerary itinerary = (Itinerary) o;
        return flightCode.equals(itinerary.flightCode) &&
                departure.equals(itinerary.departure) &&
                arrival.equals(itinerary.arrival);
    }

    @Override
    public int hashCode() {
        final int hash = 19;
        return hash * id + flightCode.hashCode();
    }

    @Override
    public String toString() {
        return flightCode + " | " + departure + " - " + arrival;
    }
}
