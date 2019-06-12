package com.parking.demo.service;


import com.parking.demo.model.ParkingSpot;
import com.parking.demo.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParkingSpotService {
    @Autowired
    private ParkingSpotRepository repository;

    public List<ParkingSpot> getAllParkingSpots() {
        return repository.getAllParkingSpots();
    }
}
