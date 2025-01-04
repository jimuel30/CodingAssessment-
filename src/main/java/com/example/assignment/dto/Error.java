package com.example.assignment.dto;

import java.util.Objects;

public class Error {
    public Error(Object message){
        this.message =  message;
    }
    private final Object message;

    public Object getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Error{message=" + message + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Check for reference equality
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // If the object is null or not of the same class, return false
        }
        Error other = (Error) obj; // Cast to the correct class type
        return Objects.equals(message, other.message); // Compare message field for equality
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
