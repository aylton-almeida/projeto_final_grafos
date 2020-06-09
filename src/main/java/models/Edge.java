package models;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Edge {
    protected final String destination;
    protected final double weight;
    protected final List<LocalTime> departureHours = new ArrayList<>();

    public Edge(String destination, double weight, List<LocalTime> departureHours) {
        this.destination = destination;
        this.weight = weight;
        this.departureHours.addAll(departureHours);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.weight, weight) == 0 &&
                destination.equals(edge.destination) &&
                departureHours.equals(edge.departureHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, weight, departureHours);
    }
}
