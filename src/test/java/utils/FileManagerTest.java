package utils;

import models.DirectedGraph;
import models.Edge;
import models.Graph;
import models.NonDirectedGraph;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileManagerTest {
    FileManager fileManager;

    @Test
    void readNonDirectedGraphFile() {
        fileManager = new FileManager("graph.txt");

        final NonDirectedGraph expected = new NonDirectedGraph();
        expected.addVertexFromString("CONFINS; GUARULHOS; 1; 606; 1:16; 7:00; 9:00; 18:00");
        expected.addVertexFromString("CONFINS; GUARULHOS; -1; 606; 1:16; 8:00; 12:00; 17:00; 21:00");
        expected.addVertexFromString("CONFINS; SANTOS DUMONT; 1; 480; 1:10; 7:30");
        expected.addVertexFromString("GUARULHOS; SANTOS DUMONT; 1; 421; 0:30; 8:00; 18:00");

        try {
            final NonDirectedGraph actual = fileManager.readNonDirectedGraphFile();

            assertEquals(expected, actual, "should create a similar graph from the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readDirectedGraphFile() {
        fileManager = new FileManager("graph.txt");

        final DirectedGraph expected = new DirectedGraph();
        expected.addVertexFromString("CONFINS; GUARULHOS; 1; 606; 1:16; 7:00; 9:00; 18:00");
        expected.addVertexFromString("CONFINS; GUARULHOS; -1; 606; 1:16; 8:00; 12:00; 17:00; 21:00");
        expected.addVertexFromString("CONFINS; SANTOS DUMONT; 1; 480; 1:10; 7:30");
        expected.addVertexFromString("GUARULHOS; SANTOS DUMONT; 1; 421; 0:30; 8:00; 18:00");

        try {
            final DirectedGraph actual = fileManager.readDirectedGraphFile();

            assertEquals(expected, actual, "should create a similar graph from the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}