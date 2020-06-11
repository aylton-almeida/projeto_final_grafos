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
    void getFirstFlightCostTest() {
        final List<LocalTime> departureTimes = new ArrayList<>(
                Arrays.asList(
                        LocalTime.of(0, 30),
                        LocalTime.of(8, 0),
                        LocalTime.of(10, 0),
                        LocalTime.of(1, 20)
                )
        );
        edge = new Edge("A", 600, departureTimes);
        FlightDistanceHelper expected = new FlightDistanceHelper(384, LocalTime.of(8, 0));
        FlightDistanceHelper actual = edge.getFirstFlightCost(LocalTime.parse("02:16"));
        assertEquals(expected, actual, "should recommend the flight at 8 and say 384 min");

        expected = new FlightDistanceHelper(730, LocalTime.of(0, 30));
        actual = edge.getFirstFlightCost(LocalTime.parse("13:00"));
        assertEquals(expected, actual, "should recommend the flight at midnight and say 700 min");

        expected = new FlightDistanceHelper(280, LocalTime.parse("08:00"));
        actual = edge.getFirstFlightCost(LocalTime.parse("04:00"));
        assertEquals(expected, actual, "should recommend the flight at 8 and say 280 min");

        expected = new FlightDistanceHelper(280, LocalTime.parse("08:00"));
        actual = edge.getFirstFlightCost(LocalTime.parse("04:00"));
        assertEquals(expected, actual, "should recommend the flight at 8 and say 280 min");
    }
}