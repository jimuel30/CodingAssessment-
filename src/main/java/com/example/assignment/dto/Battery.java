package com.example.assignment.dto;

public class Battery {
    private int percentage;

    public Battery(int percentage){
        this.percentage = percentage;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
