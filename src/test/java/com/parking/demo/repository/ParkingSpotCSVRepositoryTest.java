package com.parking.demo.repository;

import com.parking.demo.model.ParkingSpot;
import com.parking.demo.model.Reservation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ParkingSpotCSVRepositoryTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private static ParkingSpotCSVRepository repository;

    private static File dummyDataTempFile;

    private static final File sourceFile = new File(ParkingSpotCSVRepositoryTest.class.getClassLoader().getResource("parking_lots_test.csv").getFile());

    @Before
    public void setUp() throws Exception {
        dummyDataTempFile = tempFolder.newFile();
        dummyDataTempFile.delete();
        Files.copy(sourceFile.toPath(), dummyDataTempFile.toPath());
        repository = new ParkingSpotCSVRepository(dummyDataTempFile.getAbsolutePath());
    }

    @Test
    public void shouldReadAllParkingSpotsFromCSVFile() {
        List<ParkingSpot> allParkingSpots = repository.getAllParkingSpots();

        assertThat(allParkingSpots.size()).isEqualTo(3);
        assertThat(allParkingSpots.get(0)).isEqualTo(new ParkingSpot(1, 23, 45, 100, 200));
        assertThat(allParkingSpots.get(2)).isEqualTo(new ParkingSpot(3, 60, 55, 100, 600, new Reservation("foobar", new Date(3452342342L), new Date(234234234L), 500)));
    }

    @Test
    public void shouldWriteParkingSpotsToCSVFile() throws IOException {
        List<ParkingSpot> allParkingSpots = repository.getAllParkingSpots();

        allParkingSpots.get(1).reserve(new Reservation("foobar", new Date(3452342342L), new Date(234234234L), 500));
        repository.saveParkingSpots();

        ParkingSpotCSVRepository tempRepo = new ParkingSpotCSVRepository(dummyDataTempFile.getAbsolutePath());
        assertThat(tempRepo.getAllParkingSpots().get(1)).isEqualTo(new ParkingSpot(2,33,45,100,200, new Reservation("foobar", new Date(3452342342L), new Date(234234234L), 500)));

    }
}