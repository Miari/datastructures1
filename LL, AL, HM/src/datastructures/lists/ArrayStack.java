package datastructures.lists;

public class ArrayStack {
    //LIFO
    int size;
    Object[] array;

    ArrayStack() {
        this(5);
    }

    ArrayStack(int initialCapacity) {
        array = new Object[initialCapacity];
    }

    public void push(Object value) {
        if (size == array.length) {
            Object[] tempArray = new Object[(int) (size * 1.5) + 1];
            System.arraycopy(array, 0, tempArray, 0, size);
            array = tempArray;
        }
        array[size] = value;
        size++;
    }

    public Object pop() {
        if (size == 0) {
            System.out.println("No Objects in array");
            return null;
        }
        Object result = array[size - 1];
        array[size - 1] = null;
        size--;
        return result;
    }

    public Object peek() {
        if (size == 0) {
            System.out.println("No Objects in array");
            return null;
        }
        return array[size - 1];
    }

    public int size() {
        return size;
    }
}
