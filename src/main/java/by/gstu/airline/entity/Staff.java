package main.java.by.gstu.airline.entity;

/**
 * Class with description entity of staff objects
 */
public class Staff {

    private int id;
    private String firstName;
    private String lastName;
    private Profession profession;
    private CurrentState state;

    public Staff(String firstName, String lastName, Profession professionID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profession = professionID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public CurrentState getState() {
        return state;
    }

    public void setState(CurrentState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return id == staff.id && firstName.equals(staff.firstName) &&
                lastName.equals(staff.lastName) && profession.equals(staff.profession);
    }

    @Override
    public int hashCode() {
        final int hash = 19;
        return hash * id;
    }

    @Override
    public String toString() {
        return profession.getProfession() + ": " + firstName + " " + lastName;
    }
}



