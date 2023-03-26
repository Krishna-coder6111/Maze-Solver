import java.util.Comparator;

/**
 * This class implements a Comparator for edges based on their weights.
 */
class WeightComparator implements Comparator<Edge> {
  /**
   * Compares the weight of the two given edges.
   *
   * @param e1 The first edge to be compared.
   * @param e2 The second edge to be compared.
   * @return int Returns 0 if both edges have the same weight, 1 if the weight of the first edge is greater,
   *         and -1 if the weight of the second edge is greater.
   */
  public int compare(Edge e1, Edge e2) {
    if (e1.weight == e2.weight) {
      return 0;
    } else if (e1.weight > e2.weight) {
      return 1;
    } else {
      return -1;
    }
  }
}
