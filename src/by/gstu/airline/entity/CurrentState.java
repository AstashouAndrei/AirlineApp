package by.gstu.airline.entity;

import by.gstu.airline.exception.CurrentStateException;

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

    /**
     * Returns CurrentState object according with given state description (string)
     *
     * @param state state
     * @return CurrentState
     */
    public static CurrentState getStateByDescription(String state) {
        switch (state) {
            case "Standby":
                return STANDBY;
            case "Scheduled":
                return SCHEDULED;
            case "On flight":
                return ON_FLIGHT;
            case "Arrived":
                return ARRIVED;
            case "Delayed":
                return DELAYED;
            case "Cancelled":
                return CANCELLED;
            default:
                throw new CurrentStateException("Incorrect current state: " + state);
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
