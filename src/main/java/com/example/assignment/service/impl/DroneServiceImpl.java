package com.example.assignment.service.impl;


import com.example.assignment.dto.Availability;
import com.example.assignment.dto.Battery;
import com.example.assignment.dto.Error;
import com.example.assignment.enums.DroneState;
import com.example.assignment.models.Drone;
import com.example.assignment.models.Medication;
import com.example.assignment.repo.DroneRepo;
import com.example.assignment.repo.MedicationRepo;
import com.example.assignment.service.DroneService;
import com.example.assignment.service.SchedulingService;
import com.example.assignment.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class DroneServiceImpl implements DroneService {


    private static final Logger LOG = LoggerFactory.getLogger(DroneServiceImpl.class);
    private final DroneRepo droneRepo;
    private final SchedulingService schedulingService;

    private final MedicationRepo medicationRepo;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public DroneServiceImpl(final DroneRepo droneRepo,
                            final SchedulingService schedulingService,
                            final MedicationRepo medicationRepo) {
        this.droneRepo = droneRepo;
        this.schedulingService = schedulingService;
        this.medicationRepo = medicationRepo;
    }

    //save drone to database4
    @Override
    public ResponseEntity<?> register(Drone drone) {
        final List<String> errorList = validateDrone(drone);
        drone.setSerialNumber(StringUtil.generateRandomStringId());
        drone.setState(DroneState.IDLE);
        drone.setBattery(100);

        return errorList.isEmpty() ? ResponseEntity.ok().body(droneRepo.save(drone)) :
                ResponseEntity.badRequest().body(new Error(errorList));
    }

    @Override
    public ResponseEntity<?> load(Medication medication, String serialId) {

        LOG.info("DRONE SERIAL #: {}",serialId);
        LOG.info("MEDICATION: {}", medication);

        ResponseEntity<?> response;


        final List<String> errorList = validateMedication(medication);

        if(errorList.isEmpty()){
            response = processTransport(medication,serialId);
        }
        else{
            response = ResponseEntity.badRequest().body(new Error(errorList));
        }
        return response;
    }

    @Override
    public ResponseEntity<?> getInfo(String serialId) {
        final Optional<Drone> optionalDrone = droneRepo.findBySerialNumber(serialId);
        return optionalDrone.isEmpty()? ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("DRONE NOT FOUD"))
                : ResponseEntity.ok().body(new Battery(optionalDrone.get().getBattery()));
    }

    @Override
    public ResponseEntity<?> getMedication(String serialId) {
        final Optional<Drone> optionalDrone = droneRepo.findBySerialNumber(serialId);
        ResponseEntity<?> response;
        if(optionalDrone.isEmpty()){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("DRONE NOT FOUND"));
        }
        else{
            final Drone existingDrone = optionalDrone.get();
            final Object body = Objects.nonNull(existingDrone.getMedication())?existingDrone.getMedication()
                    : new Error("No Medication");
            response = ResponseEntity.ok().body(body);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> getAvailability(String serialId) {
        final Optional<Drone> optionalDrone = droneRepo.findBySerialNumber(serialId);
        return optionalDrone.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("DRONE NOT FOUND"))
                :ResponseEntity.ok().body(new Availability(DroneState.IDLE.equals(optionalDrone.get().getState()),optionalDrone.get().getState()));
    }


    private ResponseEntity<?> processTransport(Medication medication, String serialId){
        ResponseEntity<?> response;

        final Optional<Drone> optionalDrone = droneRepo.findById(serialId);
        if(optionalDrone.isEmpty()){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("DRONE NOT FOUND"));
        }
        else{
            final Drone existingDrone = optionalDrone.get();
            final List<String> errorList = checkDroneAvailability(existingDrone,medication);

            if(errorList.isEmpty()){
                medication = medicationRepo.save(medication);
                triggerScheduler(existingDrone,medication);
                response = ResponseEntity.ok().body(medication);
            }
            else{
               response = ResponseEntity.badRequest().body(new Error(errorList));
            }
        }
        return response;
    }


    private List<String> checkDroneAvailability(final Drone drone, final Medication medication){
        final List<String> errrorList = new ArrayList<>();
        if(medication.getWeight()>drone.getWeightLimit()){
            errrorList.add("MEDICATION IS HEAVIER THAN DRONE CAPACITY");
        }
        if(!DroneState.IDLE.equals(drone.getState())){
            errrorList.add("DRONE UNAVAILABLE");
        }
        if(drone.getBattery()<25){
            errrorList.add("DRONE LOW BATTERY");
        }
        return errrorList;
    }

    private void triggerScheduler(final Drone drone, final Medication medication){
        executorService.submit(() ->
                schedulingService.transport(drone,medication)
        );
    }

    private List<String> validateDrone(final Drone drone){
        final List<String> errrorList = new ArrayList<>();

        if(drone.getWeightLimit()<=0) {
          errrorList.add("INVALID WEIGHT LIMIT");
        }
        if(drone.getWeightLimit()>1000){
            errrorList.add("WEIGHT LIMIT IS TOO MUCH");
        }


        return errrorList;
    }

    private List<String> validateMedication(final Medication medication){
        final List<String> errrorList = new ArrayList<>();
        if(Objects.isNull(medication.getName()) || "".equals(medication.getName())){
            errrorList.add("Medication Name Empty");
        }
        if(medication.getWeight()<=0){
            errrorList.add("Medication Weight Invalid");
        }

        final String name = medication.getName();
        final String code = medication.getCode();
        if(Objects.isNull(name) || name.isEmpty() || !StringUtil.isValidName(name)){
            errrorList.add("Invalid Name");
        }

        if(Objects.isNull(code) || code.isEmpty() || !StringUtil.isValidCode(code)){
            errrorList.add("Invalid Code");
        }

        return errrorList;
    }



}
