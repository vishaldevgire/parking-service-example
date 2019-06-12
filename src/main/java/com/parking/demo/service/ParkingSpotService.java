package com.parking.demo.service;


import com.parking.demo.model.ParkingSpot;
import com.parking.demo.model.Reservation;
import com.parking.demo.model.ReservationDTO;
import com.parking.demo.repository.ParkingSpotCSVRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ParkingSpotService {
    @Autowired
    private ParkingSpotCSVRepository repository;

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

    public Optional<ParkingSpot>  findAvailableParkingSpotWithId(int id) {
        return getAllAvailableParkingSpots().stream().filter(ps -> ps.getId() == id).findFirst();
    }

    public Optional<ParkingSpot>  findReservedParkingSpotWithId(int id) {
        return getAllReservedSpots().stream().filter(ps -> ps.getId() == id).findFirst();
    }

    public void reserveParkingSpot(ReservationDTO reservationDTO, int id) {
        Optional<ParkingSpot> availableParkingSpot = findAvailableParkingSpotWithId(id);

        if (!availableParkingSpot.isPresent()) {
            throw new ResponseStatusException(NOT_FOUND,
                    String.format("Parking spot with id %d is not available", id));
        }

        if (availableParkingSpot.get().isReserved()) {
            throw new ResponseStatusException(BAD_REQUEST,
                    String.format("Parking spot with id %d is already reserved available", id));
        }

        int cost = calculateCost(availableParkingSpot.get(), reservationDTO.getNumOfDays());
        Date endDate = new DateTime(new Date()).plusDays(reservationDTO.getNumOfDays()).toDate();
        availableParkingSpot.get().reserve(new Reservation(reservationDTO.getUser(), new Date(), endDate, cost));

        repository.saveParkingSpots();
    }

    private int calculateCost(ParkingSpot availableParkingSpotWithId, int numOfDays) {
        return availableParkingSpotWithId.getHeight() * availableParkingSpotWithId.getWidth() * numOfDays;
    }
}
