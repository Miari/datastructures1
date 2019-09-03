package testdatastructures.Map;

import datastructures.map.HashMap;
import datastructures.map.Map;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class HashMapTest {
    private Map<Object, String> firstMap = new HashMap<>(3, 0.6);
    private Map<Object, String> secondMap = new HashMap<>(3, 0.6);

    @Before
    public void fillMap() {
        firstMap.put("user", "value1");
        firstMap.put("user", "value2");
        firstMap.put("user1", "value3");
        firstMap.put("user1", "value4");
        firstMap.put("user2", null);
        firstMap.put("user2", "value5");
        firstMap.put("user2", null);
        firstMap.put(null, "value6");
        //new bucket
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
        assertEquals("value2", firstMap.putIfAbsent("user", "value15"));
    }

    @Test
    public void testGet() {
        assertEquals("value2", firstMap.get("user"));
        assertEquals("value7", firstMap.get(null));
        assertNull(firstMap.get(12));
        assertEquals("value9", firstMap.get(0));
        assertEquals("value10", firstMap.get(-1));
    }

    @Test
    // переделала тест PutAll без использования метода "toString" но не так, как ты делал на ревью. Там ты удалял по ключу, а у меня этот тест совсем не на удаление.
    // тут я проверяю метода putAll, который берёт содержимое одной мапы и полностью копирует его в другую, заменяя при этом значения, если запись с таким ключом уже есть в той мапе, в которую копируют.
    // в итоге, я проверяю, что все знаения из secondMap были скопированы в firstMap, и то, что у скопированных записей значения в первой и второй мапе равны.
    // а на удаление у меня есть отдельный тест testRemove, он изначально сделан без toString и null передаётся там как знаение, а не как строка

    public void testPutAll() {
        firstMap.putAll(secondMap);
        for (HashMap.Entry<Object, String> entry : secondMap) {
            Object key = entry.getKey();
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
        assertTrue(firstMap.containsKey("user"));
        assertEquals("value2", firstMap.get("user"));
    }

    @Test
    public void testRemove() {
        assertEquals("value2", firstMap.remove("user"));
        assertNull(firstMap.remove(101));
        assertEquals("value7", firstMap.remove(null));
        assertNull(firstMap.remove("user2"));
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
        for (HashMap.Entry<Object, String> entry : firstMap) {
            Object key = entry.getKey();
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


