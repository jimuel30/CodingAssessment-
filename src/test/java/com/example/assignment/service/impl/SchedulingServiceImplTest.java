package com.example.assignment.service.impl;

import com.example.assignment.enums.DroneState;
import com.example.assignment.enums.Model;
import com.example.assignment.models.Drone;
import com.example.assignment.models.Medication;
import com.example.assignment.repo.DroneRepo;
import com.example.assignment.util.StringUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SchedulingServiceImplTest {

    @Mock
    DroneRepo droneRepo;

    SchedulingServiceImpl schedulingService;

    Drone savedDrone;

    Medication savedMedication;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);


        schedulingService = new SchedulingServiceImpl(0, droneRepo);

        savedDrone = new Drone();
        savedDrone.setState(DroneState.IDLE);
        savedDrone.setModel(Model.Lightweight);
        savedDrone.setBattery(100);
        savedDrone.setWeightLimit(888);
        savedDrone.setMedication(null);
        savedDrone.setSerialNumber(StringUtil.generateRandomStringId());

        savedMedication = new Medication();
        savedMedication.setCode("123456");
        savedMedication.setImage("random-url");
        savedMedication.setName("ASPIRIN");
        savedMedication.setMedicationId(1);
        savedMedication.setWeight(777);

        when(droneRepo.save(Mockito.any())).thenReturn(savedDrone);
    }

    @Test
    @DisplayName("Verify State Cycle of Drone State")
    void droneState(){
        schedulingService.transport(savedDrone,savedMedication);
        verify(droneRepo, times(7)).save(savedDrone);
    }
}
