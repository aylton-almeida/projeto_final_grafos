package models;

import java.time.LocalTime;
import java.util.Objects;

class FlightDistanceHelper {
    final int minutesTaken;
    final LocalTime flightTaken;

    FlightDistanceHelper(int minutesTaken, LocalTime flightTaken) {
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
