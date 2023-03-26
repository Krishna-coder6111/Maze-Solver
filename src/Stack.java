import java.util.ArrayDeque;

/**
 * Represents a stack to be used in a depth-first search.
 *
 * @param <T> the type of elements stored in the stack
 */
class Stack<T> extends ACollection<T> {

  /**
   * Constructor for the Stack class.
   */
  Stack() {
    this.contents = new ArrayDeque<T>();
  }

  /**
   * Adds the given item to the front of the contents deque.
   *
   * @param item the item to be added to the stack
   */
  @Override
  public void add(T item) {
    this.contents.addFirst(item);
  }

  /**
   * Removes the first item from the stack and returns it.
   *
   * @return the first item removed from the stack
   */
  public T remove() {
    return this.contents.pop();
  }
}