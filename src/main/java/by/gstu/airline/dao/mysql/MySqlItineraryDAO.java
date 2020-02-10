package main.java.by.gstu.airline.dao.mysql;

import main.java.by.gstu.airline.dao.ItineraryDAO;
import main.java.by.gstu.airline.entity.Itinerary;
import main.java.by.gstu.airline.exception.DAOException;
import main.java.by.gstu.airline.db.ConnectionPool;
import main.java.by.gstu.airline.db.SqlCommands;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * DAO class with Itinerary CRUD methods
 */
public class MySqlItineraryDAO implements ItineraryDAO {

    private static Logger logger = Logger.getLogger(MySqlItineraryDAO.class.getName());

    private ConnectionPool connectionPool = ConnectionPool.createConnectionPool();

    /**
     * Method adds given itinerary to data base
     *
     * @param itinerary itinerary
     * @throws DAOException DAOException
     */
    @Override
    public void addItinerary(Itinerary itinerary) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("ADD_ITINERARY"),
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, itinerary.getFlightCode());
            statement.setString(2, itinerary.getDeparture());
            statement.setString(3, itinerary.getArrival());
            statement.executeUpdate();
            logger.trace("Create result set");
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            itinerary.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            logger.error("Cannot add itinerary to data base", e);
            throw new DAOException("Cannot add itinerary to data base", e);
        } finally {
            connectionPool.close(resultSet);
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Returns itinerary from data base by given id
     *
     * @param id itinerary id
     * @return itinerary
     * @throws DAOException DAOException
     */
    @Override
    public Itinerary getItineraryByID(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Itinerary itinerary = null;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_ITINERARY_BY_ID"));
            statement.setInt(1, id);
            logger.trace("Create result set");
            resultSet = statement.executeQuery();
            resultSet.next();
            String flightCode = resultSet.getString("FlightCode");
            String departure = resultSet.getString("Departure");
            String arrival = resultSet.getString("Arrival");
            itinerary = new Itinerary(flightCode, departure, arrival);
            itinerary.setId(id);
        } catch (SQLException e) {
            logger.error("Cannot get itinerary from data base", e);
            throw new DAOException("Cannot get itinerary from data base", e);
        } finally {
            connectionPool.close(resultSet);
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
        return itinerary;
    }

    /**
     * Changing itinerary flight code on given one
     *
     * @param itinerary  itinerary
     * @param flightCode flight code
     * @throws DAOException DAOException
     */
    @Override
    public void changeItineraryFlightCode(Itinerary itinerary, String flightCode) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("CHANGE_FLIGHT_CODE"));
            statement.setString(1, flightCode);
            statement.setInt(2, itinerary.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot change itinerary flight code in data base", e);
            throw new DAOException("Cannot change itinerary flight code in data base", e);
        } finally {
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Removes itinerary from data base by given id
     *
     * @param id itinerary id
     * @throws DAOException DAOException
     */
    @Override
    public void removeItineraryByID(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("REMOVE_ITINERARY_BY_ID"));
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot remove itinerary from data base", e);
            throw new DAOException("Cannot remove itinerary from data base", e);
        } finally {
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
    }
}
