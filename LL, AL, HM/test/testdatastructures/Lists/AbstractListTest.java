package testdatastructures.Lists;

import datastructures.lists.List;
import org.junit.Test;

import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class AbstractListTest {

    List listIntegers = getListOfIntegers();
    List listMixed = getMixedList();
    List listEmpty = getEmptyList();

    protected abstract List getListOfIntegers();

    protected abstract List getMixedList();

    protected abstract List getEmptyList();

    protected List fillListOfIntegers(List list) {
        list.add(2, 0);
        list.add(1, 0);
        list.add(0, 0);
        list.add(3, 3);
        list.add(5, 2);
        return list;
    }

    protected List fillMixedList(List list) {
        list.add(7);
        list.add(null);
        list.add("Hello");
        list.add(true);
        list.add("Hello");
        list.add(9.5);
        return list;
    }

    @Test
    public void testSize() {
        assertEquals(5, listIntegers.size());
    }

    @Test
    public void testRemove() {
        assertEquals(2, listIntegers.remove(3));
        assertEquals(3, listIntegers.remove(3));
        assertEquals(0, listIntegers.remove(0));
        assertEquals(2, listIntegers.size());
        assertEquals(1, listIntegers.remove(0));
        assertEquals(5, listIntegers.remove(0));
        assertEquals(0, listIntegers.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveEmpty() {
        listEmpty.remove(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveNegative() {
        assertEquals(2, listIntegers.remove(-1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIncorrectIndex() {
        assertEquals(2, listIntegers.remove(5));
    }

    @Test
    public void testGet() {
        assertEquals(5, listIntegers.get(2));
        assertEquals(0, listIntegers.get(0));
        assertEquals(3, listIntegers.get(4));
        assertEquals(5, listIntegers.size());
        assertEquals(null, listMixed.get(1));
        assertTrue((boolean) listMixed.get(3));
        assertEquals(6, listMixed.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetEmpty() {
        listEmpty.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetNegative() {
        assertEquals(5, listIntegers.get(-1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIncorrectIndex() {
        assertEquals(5, listIntegers.get(5));
    }

    @Test
    public void testSet() {
        assertEquals(1, listIntegers.set(11, 1));
        assertEquals(11, listIntegers.get(1));
        assertEquals(0, listIntegers.set(12, 0));
        assertEquals(12, listIntegers.get(0));
        assertEquals(3, listIntegers.set(13, 4));
        assertEquals(13, listIntegers.get(4));
        assertEquals(5, listIntegers.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetEmpty() {
        listEmpty.set(11, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetNegative() {
        listIntegers.set(11, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetIncorrectIndex() {
        listIntegers.set(11, 5);
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void testClearIncorrectIndex() {
        listIntegers.clear();
        listIntegers.add("Element");
        listIntegers.get(1);
    }

    @Test
    public void testEmpty() {
        assertFalse(listIntegers.isEmpty());
        listIntegers.clear();
        assertTrue(listIntegers.isEmpty());
    }

    @Test
    public void testContains() {
        assertTrue(listIntegers.contains(0));
        assertTrue(listIntegers.contains(1));
        assertTrue(listIntegers.contains(3));
        assertFalse(listIntegers.contains(-1));
        assertTrue(listMixed.contains(null));
        assertTrue(listMixed.contains("Hello"));
        assertTrue(listMixed.contains(true));
    }

    @Test
    public void testIndexOf() {
        assertEquals(2, listIntegers.indexOf(5));
        assertEquals(0, listIntegers.indexOf(0));
        assertEquals(4, listIntegers.indexOf(3));
        assertEquals(-1, listIntegers.indexOf(7));
        assertEquals(1, listMixed.indexOf(null));
        assertEquals(2, listMixed.indexOf("Hello"));
        assertEquals(3, listMixed.indexOf(true));
    }

    @Test
    public void testIndexOfEmpty() {
        assertEquals(-1, listEmpty.indexOf(5));
    }

    @Test
    public void testLastIndexOf() {
        assertEquals(2, listIntegers.lastIndexOf(5));
        assertEquals(4, listMixed.lastIndexOf("Hello"));
    }

    @Test
    public void testLastIndexOfEmpty() {
        assertEquals(-1, listEmpty.lastIndexOf(0));
    }

    @Test
    public void testToString() {
        assertEquals("[]", listEmpty.toString());
        listEmpty.add(5);
        assertEquals("[5]", listEmpty.toString());
        listEmpty.add(null);
        listEmpty.add("New");
        assertEquals("[5, null, New]", listEmpty.toString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testClearAndGet() {
        listIntegers.clear();
        listIntegers.get(0);
    }

    @Test
    public void testClear() {
        listIntegers.clear();
        assertTrue(listIntegers.isEmpty());
    }

    @Test
    public void testClearOfEmptyArray() {
        listMixed.clear();
        assertTrue(listMixed.isEmpty());
    }

    @Test
    public void testClearAndAdd() {
        listIntegers.clear();
        listIntegers.add(5);
        assertEquals(5, listIntegers.get(0));
        assertEquals(1, listIntegers.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testClearAndAddWithIncorrectIndex() {
        listIntegers.clear();
        listIntegers.add(6, 2);
        assertEquals(null, listIntegers.get(0));
    }

    @Test
    public void testClearAndAddWithZeroIndex() {
        listIntegers.clear();
        listIntegers.add(null, 0);
        assertEquals(null, listIntegers.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testClearAndSet() {
        listIntegers.clear();
        listIntegers.set(21, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testClearAndRemove() {
        listIntegers.clear();
        listIntegers.remove(1);
    }

    @Test
    public void testClearAndContains() {
        listIntegers.clear();
        assertFalse(listIntegers.contains(1));
    }

    @Test
    public void testClearAndIndexOf() {
        listIntegers.clear();
        assertEquals(-1, listIntegers.indexOf(1));
    }

    @Test
    public void testClearAndToString() {
        listIntegers.clear();
        assertEquals("[]", listIntegers.toString());
    }

    @Test
    public void testForEach() {
        StringJoiner string = new StringJoiner(", ");
        for (Object obj : listIntegers) {
            string.add(String.valueOf(obj));
        }
        assertEquals("0, 1, 5, 2, 3", string.toString());
    }
}
