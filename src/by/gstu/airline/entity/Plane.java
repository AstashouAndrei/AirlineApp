package by.gstu.airline.entity;

/**
 * Class with description entity of plane objects
 */
public class Plane {

    private int ID;
    private String planeType;
    private int passengerCapacity;
    private int flightRange;
    private int fuelConsumption;

    public Plane(String planeType, int passengerCapacity, int flightRange, int fuelConsumption) {
        this.planeType = planeType;
        this.passengerCapacity = passengerCapacity;
        this.flightRange = flightRange;
        this.fuelConsumption = fuelConsumption;
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public int getFlightRange() {
        return flightRange;
    }

    public void setFlightRange(int flightRange) {
        this.flightRange = flightRange;
    }

    public int getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(int fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return planeType.equals(plane.planeType) &&
                passengerCapacity == plane.passengerCapacity &&
                flightRange == plane.flightRange &&
                fuelConsumption == plane.fuelConsumption;
    }

    @Override
    public int hashCode() {
        final int hash = 19;
        int result = planeType.hashCode() + hash * passengerCapacity;
        result += (fuelConsumption + flightRange) * hash / 1000;
        return result;
    }

    @Override
    public String toString() {
        return "Type: " + planeType + ", passenger capacity: " + passengerCapacity +
                ", flightRange: " + flightRange + ", fuelConsumption: " + fuelConsumption;
    }
}
