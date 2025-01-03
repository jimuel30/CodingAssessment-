package com.example.assignment.service.impl;

import com.example.assignment.enums.DroneState;
import com.example.assignment.models.Drone;
import com.example.assignment.models.Medication;
import com.example.assignment.repo.DroneRepo;
import com.example.assignment.service.SchedulingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SchedulingServiceImpl implements SchedulingService {


    private final int interval;

    private static final Logger LOG = LoggerFactory.getLogger(SchedulingServiceImpl.class);

    private final DroneRepo droneRepo;




    public SchedulingServiceImpl(@Value("${schedule.interval}") final int interval,
                                 final DroneRepo droneRepo) {
        this.interval = interval;
        this.droneRepo = droneRepo;
    }

    @Override
    public void transport(Drone drone, Medication medication) {

        for (DroneState state:DroneState.values()) {


                if(DroneState.LOADED.equals(state)){
                    drone.setMedication(medication);
                }

                if(DroneState.DELIVERED.equals(state)){
                    drone.setMedication(null);
                }

                drone.setState(state);

                LOG.info("DRONE: {} ",drone.getSerialNumber());
                LOG.info("STATE: {}", drone.getState());
                droneRepo.save(drone);

                triggerSleep(state);

        }
        drone.setState(DroneState.IDLE);
        drone.setMedication(null);
        drone.setBattery(drone.getBattery()-10);
        LOG.info("DRONE: {} ",drone.getSerialNumber());
        LOG.info("STATE: {}", drone.getState());
        droneRepo.save(drone);
    }


    void triggerSleep(DroneState droneState){
        if(!DroneState.IDLE.equals(droneState)){
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                LOG.info("Error Occurred: {}",e.getMessage());
            }

        }
    }
}
