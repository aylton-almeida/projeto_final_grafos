package models;

import org.junit.jupiter.api.Test;
import utils.FileManager;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {
    DirectedGraph graph;

    @Test
    void addVertexFromString() {
        graph = new DirectedGraph();

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

        final DirectedGraph actual = new DirectedGraph();
        actual.addVertexFromString("CONFINS; GUARULHOS; 1; 606; 1:16; 12:00");

        assertEquals(graph, actual, "should add 2 vertex and 1 edge connecting them");
    }

    @Test
    void dijkstraAlgorithm() {
        FileManager fileManager = new FileManager("graph.txt");
        try {
            graph = fileManager.readDirectedGraphFile();
            //TODO: finish
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}