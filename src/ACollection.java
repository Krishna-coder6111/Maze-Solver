import java.util.ArrayDeque;
import java.util.Deque;

/**

 An abstract class representing a collection for searching.
 @param <T> the type of item to be stored in the collection
 */
abstract class ACollection<T> implements ICollection<T> {
  Deque<T> contents;
  /**

   Constructs a new empty collection.
   */
  ACollection() {
    this.contents = new ArrayDeque<T>();
  }
  /**

   Returns true if the contents deque is empty.
   @return true if the contents deque is empty
   */
  public boolean isEmpty() {
    return this.contents.isEmpty();
  }
  /**

   Removes and returns the first element in the contents deque.
   @return the first element in the contents deque
   */
  public abstract T remove();
  /**

   Adds the given item to the front of the contents deque.
   @param item the item to add to the front of the contents deque
   */
  public void add(T item) {
    this.contents.addFirst(item);
  }
}