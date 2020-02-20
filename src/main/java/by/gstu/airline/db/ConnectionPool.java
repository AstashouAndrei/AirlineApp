package main.java.by.gstu.airline.db;

import main.java.by.gstu.airline.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class with description of connecting method using connection pool
 */
public class ConnectionPool {

    private static Logger logger = Logger.getLogger(ConnectionPool.class.getName());

    private static ConnectionPool connectionPool = null;

    private List<Connection> availableConnections = new ArrayList<>();
    private List<Connection> usingConnections = new ArrayList<>();

    private static int connectionPoolSize = 20;

    private ConnectionPool() {
    }

    /**
     * Returns connection taking from available connections list, remove its from this list
     * and adds it into the using connection list
     *
     * @return connection
     * @throws SQLException SQLException
     */
    public Connection getConnection() throws SQLException {
        if (availableConnections.isEmpty()) {
            availableConnections.add(createConnection());
        }
        Connection connection = availableConnections.remove(availableConnections.size() - 1);
        usingConnections.add(connection);
        return connection;
    }

    /**
     * Returns an instance of connection pull with a specified size of available connections.
     * Creates new connection pull if its not exist, returns existing instance otherwise
     *
     * @return instance of connection pool
     */
    public static ConnectionPool createConnectionPool() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
            for (int i = 0; i < connectionPoolSize; i++) {
                connectionPool.availableConnections.add(createConnection());
            }
        }
        return connectionPool;
    }

    /**
     * Removes given connection from list of using connections and adds it
     * into the available connections list
     *
     * @param connection connection
     */
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            if (usingConnections.remove(connection)) {
                availableConnections.add(connection);
            }
        }
    }

    /**
     * Returns connection to data base
     * (database URL, login and password read from .properties file)
     *
     * @return connection
     * @throws DAOException DAOException
     */
    private static Connection createConnection() throws DAOException {
        ConfigurationManager manager = ConfigurationManager.getInstance();

        try {
            Class.forName(manager.DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error("Class not found: ", e);
        }
        Connection connection;

        try {
            connection = DriverManager.getConnection(manager.URL, manager.USER, manager.PASSWORD);
        } catch (SQLException e) {
            logger.error("Cannot create connection: ", e);
            throw new DAOException("Cannot create connection: ", e);
        }
        if (connection != null) {
            return connection;
        } else {
            throw new RuntimeException("Connection failed");
        }
    }

    /**
     * Method for closing given prepared statement
     *
     * @param statement statement
     * @throws DAOException DAOException
     */
    public void close(PreparedStatement statement) throws DAOException {
        if (statement != null) {
            try {
                statement.close();
                logger.trace("Prepared statement closed");
            } catch (SQLException e) {
                logger.error("Cannot close prepared statement: ", e);
                throw new DAOException("Cannot close prepared statement: ", e);
            }
        }
    }

    /**
     * Method for closing given result set
     *
     * @param resultSet result set
     * @throws DAOException DAOException
     */
    public void close(ResultSet resultSet) throws DAOException {
        if (resultSet != null) {
            try {
                resultSet.close();
                logger.trace("Result set closed");
            } catch (SQLException e) {
                logger.error("Cannot close result set: ", e);
                throw new DAOException("Cannot close result set: ", e);
            }
        }
    }
}
