package com.parking.demo.service;


import com.parking.demo.model.ParkingSpot;
import com.parking.demo.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParkingSpotService {
    @Autowired
    private ParkingSpotRepository repository;

    public List<ParkingSpot> getAllAvailableParkingSpots() {
        return repository
                .getAllParkingSpots()
                .stream()
                .filter(ps -> !ps.isReserved())
                .collect(Collectors.toList());
    }

    public List<ParkingSpot> getAllReservedSpots() {
        return repository
                .getAllParkingSpots()
                .stream()
                .filter(ps -> ps.isReserved())
                .collect(Collectors.toList());
    }
}
