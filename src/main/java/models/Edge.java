package models;

import utils.Converter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Edge {
    protected final String destination;
    protected final double distance;
    protected final List<LocalTime> departureHours = new ArrayList<>();

    public Edge(String destination, double distance, List<LocalTime> departureHours) {
        this.destination = destination;
        this.distance = distance;
        this.departureHours.addAll(departureHours);
    }

    /**
     * When arriving at given time, returns the time taken for the next flight, including the flight
     * there and which one is it
     *
     * @param departureTime Starting time
     * @return Time and Flight taken
     */
    public FlightDistanceHelper getFirstFlightCost(LocalTime departureTime) {
        final int distanceInMinutes = Converter.distanceInMinutes(this.distance);
        final AtomicReference<LocalTime> firstFlightSameDay = new AtomicReference<>(null);
        final AtomicReference<LocalTime> firstFlightNextDay = new AtomicReference<>(null);

        departureHours.forEach(hour -> {
            final int differenceFirstFlightSameDay = firstFlightSameDay.get() == null ? Integer.MAX_VALUE : (int) MINUTES.between(departureTime, firstFlightSameDay.get());
            final int differenceFirstFlightNextDay = firstFlightNextDay.get() == null ? Integer.MAX_VALUE : (int) MINUTES.between(departureTime, firstFlightNextDay.get());

            final int difference = (int) MINUTES.between(departureTime, hour);
            if (difference > 0 && difference < differenceFirstFlightSameDay)
                firstFlightSameDay.set(hour);
            else if (difference < 0 && difference < differenceFirstFlightNextDay)
                firstFlightNextDay.set(hour);
        });

        final int differenceFirstFlightSameDay = firstFlightSameDay.get() == null ? Integer.MAX_VALUE : (int) MINUTES.between(departureTime, firstFlightSameDay.get());
        final int differenceFirstFlightNextDay = firstFlightNextDay.get() == null ? Integer.MAX_VALUE : (24 * 60) - (((int) MINUTES.between(departureTime, firstFlightNextDay.get())) * -1);

        final LocalTime flightTime = differenceFirstFlightSameDay < differenceFirstFlightNextDay
                ? firstFlightSameDay.get()
                : firstFlightNextDay.get();

        final int minutesTaken = distanceInMinutes + (flightTime.equals(firstFlightNextDay.get()) ? differenceFirstFlightNextDay : differenceFirstFlightSameDay);

        return new FlightDistanceHelper(minutesTaken, flightTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.distance, distance) == 0 &&
                destination.equals(edge.destination) &&
                departureHours.equals(edge.departureHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, distance, departureHours);
    }
}

class FlightDistanceHelper {
    final double minutesTaken;
    final LocalTime flightTaken;

    FlightDistanceHelper(double minutesTaken, LocalTime flightTaken) {
        this.minutesTaken = minutesTaken;
        this.flightTaken = flightTaken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlightDistanceHelper)) return false;
        FlightDistanceHelper that = (FlightDistanceHelper) o;
        return Double.compare(that.minutesTaken, minutesTaken) == 0 &&
                Objects.equals(flightTaken, that.flightTaken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minutesTaken, flightTaken);
    }
}
