package com.example.assignment.repo;

import com.example.assignment.models.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DroneRepo extends JpaRepository<Drone, String> {
    Optional<Drone> findBySerialNumber(String serialNumber);
}
