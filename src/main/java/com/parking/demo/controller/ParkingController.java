package com.parking.demo.controller;

import com.parking.demo.model.ParkingSpot;
import com.parking.demo.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ParkingController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @RequestMapping("/")
    public List<ParkingSpot> getSpots() {
       return parkingSpotService.getAllParkingSpots();
    }
}
