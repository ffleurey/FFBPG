/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.ExtinctionSequence;
import eu.diversify.ffbpg.gui.BPGraphFrame;
import eu.diversify.ffbpg.utils.FileUtils;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ffl
 */
public class GenerateForFullScaleExperiment extends Thread {
    
    public static void main(String[] args) {
        
         System.out.println( "Max Heap memory: "+
                 ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax()
    );
         
         //System.exit(0);
        
        File outdir = new File("./sgh_out_large/" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
        outdir.mkdirs();
        System.out.println("Output Folder: " + outdir.getAbsolutePath());
        
        SGHSystem graph = SGHSystem.generateSGHSystem(5000,750);
        FileUtils.writeTextFile(outdir, "InitialGraph.txt", graph.dumpData(true));
        graph.exportGraphStatistics(outdir);
        System.out.println(graph.dumpData(false));
        try {
            graph.exportClientsToJSONFiles(outdir, "InitialGraph", new File("host_ip_list_wide.txt"));
        } catch (Exception ex) {
            Logger.getLogger(GenerateForFullScaleExperiment.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Graph generated.");
        
        /*
        Long before_time = System.currentTimeMillis();
        BPGraph bpg = graph.toBPGraph();
        Long after_time = System.currentTimeMillis();
        
        System.out.println("Time to translate to BPG: " + (after_time - before_time));
 
        before_time = System.currentTimeMillis();
        {
            ExtinctionSequence[] seqs = bpg.performRandomExtinctionSequences(250, bpg.getPlatforms().size() );
            double[] avg_seq = ExtinctionSequence.averageExtinctionSequences(seqs);
            double robustness = ExtinctionSequence.averageRobustnessIndex(avg_seq, bpg.getPlatforms().size());
            ExtinctionSequence.writeGNUPlotScriptForAll(seqs, outdir, "Extinctions_Initial_BPG");
            System.out.println("Robustness (BPG) = " + robustness);
        }
        after_time = System.currentTimeMillis();
        System.out.println("Time for 250 extinctions on BPG: " + (after_time - before_time));
        
        BPGraphFrame frame = new BPGraphFrame();
        frame.setBPGraph(bpg, true);
        frame.setVisible(true);
        
        */
        
        Long before_time = System.currentTimeMillis();
        {
            SGHExtinctionSequence[] eseqs = graph.computeRandomExtinctionSequence(100, 10);
            double[] avg_seq = SGHExtinctionSequence.averageExtinctionSequences(eseqs);
            //System.out.println(Arrays.toString(avg_seq));
            double robustness = SGHExtinctionSequence.averageRobustnessIndex(avg_seq);
            SGHExtinctionSequence.writeGNUPlotScriptForAll(eseqs, outdir, "Extinctions_Initial");
            System.out.println("Robustness (SGH) = " + robustness);
        }
        Long after_time = System.currentTimeMillis();
        System.out.println("Time for 100 extinctions on SGH: " + (after_time - before_time));

        
        SGHSimulation sim_CR_SR = new SGHSimulation(graph, true, true, false, false, false);
        SGHSimulation sim_CS_SS = new SGHSimulation(graph, true, true, true, true, false);
        
      
        new GenerateForFullScaleExperiment("CR_SR", sim_CR_SR, outdir).start();
        new GenerateForFullScaleExperiment("CS_SS", sim_CS_SS, outdir).start();
        


    }
    
    
    String name;
    SGHSimulation sim;
    File basedir; 
    
    public GenerateForFullScaleExperiment(String name, SGHSimulation simulation, File basedir) {
        this.name = name;
        this.sim = simulation;
        this.basedir = basedir;
    }
            
    public void run() {
        File outdir = new File(basedir, name);
        outdir.mkdir();
        
        sim.startSimulation(50);
        sim.exportRobustnessData("Simulation", outdir);
        
        SGHSystem graph = sim.system;
        FileUtils.writeTextFile(outdir, "FinalGraph.txt", graph.dumpData(true));
        
        {
            SGHExtinctionSequence[] eseqs = graph.computeRandomExtinctionSequence(100, 10);
            double[] avg_seq = SGHExtinctionSequence.averageExtinctionSequences(eseqs);
            System.out.println(Arrays.toString(avg_seq));
            double robustness = SGHExtinctionSequence.averageRobustnessIndex(avg_seq);
            SGHExtinctionSequence.writeGNUPlotScriptForAll(eseqs, outdir, "Extinctions_Final");
            System.out.println("Robustness = " + robustness);
            try {
                graph.exportClientsToJSONFiles(outdir, "FinalGraph", new File("host_ip_list_wide.txt"));
            } catch (Exception ex) {
                Logger.getLogger(GenerateForFullScaleExperiment.class.getName()).log(Level.SEVERE, null, ex);
            }
            graph.exportGraphStatistics(outdir);
        }
    }
    
    
}
