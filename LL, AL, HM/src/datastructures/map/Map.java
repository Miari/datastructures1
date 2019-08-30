package datastructures.map;

public interface Map<K, V> extends Iterable<HashMap.Entry<K, V>> {
    V put(K key, V value);

    void putAll(Map<K, V> map);

    V putIfAbsent(K key, V value);

    void putAllIfAbsent(Map<K, V> map);

    V get(K key);

    V remove(K key);

    boolean containsKey(K key);

    int size();

    String toString();
}
