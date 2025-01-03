package com.example.assignment.models;

import com.example.assignment.enums.DroneState;
import com.example.assignment.enums.Model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;




@Entity
@Table(name = "drones")
public class Drone {


    @Id
    private String serialNumber;
    private Model model;
    private double weightLimit;
    private DroneState state;

    private int battery;

    @OneToOne()
    private Medication medication;

    @Override
    public String toString() {
        return "Drone{" +
                "serialNumber='" + serialNumber + '\'' +
                ", model=" + model +
                ", weightLimit=" + weightLimit +
                ", state=" + state +
                ", battery=" + battery +
                ", medication=" + (medication != null ? medication.toString() : "null") +
                '}';
    }
    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public void setMedication(Medication medication){
        this.medication = medication;
    }

    public Medication getMedication() {
        return medication;
    }

    // Getter and Setter for serialNumber
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    // Getter and Setter for model
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    // Getter and Setter for weightLimit
    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }

    // Getter and Setter for state
    public DroneState getState() {
        return state;
    }

    public void setState(DroneState state) {
        this.state = state;
    }
}

