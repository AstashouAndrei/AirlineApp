package by.gstu.airline.dao;

import by.gstu.airline.entity.Plane;
import by.gstu.airline.exception.DAOException;

/**
 * PlaneDAO methods description
 */
public interface PlaneDAO {

    void addPlane(Plane plane) throws DAOException;

    Plane getPlaneByID(int ID) throws DAOException;

    void changePassengersCapacity(Plane plane, int passengerCapacity) throws DAOException;

    void removePlaneByID(int ID) throws DAOException;
}
