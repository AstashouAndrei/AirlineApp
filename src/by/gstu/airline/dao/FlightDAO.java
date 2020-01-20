package by.gstu.airline.dao;

import by.gstu.airline.entity.Flight;
import by.gstu.airline.entity.Itinerary;
import by.gstu.airline.entity.Plane;
import by.gstu.airline.exception.DAOException;

/**
 * FlightDAO methods description
 */
public interface FlightDAO {

    void addFlight(Flight flight) throws DAOException;

    Flight getIFlightByID(int ID) throws DAOException;

    void changeFlightData(Flight flight, Plane plane, Itinerary itinerary) throws DAOException;

    void removeFlightByID(int ID) throws DAOException;
}
