package eu.diversify.ffbpg.collections;

import java.util.ArrayList;

public class SortedIntegerBag extends AbstractSortedIntegerCollection {

 
    public SortedIntegerBag() {
        _list = new ArrayList<Integer>();
    }

    public SortedIntegerBag(String data) {
        super(data);
    }

    private SortedIntegerBag(ArrayList<Integer> list) {
        _list = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            _add_last(list.get(i));
        }
    }

    public SortedIntegerBag clone() {
        return new SortedIntegerBag(_list);
    }

    @Override
    public void add(int value) {
        // Keep the list sorted
        for (int i = 0; i < _list.size(); i++) {
            if (value <= _list.get(i)) {
                _list.add(i, value);
                return; // New index has been added
            }
        }
        // the new index should be added last in the list
        _add_last(value);
    }

 
}
