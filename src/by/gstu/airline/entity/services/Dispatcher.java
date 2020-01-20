package by.gstu.airline.entity.services;

import by.gstu.airline.entity.Crew;
import by.gstu.airline.entity.Flight;
import by.gstu.airline.exception.DAOException;
import org.apache.log4j.Logger;

/**
 * Class with description entity of dispatcher object
 */
public class Dispatcher {

    private static Logger logger = Logger.getLogger(Dispatcher.class.getName());

    private final User user;
    private final DispatcherService dispatcherService;

    public Dispatcher(User user, DispatcherService dispatcherService) {
        this.user = user;
        this.dispatcherService = dispatcherService;
    }

    /**
     * Builds and returns crew for given flight with random staff
     *
     * @param flight flight
     * @return crew
     * @throws DAOException DAOException
     */
    public Crew buildCrewWithRandomStaff(Flight flight) throws DAOException {
        logger.trace(user.getLogin() + " builds random crew for flight");
        return dispatcherService.buildCrewWithRandomStaff(flight);
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
