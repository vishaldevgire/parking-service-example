package com.parking.demo.controller;

import com.parking.demo.model.ParkingSpot;
import com.parking.demo.service.ParkingSpotService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ParkingSpotController.class)
public class ParkingSpotController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingSpotService parkingSpotService;

    private List<ParkingSpot> parkingSpots = new ArrayList<>(Arrays.asList(
            new ParkingSpot(1, 34, 25, 12, 6),
            new ParkingSpot(2, 40, 54, 12, 6),
            new ParkingSpot(3, 50, 55, 12, 6),
            new ParkingSpot(4, 60, 45, 12, 6),
            new ParkingSpot(5, 70, 95, 12, 6)
    ));

    @Test
    public void shouldReturnAvailableParkingSpots() throws Exception {
        Mockito.when(parkingSpotService.getAllAvailableParkingSpots()).thenReturn(parkingSpots);
        this.mockMvc.perform(get("parking_spots/available"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World")));
    }
}