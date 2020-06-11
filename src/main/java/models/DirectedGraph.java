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


    /**
     * Depth-first search
     */
    void DepthFirstSearch(int source, boolean[] visited) {
//        //mark the vertice as visited
//        visited[source] = true;
//
//        // do for every edge
//        for (String vertice : this.adjacencyMap.get(source)) {
//            if (!visited[])
//        }
//
//        List<Vertice> adjList = this.edges
//                .stream()
//                .filter(e -> e.vertices.contains(this.vertices.get(source)))
//                .map(e -> {
//                    int i = e.vertices.indexOf(this.vertices.get(source));
//                    return i == 0 ? e.vertices.get(1) : e.vertices.get(0);
//                })
//                .collect(Collectors.toList());
//
//        for (Vertice v : adjList) {
//            int index = this.vertices.indexOf(v);
//            if (!visited[index]) {
//                DepthFirstSearch(index, visited);
//            }
//        }
    }
    /**
     * @return if graph is strongly connected or not
     */
    public boolean isStronglyConnected() {
        int numVertices = this.adjacencyMap.size();

        // do for every vertice
        for (int verticeIndex = 0; verticeIndex < numVertices; verticeIndex++) {
            // stores vertices visited or not
            boolean[] visited = new boolean[numVertices];

            // start DFS from first vertice
            this.DepthFirstSearch(verticeIndex, visited);

            // if DFS doesn't visit all vertices, then graph is not strongly connected
            for (boolean b: visited)
                if (!b) return false;
        }
        return true;
    }


}
