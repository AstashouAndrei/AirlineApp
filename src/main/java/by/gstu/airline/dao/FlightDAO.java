package main.java.by.gstu.airline.dao;

import main.java.by.gstu.airline.entity.CurrentState;
import main.java.by.gstu.airline.entity.Flight;
import main.java.by.gstu.airline.entity.Itinerary;
import main.java.by.gstu.airline.entity.Plane;
import main.java.by.gstu.airline.exception.DAOException;

import java.util.List;

/**
 * FlightDAO methods description
 */
public interface FlightDAO {

    void addFlight(Flight flight) throws DAOException;

    Flight getIFlightByID(int ID) throws DAOException;

    Flight getIFlightByCode(String flightCode) throws DAOException;

    void changeFlightData(Flight flight, Plane plane, Itinerary itinerary) throws DAOException;

    void changeFlightState(Flight flight, CurrentState state) throws DAOException;

    List<Flight> getFlightsList() throws DAOException;

    List<Flight> getFlightsExceptState(CurrentState state) throws DAOException;

    List<Flight> getFlightsByState(CurrentState state) throws DAOException;

    void removeFlight(String flightCode) throws DAOException;
}
