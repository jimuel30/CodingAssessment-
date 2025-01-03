package com.example.assignment.controller;

import com.example.assignment.models.Drone;
import com.example.assignment.models.Medication;
import com.example.assignment.service.DroneService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drone")
public class DroneController {


    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }


    @PostMapping()
    ResponseEntity<?> register (@RequestBody Drone drone){
        return droneService.register(drone);
    }

    @PostMapping("/load/{serialId}")
    ResponseEntity<?> load(@RequestBody Medication medication, @PathVariable("serialId") String serialId){
       return droneService.load(medication,serialId);
    }

    @GetMapping("/medication/{serialId}")
    ResponseEntity<?> getDroneMedication(@PathVariable("serialId") String serialId){
        return droneService.getMedication(serialId);
    }
    @GetMapping("/info/{serialId}")
    ResponseEntity<?> getDroneBattery(@PathVariable("serialId") String serialId){
        return droneService.getInfo(serialId);
    }

    @GetMapping("/availability/{serialId}")
    ResponseEntity<?> getDroneAvailability(@PathVariable("serialId") String serialId){
        return droneService.getAvailability(serialId);
    }
}
