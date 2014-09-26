package eu.diversify.ffbpg.collections;

import java.util.ArrayList;

public class SortedIntegerSet extends AbstractSortedIntegerCollection {

 
    public SortedIntegerSet() {
        _list = new ArrayList<Integer>();
    }

    public SortedIntegerSet(String data) {
        super(data);
    }

    private SortedIntegerSet(ArrayList<Integer> list) {
        _list = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            _add_last(list.get(i));
        }
    }

    public SortedIntegerSet clone() {
        return new SortedIntegerSet(_list);
    }

    @Override
    public void add(int value) {
        // Keep the list sorted
        for (int i = 0; i < _list.size(); i++) {
            if (value < _list.get(i)) {
                _list.add(i, value);
                return; // New index has been added
            } else if (value == _list.get(i)) {
                return; // This index was already here
            }
        }
        // the new index should be added last in the list
        _add_last(value);
    }

   
    public void addAll(SortedIntegerSet other) {
        if (this.size() == 0) {
            for (int i=0; i<other.size(); i++) {
                this._add_last(other.get(i));
            }
        } else {
            int i = 0;
            int j = 0;
            while (i < other.size()) {
                if (this.get(j) < other.get(i)) {
                    j++;
                    if (j >= this.size()) {
                        _list.add(j, other.get(i));
                        i++;
                    }
                } else if (other.get(i) == this.get(j)) {
                    i++; // found, look for the next one
                } else {
                    _list.add(j, other.get(i));
                    i++;
                    j++;
                }
            }
        }
    }

     public boolean containsAll(SortedIntegerSet other) {
        if (other.size() > this.size()) {
            return false;
        }
        int i = 0;
        int j = 0;
        while (i < other.size()) {
            if (this.get(j) < other.get(i)) {
                j++; // move to the next index for this
                if (j >= this.size()) return true;
            } else if (other.get(i) == this.get(j)) {
                i++; // found, look for the next one
            } else {
                return false; // we did not find other.getServiceIDX(i) in this collection
            }
        }
        return true;
    }

    public boolean containsSome(SortedIntegerSet other) {
        if (this.size() == 0) {
            return false;
        }
        int j = 0;
        for (int i = 0; i < other.size(); i++) {
            while (this.get(j) < other.get(i)) {
                j++;
                if (j >= this.size()) {
                    return false;
                }
            } // move to the next index for this
            if (other.get(i) == this.get(j)) {
                return true;
            }
        }
        return false;
    }


    public SortedIntegerSet intersection(SortedIntegerSet other) {
        SortedIntegerSet result = new SortedIntegerSet();
        if (this.size() == 0) {
            return result;
        }
        int j = 0;
        for (int i = 0; i < other.size(); i++) {
            while (this.get(j) < other.get(i)) {
                j++;
                if (j >= this.size()) {
                    return result;
                }
            }
            if (other.get(i) == this.get(j)) {
                result._add_last(this.get(j));
            }
        }
        return result;
    }

    public SortedIntegerSet union(SortedIntegerSet other) {
        SortedIntegerSet result = this.clone();
        result.addAll(other);
        return result;
    }

    public SortedIntegerSet minus(SortedIntegerSet other) {
        SortedIntegerSet result = this.clone();
        for (int i=0; i<other.size(); i++) {
            result.remove(other.get(i));
        }
        return result;
    }
}
