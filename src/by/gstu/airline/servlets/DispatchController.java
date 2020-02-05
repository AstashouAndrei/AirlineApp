package by.gstu.airline.servlets;

import by.gstu.airline.entity.*;
import by.gstu.airline.entity.services.Dispatcher;
import by.gstu.airline.entity.services.DispatcherService;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/DispatchController")
public class DispatchController extends HttpServlet {

    public DispatchController() {
        super();
    }

    private Dispatcher dispatcher;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute("user") != null) {
            showDispatcherPage(request, response);
        } else {
            executeDispatcherCommand(request, response);
            System.out.println("hey");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Create object of airline dispatcher and forward to dispatcher page
     *
     * @param request  request
     * @param response response
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    private void showDispatcherPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher requestDispatcher;
        dispatcher = new Dispatcher((User) request.getAttribute("user"), new DispatcherService());
        requestDispatcher = request.getRequestDispatcher("/dispatcher.html");
        requestDispatcher.forward(request, response);
    }

    private void executeDispatcherCommand(HttpServletRequest request, HttpServletResponse response) throws IOException {

        JSONObject json = getJSONObject(request);
        String action = json.getString("action");
        switch (action) {
            case ("addStaff"):
                Staff staff = initializeStaff(json);
                dispatcher.hireStaff(staff);
                showStaffs(response, staff.getProfession());
                break;
            case ("submitCrew"):
                dispatcher.submitCrewForFlight(getStaffs(json.getJSONArray("crew")), json.getString("flightCode"));
                showFlights(response);
                break;
            case ("showStaffs"):
                Profession profession = Profession.getProfessionByID(json.getInt("profession"));
                showStaffs(response, profession);
                break;
            case ("showNoCrewFlights"):
                showFlightsWithoutCrew(response);
                break;
            case ("init"):
                initUser(response);
                break;
            default:
                showFlights(response);
                break;
        }
    }

    private List<Staff> getStaffs(JSONArray crewArray) {
        List<Staff> cabinStaff = new ArrayList<>();
        for (int i = 0; i < crewArray.length(); i++) {
            cabinStaff.add(dispatcher.getStaffByFullName(crewArray.getJSONObject(i).getString("staff")));
        }
        return cabinStaff;
    }

    private void showStaffs(HttpServletResponse response, Profession profession) throws IOException {
        sendArrayResponse(new JSONArray(dispatcher.getAllStaff(profession)), response);
    }

    private void showFlightsWithoutCrew(HttpServletResponse response) throws IOException {
        JSONArray array = new JSONArray(dispatcher.getFlightsByState(CurrentState.STANDBY));
        response.setContentType("application/json");
        response.getWriter().write(array.toString());
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
     * Getting from data base list of all airline flights and sending it in servlet response
     *
     * @param response response
     * @throws IOException IOException
     */
    private void showFlights(HttpServletResponse response) throws IOException {
        sendArrayResponse(new JSONArray(dispatcher.getAllFlights()), response);
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

    /**
     * Returns staff initialized by data from request
     *
     * @param jsonObject jsonObject
     * @return staff
     */
    private Staff initializeStaff(JSONObject jsonObject) {
        String firstName = jsonObject.getString("firstName");
        String lastName = jsonObject.getString("lastName");
        int professionID = jsonObject.getInt("professionID");
        Profession profession = Profession.getProfessionByID(professionID);
        return new Staff(firstName, lastName, profession);
    }

    private void initUser(HttpServletResponse response) throws IOException {
        JSONObject responseData = new JSONObject();
        responseData.put("dispatcher", dispatcher.getUser().getLogin());
        response.setContentType("application/json");
        response.getWriter().write(responseData.toString());
    }
}
