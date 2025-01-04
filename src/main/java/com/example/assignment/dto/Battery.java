package com.example.assignment.dto;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Battery other = (Battery) obj;
        return percentage == other.percentage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(percentage);
    }

    @Override
    public String toString() {
        return "Battery{percentage=" + percentage + "%}";
    }
}
