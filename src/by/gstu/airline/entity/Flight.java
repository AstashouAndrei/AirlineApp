package by.gstu.airline.entity;

/**
 * Class with description entity of flights objects
 */
public class Flight {

    private int id;
    private Itinerary itinerary;
    private Plane plane;
    private CurrentState state;


    public Flight(Itinerary itinerary, Plane plane) {
        this.itinerary = itinerary;
        this.plane = plane;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public CurrentState getState() {
        return state;
    }

    public void setState(CurrentState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id == flight.id && itinerary.equals(flight.itinerary) &&
                plane.equals(flight.plane);
    }

    @Override
    public int hashCode() {
        final int hash = 19;
        int result = hash * id;
        result += hash * itinerary.getId() + hash * plane.getID();
        return result;
    }

    @Override
    public String toString() {
        return "Flight id: " + id + " ["  + itinerary + " | " + plane.getPlaneType() + "]";
    }
}
