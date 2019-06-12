package com.parking.demo.controller;

import com.parking.demo.model.ParkingSpot;
import com.parking.demo.model.ReservationDTO;
import com.parking.demo.service.ParkingSpotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingSpotController {
    private ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @RequestMapping("/parking_spots/available")
    public List<ParkingSpot> getAvailableParkingSpots() {
       return parkingSpotService.getAllAvailableParkingSpots();
    }

    @RequestMapping("/parking_spots/reserved")
    public List<ParkingSpot> getReservedParkingSpots() {
        return parkingSpotService.getAllReservedSpots();
    }

    @PostMapping("/parking_spots/reserve/{id}")
    public void reserveParkingSpot(@PathVariable("id") int id, @RequestBody ReservationDTO reservationDTO)   {
        parkingSpotService.reserveParkingSpot(reservationDTO, id);
    }

    @PostMapping("/parking_spots/cancel/{id}")
    public void cancelReservation(@PathVariable("id") int id)   {
        parkingSpotService.cancelReservation(id);
    }
}
