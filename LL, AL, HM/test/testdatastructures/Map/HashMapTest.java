package testdatastructures.Map;

import datastructures.map.HashMap;
import datastructures.map.Map;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class HashMapTest {
    private Map<Integer, String> firstMap = new HashMap<>(3, 0.7);
    private Map<Integer, String> secondMap = new HashMap<>();

    @Before
    public void fillMap() {
        firstMap.put(101, "value1");
        firstMap.put(101, "value2");
        firstMap.put(11, "value3");
        firstMap.put(11, "value4");
        firstMap.put(12, null);
        firstMap.put(12, "value5");
        firstMap.put(12, null);
        firstMap.put(null, "value6");
        firstMap.put(null, "value7");
        firstMap.put(1, "value8");
        firstMap.put(0, "value9");
        firstMap.put(-1, "value10");

        secondMap.put(10, "value21");
        secondMap.put(123, "!!!");
        secondMap.put(456, "???");
    }

    @Test
    public void testPut() {
        assertNull(firstMap.put(2, "155"));
        assertEquals("155", firstMap.put(2, "value12"));
    }

    @Test
    public void testPutIfAbsent() {
        assertNull(firstMap.putIfAbsent(3, "value13"));
        assertEquals("value13", firstMap.putIfAbsent(3, "value14"));
        assertEquals("value2", firstMap.putIfAbsent(101, "value15"));
    }

    @Test
    public void testGet() {
        assertEquals("value2", firstMap.get(101));
        assertEquals("value7", firstMap.get(null));
        assertNull(firstMap.get(12));
        assertEquals("value9", firstMap.get(0));
        assertEquals("value10", firstMap.get(-1));
    }

    @Test
    public void testPutAll() {
        firstMap.putAll(secondMap);
        for (HashMap.Entry<Integer, String> entry : secondMap) {
            Integer key = entry.getKey();
            assertTrue(firstMap.containsKey(key));
            assertEquals(firstMap.get(key), secondMap.get(key));
        }
    }

    @Test
    public void testPutAllIfAbsent() {
        firstMap.putAllIfAbsent(secondMap);
        assertTrue(firstMap.containsKey(123));
        assertEquals("!!!", firstMap.get(123));
        assertTrue(firstMap.containsKey(456));
        assertEquals("???", firstMap.get(456));
        assertTrue(firstMap.containsKey(101));
        assertEquals("value2", firstMap.get(101));
    }

    @Test
    public void testRemove() {
        assertEquals("value2", firstMap.remove(101));
        assertNull(firstMap.remove(101));
        assertEquals("value7", firstMap.remove(null));
        assertNull(firstMap.remove(12));
        assertEquals("value9", firstMap.remove(0));
        assertEquals("value10", firstMap.remove(-1));
    }

    @Test
    public void testContainsKey() {
        assertTrue(firstMap.containsKey(1));
        assertFalse(firstMap.containsKey(4));
    }

    @Test
    public void testSize() {
        assertEquals(7, firstMap.size());
        firstMap.put(4, "value16");
        assertEquals(8, firstMap.size());
    }

    @Test
    public void testForEach() {
        for (HashMap.Entry<Integer, String> entry : firstMap) {
            Integer key = entry.getKey();
            assertTrue(firstMap.containsKey(key));
        }
    }

    @Test
    public void testIteratorRemove() {
        for (Iterator iterator = firstMap.iterator(); iterator.hasNext(); ) {
            iterator.next();
            iterator.remove();
        }
        assertEquals(0, firstMap.size());
    }
}


