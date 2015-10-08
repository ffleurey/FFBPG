/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.sgh.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ffl
 */
public class MainGeneratorStats {
    
    public static void main(String[] args) {
        SGHSystem graph = SGHSystem.generateSGHSystem(500, 50);
        
        System.out.println(graph.dumpData());
        
        SGHExtinctionSequence[] eseqs = graph.computeRandomExtinctionSequence(50);
        
        double[] avg_seq = SGHExtinctionSequence.averageExtinctionSequences(eseqs);
        
        System.out.println(Arrays.toString(avg_seq));
        
        double robustness = SGHExtinctionSequence.averageRobustnessIndex(avg_seq);
        
        File outdir = new File("./sgh_out/");
        
        System.out.println("Output Folder: " + outdir.getAbsolutePath());
        SGHExtinctionSequence.writeGNUPlotScriptForAll(eseqs, outdir, "Extinctions");
        
        
        System.out.println("Robustness = " + robustness);
        
        SGHSimulation sim = new SGHSimulation(graph);
        sim.startSimulation(100);
        
    }
    
    
}
