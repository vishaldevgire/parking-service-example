package com.parking.demo.service;

import com.parking.demo.model.ParkingSpot;
import com.parking.demo.model.Reservation;
import com.parking.demo.model.ReservationDTO;
import com.parking.demo.repository.ParkingSpotCSVRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class ParkingSpotServiceTest {
    @InjectMocks
    private ParkingSpotService service;

    @Mock
    private ParkingSpotCSVRepository repository;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    private final List<ParkingSpot> parkingSpots = Arrays.asList(
            new ParkingSpot(1, 23, 146, 12, 45),
            new ParkingSpot(2, 40, 33, 12, 45, new Reservation("A", new Date(), new Date(), 200)),
            new ParkingSpot(3, 60, 43, 12, 45),
            new ParkingSpot(4, 70, 53, 12, 45, new Reservation("B", new Date(), new Date(), 300))
    );

    @Test
    public void shouldReturnAllAvailableParkingSpots() {
        when(repository.getAllParkingSpots()).thenReturn(parkingSpots);

        List<ParkingSpot> availableParkingSpots = service.getAllAvailableParkingSpots();
        assertThat(availableParkingSpots.size()).isEqualTo(2);
        assertThat(availableParkingSpots.get(0).getId()).isEqualTo(1);
        assertThat(availableParkingSpots.get(1).getId()).isEqualTo(3);
    }

    @Test
    public void shouldReturnAllReservedParkingSpots() {
        when(repository.getAllParkingSpots()).thenReturn(parkingSpots);

        List<ParkingSpot> reservedParkingSpots = service.getAllReservedSpots();
        assertThat(reservedParkingSpots.size()).isEqualTo(2);
        assertThat(reservedParkingSpots.get(0).getId()).isEqualTo(2);
        assertThat(reservedParkingSpots.get(1).getId()).isEqualTo(4);
    }

    @Test
    public void shouldReserveParkingSpot() {
        ParkingSpot parkingSpot = new ParkingSpot(1, 12, 23, 12, 45);
        when(repository.getAllParkingSpots()).thenReturn(Arrays.asList(parkingSpot));
        doAnswer(i -> null).when(repository).saveParkingSpots();
        service.reserveParkingSpot(new ReservationDTO("foobar", 3), 1);

        assertThat(parkingSpot.isReserved()).isTrue();
    }

    @Test
    public void shouldReturnCostForReservingParkingSpot() {
        ParkingSpot parkingSpot = new ParkingSpot(1, 12, 23, 12, 45);
        when(repository.getAllParkingSpots()).thenReturn(Arrays.asList(parkingSpot));
        int cost = service.getCostOfReservation(new ReservationDTO("foo", 3), 1);

        assertThat(cost).isEqualTo(1620);
    }


    @Test
    public void shouldCancelReservedParkingSpot() {
        ParkingSpot parkingSpot = new ParkingSpot(1, 12, 23, 12, 45,
                new Reservation("foobar", new Date(), new Date(), 400));
        when(repository.getAllParkingSpots()).thenReturn(Arrays.asList(parkingSpot));
        doAnswer(i -> null).when(repository).saveParkingSpots();
        service.cancelReservation(1);

        assertThat(parkingSpot.isReserved()).isFalse();
    }

    @Test
    public void shouldReturnParkingSpotsNearGivenCoordinates() {
        when(repository.getAllParkingSpots()).thenReturn(parkingSpots);

        List<ParkingSpot> parkingSpots = service.getParkingSpotsInVicinityOf(103000, 23.0000, 147.0000);

        assertThat(parkingSpots.size()).isEqualTo(1);
        assertThat(parkingSpots).isEqualTo(Arrays.asList(parkingSpots.get(0)));

        List<ParkingSpot> parkingSpotsWhichAreTooFar = service.getParkingSpotsInVicinityOf(100, 44.0000, 147.0000);

        assertThat(parkingSpotsWhichAreTooFar).isEmpty();
    }
}