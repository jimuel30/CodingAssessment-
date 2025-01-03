package com.example.assignment.dto;

public class Error {
    public Error(Object message){
        this.message =  message;
    }
    private final Object message;

    public Object getMessage() {
        return message;
    }
}
