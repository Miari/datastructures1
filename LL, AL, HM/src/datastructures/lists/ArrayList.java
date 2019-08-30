package datastructures.lists;

import java.util.Iterator;
import java.util.StringJoiner;

public class ArrayList<E> implements List<E> {
    private E[] array;
    int size;

    public ArrayList() {
        this(5);
    }

    public ArrayList(int initialCapacity) {
        array = (E[]) new Object[initialCapacity];
    }

    @Override
    public void add(E value) {
        add(value, size);
    }

    @Override
    public void add(E value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Valid index is between 0 and " + size + " (inclusive), but it was " + index);
        }
        if (array.length == size) {
            E[] tempArray = (E[]) new Object[(int) (array.length * 1.5) + 1];
            System.arraycopy(array, 0, tempArray, 0, size);
            array = tempArray;
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = value;
        size++;
    }

    private void validateIndex(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Index does not exist, because of the array is empty");
        }
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Valid index is between 0 and " + (size - 1) + " (inclusive), but it was " + index);
        }
    }

    @Override
    public E remove(int index) {
        E removedElement = array[index];
        validateIndex(index);
        System.arraycopy(array, index + 1, array, index, size - 1 - index);
        array[size - 1] = null;
        size--;
        return removedElement;
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        return array[index];
    }

    @Override
    public E set(E value, int index) {
        validateIndex(index);
        E oldValue = array[index];
        array[index] = value;
        return oldValue;
    }

    @Override
    public void clear() {
        for (E i : array) {
            i = null;
        }
        size = 0; //не нашла ничего против такого решения, кроме того, что массив физически остаётся в памяти. Но если я всё заменю на null, будет фактически будет то жн самое.
        // Все тесты тоже прошли успешно- я не нашла кейса, при котором бы я могла обратиться к существкющим элементамм после такой реализации иетода clear.
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E value) {
        return indexOf(value) != -1;
    }

    private boolean checkElement(int i, E value) {
        if (array[i] != null && array[i].equals(value)) {
            return true;
        } else if (array[i] == null && array[i] == value) {
            return true;
        } else {
            return false;
        }
    }

    // нашла неплохое объяснени разницы между equals и ==, но всё равно до конца не понимаю:
    //  Однако, нужно не забывать, что, если объект ни на что не ссылается(null), то вызов метода equals этого объекта приведет к NullPointerException.
    //  Также нужно помнить, что при сравнении объектов оба они могут быть null и операция obj1 == obj2 в данном случае будет true, а вызов equals приведет к исключению NullPointerException.
    //  Как мы видим, при помощи операции == сравниваются ссылки на объекты. http://dr-magic.blogspot.com/2009/01/java-se-1.html

    @Override
    public int indexOf(E value) {
        for (int i = 0; i < size; i++) {
            if (checkElement(i, value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E value) {
        for (int i = size - 1; i >= 0; i--) {
            if (checkElement(i, value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringJoiner string = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < size; i++) {
            string.add(String.valueOf(array[i]));
        }
        return string.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<E> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return (index != size);
        }

        @Override
        public E next() {
            return array[index++];
        }
    }
}


