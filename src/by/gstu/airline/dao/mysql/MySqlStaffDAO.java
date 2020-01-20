package by.gstu.airline.dao.mysql;

import by.gstu.airline.entity.CurrentState;
import by.gstu.airline.dao.StaffDAO;
import by.gstu.airline.entity.Staff;
import by.gstu.airline.entity.Profession;
import by.gstu.airline.exception.DAOException;
import by.gstu.airline.sql.SqlCommands;
import by.gstu.airline.sql.SqlConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class with Staff CRUD methods
 */

public class MySqlStaffDAO implements StaffDAO {

    private static Logger logger = Logger.getLogger(MySqlStaffDAO.class.getName());

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
            connection = SqlConnection.createConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("ADD_STAFF"),
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, staff.getFirstName());
            statement.setString(2, staff.getLastName());
            statement.setInt(3, staff.getProfession().getProfessionID());
            statement.setString(4, CurrentState.STANDBY.getState());
            statement.executeUpdate();
            logger.trace("Create result set");
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            staff.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            logger.error("Cannot add staff to data base", e);
            throw new DAOException("Cannot add staff to data base", e);
        } finally {
            SqlConnection.close(resultSet);
            SqlConnection.close(statement);
            SqlConnection.close(connection);
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
            connection = SqlConnection.createConnection();
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
            SqlConnection.close(resultSet);
            SqlConnection.close(statement);
            SqlConnection.close(connection);
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
            connection = SqlConnection.createConnection();
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
            SqlConnection.close(resultSet);
            SqlConnection.close(statement);
            SqlConnection.close(connection);
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
            connection = SqlConnection.createConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("CHANGE_STAFF_STATE"));
            statement.setString(1, state.getState());
            statement.setInt(2, staff.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot change staff state into given one", e);
            throw new DAOException("Cannot change staff state into given one", e);
        } finally {
            SqlConnection.close(statement);
            SqlConnection.close(connection);
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
            connection = SqlConnection.createConnection();
            logger.trace("Create prepared statement");
            statement = connection.prepareStatement(SqlCommands.getCommand("REMOVE_STAFF_BY_ID"));
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot remove staff from date base", e);
            throw new DAOException("Cannot remove staff from date base", e);
        } finally {
            SqlConnection.close(statement);
            SqlConnection.close(connection);
        }
    }

    /**
     * Initialize and returns user by fields from data base
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
            Profession profession = Profession.getProfessionByID(professionID);
            staff = new Staff(firstName, lastName, profession);
            staff.setId(id);
        } catch (SQLException e) {
            logger.error("Cannot initialize staff", e);
            throw new DAOException("Cannot initialize staff", e);
        }
        return staff;
    }
}
