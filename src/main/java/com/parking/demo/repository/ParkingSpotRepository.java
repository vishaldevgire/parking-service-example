package com.parking.demo.repository;

import com.parking.demo.model.ParkingSpot;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ParkingSpotRepository {
    public List<ParkingSpot> getAllParkingSpots();
}
