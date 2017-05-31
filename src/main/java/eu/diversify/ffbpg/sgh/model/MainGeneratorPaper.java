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
public class MainGeneratorPaper extends Thread {
    
    public static int NB_EXTINCTIONS = 48; // Number of random extinction sequences to calculate robustness at each steps
    public static int NB_EXTINCTIONS_THREADS = 8;

    public static ArrayList<SGHExecSimulation> simulateExecution(SGHSystem graph) {
        ArrayList<SGHExecSimulation> result = new ArrayList<SGHExecSimulation>();
        for(int i=0; i<graph.servers.size()/10+1; i++) {
            SGHExecSimulation s = new SGHExecSimulation(graph, i, 1000); s.execute();
            result.add(s);
        }
        { SGHExecSimulation s = new SGHExecSimulation(graph, graph.servers.size()/4, 1000); s.execute(); result.add(s); }
        { SGHExecSimulation s = new SGHExecSimulation(graph, graph.servers.size()/2, 1000); s.execute(); result.add(s); }
        
       return result;
    }

    public static void main(String[] args) {
    
        if(NB_EXTINCTIONS % NB_EXTINCTIONS_THREADS != 0) {
            System.err.println("Error: NB_EXTINCTIONS_THREADS must be a multiple of NB_EXTINCTIONS !");
            System.exit(-1);
        }
        
         System.out.println( "Max Heap memory: "+
                 ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax()
    );
         
         //System.exit(0);
        
        File outdir = new File("./new_paper_out/" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
        outdir.mkdirs();
        System.out.println("Output Folder: " + outdir.getAbsolutePath());
        
        SGHSystem graph = SGHSystem.generateSGHSystem(250,50);
        //SGHSystem graph = SGHSystem.generateRealisticManualSGHSystem(250,50);
        /*
        int links_removed = graph.removedSomeRedondancy();
        System.out.println("Number of links removed in the generated graph:" + links_removed);
        
        links_removed = graph.removedSomeRedondancy();
        System.out.println("Number of links removed in the generated graph:" + links_removed);
        
        links_removed = graph.removedSomeRedondancy();
        System.out.println("Number of links removed in the generated graph:" + links_removed);
        */

        FileUtils.writeTextFile(outdir, "InitialGraph.txt", graph.dumpData(true));
        graph.exportGraphStatistics(outdir);
        System.out.println(graph.dumpData(false));
        try {
            graph.exportClientsToJSONFiles(outdir, "InitialGraph", new File("host_ip_list_wide.txt"));
        } catch (Exception ex) {
            Logger.getLogger(MainGeneratorPaper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Graph generated.");
        
       // System.exit(0);
        
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
            SGHExtinctionSequence[] eseqs = graph.computeRandomExtinctionSequence(NB_EXTINCTIONS, NB_EXTINCTIONS_THREADS);
            double[] avg_seq = SGHExtinctionSequence.averageExtinctionSequences(eseqs);
            //System.out.println(Arrays.toString(avg_seq));
            double robustness = SGHExtinctionSequence.averageRobustnessIndex(avg_seq);
            SGHExtinctionSequence.writeGNUPlotScriptForAll(eseqs, outdir, "Extinctions_Initial");
            System.out.println("Robustness (SGH) = " + robustness);
            SGHExecSimulation.writeResults(outdir, simulateExecution(graph));
        }
        Long after_time = System.currentTimeMillis();
        System.out.println("Time for 100 extinctions on SGH: " + (after_time - before_time));

        System.out.println("\nSimulating 10 random evolutions of the initial graph");
        for (int i=0; i<5; i++) {
            SGHSimulation sim_CR_SR = new SGHSimulation(graph, true, true, false, false, true);
            System.out.println("Random Simulation " + i  + "/10...");
            MainGeneratorPaper t = new MainGeneratorPaper("RANDOM_" + i, sim_CR_SR, outdir);
            t.start();
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(MainGeneratorPaper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println("\nSimulating 10 SMART evolutions of the initial graph");
        for (int i=0; i<5; i++) {
            SGHSimulation sim_CS_SS = new SGHSimulation(graph, true, true, true, true, true);
            System.out.println("SMART Simulation " + i  + "/10...");
            MainGeneratorPaper t = new MainGeneratorPaper("SMART_" + i, sim_CS_SS, outdir);
            t.start();
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(MainGeneratorPaper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    
    String name;
    SGHSimulation sim;
    File basedir; 
    
    public MainGeneratorPaper(String name, SGHSimulation simulation, File basedir) {
        this.name = name;
        this.sim = simulation;
        this.basedir = basedir;
    }
            
    public void run() {
        File outdir = new File(basedir, name);
        outdir.mkdir();
        
        sim.startSimulation(100);
        sim.exportRobustnessData("Simulation", outdir);
        
        SGHSystem graph = sim.system;
        FileUtils.writeTextFile(outdir, "FinalGraph.txt", graph.dumpData(true));
        
        {
            SGHExtinctionSequence[] eseqs = graph.computeRandomExtinctionSequence(NB_EXTINCTIONS, NB_EXTINCTIONS_THREADS);
            double[] avg_seq = SGHExtinctionSequence.averageExtinctionSequences(eseqs);
            System.out.println(Arrays.toString(avg_seq));
            double robustness = SGHExtinctionSequence.averageRobustnessIndex(avg_seq);
            SGHExtinctionSequence.writeGNUPlotScriptForAll(eseqs, outdir, "Extinctions_Final");
            System.out.println("Robustness = " + robustness);
            try {
                graph.exportClientsToJSONFiles(outdir, "FinalGraph", new File("host_ip_list_wide.txt"));
            } catch (Exception ex) {
                Logger.getLogger(MainGeneratorPaper.class.getName()).log(Level.SEVERE, null, ex);
            }
            graph.exportGraphStatistics(outdir);
            System.out.println(graph.dumpData(false));
            SGHExecSimulation.writeResults(outdir, simulateExecution(graph));
        }
    }
    
    
}
