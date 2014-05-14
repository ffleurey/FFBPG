/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import eu.diversify.ffbpg.BPGraph;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author ffl
 */
public class BPGraphListModel extends AbstractListModel<BPGraph> {

    private ArrayList<BPGraph> data = new ArrayList<BPGraph>();
    
    public void clear() {
        data.clear();
        fireIntervalRemoved(this, 0, 0);
    }
    
    public ArrayList<BPGraph> getData() {
        return data;
    } 
    
    public void add(BPGraph g) {
        data.add(g);
        fireIntervalAdded(this, data.size()-1, data.size()-1);
    }
    
    public int getSize() {
        return data.size();
    }

    public BPGraph getElementAt(int i) {
        return data.get(i);
    }
    
}
