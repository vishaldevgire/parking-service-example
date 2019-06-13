package com.parking.demo.controller;

import com.parking.demo.model.ParkingSpot;
import com.parking.demo.model.Reservation;
import com.parking.demo.service.ParkingSpotService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private List<ParkingSpot> parkingSpots = new ArrayList<>(Arrays.asList(
            new ParkingSpot(1, 34, 25, 12, 6),
            new ParkingSpot(2, 54, 255, 12, 6, new Reservation("data", DATE, DATE, 10000))
    ));

    @Test
    public void shouldReturnAvailableParkingSpots() throws Exception {
        Mockito.when(parkingSpotService.getAllAvailableParkingSpots()).thenReturn(parkingSpots.subList(0, 1));
        this.mockMvc.perform(get("/parking_spots/available"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"latitudes\":34.0,\"longitude\":25.0,\"height\":12,\"width\":6}]"));
    }

    @Test
    public void shouldReturnReservedParkingSpots() throws Exception {
        Mockito.when(parkingSpotService.getAllReservedSpots()).thenReturn(parkingSpots.subList(1, 2));
        this.mockMvc.perform(get("/parking_spots/reserved"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":2,\"latitudes\":54.0,\"longitude\":255.0,\"height\":12,\"width\":6,\"reservation\":{\"user\":\"data\",\"start\":\"1970-01-12T13:46:40.000+0000\",\"end\":\"1970-01-12T13:46:40.000+0000\",\"cost\":10000}}]"));
    }
}