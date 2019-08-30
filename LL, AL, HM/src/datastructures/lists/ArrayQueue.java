package datastructures.lists;

import java.util.NoSuchElementException;

public class ArrayQueue {
    //FIFO
    int size;
    Object[] array;


    ArrayQueue() {
        this(5);
    }

    ArrayQueue(int initialCapacity) {
        array = new Object[initialCapacity];
    }

    public void push(Object value) {
        if (size == array.length) {
            Object[] tempArray = new Object[(int) (array.length * 1.5) + 1];
            System.arraycopy(array, 0, tempArray, 0, size);
            array = tempArray;
        }
        array[size] = value;
        size++;
    }

    public Object pop() {
        if (size == 0) {
            throw new NoSuchElementException("No Objects in array");
        }
        Object result = array[0];
        System.arraycopy(array, 1, array, 0, size - 1);
        array[size - 1] = null;
        size--;
        return result;
    }

    public Object peek() {
        if (size == 0) {
            throw new NoSuchElementException("No Objects in array");
        }
        return array[0];
    }

    public int size() {
        return size;
    }
}
