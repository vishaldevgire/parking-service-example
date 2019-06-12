package com.parking.demo.repository;

import com.parking.demo.model.ParkingSpot;
import com.parking.demo.util.CSVUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ParkingSpotRepository {
    public List<ParkingSpot> getAllParkingSpots();
    public void saveParkingSpots();
}
