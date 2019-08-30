package testdatastructures.Map;

import datastructures.map.HashMap;
import datastructures.map.Map;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.StringJoiner;

import static org.junit.Assert.*;

public class HashMapTest {
    private Map map = new HashMap();
    private Map map1 = new HashMap();

    @Before
    public void fillMap() {
        map.put("user", "value1");
        map.put("user", "value2");
        map.put("user1", "value3");
        map.put("user1", "value4");
        map.put("user2", null);
        map.put("user2", "value5");
        map.put("user2", null);
        map.put(null, "value6");
        //new bucket
        map.put(null, "value7");
        map.put(1, "value8");
        map.put(0, "value9");
        map.put(-1, "value10");
        map1.put("user", "value21");
        map1.put("123", "!!!");
        map1.put("456", "???");
    }

    @Test
    public void testPut() {
        assertNull(map.put(2, 155));
        assertEquals(155, map.put(2, "value12"));
    }

    @Test
    public void testPutIfAbsent() {
        assertNull(map.putIfAbsent(3, "value13"));
        assertEquals("value13", map.putIfAbsent(3, "value14"));
        assertEquals("value2", map.putIfAbsent("user", "value15"));
    }

    @Test
    public void testGet() {
        assertEquals("value2", map.get("user"));
        assertEquals("value7", map.get(null));
        assertNull(map.get("user2"));
        assertEquals("value9", map.get(0));
        assertEquals("value10", map.get(-1));
    }

    @Test
    public void testPutAll() {
        map.putAll(map1);
        StringJoiner string = new StringJoiner(", ");
        for (Object entry : map) {
            string.add(String.valueOf((((HashMap.Entry) entry).getValue())));
        }
        assertEquals("value7, value9, value8, value10, value4, value21, null, ???, !!!", string.toString());
    }

    @Test
    public void testPutAllIfAbsent() {
        map.putAllIfAbsent(map1);
        StringJoiner string = new StringJoiner(", ");
        for (Object entry : map) {
            string.add(String.valueOf((((HashMap.Entry) entry).getValue())));
        }
        assertEquals("value7, value9, value8, value10, value4, value2, null, ???, !!!", string.toString());
    }

    @Test
    public void testRemove() {
        assertEquals("value2", map.remove("user"));
        assertNull(map.remove("user"));
        assertEquals("value7", map.remove(null));
        assertNull(map.remove("user2"));
        assertEquals("value9", map.remove(0));
        assertEquals("value10", map.remove(-1));
    }

    @Test
    public void testContainsKey() {
        assertTrue(map.containsKey(1));
        assertFalse(map.containsKey(4));
    }

    @Test
    public void testSize() {
        assertEquals(7, map.size());
        map.put(4, "value16");
        assertEquals(8, map.size());
    }

    @Test
    public void testForEach() {
        StringJoiner string = new StringJoiner(", ");
        for (Object entry : map) {
            string.add(String.valueOf((((HashMap.Entry) entry).getValue())));
        }
        assertEquals("value7, value9, value8, value10, value4, value2, null", string.toString());
    }

    @Test
    public void testIteratorRemove() {
        for (Iterator iterator = map.iterator(); iterator.hasNext(); ) {
            iterator.next();
            iterator.remove();
        }
        assertEquals("", map.toString());
    }

    @Test
    public void testToString() {
        assertEquals("null: value7; 0: value9; 1: value8; -1: value10; user1: value4; user: value2; user2: null", map.toString());
    }
}


