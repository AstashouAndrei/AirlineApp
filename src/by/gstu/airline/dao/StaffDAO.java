package by.gstu.airline.dao;

import by.gstu.airline.entity.CurrentState;
import by.gstu.airline.entity.Profession;
import by.gstu.airline.entity.Staff;
import by.gstu.airline.exception.DAOException;

import java.util.List;

/**
 * StaffDAO methods description
 */
public interface StaffDAO {

    void hireStaff(Staff member) throws DAOException;

    Staff getStaffByID(int ID) throws DAOException;

    Staff getStaffByFullName(String fullName) throws DAOException;

    List<Staff> getStaffByState(CurrentState state) throws DAOException;

    List<Staff> getStaffByProfession(Profession profession) throws DAOException;

    void changeStaffState(Staff member, CurrentState state) throws DAOException;

    void dismissStaffByID(int ID)  throws DAOException;
}
