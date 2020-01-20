package by.gstu.airline.entity;

import java.util.List;

/**
 * Class with description entity of Crew objects
 */
public class Crew {

    private int flightID;
    private List<Staff> cabinStaff;

    public Crew(int flightID, List<Staff> cabinStaff) {
        this.flightID = flightID;
        this.cabinStaff = cabinStaff;
    }


    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public List<Staff> getCabinStaff() {
        return cabinStaff;
    }

    public void setCabinStaff(List<Staff> cabinStaff) {
        this.cabinStaff = cabinStaff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crew crew = (Crew) o;
        return flightID == crew.getFlightID() &&
                cabinStaff.equals(crew.cabinStaff);
    }

    @Override
    public int hashCode() {
        final int hash = 19;
        int result = hash * flightID;
        result += cabinStaff.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Flight id: " + flightID + " " + cabinStaff;
    }
}
