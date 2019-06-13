package com.parking.demo.controller;

import com.parking.demo.model.ParkingSpot;
import com.parking.demo.model.ReservationDTO;
import com.parking.demo.service.ParkingSpotService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/parking_spots/available/find")
    public List<ParkingSpot> getAvailableParkingSpotsNearALocation(@RequestParam("lat") double lat, @RequestParam("lng") double lng, @RequestParam("radius") double radiusInMeters) {
        return parkingSpotService.getParkingSpotsInVicinityOf(radiusInMeters, lat, lng);
    }


    @PostMapping("/parking_spots/{id}/cost")
    public Map<String, Integer> getCostOfReservation(@PathVariable("id") int id, @RequestBody ReservationDTO reservationDTO) {
        int costOfReservation = parkingSpotService.getCostOfReservation(reservationDTO, id);
        return Collections.singletonMap("cost", costOfReservation);
    }

    @RequestMapping("/parking_spots/reserved")
    public List<ParkingSpot> getReservedParkingSpots() {
        return parkingSpotService.getAllReservedSpots();
    }

    @PostMapping("/parking_spots/{id}/reserve")
    public ParkingSpot reserveParkingSpot(@PathVariable("id") int id, @RequestBody ReservationDTO reservationDTO)   {
        return parkingSpotService.reserveParkingSpot(reservationDTO, id);
    }

    @PostMapping("/parking_spots/{id}/cancel")
    public ParkingSpot cancelReservation(@PathVariable("id") int id)   {
        return parkingSpotService.cancelReservation(id);
    }
}
