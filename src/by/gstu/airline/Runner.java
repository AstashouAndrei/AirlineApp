package by.gstu.airline;

import by.gstu.airline.dao.*;
import by.gstu.airline.dao.mysql.MySqlStaffDAO;
import by.gstu.airline.entity.*;
import by.gstu.airline.entity.services.*;
import by.gstu.airline.exception.DAOException;
import org.json.JSONObject;

import java.util.Vector;

/**
 * Class for running application
 */
public class Runner {

    public static void main(String[] args) throws Exception {


//
        FactoryDAO factoryDAO = FactoryDAO.getFactoryDAO();
        FlightDAO flightDAO = factoryDAO.getFlightDAO();
        for (Flight flight : flightDAO.getFlightsByState(CurrentState.ON_FLIGHT)) {
            System.out.println(flight);
        }
//        System.out.println(flightDAO.getIFlightByID(9));
//        System.out.println(flightDAO.getIFlightByCode("SU0234"));
//        PlaneDAO planeDAO = factoryDAO.getPlaneDAO();
//        ItineraryDAO itineraryDAO = factoryDAO.getItineraryDAO();
//
//
//        flightDAO.removeFlightByID(34);
//        planeDAO.removePlaneByID(19);
//        itineraryDAO.removeItineraryByID(29);
//
////        StaffDAO staffDAO = factoryDAO.getStaffDAO();
////
////
////        CrewDAO crewDAO = factoryDAO.getCrewTeamDAO();
//
//
//        for (Flight flight : flightDAO.getFlightsList()) {
//            System.out.println(flight);
//        }
//        UserDAO userDAO = factoryDAO.getUserDAO();
//
//        userDAO.addUser(new User("Boqeb", "b2qe1ae1", "bowq@eqeeqwe.ru", Access.DISPATCHER));

//        System.out.println(userDAO.getUserById(1));
//        System.out.println(userDAO.getUserByLogin("Boqeb"));
//        System.out.println(userDAO.getUserById(12));

//        Staff s = staffDAO.getStaffByID(12);
//        Flight flight = flightDAO.getIFlightByID(2);
//        JSONObject jsonStaff = new JSONObject(flight);
//
//        System.out.println(jsonStaff);
//        var firstName = document.createTextNode(jsonStaff.plane.);
//
        // Get users from data base by id
//        User userDispatcher = userDAO.getUserById(2);
//        User userAdministrator = userDAO.getUserById(1);
//
//        // Creating dispatcher and administrator services
//        DispatcherService dService = new DispatcherService(staffDAO, crewDAO, planeDAO);
//        AdministratorService aService = new AdministratorService(staffDAO, crewDAO);
//
//        // Creating dispatcher and administrator, using users from data base and services
//        Dispatcher dispatcher = new Dispatcher(userDispatcher, dService);
//        Administrator administrator = new Administrator(userAdministrator, aService);
//
//        // Getting two existing flights from data base
//        Flight fromMoscowToBeijing = flightDAO.getIFlightByID(7);
//        Flight fromKazanToMoscow = flightDAO.getIFlightByID(4);
//
//        // Creating new flight with new Itinerary and plane getting from data base
//        Itinerary fromDubaiToMoscow = new Itinerary("SU0521", "Dubai", "Moscow");
//        itineraryDAO.addItinerary(fromDubaiToMoscow);
//        Plane a330_300 = planeDAO.getPlaneByID(1);
//        Flight flightFromDubaiToMoscow = new Flight(fromDubaiToMoscow, a330_300);
//        flightDAO.addFlight(flightFromDubaiToMoscow);
//
//        // Dispatcher build crews for all three flight with random staff.
//        // Each staff from each crew adds to crew table in data base, also each stuff
//        // changes current state in staff table into "Scheduled",
//        // which makes them unavailable to getting on others flights
//        dispatcher.buildCrewWithRandomStaff(fromMoscowToBeijing);
//        dispatcher.buildCrewWithRandomStaff(fromKazanToMoscow);
//        dispatcher.buildCrewWithRandomStaff(flightFromDubaiToMoscow);
//
//        // Administrator starts flight from Moscow to Beijing. All staff from this flight
//        // crew changes current state in staff table to "On flight"
//        administrator.startFlight(fromMoscowToBeijing);
//
//        // Administrator delays flight from Kazan to Moscow. All staff from this flight
//        // crew keeps current state as "Scheduled" and waits for starting or canceling flight.
//        administrator.delayFlight(fromKazanToMoscow);
//
//        // Administrator starts flight from Dubai to Moscow. All staff from this flight
//        // crew changes current state in staff table to "On flight"
//        administrator.startFlight(flightFromDubaiToMoscow);
//
//        // Administrator finishes flight from Moscow to Beijing. All staff from this flight
//        // crew changes current state in staff table to "Standby" which makes them
//        // available to get to other flights. Also crew from this flight removes from
//        // crew table in data base
//        administrator.finishFlight(fromMoscowToBeijing);
    }
}
