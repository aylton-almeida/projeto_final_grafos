import models.DirectedGraph;
import utils.FileManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager("bigGraph.txt");
        try {
            DirectedGraph graph = fileManager.readDirectedGraphFile();
            graph.calculateShortestPathFromSource("A", "E");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
