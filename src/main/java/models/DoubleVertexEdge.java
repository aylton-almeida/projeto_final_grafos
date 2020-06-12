package models;

import java.util.Objects;

public class DoubleVertexEdge {
    final String vertex1;
    final String vertex2;
    final double weight;

    public DoubleVertexEdge(String vertex1, String vertex2, double weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoubleVertexEdge)) return false;
        DoubleVertexEdge that = (DoubleVertexEdge) o;
        return Double.compare(that.weight, weight) == 0 &&
                (Objects.equals(vertex1, that.vertex1) &&
                        Objects.equals(vertex2, that.vertex2))
                ||
                (Objects.equals(vertex1, that.vertex2) &&
                        Objects.equals(vertex2, that.vertex1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex1, vertex2, weight);
    }
}
