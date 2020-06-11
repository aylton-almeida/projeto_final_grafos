package models;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DirectedGraph extends Graph {
    @Override
    public void addVertexFromString(String string){
        super.addVertexFromString(string, true);
    }

    /**
     * Returns the smallest path between two vertex
     * @param source Vertex to start from
     * @param destination Vertex to arrive at
     * @return a list containing all edges that need to be used
     */
    public List<Edge> dijkstraAlgorithm(String source, String destination){
        final List<Double> shortestPath = new LinkedList<>();



        final List<Edge> paths = new ArrayList<>();

        return paths;
    }

}
