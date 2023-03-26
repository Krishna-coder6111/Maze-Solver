import java.util.Random;

/**
 * Represents an edge connecting two vertices in a graph.
 */
class Edge {
  Vertex from;
  Vertex to;
  int weight;

  /**
   * Constructs an edge between two vertices with a random weight.
   *
   * @param from the starting vertex of the edge
   * @param to   the ending vertex of the edge
   */
  Edge(Vertex from, Vertex to) {
    this.from = from;
    this.to = to;
    this.weight = new Random().nextInt(1000);
  }

  /**
   * Constructs an edge between two vertices with a random or fixed weight.
   *
   * @param from the starting vertex of the edge
   * @param to   the ending vertex of the edge
   * @param rand true if the edge weight should be random, false if it should be fixed
   */
  Edge(Vertex from, Vertex to, boolean rand) {
    this.from = from;
    this.to = to;
    if (rand) {
      this.weight = new Random().nextInt(1000);
    } else {
      this.weight = new Random(1).nextInt(1000);
    }
  }

  /**
   * Constructs an edge between two vertices with a specified weight.
   *
   * @param from   the starting vertex of the edge
   * @param to     the ending vertex of the edge
   * @param weight the weight of the edge
   */
  Edge(Vertex from, Vertex to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  /**
   * Returns the other vertex in this edge where the given vertex is not from.
   *
   * @param other one of the two vertices in the edge
   * @return the other vertex in the edge
   */
  public Vertex otherVertex(Vertex other) {
    return this.to.equals(other) ? this.from : this.to;
  }

  /**
   * Custom implementation of equals method for Edge objects.
   *
   * @param other the object to compare this edge to
   * @return true if the other object is an Edge with the same from vertex, to vertex, and weight as this edge
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (!(other instanceof Edge)) {
      return false;
    }

    Edge o = (Edge) other;
    return this.from.equals(o.from) && this.to.equals(o.to) && this.weight == o.weight;
  }

  /**
   * Custom implementation of hashCode method for Edge objects.
   *
   * @return the hash code value for this edge
   */
  @Override
  public int hashCode() {
    return from.hashCode() + to.hashCode() * 1000 + weight;
  }
}
