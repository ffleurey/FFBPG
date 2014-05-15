/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.gui;

import eu.diversify.ffbpg.BPGraph;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ffl
 */
public class BPGraphTableModel extends AbstractTableModel {

    private ArrayList<BPGraph> data = new ArrayList<BPGraph>();
    
    public void clear() {
        data.clear();
        fireTableDataChanged();
    }
    
    public ArrayList<BPGraph> getData() {
        return data;
    } 
    
    public void add(BPGraph g) {
        data.add(g);
        fireTableRowsInserted(data.size()-1, data.size()-1);
    }
   
    public int getSize() {
        return data.size();
    }

    public BPGraph getElementAt(int i) {
        return data.get(i);
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return 8;
    }

    public Object getValueAt(int l, int c) {
        BPGraph g = data.get(l);
        
        switch(c) {
            case 0: return g;
            case 1: return new Integer(g.getApplications().size());
            case 2: return new Integer(g.getPlatforms().size());
            case 3: return new Integer(g.getAllUsedServices().size());
            case 4: return new Integer(g.getLinkCount());
            case 5: return new Double(g.getAvgSrvCountPerApp());
            case 6: return new Double(g.getAvgSrvCountPerPlatform());
            case 7: return new Double(g.getAvgLinkCountPerApp());
            default: return "???";
        }
        
    }

    @Override
    public boolean isCellEditable(int l, int c) {
        return false;
    }
    
/*
    @Override
    public Class<?> getColumnClass(int c) {
        if (c == 0) return BPGraph.class;
        else return Integer.class;
    }
*/
    @Override
    public String getColumnName(int c) {
        
        switch(c) {
            case 0: return "Name";
            case 1: return "#APP";
            case 2: return "#PLA";
            case 3: return "#SRV";
            case 4: return "#LINK";    
            case 5: return "#SRV/APP";
            case 6: return "#SRV/PLA";
            case 7: return "#LINK/APP";
            default: return "???";
        }
    }
    
    
    
    
}
