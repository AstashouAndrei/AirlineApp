package main.java.by.gstu.airline.services;

import main.java.by.gstu.airline.dao.*;
import main.java.by.gstu.airline.entity.*;

import java.util.List;

/**
 * Class with dispatcher methods logic
 */

public class DispatcherService {

    private final FactoryDAO factoryDAO = FactoryDAO.getFactoryDAO();
    private final FlightDAO flightDAO = factoryDAO.getFlightDAO();
    private final StaffDAO staffDAO = factoryDAO.getStaffDAO();
    private final CrewDAO crewDAO = factoryDAO.getCrewDAO();

    /**
     * Gets from data base and returns list of all airline flights
     *
     * @return list of all flights
     */
    public List<Flight> getAllFlights() {
        return flightDAO.getFlightsList();
    }

    /**
     * Gets from data base and returns list of all airline flights with given state
     *
     * @return list of all flights with given state
     */
    public List<Flight> getFlightsByState(CurrentState state) {
        return flightDAO.getFlightsByState(state);
    }

    /**
     * Gets from data base and returns list of all airline staff
     *
     * @return list of all flights with given state
     */
    public List<Staff> getAllStaff(Profession profession) {
        return staffDAO.getStaffByProfession(profession);
    }

    /**
     * Adds given staff to airline staff data base
     *
     * @param staff staff
     */
    public void hireStaff(Staff staff) {
        staffDAO.hireStaff(staff);
    }

    /**
     * Build crew from given staff's and submit it to given flight
     *
     * @param cabinStaff cabinStaff
     * @param flightCode flightCode
     */
    public void submitCrewForFlight(List<Staff> cabinStaff, String flightCode) {
        Flight flight = getFlightByCode(flightCode);
        Crew crew = new Crew(flight.getId(), cabinStaff);
        crewDAO.addCrew(crew);
        for (Staff tempStaff : crew.getCabinStaff()) {
            staffDAO.changeStaffState(tempStaff, CurrentState.SCHEDULED);
        }
        flightDAO.changeFlightState(flight, CurrentState.SCHEDULED);
    }

    /**
     * Gets from data base and returns airline staff with given full name
     *
     * @param fullName fullName
     * @return staff
     */
    public Staff getStaffByFullName(String fullName) {
        return staffDAO.getStaffByFullName(fullName);
    }

    /**
     * Gets from data base and returns airline flight with given flight code
     *
     * @param flightCode flightCode
     * @return flight
     */
    private Flight getFlightByCode(String flightCode) {
        return flightDAO.getIFlightByCode(flightCode);
    }
}
