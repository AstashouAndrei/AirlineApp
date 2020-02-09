package by.gstu.airline.services;

import by.gstu.airline.entity.CurrentState;
import by.gstu.airline.entity.Flight;

import java.util.List;

/**
 * Class with description entity and actions of administrator object
 */
public class Administrator {

    private final User user;
    private final AdministratorService administratorService;

    public Administrator(User user, AdministratorService administratorService) {
        this.user = user;
        this.administratorService = administratorService;
    }

    public void addFlight(Flight flight) {
        administratorService.addFlight(flight);
    }

    public void removeFlight(String flightCode) {
        administratorService.removeFlight(flightCode);
    }

    public List<Flight> getAllFlights() {
        return administratorService.getAllFlights();
    }

    public Flight getFlightByID(int id) {
        return administratorService.getFlightByID(id);
    }

    public List<Flight> getFlightsByState(CurrentState state) {
        return administratorService.getFlightsByState(state);
    }

    public List<Flight> getFlightsExceptState(CurrentState state) {
        return administratorService.getFlightsExceptState(state);
    }

    /**
     * Starts flight, sets to each member of crew status "On flight"
     *
     * @param flightCode flightCode
     */
    public void startFlight(String flightCode) {
        administratorService.startFlight(flightCode);
    }

    /**
     * Finishes flight, sets to each member of crew status "Standby",
     * removes given flight crew from crew table data base
     *
     * @param flightCode flightCode
     */
    public void finishFlight(String flightCode) {
        administratorService.finishFlight(flightCode);
    }

    /**
     * Delays flight, sets to each member of crew status "Scheduled"
     *
     * @param flightCode flightCode
     */
    public void delayFlight(String flightCode) {
        administratorService.delayFlight(flightCode);
    }

    /**
     * Cancels flight, sets to each member of crew status "Standby",
     * removes given flight crew from crew table data base
     *
     * @param flightCode flightCode
     */
    public void cancelFlight(String flightCode) {
        administratorService.cancelFlight(flightCode);
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrator admin = (Administrator) o;
        return user.equals(admin.user) &&
                administratorService.equals(admin.administratorService);
    }

    @Override
    public int hashCode() {
        final int hash = 19;
        int result = user.getId() * hash;
        result += administratorService.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return user.toString();
    }
}
