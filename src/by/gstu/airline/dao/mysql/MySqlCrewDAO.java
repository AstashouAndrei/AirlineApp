package by.gstu.airline.dao.mysql;

import by.gstu.airline.entity.*;
import by.gstu.airline.dao.CrewDAO;
import by.gstu.airline.exception.DAOException;
import by.gstu.airline.sql.ConnectionPool;
import by.gstu.airline.sql.SqlCommands;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class with Crew CRUD methods
 */
public class MySqlCrewDAO implements CrewDAO {

    private static Logger logger = Logger.getLogger(MySqlCrewDAO.class.getName());

    private ConnectionPool connectionPool = ConnectionPool.createConnectionPool();

    /**
     * Method adds given Crew to data base
     *
     * @param crew crew
     * @throws DAOException DAOException
     */
    @Override
    public void addCrew(Crew crew)  throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("ADD_CREW"));
            for (Staff staff : crew.getCabinStaff()) {
                statement.setInt(1, crew.getFlightID());
                statement.setInt(2, staff.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Cannot add crew to data base", e);
            throw new DAOException("Cannot add crew to data base", e);
        } finally {
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Returns crew from data base by given flight id
     *
     * @param flightID flight id
     * @return crew
     * @throws DAOException DAOException
     */
    @Override
    public Crew getCrewByFlightID(int flightID) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Staff> cabinStaff = new ArrayList<Staff>();
        Crew crew;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_CREW_BY_FLIGHT_ID"));
            statement.setInt(1, flightID);
            logger.trace("Create result set");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MySqlStaffDAO mySqlStaffDAO = new MySqlStaffDAO();
                Staff staff = mySqlStaffDAO.getStaffByID(resultSet.getInt("StaffID"));
                int staffID = resultSet.getInt("StaffID");
                staff.setId(staffID);
                cabinStaff.add(staff);
            }
            crew = new Crew(flightID, cabinStaff);
        } catch (SQLException e) {
            logger.error("Cannot get crew from data base", e);
            throw new DAOException("Cannot get crew from data base", e);
        } finally {
            {
                connectionPool.close(resultSet);
                connectionPool.close(statement);
                connectionPool.releaseConnection(connection);
            }
        }
        return crew;
    }

    /**
     * Removes crew from data base by given id
     *
     * @param flightID flight id
     * @throws DAOException DAOException
     */
    @Override
    public void removeCrewByFlightID(int flightID) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("REMOVE_CREW_BY_FLIGHT_ID"));
            statement.setInt(1, flightID);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot remove crew from data base", e);
            throw new DAOException("Cannot remove crew from data base", e);
        } finally {
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
    }
}
