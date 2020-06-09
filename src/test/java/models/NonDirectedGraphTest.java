package models;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NonDirectedGraphTest {
    NonDirectedGraph graph;

    @Test
    void addVertexFromString() {
        graph = new NonDirectedGraph();

        graph.addVertex("CONFINS");
        graph.addVertex("GUARULHOS");
        final double weight = 606;
        final List<LocalTime> departureTimes = new ArrayList<>(
                Arrays.asList(
                        LocalTime.of(1, 16),
                        LocalTime.of(12, 0)
                )
        );
        graph.addEdge("CONFINS", new Edge("GUARULHOS", weight, departureTimes));
        graph.addEdge("GUARULHOS", new Edge("CONFINS", weight, departureTimes));

        graph.numEdges--;

        final NonDirectedGraph actual = new NonDirectedGraph();
        actual.addVertexFromString("CONFINS; GUARULHOS; 1; 606; 1:16; 12:00");

        assertEquals(graph, actual, "should add 2 vertex and 1 edge connecting them");
    }
}