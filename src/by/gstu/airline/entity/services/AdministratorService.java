package by.gstu.airline.entity.services;

import by.gstu.airline.dao.*;
import by.gstu.airline.entity.*;
import by.gstu.airline.exception.DAOException;

/**
 * Class with flight management methods description
 */
public class AdministratorService {

    private final StaffDAO staffDAO;
    private final CrewDAO crewDAO;

    public AdministratorService(StaffDAO staffDAO, CrewDAO crewDAO) {
        this.staffDAO = staffDAO;
        this.crewDAO = crewDAO;
    }

    /**
     * Starts flight, sets to each member of crew status "On flight"
     *
     * @param flight flight
     * @throws DAOException DAOException
     */
    public void startFlight(Flight flight) throws DAOException {
        manageFlight(flight, CurrentState.ON_FLIGHT);
    }

    /**
     * Finishes flight, sets to each member of crew status "Standby",
     * removes given flight crew from crew table data base
     *
     * @param flight flight
     * @throws DAOException DAOException
     */
    public void finishFlight(Flight flight) throws DAOException {
        manageFlight(flight, CurrentState.STANDBY);
        crewDAO.removeCrewByFlightID(flight.getId());
    }

    /**
     * Delays flight, sets to each member of crew status "Scheduled"
     *
     * @param flight flight
     * @throws DAOException DAOException
     */
    public void delayFlight(Flight flight) throws DAOException {
        manageFlight(flight, CurrentState.SCHEDULED);
    }

    /**
     * Cancels flight, sets to each member of crew status "Standby",
     * removes given flight crew from crew table data base
     *
     * @param flight flight
     * @throws DAOException DAOException
     */
    public void cancelFlight(Flight flight) throws DAOException {
        manageFlight(flight, CurrentState.STANDBY);
        crewDAO.removeCrewByFlightID(flight.getId());
    }

    /**
     * Sets to each member of given flight crew given status
     *
     * @param flight flight
     * @param state  staff state
     * @throws DAOException DAOException
     */
    private void manageFlight(Flight flight, CurrentState state) throws DAOException {
        Crew crew = crewDAO.getCrewByFlightID(flight.getId());
        for (Staff staff : crew.getCabinStaff()) {
            staffDAO.changeStaffState(staff, state);
        }
    }


}
