package by.gstu.airline.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Class with logic building crew with random staff
 */
public class RandomCrewHandler {

    /**
     * Returns first staff from given list with given profession
     * and delete it  from list
     *
     * @param list       list of staff
     * @param profession profession
     * @return staff
     * @throws RuntimeException RuntimeException
     */
    private static Staff getStaffByProfession(List<Staff> list, Profession profession) throws RuntimeException {
        Staff temp = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProfession().equals(profession)) {
                temp = list.get(i);
                list.remove(i);
                break;
            }
        }
        if (temp != null) {
            return temp;
        } else {
            throw new RuntimeException("No available staff");
        }
    }

    /**
     * Returns list of random staff from given list of staff,
     * depending on plane passenger capacity
     *
     * @param staff             staff
     * @param passengerCapacity plane passenger capacity
     * @return list of staff
     */
    public static List<Staff> getStaffForPlane(List<Staff> staff, int passengerCapacity) {
        List<Staff> team = new ArrayList<Staff>();
        team.add(getStaffByProfession(staff, Profession.CAPTAIN));
        team.add(getStaffByProfession(staff, Profession.OFFICER));
        team.add(getStaffByProfession(staff, Profession.NAVIGATOR));
        team.add(getStaffByProfession(staff, Profession.RADIOMAN));
        for (int i = 0; i < passengerCapacity; i++) {
            team.add(getStaffByProfession(staff, Profession.ATTENDANT));
        }
        return team;
    }
}
