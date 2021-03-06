package main.java.by.gstu.airline.dao.mysql;

import main.java.by.gstu.airline.entity.CurrentState;
import main.java.by.gstu.airline.dao.StaffDAO;
import main.java.by.gstu.airline.entity.Staff;
import main.java.by.gstu.airline.entity.Profession;
import main.java.by.gstu.airline.exception.DAOException;
import main.java.by.gstu.airline.db.ConnectionPool;
import main.java.by.gstu.airline.db.SqlCommands;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class with Staff CRUD methods
 */

public class MySqlStaffDAO implements StaffDAO {

    private static Logger logger = Logger.getLogger(MySqlStaffDAO.class.getName());

    private ConnectionPool connectionPool = ConnectionPool.createConnectionPool();

    /**
     * Adds given staff to data base
     *
     * @param staff staff
     * @throws DAOException DAOException
     */
    @Override
    public void hireStaff(Staff staff) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("ADD_STAFF"),
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, staff.getFirstName());
            statement.setString(2, staff.getLastName());
            statement.setInt(3, staff.getProfession().getProfessionID());
            statement.executeUpdate();
            logger.trace("Create result set");
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            staff.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            logger.error("Cannot add staff to data base", e);
            throw new DAOException("Cannot add staff to data base", e);
        } finally {
            connectionPool.close(resultSet);
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Returns staff from data base by given id
     *
     * @param id staff id
     * @return staff
     * @throws DAOException DAOException
     */
    @Override
    public Staff getStaffByID(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Staff staff;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_STAFF_BY_ID"));
            statement.setInt(1, id);
            logger.trace("Create result set");
            resultSet = statement.executeQuery();
            resultSet.next();
            staff = initializeStaff(resultSet);
        } catch (SQLException e) {
            logger.error("Cannot get staff from data base by given id", e);
            throw new DAOException("Cannot get staff from data base by given id", e);
        } finally {
            connectionPool.close(resultSet);
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
        return staff;
    }

    /**
     * Returns staff from data base by given full name
     *
     * @param fullName staff id
     * @return staff
     * @throws DAOException DAOException
     */
    @Override
    public Staff getStaffByFullName(String fullName) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Staff staff;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_STAFF_BY_FULL_NAME"));
            statement.setString(1, fullName);
            logger.trace("Create result set");
            resultSet = statement.executeQuery();
            resultSet.next();
            staff = initializeStaff(resultSet);
        } catch (SQLException e) {
            logger.error("Cannot get staff from data base by given full name", e);
            throw new DAOException("Cannot get staff from data base by given full name", e);
        } finally {
            connectionPool.close(resultSet);
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
        return staff;
    }

    /**
     * Return list of staffs with given state
     *
     * @param state staff current state
     * @return list of staffs with given state
     * @throws DAOException DAOException
     */
    @Override
    public List<Staff> getStaffByState(CurrentState state) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Staff> staffList = new ArrayList<Staff>();
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_STAFFS_BY_STATE"));
            statement.setString(1, state.getState());
            logger.trace("Create result set");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Staff staff = initializeStaff(resultSet);
                staffList.add(staff);
            }
        } catch (SQLException e) {
            logger.error("Cannot get list of staffs from data base with given state", e);
            throw new DAOException("Cannot get list of staffs from data base with given state", e);
        } finally {
            connectionPool.close(resultSet);
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
        return staffList;
    }

    /**
     * Return list of standby staffs with given profession
     *
     * @param profession staff current state
     * @return list of staffs with given state
     * @throws DAOException DAOException
     */
    @Override
    public List<Staff> getStaffByProfession(Profession profession) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Staff> staffList = new ArrayList<Staff>();
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("GET_STANDBY_STAFFS_BY_PROFESSION"));
            statement.setInt(1, profession.getProfessionID());
            logger.trace("Create result set");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Staff staff = initializeStaff(resultSet);
                staffList.add(staff);
            }
        } catch (SQLException e) {
            logger.error("Cannot get list of staffs from data base with given profession", e);
            throw new DAOException("Cannot get list of staffs from data base with given profession", e);
        } finally {
            connectionPool.close(resultSet);
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
        return staffList;
    }

    /**
     * Changes staff current state into given one
     *
     * @param staff staff
     * @param state staff current state
     * @throws DAOException DAOException
     */
    @Override
    public void changeStaffState(Staff staff, CurrentState state) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("CHANGE_STAFF_STATE"));
            statement.setString(1, state.getState());
            statement.setInt(2, staff.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot change staff state into given one", e);
            throw new DAOException("Cannot change staff state into given one", e);
        } finally {
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Removes staff from date base by given id
     *
     * @param id staff id
     * @throws DAOException DAOException
     */
    @Override
    public void dismissStaffByID(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.trace("Open connection");
            connection = connectionPool.getConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("REMOVE_STAFF_BY_ID"));
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot remove staff from date base", e);
            throw new DAOException("Cannot remove staff from date base", e);
        } finally {
            connectionPool.close(statement);
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Initialize and returns staff by fields from data base
     *
     * @param resultSet result set
     * @return user
     * @throws DAOException DAOException
     */
    private Staff initializeStaff(ResultSet resultSet) throws DAOException {
        Staff staff;
        try {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            int professionID = resultSet.getInt("ProfessionID");
            String stateDescription = resultSet.getString("CurrentState");
            Profession profession = Profession.getProfessionByID(professionID);
            CurrentState state = CurrentState.getStateByDescription(stateDescription);
            staff = new Staff(firstName, lastName, profession);
            staff.setId(id);
            staff.setState(state);
        } catch (SQLException e) {
            logger.error("Cannot initialize staff", e);
            throw new DAOException("Cannot initialize staff", e);
        }
        return staff;
    }
}
