import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * An iterator used to visualize the search for the exit by coloring searched nodes one at a time.
 *
 * @param <T> the type of data stored in the vertices of the maze
 */
class MazeIterator<T> implements Iterator<Vertex> {
  // The worklist of vertices to iterate through
  private Deque<Vertex> worklist;

  // The backtrack deque to keep track of the correct path from the start to the exit
  private Deque<Vertex> backtrack;

  /**
   * Constructs a new MazeIterator with the specified worklist.
   *
   * @param worklist the worklist of vertices to iterate through
   */
  MazeIterator(Deque<Vertex> worklist) {
    this.worklist = worklist;
    this.backtrack = new ArrayDeque<Vertex>();
  }

  /**
   * Returns true if the next element to be iterated in the worklist is in the backtrack deque,
   * meaning it is part of the correct path from the start to the exit.
   *
   * @return true if the next element is in the backtrack deque, false otherwise
   */
  public boolean correctPath() {
    if (this.hasNext()) {
      return backtrack.contains(worklist.getFirst());
    } else {
      return false;
    }
  }

  /**
   * Returns true if the worklist has at least one element.
   *
   * @return true if the worklist has at least one element, false otherwise
   */
  public boolean hasNext() {
    return this.worklist.size() > 0;
  }

  /**
   * Removes the first item in the worklist and adds it to the backtrack deque, then returns it.
   *
   * @return the next vertex in the worklist
   */
  public Vertex next() {
    Vertex vertex = this.worklist.pop();
    backtrack.add(vertex);
    return vertex;
  }
}
