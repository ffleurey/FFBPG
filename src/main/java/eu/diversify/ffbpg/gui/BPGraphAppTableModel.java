/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.gui;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ffl
 */
public class BPGraphAppTableModel extends AbstractTableModel {

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
        return data.getApplications().size();
    }

    public int getColumnCount() {
        return 11;
    }
    
     DecimalFormat df = new DecimalFormat("#.###");

    public Object getValueAt(int l, int c) {
       
        
        Application a = data.getApplications().get(l);
        
        switch(c) {
            case 0: return a.getName();
            case 1: return a.getRequiredServices().toString();
            case 2: return a.getLinkedPlatformNames();
            case 3: return a.dependenciesSatisfied();
            case 4: return a.getServicesPopulation().toString();
            case 5: return df.format(a.getServicesPopulation().getShannonIndex());
            case 6: return df.format(a.getServicesPopulation().getShannonEquitability());
            case 7: return df.format(a.getServicesPopulation().getPopulationMeanSize());
            case 8: return df.format(a.getServicesPopulation().getPopulationMedianSize());
            case 9: return -1; //a.getApplicationNeighborhoods(data).size();
            case 10: return -1; //a.getPlatformNeighborhoods(data).size();
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
            case 0: return "Application";
            case 1: return "Req. Services";
            case 2: return "Linked Platforms";
            case 3: return "Running";
            case 4: return "Srv Pop";
            case 5: return "shannon";
            case 6: return "Equit.";
            case 7: return "Mean";
            case 8: return "Med";
            case 9: return "#N_App";
            case 10: return "#N_Plat";
            default: return "???";
        }
    }
    
    
    
    
}
