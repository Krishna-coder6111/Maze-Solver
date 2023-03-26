/**
 * Interface representing searching in a collection.
 *
 * @param <T> the type of items stored in the collection
 */
interface ICollection<T> {
  /**
   * Checks if this collection is empty.
   *
   * @return true if this collection is empty, false otherwise
   */
  boolean isEmpty();

  /**
   * Adds the given item to this collection.
   *
   * @param item the item to add to the collection
   */
  void add(T item);

  /**
   * Removes the first item from this collection.
   *
   * @return the first item in the collection
   */
  T remove();
}