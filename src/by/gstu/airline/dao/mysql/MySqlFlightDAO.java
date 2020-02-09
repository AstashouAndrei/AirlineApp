package by.gstu.airline.dao.mysql;

import by.gstu.airline.dao.FlightDAO;
import by.gstu.airline.entity.CurrentState;
import by.gstu.airline.entity.Flight;
import by.gstu.airline.entity.Itinerary;
import by.gstu.airline.entity.Plane;
import by.gstu.airline.exception.DAOException;
import by.gstu.airline.sql.ConnectionPool;
import by.gstu.airline.sql.SqlCommands;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class with Flight CRUD methods
 */
public class MySqlFlightDAO implements FlightDAO {

    private static Logger logger = Logger.getLogger(MySqlFlightDAO.class.getName());

    private ConnectionPool connectionPool = ConnectionPool.createConnectionPool();

    private MySqlItineraryDAO itineraryDAO = new MySqlItineraryDAO();
    private MySqlPlaneDAO planeDAO = new MySqlPlaneDAO();

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
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("ADD_FLIGHT"),
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, flight.getItinerary().getId());
            statement.setInt(2, flight.getPlane().getID());
            statement.setString(3, CurrentState.STANDBY.getState());
            statement.executeUpdate();
            logger.trace("Create result set");
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            flight.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            logger.error("Cannot add flight to data base", e);
            throw new DAOException("Cannot add flight to data base", e);
        } finally {
            connectionPool.close(resultSet);
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
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
        Flight flight;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_FLIGHT_BY_ID"));
            statement.setInt(1, id);
            logger.trace("Create result set");
            resultSet = statement.executeQuery();
            resultSet.next();
            flight = initializeFlight(resultSet);
        } catch (SQLException e) {
            logger.error("Cannot get flight from data base", e);
            throw new DAOException("Cannot get flight from data base", e);
        } finally {
            connectionPool.close(resultSet);
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
        return flight;
    }

    /**
     * Returns flight from data base by given flight code
     *
     * @param flightCode flightCode
     * @return flight
     * @throws DAOException DAOException
     */
    @Override
    public Flight getIFlightByCode(String flightCode) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Flight flight;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_FLIGHT_BY_CODE"));
            statement.setString(1, flightCode);
            logger.trace("Create result set");
            resultSet = statement.executeQuery();
            resultSet.next();
            flight = initializeFlight(resultSet);
        } catch (SQLException e) {
            logger.error("Cannot get flight from data base", e);
            throw new DAOException("Cannot get flight from data base", e);
        } finally {
            connectionPool.close(resultSet);
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
        return flight;
    }

    /**
     * Returns list of all airline flights from data base
     *
     * @return list of flights
     * @throws DAOException DAOException
     */
    @Override
    public List<Flight> getFlightsList() throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Flight> flightsList = new ArrayList<Flight>();
        Flight flight = null;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_FLIGHTS_LIST"));
            logger.trace("Create result set");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flight = initializeFlight(resultSet);
                flightsList.add(flight);
            }
        } catch (SQLException e) {
            logger.error("Cannot get list of staffs from data base", e);
            throw new DAOException("Cannot get list of staffs from data base", e);
        } finally {
            connectionPool.close(resultSet);
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
        return flightsList;
    }

    /**
     * Returns list of airline flights which haven't given state from data base
     *
     * @param state state
     * @return flight
     * @throws DAOException DAOException
     */
    @Override
    public List<Flight> getFlightsExceptState(CurrentState state) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Flight> flightsList = new ArrayList<Flight>();
        Flight flight = null;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_FLIGHTS_EXCEPT_STATE"));
            statement.setString(1, state.getState());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flight = initializeFlight(resultSet);
                flightsList.add(flight);
            }
        } catch (SQLException e) {
            logger.error("Cannot get list of staffs from data base with given state", e);
            throw new DAOException("Cannot get list of staffs from data base with given state", e);
        } finally {
            connectionPool.close(resultSet);
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
        return flightsList;
    }

    /**
     * Returns list of airline flights which have given state from data base
     *
     * @param state state
     * @return flight
     * @throws DAOException DAOException
     */
    @Override
    public List<Flight> getFlightsByState(CurrentState state) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Flight> flightsList = new ArrayList<Flight>();
        Flight flight = null;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_FLIGHTS_BY_STATE"));
            statement.setString(1, state.getState());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flight = initializeFlight(resultSet);
                flightsList.add(flight);
            }
        } catch (SQLException e) {
            logger.error("Cannot get list of staffs from data base with given state", e);
            throw new DAOException("Cannot get list of staffs from data base with given state", e);
        } finally {
            connectionPool.close(resultSet);
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
        return flightsList;
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
            connection = connectionPool.getConnection();
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
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Changes flight current state into given one
     *
     * @param flight flight
     * @param state  state
     * @throws DAOException DAOException
     */
    @Override
    public void changeFlightState(Flight flight, CurrentState state) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("CHANGE_FLIGHT_STATE"));
            statement.setString(1, state.getState());
            statement.setInt(2, flight.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot change flight state in data base", e);
            throw new DAOException("Cannot change flight state in data base", e);
        } finally {
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Removes flight from data base by given flight code
     *
     * @param flightCode flight code
     * @throws DAOException DAOException
     */
    @Override
    public void removeFlight(String flightCode) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("REMOVE_FLIGHT_BY_CODE"));
            statement.setString(1, flightCode);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot remove flight from data base", e);
            throw new DAOException("Cannot remove flight from data base", e);
        } finally {
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
    }


    /**
     * Returns flight initialized by data from result set
     *
     * @param resultSet resultSet
     * @return flight
     * @throws DAOException DAOException
     */
    private Flight initializeFlight(ResultSet resultSet) throws DAOException {
        Flight flight;
        try {
            int id = resultSet.getInt("id");
            CurrentState state = CurrentState.getStateByDescription(resultSet.getString("CurrentState"));
            Itinerary itinerary = itineraryDAO.getItineraryByID(resultSet.getInt("ItineraryID"));
            Plane plane = planeDAO.getPlaneByID(resultSet.getInt("PlaneID"));
            flight = new Flight(itinerary, plane);
            flight.setId(id);
            flight.setState(state);
        } catch (SQLException e) {
            logger.error("Cannot initialize flight", e);
            throw new DAOException("Cannot initialize flight", e);
        }
        return flight;
    }
}
