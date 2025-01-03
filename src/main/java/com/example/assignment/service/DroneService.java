package com.example.assignment.service;

import com.example.assignment.models.Drone;
import com.example.assignment.models.Medication;
import org.springframework.http.ResponseEntity;

public interface DroneService {
    ResponseEntity<?> register(Drone drone);
    ResponseEntity<?> load(Medication medication, String serialId);

    ResponseEntity<?> getInfo(String serialId);

    ResponseEntity<?> getMedication(String serialId);

    ResponseEntity<?> getAvailability(String serialId);
}
