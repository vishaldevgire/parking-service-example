package com.parking.demo.controller;

import com.parking.demo.model.ParkingSpot;
import com.parking.demo.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParkingController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @RequestMapping("/parking_spots/available")
    public List<ParkingSpot> getAvailableParkingSpots() {
       return parkingSpotService.getAllAvailableParkingSpots();
    }

    @RequestMapping("/parking_spots/reserved")
    public List<ParkingSpot> getReservedParkingSpots() {
        return parkingSpotService.getAllReservedSpots();
    }
}
