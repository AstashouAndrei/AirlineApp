package by.gstu.airline.entity.services;

import by.gstu.airline.entity.Flight;
import by.gstu.airline.exception.DAOException;
import org.apache.log4j.Logger;

/**
 * Class with description entity of administrator object
 */
public class Administrator {

    private static Logger logger = Logger.getLogger(Administrator.class.getName());

    private final User user;
    private final AdministratorService administratorService;

    public Administrator(User user, AdministratorService administratorService) {
        this.user = user;
        this.administratorService = administratorService;
    }

    /**
     * Starts flight, sets to each member of crew status "On flight"
     *
     * @param flight flight
     * @throws DAOException DAOException
     */
    public void startFlight(Flight flight) throws DAOException {
        logger.trace(user.getLogin() + " starts flight");
        administratorService.startFlight(flight);
    }

    /**
     * Finishes flight, sets to each member of crew status "Standby",
     * removes given flight crew from crew table data base
     *
     * @param flight flight
     * @throws DAOException DAOException
     */
    public void finishFlight(Flight flight) throws DAOException {
        logger.trace(user.getLogin() + " finishes flight");
        administratorService.finishFlight(flight);
    }

    /**
     * Delays flight, sets to each member of crew status "Scheduled"
     *
     * @param flight flight
     * @throws DAOException DAOException
     */
    public void delayFlight(Flight flight) throws DAOException {
        logger.trace(user.getLogin() + " delays flight");
        administratorService.delayFlight(flight);
    }

    /**
     * Cancels flight, sets to each member of crew status "Standby",
     * removes given flight crew from crew table data base
     *
     * @param flight flight
     * @throws DAOException DAOException
     */
    public void cancelFlight(Flight flight) throws DAOException {
        logger.trace(user.getLogin() + " cancels flight");
        administratorService.cancelFlight(flight);
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
