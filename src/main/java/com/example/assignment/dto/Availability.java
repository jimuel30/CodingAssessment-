package com.example.assignment.dto;

import com.example.assignment.enums.DroneState;

import java.util.Objects;

public class Availability {
    private Boolean isAvailable;
    private DroneState droneState;

    public Availability(Boolean isAvailable, DroneState droneState) {
        this.isAvailable = isAvailable;
        this.droneState = droneState;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public DroneState getDroneState() {
        return droneState;
    }

    public void setDroneState(DroneState droneState) {
        this.droneState = droneState;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Check for reference equality
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // If the object is null or not of the same class, return false
        }
        Availability other = (Availability) obj; // Cast to the correct class type
        return Objects.equals(isAvailable, other.isAvailable) &&
                Objects.equals(droneState, other.droneState); // Compare both fields
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAvailable, droneState); // Generate hash code based on both fields
    }

    @Override
    public String toString() {
        return "Availability{isAvailable=" + isAvailable + ", droneState=" + droneState + "}";
    }
}
