package datastructures.map;

import java.util.*;

public class HashMap<K, V> implements Map<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 5;
    private static final int GROWTH_FACTOR = 2;
    private static double DEFAULT_LOAD_FACTOR = 0.75;

    private double loadFactor;
    private ArrayList<Entry<K, V>>[] buckets;
    private int size;

    public HashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashMap(int initialCapacity, double loadFactor) {
        buckets = new ArrayList[initialCapacity];
        this.loadFactor = loadFactor;
    }

    @Override
    public V put(K key, V value) {
        Entry<K, V> result = getEntry(key);
        if (result == null) {
            add(key, value);
            if (size > buckets.length * loadFactor) {
                rebalance();
            }
            return null;
        }
        V oldValue = result.value;
        result.value = value;
        return oldValue;
    }

    @Override
    public void putAll(Map<K, V> map) {
        for (Entry<K, V> entry : map) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        Entry<K, V> result = getEntry(key);
        if (result == null) {
            add(key, value);
            if (size > buckets.length * loadFactor) {
                rebalance();
            }
            return null;
        }
        return result.value;
    }

    @Override
    public void putAllIfAbsent(Map<K, V> map) {
        for (Entry<K, V> entry : map) {
            putIfAbsent(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V get(K key) {
        Entry<K, V> foundEntry = getEntry(key);
        if (foundEntry != null) {
            return foundEntry.value;
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int index = getIndex(key);
        if (buckets[index] != null) {
            for (Iterator<Entry<K, V>> iterator = buckets[index].iterator(); iterator.hasNext(); ) {
                Entry<K, V> current = iterator.next();
                if (Objects.equals(current.key, key)) {
                    iterator.remove();
                    return current.value;
                }
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return (getEntry(key) != null);
    }

    @Override
    public int size() {
        return size;
    }

    public String toString() {
        StringJoiner result = new StringJoiner("; ");
        for (Entry<K, V> current : this) {
            result.add(current.key + ": " + current.value);
        }
        return result.toString();
    }

    public static class Entry<K, V> {
        private K key;
        private V value;

        public K getKey() {
            return key;
        }

        private V getValue() {
            return value;
        }

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int getIndex(K key) {
        return getIndex(key, buckets);
    }

    private int getIndex(K key, ArrayList[] buckets) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode() % buckets.length);
    }

    private void add(K key, V value) {
        add(key, value, buckets);
        size++;
    }

    private void add(K key, V value, ArrayList<Entry<K, V>>[] newBuckets) {
        int index = getIndex(key, newBuckets);
        if (newBuckets[index] == null) {
            newBuckets[index] = new ArrayList<>();
        }
        newBuckets[index].add(new Entry<>(key, value));
    }

    private Entry<K, V> getEntry(K key) {
        int index = getIndex(key);
        if (buckets[index] != null) {
            for (Entry<K, V> current : buckets[index]) {
                if (Objects.equals(current.key, key)) {
                    return current;
                }
            }
        }
        return null;
    }

    private void rebalance() {
        ArrayList<Entry<K, V>>[] newBuckets = new ArrayList[buckets.length * GROWTH_FACTOR];
        for (Entry<K, V> entry : this) {
            add(entry.key, entry.value, newBuckets);
        }
        buckets = newBuckets;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Entry<K, V>> {

        private int bucketIndex;
        Iterator<Entry<K, V>> bucketIterator;

        MyIterator() {
            while (buckets[bucketIndex] == null && bucketIndex < buckets.length) {
                bucketIndex++;
            }
            bucketIterator = buckets[bucketIndex].iterator();
        }

        public boolean hasNext() {
            boolean changeOfBucketIndexIndicator = false;
            if (bucketIndex >= buckets.length) {
                return false;
            }
            while (buckets[bucketIndex] == null) {
                bucketIndex++;
                changeOfBucketIndexIndicator = true;
                if (bucketIndex >= buckets.length) {
                    return false;
                }
            }
            if (changeOfBucketIndexIndicator) {
                bucketIterator = buckets[bucketIndex].iterator();
            }

            if (!bucketIterator.hasNext()) {
                bucketIndex++;
                if (bucketIndex >= buckets.length) {
                    return false;
                }
                while ((buckets[bucketIndex] == null)) {
                    bucketIndex++;
                    if (bucketIndex >= buckets.length) {
                        return false;
                    }
                }
                bucketIterator = buckets[bucketIndex].iterator();
            }

            return bucketIterator.hasNext();
        }

        @Override
        public Entry<K, V> next() {
            return bucketIterator.next();
        }

        @Override
        public void remove() {
            bucketIterator.remove();
            size--;
        }
    }
}


