package by.gstu.airline.dao.mysql;

import by.gstu.airline.dao.FlightDAO;
import by.gstu.airline.entity.Flight;
import by.gstu.airline.entity.Itinerary;
import by.gstu.airline.entity.Plane;
import by.gstu.airline.exception.DAOException;
import by.gstu.airline.sql.SqlCommands;
import by.gstu.airline.sql.SqlConnection;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * DAO class with Flight CRUD methods
 */
public class MySqlFlightDAO implements FlightDAO {

    private static Logger logger = Logger.getLogger(MySqlFlightDAO.class.getName());

    /**
     * Method adds given flight to data base
     *
     * @param flight flight
     * @throws DAOException DAOException
     */
    @Override
    public void addFlight(Flight flight) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            logger.trace("Open connection");
            connection = SqlConnection.createConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("ADD_FLIGHT"),
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, flight.getItinerary().getId());
            statement.setInt(2, flight.getPlane().getID());
            statement.executeUpdate();
            logger.trace("Create result set");
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            flight.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            logger.error("Cannot add flight to data base", e);
            throw new DAOException("Cannot add flight to data base", e);
        } finally {
            SqlConnection.close(resultSet);
            SqlConnection.close(statement);
            SqlConnection.close(connection);
        }
    }

    /**
     * Returns flight from data base by given flight id
     *
     * @param id flight id
     * @return flight
     * @throws DAOException DAOException
     */
    @Override
    public Flight getIFlightByID(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Flight flight = null;
        try {
            logger.trace("Open connection");
            connection = SqlConnection.createConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_FLIGHT_BY_ID"));
            statement.setInt(1, id);
            logger.trace("Create result set");
            resultSet = statement.executeQuery();
            resultSet.next();
            MySqlItineraryDAO itineraryDAO = new MySqlItineraryDAO();
            MySqlPlaneDAO planeDAO = new MySqlPlaneDAO();
            Itinerary itinerary = itineraryDAO.getItineraryByID(resultSet.getInt("ItineraryID"));
            Plane plane = planeDAO.getPlaneByID(resultSet.getInt("PlaneID"));
            flight = new Flight(itinerary, plane);
            flight.setId(id);
        } catch (SQLException e) {
            logger.error("Cannot get flight from data base", e);
            throw new DAOException("Cannot get flight from data base", e);
        } finally {
            SqlConnection.close(resultSet);
            SqlConnection.close(statement);
            SqlConnection.close(connection);
        }
        return flight;
    }

    /**
     * Change flight data (plane and itinerary) on given ones
     *
     * @param flight    flight
     * @param plane     plane
     * @param itinerary itinerary
     * @throws DAOException DAOException
     */
    @Override
    public void changeFlightData(Flight flight, Plane plane, Itinerary itinerary) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.trace("Open connection");
            connection = SqlConnection.createConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("CHANGE_FLIGHT_DATA"));
            statement.setInt(1, plane.getID());
            statement.setInt(2, itinerary.getId());
            statement.setInt(3, flight.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot change flight data in data base", e);
            throw new DAOException("Cannot change flight data in data base", e);
        } finally {
            SqlConnection.close(statement);
            SqlConnection.close(connection);
        }
    }

    /**
     * Removes flight from data base by given id
     *
     * @param id flight id
     * @throws DAOException DAOException
     */
    @Override
    public void removeFlightByID(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.trace("Open connection");
            connection = SqlConnection.createConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("REMOVE_FLIGHT_BY_ID"));
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot remove flight from data base", e);
            throw new DAOException("Cannot remove flight from data base", e);
        } finally {
            SqlConnection.close(statement);
            SqlConnection.close(connection);
        }
    }
}
