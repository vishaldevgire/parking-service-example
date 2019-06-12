package com.parking.demo.repository;

import com.parking.demo.model.ParkingSpot;
import com.parking.demo.model.Reservation;
import com.parking.demo.util.CSVUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParkingSpotCSVRepository implements ParkingSpotRepository {
    private List<ParkingSpot> parkingSpots;
    private File resource = new File(getClass().getClassLoader().getResource("parking_lots.csv").getFile());

    public ParkingSpotCSVRepository() throws IOException {
        this.parkingSpots = readParkingSpots(resource);
    }


    ParkingSpotCSVRepository(String filePath) throws IOException {
        this.parkingSpots = readParkingSpots(new File(filePath));
    }

    public List<ParkingSpot> getAllParkingSpots(){
        return parkingSpots;
    }

    public void saveParkingSpots() {
        try {
            writeParkingSpots(resource, parkingSpots);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save parking lots to file", e);
        }
    }

    public void writeParkingSpots(File resource, List<ParkingSpot> parkingSpots)  throws IOException  {
        List<String> parkingSpotLines = parkingSpots.stream().map(p -> parkingSpotToString(p)).collect(Collectors.toList());

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(resource, false))) {
            for(String line : parkingSpotLines) {
                buffer.write(line);
                buffer.newLine();
                buffer.flush();
            }
        }
    }

    public List<ParkingSpot> readParkingSpots(File resource) throws IOException {
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        try(
                FileInputStream fis = new FileInputStream(resource);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader reader = new BufferedReader(isr)
        ) {
            reader.readLine(); // headers
            reader.lines().forEach(line -> parkingSpots.add(stringToParkingSpot(line)));
        }

        return parkingSpots;
    }

    private String parkingSpotToString(ParkingSpot p) {
        String parkingSpot = String.format("%d,%f,%f,%d,%d",
                p.getId(),
                p.getLatitudes(),
                p.getLongitude(),
                p.getHeight(),
                p.getWidth());

        if (p.isReserved()) {
            Reservation reservation = p.getReservation();
            parkingSpot += String.format(",%s,%d,%d,%d", reservation.getUser(), reservation.getStart().getTime(),
                    reservation.getEnd().getTime(), reservation.getCost());
        }

        return parkingSpot;
    }

    private ParkingSpot stringToParkingSpot(String line) {
        String[] tokens = line.split(",");

        int id = Integer.parseInt(tokens[0]);
        double lat = Double.parseDouble(tokens[1]);
        double lng = Double.parseDouble(tokens[2]);

        int height = Integer.parseInt(tokens[3]);
        int width = Integer.parseInt(tokens[4]);

        ParkingSpot parkingSpot = new ParkingSpot(id, lat, lng, height, width);

        if (tokens.length > 5) {
            String user = tokens[5];
            Date start = new DateTime(Long.parseLong(tokens[6])).toDate();
            Date end = new DateTime(Long.parseLong(tokens[7])).toDate();
            int cost = Integer.parseInt(tokens[8]);

            parkingSpot.reserve(new Reservation(user, start, end, cost));
        }

        return parkingSpot;
    }

}
