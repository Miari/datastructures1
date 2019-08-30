package testdatastructures.Lists;

import datastructures.lists.List;
import datastructures.lists.ArrayList;

public class ArrayListTest extends AbstractListTest {

    @Override
    protected List getListOfIntegers() {
        List list = new ArrayList();
        fillListOfIntegers(list);
        return list;
    }

    @Override
    protected List getMixedList() {
        List list = new ArrayList();
        fillMixedList(list);
        return list;
    }


    @Override
    protected List getEmptyList() {
        List list = new ArrayList();
        return list;
    }
}
