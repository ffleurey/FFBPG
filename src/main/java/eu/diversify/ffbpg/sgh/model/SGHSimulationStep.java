/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.sgh.model;

/**
 *
 * @author ffl
 */
public class SGHSimulationStep {
    
    public int step;
    
    public SGHSimulationStep(int step) {
        this.step = step;
    }
    
    public int client_adaptation_space = 0;
    public int server_adaptation_space = 0;
    
    public int added_links = 0;
    public int removed_links = 0;
    
    public int added_features = 0;
    public int removed_features = 0;
    
    public int delta_features = 0;
    public int delta_links = 0;
    
    public SGHExtinctionSequence[] extinctions;
    double[] avg_seq;
    public double robustness;
    
    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append("Step "); b.append(step);b.append("\t");
        b.append(robustness);b.append("\t");
        b.append("C ");b.append(client_adaptation_space);b.append("\t");
        b.append(added_links);b.append("\t");
        b.append(removed_links);b.append("\t");
        b.append(delta_links);b.append("\t");
        b.append("S "); b.append(server_adaptation_space);b.append("\t");
        b.append(added_features);b.append("\t");
        b.append(removed_features);b.append("\t");
        b.append(delta_features);b.append("\t");
        return b.toString();
    }
    
}
