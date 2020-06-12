import models.DirectedGraph;
import models.DoubleVertexEdge;
import models.NonDirectedGraph;
import utils.FileManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

//        // SHORTEST PATH EXAMPLE
//        FileManager fileManager = new FileManager("bigGraph.txt");
//        try {
//            DirectedGraph graph = fileManager.readDirectedGraphFile();
//            graph.calculateShortestPathFromSource("D", "A");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //THIRD PROBLEM EXAMPLE
//        FileManager fileManager2 = new FileManager("stronglyConnectedGraph.txt");
//        FileManager fileManager3 = new FileManager("notStronglyConnectedGraph.txt");
//        try {
//            DirectedGraph scg = fileManager2.readDirectedGraphFile();
//            DirectedGraph nscg = fileManager3.readDirectedGraphFile();
//            scg.thirdProblem();
//            nscg.thirdProblem();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        FileManager fileManager3 = new FileManager("treeGraph.txt");
        try {
            NonDirectedGraph graph = fileManager3.readNonDirectedGraphFile();
            graph.kruskalAlgorithm();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
