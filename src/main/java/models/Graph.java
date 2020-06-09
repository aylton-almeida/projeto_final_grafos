package models;

import java.time.LocalTime;
import java.util.*;

public abstract class Graph {
    protected final HashMap<String, HashSet<Edge>> adjacencyMap = new HashMap<>();
    protected int numEdges;

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex vertex to add.
     */
    public void addVertex(String vertex) {
        if (!this.adjacencyMap.containsKey(vertex)) {
            this.adjacencyMap.put(vertex, new HashSet<>());
        }
    }

    /**
     * Adds an edge between two vertices to the graph.
     *
     * @param origin          source vertex of edge to add.
     * @param destinationEdge destination vertex of edge to add.
     */
    public void addEdge(String origin, Edge destinationEdge) {
        if (this.adjacencyMap.containsKey(origin)) {
            if (!this.adjacencyMap.get(origin).contains(destinationEdge)) {
                this.adjacencyMap.get(origin).add(destinationEdge);
                this.numEdges++;
            }
        }
    }

    /**
     * Adds an vertex and, if it is not isolated, it's edges, from a string.
     *
     * @param string String detailing edge to be added
     */
    public void addVertexFromString(String string) {
    }

    protected void addVertexFromString(String string, boolean isDirected) {
        final String[] details = string.split(";");
        Arrays.setAll(details, i -> details[i].trim());
        final String origin = details[0];
        final String destination = details[1];
        this.addVertex(origin);
        this.addVertex(destination);

        if (details.length >= 5) {
            final double weight = Double.parseDouble(details[3]);
            final List<LocalTime> departures = new ArrayList<>();
            for (int i = 4; i < details.length; i++) {
                final String[] time = details[i].split(":");
                departures.add(LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1])));
            }

            final Edge destinationEdge = new Edge(destination, weight, departures);
            this.addEdge(origin, destinationEdge);
            if (!isDirected) {
                final Edge originEdge = new Edge(origin, weight, departures);
                this.addEdge(destination, originEdge);
                this.numEdges--;
            }
        }
    }

    //TODO: verify necessity
    /**
     * Determines if two vertices are adjacent.
     *
     * @param vertex1 first vertex.
     * @param vertex2 second vertex.
     * @return true if both vertices are adjacent, false if otherwise.
     * @
     */
    public boolean isAdjacent(String vertex1, String vertex2) {
        HashSet<Edge> adjacencySet = this.adjacencyMap.get(vertex1);
        if (adjacencySet != null)
            return adjacencySet.stream().anyMatch((edge) -> edge.destination.equals(vertex2));
        return false;
    }

    //TODO: verify necessity
    /**
     * Returns a HashSet containing all neighbors of a given vertex.
     *
     * @param vertex vertex to search.
     * @return a HashSet containing all neighbors of the vertex.
     * @
     */
    public HashSet<Edge> getNeighbors(String vertex) {
        return this.adjacencyMap.get(vertex);
    }

    //TODO: verify necessity
    /**
     * Returns the number of vertices within the graph.
     *
     * @return an integer representing the number of vertices in the graph
     */
    public int getVertexCount() {
        return adjacencyMap.size();
    }

    //TODO: verify necessity
    /**
     * Returns the number of edges within the graph.
     *
     * @return an integer representing number of edges contained within the graph.
     * @
     */
    public int getEdgeCount() {
        return numEdges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Graph)) return false;
        Graph graph = (Graph) o;
        return numEdges == graph.numEdges &&
                Objects.equals(adjacencyMap, graph.adjacencyMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjacencyMap, numEdges);
    }
}
