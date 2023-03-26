import java.util.ArrayDeque;

/**
 * Represents a queue to be used in a breadth-first search.
 *
 * @param <T> the type of elements stored in the queue
 */
class Queue<T> extends ACollection<T> {

  /**
   * Constructor for the Queue class.
   */
  Queue() {
    this.contents = new ArrayDeque<T>();
  }

  /**
   * Adds the given item to the tail of the contents deque.
   *
   * @param item the item to be added to the queue
   */
  @Override
  public void add(T item) {
    this.contents.addLast(item);
  }

  /**
   * Removes the first item from the queue and returns it.
   *
   * @return the first item removed from the queue
   */
  public T remove() {
    return this.contents.removeFirst();
  }
}