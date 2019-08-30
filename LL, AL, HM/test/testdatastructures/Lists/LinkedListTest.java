package testdatastructures.Lists;

import datastructures.lists.LinkedList;
import datastructures.lists.List;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class LinkedListTest extends AbstractListTest{


    @Override
    protected List getListOfIntegers() {
        List list = new LinkedList();
        fillListOfIntegers(list);
        return list;
    }

    @Override
    protected List getMixedList() {
        List list = new LinkedList();
        fillMixedList(list);
        return list;
    }

    @Override
    protected List getEmptyList() {
        List list = new LinkedList();
        return list;
    }

    @Test
    public void testIteratorRemove() {
        for (Iterator iterator = listIntegers.iterator(); iterator.hasNext();)
        {
            iterator.remove();
            iterator.next();
        }
        assertEquals("[]", listIntegers.toString());
    }
}
