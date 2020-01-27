package by.gstu.airline.servlets;

import by.gstu.airline.dao.FactoryDAO;
import by.gstu.airline.dao.FlightDAO;
import by.gstu.airline.dao.ItineraryDAO;
import by.gstu.airline.dao.PlaneDAO;
import by.gstu.airline.entity.CurrentState;
import by.gstu.airline.entity.Flight;
import by.gstu.airline.entity.Itinerary;
import by.gstu.airline.entity.Plane;
import by.gstu.airline.exception.DAOException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/AdminController")
public class AdminController extends HttpServlet {

    public AdminController() {
        super();
    }

    private FactoryDAO factoryDAO = FactoryDAO.getFactoryDAO();
    private FlightDAO flightDAO = factoryDAO.getFlightDAO();
    private PlaneDAO planeDAO = factoryDAO.getPlaneDAO();
    private ItineraryDAO itineraryDAO = factoryDAO.getItineraryDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder builder = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        JSONObject jsonObject = new JSONObject(builder.toString());
        String action = jsonObject.getString("action");

        String flightCode;
        CurrentState state;

        switch (action) {
            case ("showAllFlights"):
                showFlights(response);
                break;
            case ("showFlightByID"):
                int flightID = jsonObject.getInt("flightID");
                showFlightByID(flightID, response);
                break;
            case ("addFlight"):
                try {
                    Flight flight = initializeFlight(jsonObject);
                    planeDAO.addPlane(flight.getPlane());
                    itineraryDAO.addItinerary(flight.getItinerary());
                    flightDAO.addFlight(flight);
                } catch (DAOException e) {
                    e.printStackTrace();
                } finally {
                    showFlights(response);
                }
                break;
            case ("removeFlight"):
                try {
                    flightCode = jsonObject.getString("flightCode");
                    flightDAO.removeFlight(flightCode);
                } catch (DAOException e) {
                    e.printStackTrace();
                } finally {
                    showFlights(response);
                }
                break;
            case ("showManageble"):
                state = CurrentState.getStateByDescription(jsonObject.getString("state"));
                showFlightsExceptState(response, state);
                break;
            case ("showRemovable"):
                state = CurrentState.getStateByDescription(jsonObject.getString("state"));
                showFlightsByState(response, state);
                break;
            case ("startFlight"):
                try {
                    flightCode = jsonObject.getString("flightCode");
                    flightDAO.changeFlightState(flightDAO.getIFlightByCode(flightCode), CurrentState.ON_FLIGHT);
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                showFlightsExceptState(response, CurrentState.STANDBY);
                break;
            case ("finishFlight"):
                try {
                    flightCode = jsonObject.getString("flightCode");
                    flightDAO.changeFlightState(flightDAO.getIFlightByCode(flightCode), CurrentState.STANDBY);
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                showFlightsExceptState(response, CurrentState.STANDBY);
                break;
            case ("delayFlight"):
                try {
                    flightCode = jsonObject.getString("flightCode");
                    flightDAO.changeFlightState(flightDAO.getIFlightByCode(flightCode), CurrentState.DELAYED);
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                showFlightsExceptState(response, CurrentState.STANDBY);
                break;
            case ("cancelFlight"):
                try {
                    flightCode = jsonObject.getString("flightCode");
                    flightDAO.changeFlightState(flightDAO.getIFlightByCode(flightCode), CurrentState.STANDBY);
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                showFlightsExceptState(response, CurrentState.STANDBY);
                break;
            default:
                break;

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Getting from data base list of all airline flights and sending it in servlet response
     *
     * @param response response
     * @throws IOException IOException
     */
    private void showFlights(HttpServletResponse response) throws IOException {
        try {
            List<Flight> flightList = flightDAO.getFlightsList();
            JSONArray array = new JSONArray(flightList);
            response.setContentType("application/json");
            response.getWriter().write(array.toString());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getting from data base list of airline flights except given state
     * and sending it in servlet response
     *
     * @param response response
     * @param state    state
     * @throws IOException IOException
     */
    private void showFlightsExceptState(HttpServletResponse response, CurrentState state) throws IOException {
        try {
            List<Flight> flightList = flightDAO.getFlightsExceptState(state);
            JSONArray array = new JSONArray(flightList);
            response.setContentType("application/json");
            response.getWriter().write(array.toString());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getting from data base list of airline flights with given state
     * and sending it in servlet response
     *
     * @param response response
     * @param state    state
     * @throws IOException IOException
     */
    private void showFlightsByState(HttpServletResponse response, CurrentState state) throws IOException {
        try {
            List<Flight> flightList = flightDAO.getFlightsByState(state);
            JSONArray array = new JSONArray(flightList);
            response.setContentType("application/json");
            response.getWriter().write(array.toString());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getting from airline data base flight with given id and sending it in servlet response
     *
     * @param id       flight id
     * @param response response
     * @throws IOException IOException
     */
    private void showFlightByID(int id, HttpServletResponse response) throws IOException {
        try {
            Flight flight = flightDAO.getIFlightByID(id);
            JSONObject responseData = new JSONObject(flight);
            response.setContentType("application/json");
            response.getWriter().write(responseData.toString());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns flight initialized by data from request
     *
     * @param jsonObject jsonObject
     * @return flight
     */
    private Flight initializeFlight(JSONObject jsonObject) {
        String flightCode = jsonObject.getString("flightCode");
        String departure = jsonObject.getString("departure");
        String arrival = jsonObject.getString("arrival");
        String planeType = jsonObject.getString("planeType");
        int passengerCapacity = jsonObject.getInt("passengerCapacity");
        int flightRange = jsonObject.getInt("flightRange");
        int fuelConsumption = jsonObject.getInt("fuelConsumption");
        Itinerary itinerary = new Itinerary(flightCode, departure, arrival);
        Plane plane = new Plane(planeType, passengerCapacity, flightRange, fuelConsumption);
        Flight flight = new Flight(itinerary, plane);
        System.out.println(flight);
        return flight;
    }
}
