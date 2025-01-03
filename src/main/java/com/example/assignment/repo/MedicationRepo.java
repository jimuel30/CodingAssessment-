package com.example.assignment.repo;

import com.example.assignment.models.Drone;
import com.example.assignment.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepo extends JpaRepository<Medication, Long> {
}
