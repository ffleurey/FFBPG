/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.gui;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ffl
 */
public class BPGraphPLatformTableModel extends AbstractTableModel {

    private BPGraph data;
    
    public BPGraph getBPGraph() {
        return data;
    } 
    
    public void setBPGraph(BPGraph g) {
        data = g;
        fireTableDataChanged();
    }
   
    public int getRowCount() {
        if (data == null) return 0;
        return data.getPlatforms().size();
    }

    public int getColumnCount() {
        return 7;
    }
    
     DecimalFormat df = new DecimalFormat("#.###");

    public Object getValueAt(int l, int c) {
       
        
        Platform p = data.getPlatforms().get(l);
        
        switch(c) {
            case 0: return p.getName();
            case 1: return p.getLoad();
            case 2: return p.getLinked_apps(data).size();
            case 3: return p.getProvidedServices().toString();
            case 4: return p.getProvidedServicesMinRedondancyPopulation(data).toString();
            case 5: return p.getAll_known_services(data).toString();
            case 6: return p.getServicesMinRedondancyPopulation(data).toString();
            
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
            case 0: return "Platform";
            case 1: return "Load";
            case 2: return "Links";
            case 3: return "Prov. Srv";
            case 4: return "Prov Srv Min Red";
            case 5: return "All Srv";
            case 6: return "All Srv Min Red";

            default: return "???";
        }
    }
    
    
    
    
}
