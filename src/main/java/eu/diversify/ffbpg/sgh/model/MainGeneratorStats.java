/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.utils.FileUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author ffl
 */
public class MainGeneratorStats extends Thread {
    
    public static void main(String[] args) {
        
        File outdir = new File("./sgh_out/" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
        outdir.mkdirs();
        System.out.println("Output Folder: " + outdir.getAbsolutePath());
        
        SGHSystem graph = SGHSystem.generateSGHSystem(200, 50);
        FileUtils.writeTextFile(outdir, "InitialGraph.txt", graph.dumpData());
        System.out.println(graph.dumpData());
        
        {
            SGHExtinctionSequence[] eseqs = graph.computeRandomExtinctionSequence(50);
            double[] avg_seq = SGHExtinctionSequence.averageExtinctionSequences(eseqs);
            System.out.println(Arrays.toString(avg_seq));
            double robustness = SGHExtinctionSequence.averageRobustnessIndex(avg_seq);
            SGHExtinctionSequence.writeGNUPlotScriptForAll(eseqs, outdir, "Extinctions_Initial");
            System.out.println("Robustness = " + robustness);
        }
        
        SGHSimulation sim_CR_SR = new SGHSimulation(graph, true, true, false, false);
        SGHSimulation sim_CS_SS = new SGHSimulation(graph, true, true, true, true);
        
        SGHSimulation sim_CR_SS = new SGHSimulation(graph, true, true, true, false);
        SGHSimulation sim_CS_SR = new SGHSimulation(graph, true, true, false, true);
        
        //new MainGeneratorStats("CR_SR", sim_CR_SR, outdir).start();
        new MainGeneratorStats("CS_SS", sim_CS_SS, outdir).start();
        
        //new MainGeneratorStats("CR_SS", sim_CR_SS, outdir).start();
        //new MainGeneratorStats("CS_SR", sim_CS_SR, outdir).start();
        
        SGHSimulation sim_CR_SN = new SGHSimulation(graph, false, true, false, false);
        SGHSimulation sim_CN_SR = new SGHSimulation(graph, true, false, false, false);
        
        SGHSimulation sim_CS_SN = new SGHSimulation(graph, false, true, true, true);
        SGHSimulation sim_CN_SS = new SGHSimulation(graph, true, false, true, true);
        
        //new MainGeneratorStats("CR_SN", sim_CR_SN, outdir).start();
        //new MainGeneratorStats("CN_SR", sim_CN_SR, outdir).start();
        
        //new MainGeneratorStats("CS_SN", sim_CS_SN, outdir).start();
        //new MainGeneratorStats("CN_SS", sim_CN_SS, outdir).start();

    }
    
    
    String name;
    SGHSimulation sim;
    File basedir; 
    
    public MainGeneratorStats(String name, SGHSimulation simulation, File basedir) {
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
        FileUtils.writeTextFile(outdir, "FinalGraph.txt", graph.dumpData());
        
        {
            SGHExtinctionSequence[] eseqs = graph.computeRandomExtinctionSequence(50);
            double[] avg_seq = SGHExtinctionSequence.averageExtinctionSequences(eseqs);
            System.out.println(Arrays.toString(avg_seq));
            double robustness = SGHExtinctionSequence.averageRobustnessIndex(avg_seq);
            SGHExtinctionSequence.writeGNUPlotScriptForAll(eseqs, outdir, "Extinctions_Final");
            System.out.println("Robustness = " + robustness);
        }
    }
    
    
}
