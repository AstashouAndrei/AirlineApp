package main.java.by.gstu.airline.services;

import main.java.by.gstu.airline.entity.CurrentState;
import main.java.by.gstu.airline.entity.Flight;
import main.java.by.gstu.airline.entity.User;

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

    /**
     * Adds flight to data base
     *
     * @param flight flight
     */
    public void addFlight(Flight flight) {
        administratorService.addFlight(flight);
    }

    /**
     * Removes flight from data base by it's flight code
     *
     * @param flightCode flightCode
     */
    public void removeFlight(String flightCode) {
        administratorService.removeFlight(flightCode);
    }

    /**
     * Gets from data base and returns list of all airline flights
     *
     * @return list of all flights
     */
    public List<Flight> getAllFlights() {
        return administratorService.getAllFlights();
    }

    /**
     * Gets from data base and returns flight by given id
     *
     * @return flight
     */
    public Flight getFlightByID(int id) {
        return administratorService.getFlightByID(id);
    }

    /**
     * Gets from data base and returns list of all airline flights with given state
     *
     * @return list of all flights with given state
     */
    public List<Flight> getFlightsByState(CurrentState state) {
        return administratorService.getFlightsByState(state);
    }

    /**
     * Gets from data base and returns list of all airline flights except flights with given state
     *
     * @return list of all flights except flights with given state
     */
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
