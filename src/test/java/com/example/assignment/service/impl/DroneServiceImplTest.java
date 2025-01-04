package com.example.assignment.service.impl;

import com.example.assignment.dto.Availability;
import com.example.assignment.dto.Battery;
import com.example.assignment.dto.Error;
import com.example.assignment.enums.DroneState;
import com.example.assignment.enums.Model;
import com.example.assignment.models.Drone;
import com.example.assignment.models.Medication;
import com.example.assignment.repo.DroneRepo;
import com.example.assignment.repo.MedicationRepo;
import com.example.assignment.util.StringUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DroneServiceImplTest {
    @InjectMocks
    DroneServiceImpl droneService;

    @Mock
    DroneRepo droneRepo;

    @Mock
    MedicationRepo medicationRepo;


    Drone savedDrone;

    Medication savedMedication;

    String droneSerialId;



    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        savedDrone = new Drone();
        savedDrone.setState(DroneState.IDLE);
        savedDrone.setModel(Model.Lightweight);
        savedDrone.setBattery(100);
        savedDrone.setWeightLimit(888);
        savedDrone.setMedication(null);
        droneSerialId = StringUtil.generateRandomStringId();
        savedDrone.setSerialNumber(droneSerialId);

        savedMedication = new Medication();
        savedMedication.setCode("123456");
        savedMedication.setImage("random-url");
        savedMedication.setName("ASPIRIN");
        savedMedication.setMedicationId(1);
        savedMedication.setWeight(777);

        when(droneRepo.save(Mockito.any())).thenReturn(savedDrone);
        when(medicationRepo.save(Mockito.any())).thenReturn(savedMedication);
    }

    @Test
    @DisplayName("Drone registered successfully")
    void registerSuccess(){

        ResponseEntity<?> response = droneService.register(savedDrone);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(savedDrone,response.getBody());
    }

    @Test
    @DisplayName("Drone registered failed invalid weight")
    void registerFail(){
        savedDrone.setWeightLimit(-999);
        ResponseEntity<?> response = droneService.register(savedDrone);
        final List<String> errorList = List.of("INVALID WEIGHT LIMIT");
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals(new Error(errorList),response.getBody());


    }

    @Test
    @DisplayName("Load success")
    void loadSuccess(){
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.of(savedDrone));
        ResponseEntity<?> response = droneService.load(savedMedication,droneSerialId);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(savedMedication,response.getBody());
    }

    @Test
    @DisplayName("Load failed drone not found")
    void loadFailedDroneNotFound(){
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.empty());
        ResponseEntity<?> response = droneService.load(savedMedication,droneSerialId);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(new Error("DRONE NOT FOUND"),response.getBody());
    }

    @Test
    @DisplayName("Load failed drone not found")
    void loadFailedDroneNotAvailable(){
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.of(savedDrone));
        savedDrone.setState(DroneState.DELIVERING);
        ResponseEntity<?> response = droneService.load(savedMedication,droneSerialId);
        final List<String> errorList = List.of("DRONE UNAVAILABLE");
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals(new Error(errorList),response.getBody());
    }

    @Test
    @DisplayName("Load failed medication Heavier")
    void loadFailedMedicationHeavier(){
        savedMedication.setWeight(999);
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.of(savedDrone));
        ResponseEntity<?> response = droneService.load(savedMedication,droneSerialId);
        final List<String> errorList = List.of("MEDICATION IS HEAVIER THAN DRONE CAPACITY");
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals(new Error(errorList),response.getBody());
    }

    @Test
    @DisplayName("Load failed medication name empty")
    void loadFailedMedicationNameEmpty(){
        savedMedication.setName("");
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.of(savedDrone));
        ResponseEntity<?> response = droneService.load(savedMedication,droneSerialId);
        final List<String> errorList = List.of("Invalid Name Medication Name");
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals(new Error(errorList),response.getBody());
    }

    @Test
    @DisplayName("Load failed medication code invalid")
    void loadFailedMedicationCodeInvalid(){
        savedMedication.setCode("#@#!@11");
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.of(savedDrone));
        ResponseEntity<?> response = droneService.load(savedMedication,droneSerialId);
        final List<String> errorList = List.of("Invalid Medication Code");
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals(new Error(errorList),response.getBody());
    }

    @Test
    @DisplayName("Load failed drone is low battery")
    void loadFailedDroneLowBattery(){
        savedDrone.setBattery(10);
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.of(savedDrone));
        ResponseEntity<?> response = droneService.load(savedMedication,droneSerialId);
        final List<String> errorList = List.of("DRONE LOW BATTERY");
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals(new Error(errorList),response.getBody());
    }

    @Test
    @DisplayName("Load failed Medication weight invalid")
    void loadFailedMedicationWeightInvalid(){
        savedMedication.setWeight(-888);
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.of(savedDrone));
        ResponseEntity<?> response = droneService.load(savedMedication,droneSerialId);
        final List<String> errorList = List.of("Medication Weight Invalid");
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals(new Error(errorList),response.getBody());
    }

    @Test
    @DisplayName("Get drone info success")
    void getInfoSuccess(){
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.of(savedDrone));
        ResponseEntity<?> response = droneService.getInfo(droneSerialId);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(new Battery(100),response.getBody());

    }

    @Test
    @DisplayName("Get drone info failed drone not found")
    void getInfoFailedDroneNotFound(){
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.empty());
        ResponseEntity<?> response = droneService.getInfo(droneSerialId);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(new Error("DRONE NOT FOUND"),response.getBody());
    }


    @Test
    @DisplayName("Get medication success")
    void getMedicationSuccess(){
        savedDrone.setMedication(savedMedication);
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.of(savedDrone));
        ResponseEntity<?> response = droneService.getMedication(droneSerialId);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(savedMedication,response.getBody());
    }

    @Test
    @DisplayName("Get medication failed drone not found")
    void getMedicationFailedDroneNotFound(){
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.empty());
        ResponseEntity<?> response = droneService.getMedication(droneSerialId);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(new Error("DRONE NOT FOUND"),response.getBody());
    }

    @Test
    @DisplayName("Get medication no medication inside drone")
    void getMedicationNoMedication(){
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.of(savedDrone));
        ResponseEntity<?> response = droneService.getMedication(droneSerialId);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(new Error("No Medication"),response.getBody());
    }


    @Test
    @DisplayName("Get drone availability success")
    void getAvailabilitySuccess(){
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.of(savedDrone));
        ResponseEntity<?> response = droneService.getAvailability(droneSerialId);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(new Availability(true,DroneState.IDLE),response.getBody());
    }


    @Test
    @DisplayName("Get drone availability failed drone not found")
    void getAvailabilityFailedDroneNotFound(){
        when(droneRepo.findBySerialNumber(Mockito.any())).thenReturn(Optional.empty());
        ResponseEntity<?> response = droneService.getAvailability(droneSerialId);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(new Error("DRONE NOT FOUND"),response.getBody());
    }



}