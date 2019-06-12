package com.parking.demo.repository;

import com.parking.demo.model.ParkingSpot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ParkingSpotRepository {
    List<ParkingSpot> parkingSpots = new ArrayList<>(Arrays.asList(
            new ParkingSpot(1, 34, 25, 12, 6),
            new ParkingSpot(2, 40, 54, 12, 6),
            new ParkingSpot(3, 50, 55, 12, 6),
            new ParkingSpot(4, 60, 45, 12, 6),
            new ParkingSpot(5, 70, 95, 12, 6)
    ));

    public List<ParkingSpot> getAllParkingSpots(){
        return parkingSpots;
    }
}
