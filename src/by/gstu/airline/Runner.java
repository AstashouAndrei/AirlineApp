package by.gstu.airline;

import by.gstu.airline.dao.*;

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
