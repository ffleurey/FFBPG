package eu.diversify.ffbpg.collections;

import java.util.ArrayList;

public class SortedIntegerCollection {

    private ArrayList<Integer> _list;

    public SortedIntegerCollection() {
        _list = new ArrayList<Integer>();
    }

    public SortedIntegerCollection(String data) {
        _list = new ArrayList<Integer>();
        String v[] = data.replace("{", " ").replace("}", " ").trim().split(",");
        if (!v[0].equals("")) {
            for (int i = 0; i < v.length; i++) {
                this.add(Integer.parseInt(v[i].trim()));
            }
        }
    }

    public String toString() {
        String result = "{";
        for (int i = 0; i < _list.size(); i++) {
            result += _list.get(i).toString();
            if (i < _list.size() - 1) {
                result += ", ";
            }
        }
        result += "}";
        return result;
    }

    private SortedIntegerCollection(ArrayList<Integer> list) {
        _list = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            _add_last(list.get(i));
        }
    }

    public SortedIntegerCollection clone() {
        return new SortedIntegerCollection(_list);
    }

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

    public void remove(int value) {
        _list.remove(value);
    }
    
    public void addAll(SortedIntegerCollection other) {
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

    private void _add_last(int value) {
        _list.add(_list.size(), value);
    }

    public int[] toArray() {
        int[] res = new int[_list.size()];
        for (int i = 0; i < _list.size(); i++) {
            res[i] = _list.get(i);
        }
        return res;
    }

    public int get(int index) {
        return _list.get(index);
    }

    public int size() {
        return _list.size();
    }

    public boolean containsAll(SortedIntegerCollection other) {
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

    public boolean containsSome(SortedIntegerCollection other) {
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
    
    public boolean contains(Integer v) {
        for (int i = 0; i < size(); i++) {
            if(this.get(i) > v) break;
            if (this.get(i) == v) return true;
        }
        return false;
    }

    public SortedIntegerCollection intersection(SortedIntegerCollection other) {
        SortedIntegerCollection result = new SortedIntegerCollection();
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

    public SortedIntegerCollection union(SortedIntegerCollection other) {
        SortedIntegerCollection result = this.clone();
        result.addAll(other);
        return result;
    }
}
