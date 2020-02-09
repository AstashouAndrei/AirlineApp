package by.gstu.airline.services;

import by.gstu.airline.entity.CurrentState;
import by.gstu.airline.entity.Flight;
import by.gstu.airline.entity.Profession;
import by.gstu.airline.entity.Staff;

import java.util.List;

/**
 * Class with description entity and actions of dispatcher object
 */

public class Dispatcher {

    private final User user;
    private final DispatcherService dispatcherService;

    public Dispatcher(User user, DispatcherService dispatcherService) {
        this.user = user;
        this.dispatcherService = dispatcherService;
    }

    /**
     * Gets from data base and returns list of all airline flights
     *
     * @return list of all flights
     */
    public List<Flight> getAllFlights() {
        return dispatcherService.getAllFlights();
    }

    /**
     * Gets from data base and returns list of all airline flights with given state
     *
     * @return list of all flights with given state
     */
    public List<Flight> getFlightsByState(CurrentState state) {
        return dispatcherService.getFlightsByState(state);
    }

    /**
     * Gets from data base and returns list of all airline staff
     *
     * @return list of all flights with given state
     */
    public List<Staff> getAllStaff(Profession profession) {
        return dispatcherService.getAllStaff(profession);
    }

    /**
     * Build crew from given staff's and submit it to given flight
     *
     * @param cabinStaff cabinStaff
     * @param flightCode flightCode
     */
    public void submitCrewForFlight(List<Staff> cabinStaff, String flightCode) {
        dispatcherService.submitCrewForFlight(cabinStaff, flightCode);
    }

    /**
     * Adds given staff to airline staff data base
     *
     * @param staff staff
     */
    public void hireStaff(Staff staff) {
        dispatcherService.hireStaff(staff);
    }

    /**
     * Gets from data base and returns airline staff with given full name
     *
     * @param fullName fullName
     * @return staff
     */
    public Staff getStaffByFullName(String fullName) {
        return dispatcherService.getStaffByFullName(fullName);
    }

    public User getUser() {
           return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dispatcher disp = (Dispatcher) o;
        return user.equals(disp.user) &&
                dispatcherService.equals(disp.dispatcherService);
    }

    @Override
    public int hashCode() {
        final int hash = 19;
        int result = user.getId() * hash;
        result += dispatcherService.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return user.toString();
    }
}
