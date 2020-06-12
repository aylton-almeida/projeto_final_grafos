import models.DirectedGraph;
import models.DoubleVertexEdge;
import models.NonDirectedGraph;
import utils.FileManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Good Morning! 😊");
        int option = 0;
        while (option != 5) {
            System.out.print("Please write your file name: ");
            String fileName = sc.nextLine();

            if (!fileName.endsWith(".txt"))
                fileName += ".txt";

            FileManager fileManager = new FileManager(fileName);

            System.out.println("[1] - Smallest Distance");
            System.out.println("[2] - Is Connected Detailed");
            System.out.println("[3] - Reunion problem");
            System.out.println("[4] - Planes necessary");
            System.out.println("[5] - Exit");
            System.out.print("Choose an option: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    smallestDistance(sc, fileManager);
                    break;
                default:
                    System.out.println("Have a good evening 😊");
                    break;
            }
        }

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

//        FileManager fileManager3 = new FileManager("treeGraph.txt");
//        try {
//            NonDirectedGraph graph = fileManager3.readNonDirectedGraphFile();
//            graph.kruskalAlgorithm();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    static void smallestDistance(Scanner sc, FileManager fileManager) {
        DirectedGraph graph;
        try {
            graph = fileManager.readDirectedGraphFile();

            System.out.println("Choose your origin airport");
            String source = sc.nextLine();
            source = source.toUpperCase();
            System.out.println("Choose the destination airport");
            String destination = sc.nextLine();
            destination = destination.toUpperCase();

            System.out.println();

            graph.calculateShortestPathFromSource(source, destination);
            sc.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
