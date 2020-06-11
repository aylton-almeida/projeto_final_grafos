package models;

import java.util.*;

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
    void DepthFirstSearch(String vertice, HashMap<String, Boolean> visited) {
        //mark the vertice as visited
        visited.put(vertice, true);

        // do for every edge which vertice is connected
        for (Edge edge : this.adjacencyMap.get(vertice))
            if (!visited.get(edge.destination))
                DepthFirstSearch(edge.destination, visited);
    }

    /**
     * @return if graph is strongly connected or not
     */
    public boolean isStronglyConnected(Set<String> verticesSet) {
        Set<String> vertices;
        if (verticesSet != null) {
            vertices = verticesSet;
        } else {
            vertices = this.adjacencyMap.keySet();
        }
        // do for every vertice
        for (String vertice : vertices) {
            // stores vertices visited or not
            HashMap<String, Boolean> visited = new HashMap<>();
            for (String v : this.adjacencyMap.keySet())
                visited.put(v, false);

            // start DFS from first vertice
            DepthFirstSearch(vertice, visited);

            // if DFS doesn't visit all vertices, then graph is not strongly connected
            for (Map.Entry<String, Boolean> v : visited.entrySet()) {
                if (!visited.get(v.getKey())) return false;
            }
        }
        return true;
    }

    public void printEssentialAirports() {
        System.out.println("Airport is strongly connected");
        Set<String> vertices = new HashSet<>(this.adjacencyMap.keySet());
        for (String vertice : this.adjacencyMap.keySet()) {
            vertices.remove(vertice);
            if (this.isStronglyConnected(vertices))
                System.out.println("Airport " + vertice + " is essential and removing it from graph turns it into a not strongly connected graph.");
            vertices = new HashSet<>(this.adjacencyMap.keySet());
        }
    }

    public void printStronglyConnectedAirports() {
        System.out.println("Airport is not fully strongly connected, but these airports sets are: ");
        // do for every vertice
        for (String vertice : this.adjacencyMap.keySet()) {
            // stores vertices visited or not
            HashMap<String, Boolean> visited = new HashMap<>();
            for (String v : this.adjacencyMap.keySet())
                visited.put(v, false);

            // start DFS from first vertice
            DepthFirstSearch(vertice, visited);

            // if hasVisitedAll, means that airport is connected to all other ones
            boolean hasVisitedAll = true;
            for (Map.Entry<String, Boolean> v : visited.entrySet()) {
                if (!visited.get(v.getKey())) {
                    hasVisitedAll = false;
                };
            }
            if (hasVisitedAll) {
                System.out.print(vertice + " can reach out to airports ");
                for (Map.Entry<String, Boolean> v : visited.entrySet()) {
                    if (!v.getKey().equals(vertice)) {
                        System.out.print(v.getKey() + " ");
                    }
                }
                System.out.println("(all remaining airports)");
            }
        }
    }

    public void thirdProblem() {
        if (this.isStronglyConnected(null)) {
            printEssentialAirports();
        } else {
            printStronglyConnectedAirports();
        }
    }


}
