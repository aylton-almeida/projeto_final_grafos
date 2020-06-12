package models;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

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
     * Removes a specified edge between two vertices from the graph, if it already exists.
     *
     * @param source source vertex of edge to remove.
     * @param edge   edge to be removed
     */
    public void removeEdge(String source, Edge edge) {
        if (this.adjacencyMap.containsKey(source)) {
            this.adjacencyMap.get(source).remove(edge);
            this.numEdges--;
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

    public Set<DoubleVertexEdge> getEdges() {
        Set<DoubleVertexEdge> edges = new HashSet<>();
        this.adjacencyMap.forEach((key, value) ->
                value.forEach(edge ->
                        edges.add(new DoubleVertexEdge(key, edge.destination, edge.distance))));
        return edges;
    }

    /**
     * If the graph is cyclic or not using depth traversal algorithm
     *
     * @return if it has a cycle
     */
    public boolean hasCycle() {
        //Visited and Stack arrays
        HashMap<String, VisitStatus> visited = new HashMap<>();
        this.adjacencyMap.forEach((key, value) -> visited.put(key, VisitStatus.NOT_VISITED));

        AtomicBoolean isCyclic = new AtomicBoolean(false);

        //Call Recursive helper function
        this.adjacencyMap.forEach((key, value) -> {
            if (visited.get(key).equals(VisitStatus.NOT_VISITED))
                if (hasCycleUtil(key, visited, null))
                    isCyclic.set(true);
            isCyclic.set(false);
        });

        return isCyclic.get();
    }

    /**
     * Recursive helper function for hasCycle method
     *
     * @param v       vertex being analyzed
     * @param visited if it was visited or not
     * @return if the vertex is in a cycle or not
     */
    private boolean hasCycleUtil(String v, HashMap<String, VisitStatus> visited, String parent) {

        visited.put(v, VisitStatus.VISITED);

        //Go through all adjacent vertices
        for (Edge edge : this.adjacencyMap.get(v)) {
            if (visited.get(edge.destination).equals(VisitStatus.NOT_VISITED)) {
                if (hasCycleUtil(edge.destination, visited, v))
                    return true;
            }

            // If an adjacent is visited and not parent of current
            // vertex, then there is a cycle.
            else if (!edge.destination.equals(parent))
                return true;
        }
        return false;
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
