package by.gstu.airline.entity;

import by.gstu.airline.exception.ProfessionException;

/**
 * User profession type enumeration
 */
public enum Profession {

    CAPTAIN("Captain", 1),
    OFFICER("First Officer", 2),
    NAVIGATOR("Navigator", 3),
    RADIOMAN("Radio Operator", 4),
    ATTENDANT("Flight Attendant", 5);

    private String profession;
    private int professionID;

    Profession(String profession, int professionID) {
        this.profession = profession;
        this.professionID = professionID;
    }

    /**
     * Returns profession by given ID
     *
     * @param id profession id
     * @return profession
     */
    public static Profession getProfessionByID(int id) {
        switch (id) {
            case 1:
                return CAPTAIN;
            case 2:
                return OFFICER;
            case 3:
                return NAVIGATOR;
            case 4:
                return RADIOMAN;
            case 5:
                return ATTENDANT;
            default:
                throw new ProfessionException("Incorrect profession ID: " + id);
        }
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getProfessionID() {
        return professionID;
    }

    public void setProfessionID(int professionID) {
        this.professionID = professionID;
    }
}
