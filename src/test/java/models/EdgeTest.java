package models;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    Edge edge;

    @Test
    void getFirstFlightCost() {
        final List<LocalTime> departureTimes = new ArrayList<>(
                Arrays.asList(
                        LocalTime.of(0, 30),
                        LocalTime.of(8, 0),
                        LocalTime.of(10, 0),
                        LocalTime.of(1, 20)
                )
        );
        edge = new Edge("A", 600, departureTimes);
        final FlightDistanceHelper expected = new FlightDistanceHelper(344, LocalTime.of(8, 0));
        final FlightDistanceHelper actual = edge.getFirstFlightCost(LocalTime.parse("02:16"));
        assertEquals(expected, actual);
    }
}