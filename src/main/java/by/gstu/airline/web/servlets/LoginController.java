package main.java.by.gstu.airline.web.servlets;

import main.java.by.gstu.airline.dao.FactoryDAO;
import main.java.by.gstu.airline.dao.UserDAO;
import main.java.by.gstu.airline.entity.User;
import main.java.by.gstu.airline.exception.DAOException;
import main.java.by.gstu.airline.services.Access;
import main.java.by.gstu.airline.services.Commands;
import main.java.by.gstu.airline.web.Pages;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginController extends HttpServlet {

    private static Logger logger = Logger.getLogger(LoginController.class.getName());

    public LoginController() {
        super();
    }

    private String path;
    private User user;

    private FactoryDAO factoryDAO = FactoryDAO.getFactoryDAO();
    private UserDAO userDAO = factoryDAO.getUserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    /**
     * Handles request from login page. Sends a response with an error if
     * there is no in data base user with entered login and password or password is incorrect.
     * Otherwise, sets an attribute with a user from data base and forwards request
     * into the appropriate servlet according with a user access
     *
     * @param request  request
     * @param response response
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String login = request.getParameter("username");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        RequestDispatcher requestDispatcher;

        try {
            user = userDAO.getUserByLogin(login);
        } catch (DAOException e) {
            logger.error("Cannot get user by login from data base", e);
        }
        if (user != null && user.getPassword().equals(password)) {
            request.setAttribute("user", user);
            if (user.getAccess().equals(Access.ADMINISTRATOR)) {
                path = Pages.ADMINISTRATOR_CONTROLLER;
            } else if (user.getAccess().equals(Access.DISPATCHER)) {
                path = Pages.DISPATCHER_CONTROLLER;
            }
            requestDispatcher = request.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        } else {
            path = Pages.LOGIN_PAGE;
            out.write(Commands.ERROR_TEXT);
            requestDispatcher = request.getRequestDispatcher(path);
            requestDispatcher.include(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
