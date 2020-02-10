package main.java.by.gstu.airline.entity;

import java.util.List;

/**
 * Class with description entity of airline objects
 */

public class Airline {

    private String name;
    private List<Flight> flights;

    public Airline(String name, List<Flight> flights) {
        this.name = name;
        this.flights = flights;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airline airline = (Airline) o;
        return name.equals(airline.name) &&
                flights.equals(airline.flights);
    }

    @Override
    public int hashCode() {
        final int hash = 19;
        int result = hash * flights.size();
        result += name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Airline: " + name;
    }
}
