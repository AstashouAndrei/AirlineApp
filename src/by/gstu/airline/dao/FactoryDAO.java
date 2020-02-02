package by.gstu.airline.dao;

import by.gstu.airline.dao.mysql.MySqlFactoryDAO;
import by.gstu.airline.exception.DBTypeException;
import by.gstu.airline.sql.ConfigurationManager;
import by.gstu.airline.sql.DataBaseType;

/**
 * FactoryDAO methods description
 */

public abstract class FactoryDAO {

    public abstract StaffDAO getStaffDAO();

    public abstract ItineraryDAO getItineraryDAO();

    public abstract CrewDAO getCrewDAO();

    public abstract PlaneDAO getPlaneDAO();

    public abstract FlightDAO getFlightDAO();

    public abstract UserDAO getUserDAO();

    /**
     * Method returns factory DAO according with data base type
     *
     * @return factory DAO
     * @throws DBTypeException DBTypeException
     */
    public static FactoryDAO getFactoryDAO() throws DBTypeException {
        ConfigurationManager manager = ConfigurationManager.getInstance();
        if (manager.DATA_BASE.equals(DataBaseType.MySQL.getDataBaseType())) {
            return new MySqlFactoryDAO();
        } else {
            throw new DBTypeException("Not supported database type: " + manager.DATA_BASE);
        }
    }
}
