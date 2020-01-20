package by.gstu.airline.sql;

import by.gstu.airline.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Class with description of connecting method
 */

public class SqlConnection {

    private static Logger logger = Logger.getLogger(SqlConnection.class.getName());

    /**
     * Returns connection to data base
     * (database URL, login and password read from .properties file)
     *
     * @return connection
     * @throws DAOException DAOException
     */
    public static Connection createConnection() throws DAOException {
        ConfigurationManager manager = ConfigurationManager.getInstance();

        try {
            Class.forName(manager.DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error("Class not found", e);
        }
        Connection connection;

        try {
            connection = DriverManager.getConnection(manager.URL, manager.USER, manager.PASSWORD);
        } catch (SQLException e) {
            logger.error("Cannot create connection", e);
            throw new DAOException("Cannot create connection", e);
        }
        if (connection != null) {
            return connection;
        } else {
            throw new RuntimeException("Connection failed");
        }

    }

    /**
     * Method for closing given result set
     *
     * @param resultSet result set
     * @throws DAOException DAOException
     */
    public static void close(ResultSet resultSet) throws DAOException {
        if (resultSet != null) {
            try {
                resultSet.close();
                logger.trace("Result set closed");
            } catch (SQLException e) {
                logger.error("Cannot close result set", e);
                throw new DAOException("Cannot close result set", e);
            }
        }
    }

    /**
     * Method for closing given prepared statement
     *
     * @param statement statement
     * @throws DAOException DAOException
     */
    public static void close(PreparedStatement statement) throws DAOException {
        if (statement != null) {
            try {
                statement.close();
                logger.trace("Prepared statement closed");
            } catch (SQLException e) {
                logger.error("Cannot close prepared statement", e);
                throw new DAOException("Cannot close prepared statement", e);
            }
        }
    }

    /**
     * Method for closing given connection
     *
     * @param connection connection
     * @throws DAOException DAOException
     */
    public static void close(Connection connection) throws DAOException {
        if (connection != null) {
            try {
                connection.close();
                logger.trace("Connection closed");
            } catch (SQLException e) {
                logger.error("Cannot close connection", e);
                throw new DAOException("Cannot close connection", e);
            }
        }
    }

}
