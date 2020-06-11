import models.DirectedGraph;
import models.Graph;
import utils.FileManager;

import java.io.IOException;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;


public class Main {
    public static void main(String[] args) {
        final LocalTime lc1 = LocalTime.parse("01:20");
        final LocalTime lc2 = LocalTime.parse("00:20");
        System.out.println(MINUTES.between(lc2, lc1));

        //THIRD PROBLEM EXAMPLE
        FileManager fileManager = new FileManager("stronglyConnectedGraph.txt");
        FileManager fileManager2 = new FileManager("notStronglyConnectedGraph.txt");
        try {
            DirectedGraph scg = fileManager.readDirectedGraphFile();
            DirectedGraph nscg = fileManager2.readDirectedGraphFile();
            scg.thirdProblem();
            nscg.thirdProblem();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
