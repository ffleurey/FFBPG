package eu.diversify.ffbpg.collections;

import java.util.ArrayList;

public abstract class AbstractSortedIntegerCollection {

    protected ArrayList<Integer> _list;

    protected AbstractSortedIntegerCollection() {
        _list = new ArrayList<Integer>();
    }

    protected AbstractSortedIntegerCollection(String data) {
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

    private AbstractSortedIntegerCollection(ArrayList<Integer> list) {
        _list = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            _add_last(list.get(i));
        }
    }

 
    public abstract void add(int value);

    public void remove(int value) {
        _list.remove((Object)value);
    }
    
   

    protected void _add_last(int value) {
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

    public boolean contains(Integer v) {
        for (int i = 0; i < size(); i++) {
            if(this.get(i) > v) break;
            if (this.get(i) == v) return true;
        }
        return false;
    }
}
