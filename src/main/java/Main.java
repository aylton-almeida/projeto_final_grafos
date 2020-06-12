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
            System.out.println("[1] - Smallest Distance");
            System.out.println("[2] - Is Connected Detailed");
            System.out.println("[3] - Reunion problem");
            System.out.println("[4] - Planes necessary");
            System.out.println("[5] - Exit");
            System.out.print("Choose an option: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1 -> smallestDistance(sc);
                case 2 -> isConnectedDetailed(sc);
                case 3 -> reunionProblem();
                case 4 -> planesNecessary(sc);
                default -> System.out.println("Have a good evening 😊");
            }
        }
    }

    static void smallestDistance(Scanner sc) {
        System.out.print("Please write your file name: ");
        String fileName = sc.nextLine();

        if (!fileName.endsWith(".txt"))
            fileName += ".txt";

        FileManager fileManager = new FileManager(fileName);
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

    static void isConnectedDetailed(Scanner sc) {
        System.out.print("Please write your file name: ");
        String fileName = sc.nextLine();

        if (!fileName.endsWith(".txt"))
            fileName += ".txt";

        FileManager fileManager = new FileManager(fileName);
        DirectedGraph graph;
        try {
            graph = fileManager.readDirectedGraphFile();
            graph.thirdProblem();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void reunionProblem(){
        System.out.println("\nSorry 😥, this method is not available at the moment, please try again later\n");
    }

    static void planesNecessary(Scanner sc) {
        System.out.print("Please write your file name: ");
        String fileName = sc.nextLine();

        if (!fileName.endsWith(".txt"))
            fileName += ".txt";

        FileManager fileManager = new FileManager(fileName);
        try {
            NonDirectedGraph graph = fileManager.readNonDirectedGraphFile();
            int numberPlanes = graph.kruskalAlgorithm();
            System.out.println(String.format("\nYou will need %s planes\n", numberPlanes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
