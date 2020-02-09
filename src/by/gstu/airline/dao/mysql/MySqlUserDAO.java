package by.gstu.airline.dao.mysql;

import by.gstu.airline.dao.UserDAO;
import by.gstu.airline.services.Access;
import by.gstu.airline.services.User;
import by.gstu.airline.exception.DAOException;
import by.gstu.airline.sql.SqlCommands;
import by.gstu.airline.sql.SqlConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class with User CRUD methods
 */
public class MySqlUserDAO implements UserDAO {

    private static Logger logger = Logger.getLogger(MySqlUserDAO.class.getName());

    /**
     * Method adds given user to data base
     *
     * @param user user
     * @throws DAOException DAOException
     */
    @Override
    public void addUser(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            logger.trace("Open connection");
            connection = SqlConnection.createConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("ADD_USER"),
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getAccess().getAccessID());
            statement.executeUpdate();
            logger.trace("Create result set");
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getInt(1));
            logger.trace("Adds to data base user with id: " + user.getId());
        } catch (SQLException e) {
            logger.error("Cannot add user to data base", e);
            throw new DAOException("Cannot add user to data base", e);
        } finally {
            SqlConnection.close(resultSet);
            SqlConnection.close(statement);
            SqlConnection.close(connection);
        }
    }

    /**
     * Returns user from data base by given id
     *
     * @param id user id
     * @throws DAOException DAOException
     */
    @Override
    public User getUserById(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user;
        try {
            logger.trace("Open connection");
            connection = SqlConnection.createConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_USER_BY_ID"));
            statement.setInt(1, id);
            logger.trace("Create result set");
            resultSet = statement.executeQuery();
            resultSet.next();
            user = initializeUser(resultSet);
            user.setId(resultSet.getInt("id"));
            logger.trace("Returning user with id" + user.getId());
        } catch (SQLException e) {
            logger.error("Cannot get user by id from data base", e);
            throw new DAOException("Cannot get user by id from data base", e);
        } finally {
            SqlConnection.close(resultSet);
            SqlConnection.close(statement);
            SqlConnection.close(connection);
        }
        return user;
    }

    /**
     * Returns user from data base by given login
     *
     * @param login user login
     * @return user
     * @throws DAOException DAOException
     */
    @Override
    public User getUserByLogin(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user;
        try {
            logger.trace("Open connection");
            connection = SqlConnection.createConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_USER_BY_LOGIN"));
            statement.setString(1, login);
            logger.trace("Create result set");
            resultSet = statement.executeQuery();
            resultSet.next();
            user = initializeUser(resultSet);
            user.setId(resultSet.getInt(1));
            logger.trace("Returning user with login" + user.getLogin());
        } catch (SQLException e) {
            logger.error("Cannot get user by login from data base", e);
            throw new DAOException("Cannot get user by login from data base", e);
        } finally {
            SqlConnection.close(resultSet);
            SqlConnection.close(statement);
            SqlConnection.close(connection);
        }
        return user;
    }

    /**
     * Returns list of users from data base with given access level
     *
     * @param access user access
     * @return list of users
     * @throws DAOException DAOException
     */
    @Override
    public List<User> getUsers(Access access) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<User>();
        try {
            logger.trace("Open connection");
            connection = SqlConnection.createConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_USERS_BY_ACCESS"));
            statement.setInt(1, access.getAccessID());
            logger.trace("Create result set");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = initializeUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Cannot get list of users by access from data base", e);
            throw new DAOException("Cannot get list of users by access from data base", e);
        } finally {
            SqlConnection.close(resultSet);
            SqlConnection.close(statement);
            SqlConnection.close(connection);
        }
        return users;
    }

    /**
     * Removes user with given id from data base
     *
     * @param id user id
     * @throws DAOException DAOException
     */
    @Override
    public void removeUserById(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.trace("Open connection");
            connection = SqlConnection.createConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("REMOVE_USER_BY_ID"));
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot remove user from data base", e);
            throw new DAOException("Cannot remove user from data base", e);
        } finally {
            SqlConnection.close(statement);
            SqlConnection.close(connection);
        }
    }

    /**
     * Initialize and returns user by fields from data base
     *
     * @param resultSet resultset
     * @return user
     * @throws DAOException DAOException
     */
    private User initializeUser(ResultSet resultSet) throws DAOException {
        User user;
        try {
            String login = resultSet.getString("Login");
            String password = resultSet.getString("Password");
            String email = resultSet.getString("Email");
            int accessID = resultSet.getInt("AccessID");
            Access access = Access.getAccessByID(accessID);
            user = new User(login, password, email, access);
        } catch (SQLException e) {
            logger.error("Cannot initialize user", e);
            throw new DAOException("Cannot initialize user", e);
        }
        return user;
    }
}
