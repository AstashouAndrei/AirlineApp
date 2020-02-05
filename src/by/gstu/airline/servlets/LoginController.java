package by.gstu.airline.servlets;

import by.gstu.airline.dao.FactoryDAO;
import by.gstu.airline.dao.UserDAO;
import by.gstu.airline.dao.mysql.MySqlCrewDAO;
import by.gstu.airline.entity.services.Access;
import by.gstu.airline.entity.services.User;
import by.gstu.airline.exception.DAOException;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static Logger logger = Logger.getLogger(LoginController.class.getName());

    public LoginController() {
        super();
    }

    private User user;
    private String path = "/index.html";
    private FactoryDAO factoryDAO = FactoryDAO.getFactoryDAO();
    private UserDAO userDAO = factoryDAO.getUserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

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
                path = "/AdminController";
            } else if (user.getAccess().equals(Access.DISPATCHER)) {
                path = "/DispatchController";
            }
            requestDispatcher = request.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        } else {
            out.write("<p id='welc' style='color: red; font-size: larger;'>You are not an authorised user! Please check with administrator!</p>");
            requestDispatcher = request.getRequestDispatcher(path);
            requestDispatcher.include(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

