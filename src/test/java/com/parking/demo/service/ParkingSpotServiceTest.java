package com.parking.demo.service;

import com.parking.demo.model.ParkingSpot;
import com.parking.demo.model.Reservation;
import com.parking.demo.repository.ParkingSpotRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ParkingSpotServiceTest {
    @InjectMocks
    private ParkingSpotService service;

    @Mock
    private ParkingSpotRepository repository;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    private final List<ParkingSpot> parkingSpots = Arrays.asList(
            new ParkingSpot(1, 12, 23, 12, 45),

            new ParkingSpot(2, 12, 23, 12, 45,
                    new Reservation("A", new Date(), new Date(), 200)),

            new ParkingSpot(3, 12, 23, 12, 45),

            new ParkingSpot(4, 12, 23, 12, 45,
                    new Reservation("B", new Date(), new Date(), 300))
    );

    @Test
    public void serviceShouldReturnAllAvailableParkingSpots() {
        when(repository.getAllParkingSpots()).thenReturn(parkingSpots);

        List<ParkingSpot> availableParkingSpots = service.getAllAvailableParkingSpots();
        assertThat(availableParkingSpots.size()).isEqualTo(2);
        assertThat(availableParkingSpots.get(0).getId()).isEqualTo(1);
        assertThat(availableParkingSpots.get(1).getId()).isEqualTo(3);
    }

    @Test
    public void serviceShouldReturnAllReservedParkingSpots() {
        when(repository.getAllParkingSpots()).thenReturn(parkingSpots);

        List<ParkingSpot> reservedParkingSpots = service.getAllReservedSpots();
        assertThat(reservedParkingSpots.size()).isEqualTo(2);
        assertThat(reservedParkingSpots.get(0).getId()).isEqualTo(2);
        assertThat(reservedParkingSpots.get(1).getId()).isEqualTo(4);
    }
}