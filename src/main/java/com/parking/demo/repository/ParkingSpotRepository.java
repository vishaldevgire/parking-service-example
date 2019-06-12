package com.parking.demo.repository;

import com.parking.demo.model.ParkingSpot;

import java.util.List;

public interface ParkingSpotRepository {
    public List<ParkingSpot> getAllParkingSpots();
    public void saveParkingSpots();
}
