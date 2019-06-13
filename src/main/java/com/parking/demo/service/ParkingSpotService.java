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

    public ParkingSpot reserveParkingSpot(ReservationDTO reservationDTO, int id) {
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

        return availableParkingSpot.get();
    }

    public ParkingSpot cancelReservation(int id) {
        Optional<ParkingSpot> availableParkingSpot = findReservedParkingSpotWithId(id);

        if (!availableParkingSpot.isPresent()) {
            throw new ResponseStatusException(NOT_FOUND,
                    String.format("Parking spot with id %d is not found", id));
        }

        if (!availableParkingSpot.get().isReserved()) {
            throw new ResponseStatusException(BAD_REQUEST,
                    String.format("Parking spot with id %d is not reserved", id));
        }

        availableParkingSpot.get().reserve(null);
        repository.saveParkingSpots();

        return availableParkingSpot.get();
    }

    public int getCostOfReservation(ReservationDTO reservationDTO, int id) {
        Optional<ParkingSpot> availableParkingSpot = findAvailableParkingSpotWithId(id);
        if (!availableParkingSpot.isPresent()) {
            throw new ResponseStatusException(NOT_FOUND,
                    String.format("Parking spot with id %d is not available", id));
        }

        return calculateCost(availableParkingSpot.get(), reservationDTO.getNumOfDays());
    }

    public List<ParkingSpot> getParkingSpotsInVicinityOf(double desiredRadiusInMeters, double lat, double lng) {
        List<ParkingSpot> allAvailableParkingSpots = getAllAvailableParkingSpots();

        return allAvailableParkingSpots
                .stream()
                .filter(ps -> distance(ps.getLatitudes(), lat, ps.getLongitude(), lng) <= desiredRadiusInMeters)
                .collect(Collectors.toList());
    }

    private static int calculateCost(ParkingSpot parkingSpot, int numOfDays) {
        return parkingSpot.getHeight() * parkingSpot.getWidth() * numOfDays;
    }


    /*
        Haversine formula to calculate distance
    */
    private static double distance(double lat1, double lat2, double lon1, double lon2) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters


        distance = Math.pow(distance, 2) + Math.pow(0, 2);

        return Math.sqrt(distance);
    }
}
