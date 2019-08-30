package datastructures.lists;

public interface List<E> extends Iterable<E> {


    // add value to the end of the list
    void add(E value);

    // we can add value by index between [0, size]
    // otherwise throw new IndexOutOfBoundsException
    // [A, B, C] . add("D", [0, 1, 2, 3])
    void add(E value, int index);

    // we can remove value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException
    // [A, B, C] remove = 0
    // [B (index = 0) , C (index = 1)]
    E remove(int index);

    // [A, B, C] size = 3
    // we can get value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException
    E get(int index);

    // we can set value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException
    E set(E value, int index);

    void clear();

    int size();

    boolean isEmpty();

    boolean contains(E value);

    int indexOf(E value);

    int lastIndexOf(E value);

    // [A, B, C]
    String toString();
}
