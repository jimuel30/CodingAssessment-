package com.example.assignment.init;

import com.example.assignment.enums.DroneState;
import com.example.assignment.enums.Model;
import com.example.assignment.models.Drone;
import com.example.assignment.repo.DroneRepo;
import com.example.assignment.service.impl.DroneServiceImpl;
import com.example.assignment.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {


    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);

    private final DroneRepo droneRepo;

    public DataInitializer(DroneRepo droneRepo) {
        this.droneRepo = droneRepo;
    }


    @Override
    public void run(String... args) throws Exception {
        for (Drone drone:createDrones()) {
            LOG.info("DRONE: {}",drone);
            droneRepo.save(drone);
        }
    }


    private List<Drone> createDrones(){

      final List<Drone> droneList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Drone drone = new Drone();
            drone.setBattery(100);
            drone.setState(DroneState.IDLE);
            drone.setSerialNumber(StringUtil.generateRandomStringId());
            drone.setWeightLimit(100+i);
            drone.setModel(Model.Lightweight);
            droneList.add(drone);
        }
      return droneList;
    }





}
