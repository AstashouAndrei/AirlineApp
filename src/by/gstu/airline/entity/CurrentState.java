package by.gstu.airline.entity;

/**
 * Staff current state type enumeration
 */
public enum CurrentState {

    STANDBY("Standby"),
    SCHEDULED("Scheduled"),
    ON_FLIGHT("On flight"),
    ARRIVED("Arrived"),
    DELAYED("Delayed"),
    CANCELLED("Cancelled");

    private String state;

    CurrentState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
