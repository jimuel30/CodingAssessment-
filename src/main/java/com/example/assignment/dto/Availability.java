package com.example.assignment.dto;

import com.example.assignment.enums.DroneState;

public class Availability {


    public Availability(Boolean isAvailable, DroneState droneState){
        this.isAvailable = isAvailable;
        this.droneState = droneState;
    }


    private Boolean isAvailable;
    private DroneState droneState;

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
}
