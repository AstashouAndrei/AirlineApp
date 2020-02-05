package by.gstu.airline.servlets;

import by.gstu.airline.entity.CurrentState;
import by.gstu.airline.entity.Flight;
import by.gstu.airline.entity.Itinerary;
import by.gstu.airline.entity.Plane;
import by.gstu.airline.entity.services.Administrator;
import by.gstu.airline.entity.services.AdministratorService;
import by.gstu.airline.entity.services.User;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/AdminController")
public class AdminController extends HttpServlet {

    private Administrator administrator;

    public AdminController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute("user") != null) {
            showAdministratorPage(request, response);
        } else {
            executeAdministratorCommand(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Create object of airline administrator and forward to administrator page
     *
     * @param request  request
     * @param response response
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    private void showAdministratorPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher requestDispatcher;
        administrator = new Administrator((User) request.getAttribute("user"), new AdministratorService());
        requestDispatcher = request.getRequestDispatcher("/admin.html");
        requestDispatcher.forward(request, response);
    }

    /**
     * Handles request according by action and sends corresponding response
     *
     * @param request  request
     * @param response response
     * @throws IOException IOException
     */
    private void executeAdministratorCommand(HttpServletRequest request, HttpServletResponse response) throws IOException {

        JSONObject json = getJSONObject(request);
        String action = json.getString("action");

        switch (action) {
            case ("addFlight"):
                administrator.addFlight(initializeFlight(json));
                showFlights(response);
                break;
            case ("removeFlight"):
                administrator.removeFlight(json.getString("flightCode"));
                showFlights(response);
                break;
            case ("startFlight"):
                administrator.startFlight(json.getString("flightCode"));
                showFlightsExceptState(response, CurrentState.STANDBY);
                break;
            case ("finishFlight"):
                administrator.finishFlight(json.getString("flightCode"));
                showFlightsExceptState(response, CurrentState.STANDBY);
                break;
            case ("delayFlight"):
                administrator.delayFlight(json.getString("flightCode"));
                showFlightsExceptState(response, CurrentState.STANDBY);
                break;
            case ("cancelFlight"):
                administrator.cancelFlight(json.getString("flightCode"));
                showFlightsExceptState(response, CurrentState.STANDBY);
                break;
            case ("showFlightByID"):
                showFlightByID(json.getInt("flightID"), response);
                break;
            case ("showManageble"):
                showFlightsExceptState(response, CurrentState.getStateByDescription(json.getString("state")));
                break;
            case ("showRemovable"):
                showFlightsByState(response, CurrentState.getStateByDescription(json.getString("state")));
                break;
            case ("init"):
                initUser(response);
                break;
            default:
                showFlights(response);
                break;
        }
    }

    /**
     * Getting from data base list of all airline flights and sending it in servlet response
     *
     * @param response response
     * @throws IOException IOException
     */
    private void showFlights(HttpServletResponse response) throws IOException {
        sendArrayResponse(new JSONArray(administrator.getAllFlights()), response);
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
        sendArrayResponse(new JSONArray(administrator.getFlightsExceptState(state)), response);
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
        sendArrayResponse(new JSONArray(administrator.getFlightsByState(state)), response);
    }

    /**
     * Getting from airline data base flight with given id and sending it in servlet response
     *
     * @param id       flight id
     * @param response response
     * @throws IOException IOException
     */
    private void showFlightByID(int id, HttpServletResponse response) throws IOException {
        JSONObject responseData = new JSONObject(administrator.getFlightByID(id));
        response.setContentType("application/json");
        response.getWriter().write(responseData.toString());
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
        Itinerary itinerary = new Itinerary(flightCode, departure, arrival);

        String planeType = jsonObject.getString("planeType");
        int passengerCapacity = jsonObject.getInt("passengerCapacity");
        int flightRange = jsonObject.getInt("flightRange");
        int fuelConsumption = jsonObject.getInt("fuelConsumption");
        Plane plane = new Plane(planeType, passengerCapacity, flightRange, fuelConsumption);

        return new Flight(itinerary, plane);
    }

    /**
     * Reurns JSONObject received from request
     *
     * @param request request
     * @return JSONObject
     * @throws IOException IOException
     */
    private JSONObject getJSONObject(HttpServletRequest request) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return new JSONObject(builder.toString());
    }

    /**
     * Sends JSONArray in response
     *
     * @param array    JSONArray
     * @param response response
     * @throws IOException IOException
     */
    private void sendArrayResponse(JSONArray array, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(array.toString());
    }

    private void initUser(HttpServletResponse response) throws IOException {
        JSONObject responseData = new JSONObject();
        responseData.put("administrator", administrator.getUser().getLogin());
        response.setContentType("application/json");
        response.getWriter().write(responseData.toString());
    }

}
