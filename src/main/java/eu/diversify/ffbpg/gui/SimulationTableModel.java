/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.gui;

import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Simulation;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ffl
 */
public class SimulationTableModel extends AbstractTableModel {

    private ArrayList<Simulation> data = new ArrayList<Simulation>();
    
    public void clear() {
        data.clear();
        fireTableDataChanged();
    }
    
    public ArrayList<Simulation> getData() {
        return data;
    } 
    
    public void add(Simulation g) {
        data.add(g);
        fireTableRowsInserted(data.size()-1, data.size()-1);
    }
   
    public int getSize() {
        return data.size();
    }

    public Simulation getElementAt(int i) {
        return data.get(i);
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return 5;
    }

    public Object getValueAt(int l, int c) {
        Simulation s = data.get(l);
        
        switch(c) {
            case 0: return s;
            case 1: return s.getInitial();
            case 2: return s.getApplicationLinksEvolutionScenario().getName();
            case 3: return s.getPlatformServicesEvolutionScenario().getName();
            case 4: return new Integer(s.getSteps().size());
          
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
            case 1: return "Initial";
            case 2: return "App Evo.";
            case 3: return "Pla Evo.";
            case 4: return "#Steps";    
            default: return "???";
        }
    }
    
    
    
    
}
