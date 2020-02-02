package by.gstu.airline.servlets;

import by.gstu.airline.dao.*;
import by.gstu.airline.entity.*;
import by.gstu.airline.exception.DAOException;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

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

    private static Logger logger = Logger.getLogger(DispatchController.class.getName());

    public DispatchController() {
        super();
    }

    private FactoryDAO factoryDAO = FactoryDAO.getFactoryDAO();
    private FlightDAO flightDAO = factoryDAO.getFlightDAO();
    private StaffDAO staffDAO = factoryDAO.getStaffDAO();
    private CrewDAO crewDAO = factoryDAO.getCrewDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

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
        Profession profession;

        switch (action) {
            case ("showAllFlights"):
                showFlights(response);
                break;
            case ("showNoCrewFlights"):
                showFlightsWithoutCrew(response);
                break;
            case ("showStaffs"):
                profession = Profession.getProfessionByID(jsonObject.getInt("profession"));
                showStaffsByProfession(response, profession);
                break;
            case ("addStaff"):
                Staff staff = initializeStaff(jsonObject);
                try {
                    staffDAO.hireStaff(staff);
                } catch (DAOException e) {
                    logger.error("Cannot add staff to data base", e);
                }
                break;
            case ("submitCrew"):
                List<Staff> cabinStaff = new ArrayList<>();
                JSONArray crewArray = jsonObject.getJSONArray("crew");
                try {
                    Flight flight = flightDAO.getIFlightByCode(jsonObject.getString("flightCode"));
                    for (int i = 0; i < crewArray.length(); i++) {
                        cabinStaff.add(staffDAO.getStaffByFullName(crewArray.getJSONObject(i).getString("staff")));
                    }
                    Crew crew = new Crew(flight.getId(), cabinStaff);
                    crewDAO.addCrew(crew);
                    for (Staff tempStaff : crew.getCabinStaff()) {
                        staffDAO.changeStaffState(tempStaff, CurrentState.SCHEDULED);
                    }
                    flightDAO.changeFlightState(flight, CurrentState.SCHEDULED);
                } catch (DAOException e) {
                    logger.error("Cannot submit crew for flight", e);
                }
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
            logger.error("Cannot get list of flights from data base", e);
        }
    }

    /**
     * Getting from data base list of airline flights without crew (standby flights)
     *
     * @param response response
     * @throws IOException IOException
     */
    private void showFlightsWithoutCrew(HttpServletResponse response) throws IOException {
        try {
            List<Flight> flightList = flightDAO.getFlightsByState(CurrentState.STANDBY);
            JSONArray array = new JSONArray(flightList);
            response.setContentType("application/json");
            response.getWriter().write(array.toString());
        } catch (DAOException e) {
            logger.error("Cannot get list of flights without crew from data base", e);
        }
    }

    /**
     * Getting from data base list of airline staff with given profession and sending it in servlet response
     *
     * @param response   response
     * @param profession profession
     * @throws IOException
     */
    private void showStaffsByProfession(HttpServletResponse response, Profession profession) throws IOException {
        try {
            List<Staff> staffs = factoryDAO.getStaffDAO().getStaffByProfession(profession);
            JSONArray array = new JSONArray(staffs);
            response.setContentType("application/json");
            response.getWriter().write(array.toString());
        } catch (DAOException e) {
            logger.error("Cannot get list of staffs with given profession from data base", e);
        }
    }

    /**
     * Returns staff initialized by data from request
     * @param jsonObject jsonObject
     * @return staff
     */
    private Staff initializeStaff(JSONObject jsonObject) {
        Staff staff;
        String firstName = jsonObject.getString("firstName");
        String lastName = jsonObject.getString("lastName");
        int professionID = jsonObject.getInt("professionID");
        Profession profession = Profession.getProfessionByID(professionID);
        staff = new Staff(firstName, lastName, profession);
        return staff;
    }
}
