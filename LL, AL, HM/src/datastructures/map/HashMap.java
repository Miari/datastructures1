package datastructures.map;

import java.util.*;

public class HashMap<K, V> implements Map<K, V> {

    private static final int INITIAL_CAPACITY = 5;
    private static final int GROWTH_FACTOR = 2;
    private static final double LOAD_FACTOR = 0.75;

    private int newCapacity = INITIAL_CAPACITY; //не могу сделать её локальной переменной метода rebuild, т.к. она испрользуется также в методе getIndex (где я не могу использовать buckets.length, потому что он вызывается митодом add для ArrayList<Entry>[] newBuckets)

    private ArrayList<Entry<K, V>>[] buckets = new ArrayList[INITIAL_CAPACITY];
    private int size;

    @Override
    public V put(K key, V value) {
        Entry<K, V> result = getEntry(key);
        if (result == null) {
            add(key, value);
            if (size > buckets.length * LOAD_FACTOR) {
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
            if (size > buckets.length * LOAD_FACTOR) {
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
            for (Iterator iterator = buckets[index].iterator(); iterator.hasNext(); ) {
                Entry<K, V> current = (Entry<K, V>) iterator.next();
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
            result.add(String.valueOf(current.key + ": " + current.value));
        }
        return result.toString();
    }

    public static class Entry<K, V> {
        private K key;
        private V value;

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int getIndex(K key) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode() % newCapacity);
    }

    private void add(K key, V value) {
        int index = getIndex(key);
        if (buckets[index] == null) {
            buckets[index] = new ArrayList<>();
        }
        buckets[index].add(new Entry<>(key, value));
        size++;
    }

    private void add(K key, V value, ArrayList<Entry<K, V>>[] newBuckets) {
        int index = getIndex(key);
        if (newBuckets[index] == null) {
            newBuckets[index] = new ArrayList<>();
        }
        newBuckets[index].add(new Entry<>(key, value));
    }

    private Entry<K, V> getEntry(K key) {
        int index = getIndex(key);
        if (buckets[index] != null) {
            for (Entry current : buckets[index]) {
                if (Objects.equals(current.key, key)) {
                    return current;
                }
            }
        }
        return null;
    }

    private void rebalance() {
        newCapacity = buckets.length * GROWTH_FACTOR;
        ArrayList<Entry<K, V>>[] newBuckets = new ArrayList[newCapacity];
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
        int bucketIndex;

        List<Entry<K, V>> bucket = buckets[bucketIndex];
        Iterator<Entry<K, V>> bucketIterator = bucket.iterator();

        public boolean hasNext() {
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
                bucket = buckets[bucketIndex];
                bucketIterator = bucket.iterator();
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
        }
    }
}


