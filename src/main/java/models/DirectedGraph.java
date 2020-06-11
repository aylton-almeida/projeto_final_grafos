package models;

import utils.Converter;

import java.time.LocalTime;
import java.util.*;

public class DirectedGraph extends Graph {
    @Override
    public void addVertexFromString(String string){
        super.addVertexFromString(string, true);
    }

    /**
     * Returns the smallest path between two vertex
     *
     * @param source      Vertex to start from
     * @param destination Vertex to arrive at
     */
    public void calculateShortestPathFromSource(String source, String destination) {

        //Define all weights maps
        final HashMap<String, Double> distances = new HashMap<>();
        final HashMap<String, Double> connections = new HashMap<>();
        final HashMap<String, Double> flyingTime = new HashMap<>();
        final HashMap<String, Double> totalDurationTime = new HashMap<>();

        final HashMap<String, LinkedList<String>> distancesPaths = new HashMap<>();
        final HashMap<String, LinkedList<String>> connectionsPaths = new HashMap<>();
        final HashMap<String, LinkedList<String>> flyingTimePaths = new HashMap<>();
        final HashMap<String, LinkedList<String>> totalDurationTimePaths = new HashMap<>();

        final HashMap<String, VisitStatus> vertexStates = new HashMap<>();

        this.adjacencyMap.forEach((key, value) -> {
            //? Initialize paths map
            distancesPaths.put(key, new LinkedList<>());
            connectionsPaths.put(key, new LinkedList<>());
            flyingTimePaths.put(key, new LinkedList<>());
            totalDurationTimePaths.put(key, new LinkedList<>());

            //? Initialize distances map
            distances.put(key, Double.MAX_VALUE);
            connections.put(key, Double.MAX_VALUE);
            flyingTime.put(key, Double.MAX_VALUE);
            totalDurationTime.put(key, Double.MAX_VALUE);
        });

        //? Add information obtained from source
        distances.put(source, 0.0);
        connections.put(source, 0.0);
        flyingTime.put(source, 0.0);
        totalDurationTime.put(source, 0.0);

        distancesPaths.get(source).add(source);

        this.adjacencyMap.get(source).forEach(edge -> {
                    distances.put(edge.destination, edge.distance);
                    connections.put(edge.destination, 1.0);
                    flyingTime.put(edge.destination, Converter.distanceInMinutes(edge.distance));
                    totalDurationTime.put(edge.destination, edge.getFirstFlightCost(LocalTime.now()).minutesTaken);

                    distancesPaths.get(edge.destination).addAll(new ArrayList<>(Arrays.asList(source, edge.destination)));
                    connectionsPaths.get(edge.destination).addAll(new ArrayList<>(Arrays.asList(source, edge.destination)));
                    flyingTimePaths.get(edge.destination).addAll(new ArrayList<>(Arrays.asList(source, edge.destination)));
                    totalDurationTimePaths.get(edge.destination).addAll(new ArrayList<>(Arrays.asList(source, edge.destination)));
                }
        );

        resetVertexStates(source, vertexStates);
        calculateShortestPathFromSource(distances, distancesPaths, vertexStates, DijkstraParam.DISTANCES);
        printResults(source, destination, "Distance", "Km", distances.get(destination), distancesPaths.get(destination));

        resetVertexStates(source, vertexStates);
        calculateShortestPathFromSource(connections, connectionsPaths, vertexStates, DijkstraParam.CONNECTIONS);
        printResults(source, destination, "Connections", "", connections.get(destination), connectionsPaths.get(destination));

        resetVertexStates(source, vertexStates);
        calculateShortestPathFromSource(flyingTime, flyingTimePaths, vertexStates, DijkstraParam.FLYING_TIME);
        printResults(source, destination, "Flying Time", "h", flyingTime.get(destination), flyingTimePaths.get(destination));

        resetVertexStates(source, vertexStates);
        calculateShortestPathFromSource(totalDurationTime, totalDurationTimePaths, vertexStates, DijkstraParam.TOTAL_DURATION_TIME);
        printResults(source, destination, "Total Duration Time", "h", totalDurationTime.get(destination), totalDurationTimePaths.get(destination));
    }

    private void calculateShortestPathFromSource(
            HashMap<String, Double> weights,
            HashMap<String, LinkedList<String>> paths,
            HashMap<String, VisitStatus> vertexState,
            DijkstraParam param
    ) {
        for (int i = 0; i < this.adjacencyMap.size()
                && vertexState.values().stream().anyMatch(visitStatus -> visitStatus.equals(VisitStatus.NOT_VISITED)); i++) {

            //? Get min weight not visited vertex
            String currentVertex = weights.entrySet()
                    .stream()
                    .filter(entry -> vertexState.get(entry.getKey()).equals(VisitStatus.NOT_VISITED))
                    .min(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            if (currentVertex == null)
                return;

            //? Set x as visited
            vertexState.put(currentVertex, VisitStatus.VISITED);

            this.adjacencyMap.get(currentVertex)
                    .stream()
                    .filter(edge -> vertexState.get(edge.destination).equals(VisitStatus.NOT_VISITED))
                    .forEach(edge -> {
                        double currentWeight = weights.get(edge.destination);
                        double newWeight = switch (param) {
                            case DISTANCES -> weights.get(currentVertex) + edge.distance;
                            case CONNECTIONS -> weights.get(currentVertex) + 1;
                            case FLYING_TIME -> weights.get(currentVertex) + Converter.distanceInMinutes(edge.distance);
                            case TOTAL_DURATION_TIME -> weights.get(currentVertex) + edge.getFirstFlightCost(LocalTime.now()).minutesTaken;
                        };
                        if (newWeight < currentWeight) {
                            weights.put(edge.destination, newWeight);
                            paths.put(edge.destination, new LinkedList<>());
                            paths.get(currentVertex).forEach(paths.get(edge.destination)::add);
                            paths.get(edge.destination).add(edge.destination);
                        }
                    });
        }
    }

    /**
     * Prints shortest path results
     *
     * @param source         Origin vertex
     * @param destination    Vertex gone to
     * @param name           Name of the parameter
     * @param measureUnit    Parameter measure unit
     * @param smallestWeight Parameter weight
     * @param smallestPath   Parameter path
     */
    private void printResults(String source, String destination, String name, String measureUnit, Double smallestWeight, LinkedList<String> smallestPath) {
        if (smallestPath.size() == 0)
            System.out.println("There is no available path");
        else if (measureUnit.equals("h")) {
            System.out.println(String.format("%s (%s, %S) -> weight: %d:%d %s, path: %s\n",
                    name, source, destination, (int) (smallestWeight / 60), (int) (smallestWeight % 60), measureUnit, Arrays.toString(smallestPath.toArray())))
            ;
        } else
            System.out.println(String.format("%s (%s, %S) -> weight: %s %s, path: %s\n",
                    name, source, destination, smallestWeight.toString(), measureUnit, Arrays.toString(smallestPath.toArray())));

    }

    private void resetVertexStates(String source, HashMap<String, VisitStatus> vertexStates) {
        this.adjacencyMap.forEach((key, value) -> {
            if (key.equals(source))
                vertexStates.put(key, VisitStatus.VISITED);
            else
                vertexStates.put(key, VisitStatus.NOT_VISITED);
        });
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
        vertices = Objects.requireNonNullElseGet(verticesSet, this.adjacencyMap::keySet);
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
                }
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
