package by.gstu.airline.services;

import by.gstu.airline.dao.*;
import by.gstu.airline.entity.*;
import by.gstu.airline.exception.DAOException;

import java.util.List;

/**
 * Class with administrator methods description
 */
public class AdministratorService {

    private final FactoryDAO factoryDAO = FactoryDAO.getFactoryDAO();
    private final PlaneDAO planeDAO = factoryDAO.getPlaneDAO();
    private final ItineraryDAO itineraryDAO = factoryDAO.getItineraryDAO();
    private final FlightDAO flightDAO = factoryDAO.getFlightDAO();
    private final StaffDAO staffDAO = factoryDAO.getStaffDAO();
    private final CrewDAO crewDAO = factoryDAO.getCrewDAO();

    public void addFlight(Flight flight) throws DAOException {
        planeDAO.addPlane(flight.getPlane());
        itineraryDAO.addItinerary(flight.getItinerary());
        flightDAO.addFlight(flight);
    }

    public void removeFlight(String flightCode) {
        flightDAO.removeFlight(flightCode);
    }

    /**
     * Gets from data base and returns list of all airline flights
     *
     * @return list of all flights
     */
    public List<Flight> getAllFlights() {
        return flightDAO.getFlightsList();
    }

    /**
     * Gets from data base and returns flight by given id
     *
     * @return flight
     */
    public Flight getFlightByID(int id) {
        return flightDAO.getIFlightByID(id);
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
     * Gets from data base and returns list of all airline flights except flights with given state
     *
     * @return list of all flights except flights with given state
     */
    public List<Flight> getFlightsExceptState(CurrentState state) {
        return flightDAO.getFlightsExceptState(state);
    }

    /**
     * Starts flight, sets to each member of crew status "On flight"
     *
     * @param flightCode flightCode
     * @throws DAOException DAOException
     */
    public void startFlight(String flightCode) throws DAOException {
        if (CurrentState.SCHEDULED.equals(flightDAO.getIFlightByCode(flightCode).getState())
                || CurrentState.DELAYED.equals(flightDAO.getIFlightByCode(flightCode).getState())) {
            manageFlight(flightDAO.getIFlightByCode(flightCode), CurrentState.ON_FLIGHT);
        }
    }

    /**
     * Finishes flight, sets to each member of crew status "Standby",
     * removes given flight crew from crew table data base
     *
     * @param flightCode flightCode
     * @throws DAOException DAOException
     */
    public void finishFlight(String flightCode) throws DAOException {
        if (CurrentState.ON_FLIGHT.equals(flightDAO.getIFlightByCode(flightCode).getState())) {
            manageFlight(flightDAO.getIFlightByCode(flightCode), CurrentState.SCHEDULED);
        }
    }

    /**
     * Delays flight, sets to each member of crew status "Scheduled"
     *
     * @param flightCode flightCode
     * @throws DAOException DAOException
     */
    public void delayFlight(String flightCode) throws DAOException {
        if (CurrentState.SCHEDULED.equals(flightDAO.getIFlightByCode(flightCode).getState())) {
            manageFlight(flightDAO.getIFlightByCode(flightCode), CurrentState.DELAYED);
        }
    }

    /**
     * Cancels flight, sets to each member of crew status "Standby",
     * removes given flight crew from crew table data base
     *
     * @param flightCode flightCode
     * @throws DAOException DAOException
     */
    public void cancelFlight(String flightCode) throws DAOException {
        if (CurrentState.SCHEDULED.equals(flightDAO.getIFlightByCode(flightCode).getState())
                || CurrentState.DELAYED.equals(flightDAO.getIFlightByCode(flightCode).getState())) {
            manageFlight(flightDAO.getIFlightByCode(flightCode), CurrentState.STANDBY);
            crewDAO.removeCrewByFlightID(flightDAO.getIFlightByCode(flightCode).getId());
        }
    }

    /**
     * Sets to each member of given flight crew given status
     *
     * @param flight flight
     * @param state  staff state
     * @throws DAOException DAOException
     */
    private void manageFlight(Flight flight, CurrentState state) throws DAOException {
        flightDAO.changeFlightState(flight, state);
        for (Staff staff : crewDAO.getCrewByFlightID(flight.getId()).getCabinStaff()) {
            staffDAO.changeStaffState(staff, state);
        }
    }
}
