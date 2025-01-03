package com.example.assignment.service;

import com.example.assignment.models.Drone;
import com.example.assignment.models.Medication;

public interface SchedulingService {
    void transport(Drone drone, Medication medication);
}
