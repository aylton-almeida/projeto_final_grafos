package models;

import utils.Converter;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class DirectedGraph extends Graph {
    @Override
    public void addVertexFromString(String string) {
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

            //? Initialize vertex states
            vertexStates.put(key, VisitStatus.NOT_VISITED);
        });

        //? Add information obtained from source
        distances.put(source, 0.0);
        connections.put(source, 0.0);
        flyingTime.put(source, 0.0);
        totalDurationTime.put(source, 0.0);

        distancesPaths.get(source).add(source);

        vertexStates.put(source, VisitStatus.VISITED);

        this.adjacencyMap.get(source).forEach(edge -> {
                    distances.put(edge.destination, edge.distance);
                    connections.put(edge.destination, 1.0);
                    flyingTime.put(edge.destination, (double) Converter.distanceInMinutes(edge.distance));
                    totalDurationTime.put(edge.destination, (double) edge.getFirstFlightCost(LocalTime.now()).minutesTaken);

                    distancesPaths.get(edge.destination).addAll(new ArrayList<>(Arrays.asList(source, edge.destination)));
                    connectionsPaths.get(edge.destination).addAll(new ArrayList<>(Arrays.asList(source, edge.destination)));
                    flyingTimePaths.get(edge.destination).addAll(new ArrayList<>(Arrays.asList(source, edge.destination)));
                    totalDurationTimePaths.get(edge.destination).addAll(new ArrayList<>(Arrays.asList(source, edge.destination)));
                }
        );

        calculateShortestPathFromSource(distances, distancesPaths, vertexStates, DijkstraParam.DISTANCES);
        printResults("Distance", "Km", distances.get(destination), distancesPaths.get(destination));

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
                        switch (param) {
                            case DISTANCES -> distancesCompare(weights, paths, edge, currentVertex);
                        }
                    });
        }
    }

    private void distancesCompare(HashMap<String, Double> weights, HashMap<String, LinkedList<String>> paths, Edge currentEdge, String currentVertex) {
        double currentWeight = weights.get(currentEdge.destination);
        double newWeight = weights.get(currentVertex) + currentEdge.distance;
        if (newWeight < currentWeight) {
            weights.put(currentEdge.destination, newWeight);
            paths.put(currentEdge.destination, new LinkedList<>());
            paths.get(currentVertex).forEach(paths.get(currentEdge.destination)::add);
            paths.get(currentEdge.destination).add(currentEdge.destination);
        }
    }

    private void printResults(String name, String measureUnit, Double smallestWeight, LinkedList<String> smallestPath) {
        if (smallestPath.size() == 0)
            System.out.println("There is no available path");
        else
            System.out.println(String.format("%s -> weight: %s %s, path: %s\n", name, smallestWeight.toString(), measureUnit, Arrays.toString(smallestPath.toArray())));
    }

}
