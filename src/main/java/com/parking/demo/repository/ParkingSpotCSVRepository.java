package com.parking.demo.repository;

import com.parking.demo.model.ParkingSpot;
import com.parking.demo.util.CSVUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class ParkingSpotCSVRepository implements ParkingSpotRepository {
    private List<ParkingSpot> parkingSpots;
    private File resource = new File(getClass().getClassLoader().getResource("parking_lots.csv").getFile());

    public ParkingSpotCSVRepository() throws IOException {
        this.parkingSpots = new CSVUtil().readParkingSpots(resource);
    }


    ParkingSpotCSVRepository(String filePath) throws IOException {
        this.parkingSpots = new CSVUtil().readParkingSpots(new File(filePath));
    }

    public List<ParkingSpot> getAllParkingSpots(){
        return parkingSpots;
    }

    public void saveParkingSpots() {
        try {
            new CSVUtil().writeParkingSpots(resource, parkingSpots);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save parking lots to file", e);
        }
    }
}
