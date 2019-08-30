package datastructures.lists;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public class LinkedList<E> implements List<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    @Override
    public void add(E value) {
        add(value, size);
    }

    @Override
    public void add(E value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Valid index is between 0 and " + size + " (inclusive), but your index was " + index);
        }

        Node<E> newNode = new Node<>(value);
        if (size == 0) {
            head = tail = newNode;
        } else if (index == 0) {
            head.previous = newNode;
            newNode.next = head;
            head = newNode;
        } else if (index == size) {
            tail.next = new Node<>(value);
            tail.next.previous = tail;
            tail = tail.next;
        } else {
            Node<E> current = getNode(index - 1);
            Node<E> next = current.next;
            current.next = new Node<>(value);
            current.next.previous = current;
            current.next.next = next;
            next.previous = current.next;
        }
        size++;
    }

    @Override
    public E remove(int index) {
        validateIndex(index);
        Node<E> current = head;
        if (size == 1) {
            head = tail = null;
        } else if (index == 0) {
            head = head.next;
            head.previous = null;
        } else if (index == size - 1) {
            current = tail;
            tail = tail.previous;
            tail.next = null;
        } else {
            current = getNode(index);
            current.previous.next = current.next;
            current.next.previous = current.previous;
        }
        size--;
        return current.value;
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        Node<E> current = getNode(index);
        return current.value;
    }

    @Override
    public E set(E value, int index) {
        validateIndex(index);
        Node<E> current = getNode(index);
        E oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public void clear() {
        size = 0;
        tail = head = null;
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

    @Override
    public int indexOf(E value) {
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(current.value, value)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E value) {
        Node<E> current = tail;
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(current.value, value)) {
                return i;
            }
            current = current.previous;
        }
        return -1;
    }

    private void validateIndex(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Index does not exist, because of the array is empty");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Valid index is between 0 and " + (size - 1) + " (inclusive), but your index was " + index);
        }
    }

    private Node<E> getNode(int index) {
        Node<E> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    @Override
    public String toString() {
        Node<E> current = head;
        if (head == null) {
            return "[]";
        }
        StringJoiner string = new StringJoiner(", ", "[", "]");
        while (current != null) {
            string.add(String.valueOf(current.value));
            current = current.next;
        }
        return string.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<E> {
        Node<E> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E result = current.value;
            current = current.next;
            return result;
        }

        @Override
        public void remove() {
            if ((current == head) && (current == tail)) {
                head = tail = null;
            } else if (current == head) {
                head = head.next;
                head.previous = null;
            } else if (current == tail) {
                tail = tail.previous;
                tail.next = null;
            } else {
                current.previous.next = current.next;
                current.next.previous = current.previous;
            }
            size--;
        }
    }

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> previous;

        public Node(T value) {
            this.value = value;
        }
    }
}
