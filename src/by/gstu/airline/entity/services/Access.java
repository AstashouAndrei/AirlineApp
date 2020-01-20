package by.gstu.airline.entity.services;

import by.gstu.airline.exception.AccessException;

/**
 * User access type enumeration
 */
public enum Access {

    ADMINISTRATOR("Administrator", 1),
    DISPATCHER("Dispatcher", 2);

    private String access;
    private int accessID;

    Access(String access, int accessID) {
        this.access = access;
        this.accessID = accessID;
    }

    /**
     * Returns access by given ID
     *
     * @param id access id
     * @return access
     */
    public static Access getAccessByID(int id) {
        switch (id) {
            case 1:
                return ADMINISTRATOR;
            case 2:
                return DISPATCHER;
            default:
                throw new AccessException("Incorrect profession ID: " + id);
        }
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public int getAccessID() {
        return accessID;
    }

    public void setAccessID(int accessID) {
        this.accessID = accessID;
    }
}
