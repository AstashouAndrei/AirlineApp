package by.gstu.airline.entity.services;

import by.gstu.airline.dao.CrewDAO;
import by.gstu.airline.dao.PlaneDAO;
import by.gstu.airline.dao.StaffDAO;
import by.gstu.airline.entity.*;
import by.gstu.airline.exception.DAOException;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Class with crew building methods description
 */

public class DispatcherService {

    private static Logger logger = Logger.getLogger(DispatcherService.class.getName());

    private final StaffDAO staffDAO;
    private final CrewDAO crewDAO;
    private final PlaneDAO planeDAO;

    public DispatcherService(StaffDAO staffDAO, CrewDAO crewDAO, PlaneDAO planeDAO) {
        this.staffDAO = staffDAO;
        this.crewDAO = crewDAO;
        this.planeDAO = planeDAO;
    }

    /**
     * Builds and returns crew with random staff for given flight
     *
     * @param flight flight
     * @return crew random staff
     * @throws DAOException DAOException
     */
    public Crew buildCrewWithRandomStaff(Flight flight) throws DAOException {
        logger.trace("Get plane from data base with id: " + flight.getPlane().getID());
        Plane plane = planeDAO.getPlaneByID(flight.getPlane().getID());
        int numberOfFlightAttendants = plane.getPassengerCapacity() / 50;
        logger.trace("Get list of staff with state: " + CurrentState.STANDBY.getState());
        List<Staff> standbyStaff = staffDAO.getStaffByState(CurrentState.STANDBY);
        logger.trace("Build crew with random staff");
        List<Staff> cabinCrew = RandomCrewHandler.getStaffForPlane(standbyStaff, numberOfFlightAttendants);
        for (Staff staff : cabinCrew) {
            staffDAO.changeStaffState(staff, CurrentState.SCHEDULED);
        }
        Crew crew = new Crew(flight.getId(), cabinCrew);
        logger.trace("Adds crew to data base");
        crewDAO.addCrew(crew);
        return crew;
    }
}
