package com.parking.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.demo.model.ParkingSpot;
import com.parking.demo.model.Reservation;
import com.parking.demo.model.ReservationDTO;
import com.parking.demo.service.ParkingSpotService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ParkingSpotController.class)
public class ParkingSpotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingSpotService parkingSpotService;

    private final Date DATE = new Date(1000000000);

    @Test
    public void shouldReturnAvailableParkingSpots() throws Exception {
        when(parkingSpotService.getAllAvailableParkingSpots()).thenReturn(availableParkingSpots());
        this.mockMvc.perform(get("/parking_spots/available"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"latitudes\":34.0,\"longitude\":25.0,\"height\":12,\"width\":6}]"));
    }

    @Test
    public void shouldReturnReservedParkingSpots() throws Exception {
        when(parkingSpotService.getAllReservedSpots()).thenReturn(reservedParkingSpots());
        this.mockMvc.perform(get("/parking_spots/reserved"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":2,\"latitudes\":54.0,\"longitude\":255.0,\"height\":12,\"width\":6,\"reservation\":{\"user\":\"data\",\"start\":\"1970-01-12T13:46:40.000+0000\",\"end\":\"1970-01-12T13:46:40.000+0000\",\"cost\":10000}}]"));
    }

    @Test
    public void reserve_shouldReserveAParkingSpot() throws Exception {
        ParkingSpot parkingSpot = availableParkingSpots().get(0);

        when(parkingSpotService
                .reserveParkingSpot(any(ReservationDTO.class), anyInt()))
                .thenAnswer(e -> {
                    ReservationDTO dto = e.getArgument(0);
                    parkingSpot.reserve(new Reservation(dto.getUser(), DATE, DATE, dto.getNumOfDays()));
                    return parkingSpot;
                });

        ReservationDTO reservationDTO = new ReservationDTO("foo", 3);

        this.mockMvc.perform(post("/parking_spots/1/reserve")
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(reservationDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"latitudes\":34.0,\"longitude\":25.0,\"height\":12,\"width\":6,\"reservation\":{\"user\":\"foo\",\"start\":\"1970-01-12T13:46:40.000+0000\",\"end\":\"1970-01-12T13:46:40.000+0000\",\"cost\":3}}"));
    }

    @Test
    public void reserve_shouldReturn404WhenParkingSpotIsAlreadyReserved() throws Exception {
        when(parkingSpotService.findAvailableParkingSpotWithId(eq(1)))
                .thenReturn(Optional.empty());
        when(parkingSpotService
                .reserveParkingSpot(any(ReservationDTO.class), anyInt()))
                .thenCallRealMethod();

        ReservationDTO reservationDTO = new ReservationDTO("foo", 3);
        this.mockMvc.perform(post("/parking_spots/1/reserve")
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(reservationDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<ParkingSpot> availableParkingSpots() {
        return Arrays.asList(new ParkingSpot(1, 34, 25, 12, 6));
    }

    private List<ParkingSpot> reservedParkingSpots() {
        return new ArrayList<>(Arrays.asList(
                new ParkingSpot(2, 54, 255, 12, 6, new Reservation("data", DATE, DATE, 10000))
        ));
    }
}