package by.gstu.airline.servlets;


import by.gstu.airline.dao.FactoryDAO;
import by.gstu.airline.dao.UserDAO;
import by.gstu.airline.entity.services.Access;
import by.gstu.airline.entity.services.User;
import by.gstu.airline.exception.DAOException;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/UserController")
public class UserController extends HttpServlet {

    private static Logger logger = Logger.getLogger(UserController.class.getName());

    public UserController() {
        super();
    }

    private FactoryDAO factoryDAO = FactoryDAO.getFactoryDAO();
    private UserDAO userDAO = factoryDAO.getUserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message;
        StringBuilder builder = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        JSONObject jsonObject = new JSONObject(builder.toString());
        String action = jsonObject.getString("action");
        String login = jsonObject.getString("login");

        if ("register".equals(action)) {
            message = userRegister(jsonObject);
        } else {
            message = userLogin(jsonObject);
        }
        JSONObject responseData = new JSONObject();
        responseData.put("message", message);
        responseData.put("login", login);
        response.setContentType("application/json");
        response.getWriter().write(responseData.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Method checks users data base for existence entered login
     * and password and it's matching
     *
     * @param jsonObject jsonObject
     * @return text message of checking result
     */
    private String userLogin(JSONObject jsonObject) {
        String login = jsonObject.getString("login");
        String password = jsonObject.getString("password");
        String message;
        try {
            User user = userDAO.getUserByLogin(login);
            if (user.getPassword().equals(password)) {
                message = user.getAccess().getAccess();
            } else {
                message = "wrongpassword";
            }
        } catch (DAOException e) {
            message = "notexist";
            logger.error("User with login " + login + " not found", e);
        }
        return message;
    }

    /**
     * Method adds to users data base an user, which creating with entered login,
     * password, email and access level if it's not already exist in above data base
     *
     * @param jsonObject jsonObject
     * @return text message of adding result
     */
    private String userRegister(JSONObject jsonObject) {

        String login = jsonObject.getString("login");
        String password = jsonObject.getString("password");
        String email = jsonObject.getString("email");
        int accessLevel = jsonObject.getInt("access");
        String message;

        try {
            User regUser = new User(login, password, email, Access.getAccessByID(accessLevel));
            userDAO.addUser(regUser);
            message = "regsuccess";
        } catch (DAOException e) {
            message = "exist";
            logger.error("User with login " + login + " already exist", e);
        }
        return message;
    }
}
