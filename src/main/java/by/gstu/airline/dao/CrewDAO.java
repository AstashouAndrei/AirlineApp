package main.java.by.gstu.airline.dao;

import main.java.by.gstu.airline.entity.Crew;
import main.java.by.gstu.airline.exception.DAOException;


/**
 * CrewDAO methods description
 */
public interface CrewDAO {

    void addCrew(Crew team)  throws DAOException;

    Crew getCrewByFlightID(int flightID)  throws DAOException;

    void removeCrewByFlightID(int flightID)  throws DAOException;
}
