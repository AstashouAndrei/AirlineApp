package main.java.by.gstu.airline;

import main.java.by.gstu.airline.dao.*;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Class for running application
 */
public class Runner {

    public static void main(String[] args) throws Exception {

       FactoryDAO factoryDAO = FactoryDAO.getFactoryDAO();
        CrewDAO crewDAO = factoryDAO.getCrewDAO();
        StaffDAO staffDAO = factoryDAO.getStaffDAO();
        System.out.println(staffDAO.getStaffByFullName("Bruce Cross"));

    }
}
