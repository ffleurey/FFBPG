/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author franck
 */
public class MainStatsCollector {
    
    public static String root_folder = "C:\\home\\checkouts\\DIVERSIFY\\FFBPG\\new_paper_out\\RandomInit_250_50";
    
    
    private static final Pattern p_server_pop = Pattern.compile("\\s*SERVER POPULATION\\s*:\\s*(\\d+)");
    
    static StringBuilder evol_graph = new StringBuilder();
    static StringBuilder sim_graph = new StringBuilder();
    
    public static void processExperiment(File xp_folder) {
        BufferedReader br = null;
        
            System.out.println("Found Experiment " + xp_folder.getName());
            
            
            sim_graph.append("\""+xp_folder.getName()+"/ExecutionSim.txt\" using 1:3 notitle with point lc rgb 'green', \\\n");
            
            for (File run_folder : xp_folder.listFiles()) {
                if (run_folder.isDirectory()) {
                    
                    if (run_folder.getName().contains("RANDOM")) {
                        evol_graph.append("\""+xp_folder.getName()+"/"+run_folder.getName()+"/Simulation_robustness.dat\" using 1 notitle with line lc rgb 'blue', \\\n");
                        sim_graph.append("\""+xp_folder.getName()+"/"+run_folder.getName()+"/ExecutionSim.txt\" using 1:3 notitle with point lc rgb 'blue', \\\n");
                    }
                    else if (run_folder.getName().contains("SMART")) {
                        evol_graph.append("\""+xp_folder.getName()+"/"+run_folder.getName()+"/Simulation_robustness.dat\" using 1 notitle with line lc rgb 'red', \\\n");
                        sim_graph.append("\""+xp_folder.getName()+"/"+run_folder.getName()+"/ExecutionSim.txt\" using 1:3 notitle with point lc rgb 'red', \\\n");
                    }
                    
                }
            }
            
            
            // Parse initial graph
            //String initgraph = FileUtils.readTextFile(xp_folder, "InitialGraph.txt");
            /*
            Matcher m = p_server_pop.matcher(initgraph);
            if (m.find()) {
                
                System.out.println("Server population: " + Integer.parseInt(m.group(1)));
            }
            */
            

    }
    
    public static void main(String[] args) {
        
        File root = new File(root_folder);
        if (!root.isDirectory()) {
            System.err.println("Invalid root directory: " + root_folder);
            return;
        }
        
        evol_graph.append("set title 'Evolution of robustness with decentralized adaptation - Red:Smart Blue:Random'\n");
        evol_graph.append("set xlabel 'Adaptation step'\n");
        evol_graph.append("set ylabel 'Robusness index (%)'\n");
        evol_graph.append("set xrange [0:100]\n");
        evol_graph.append("set yrange [45:75]\n");
        evol_graph.append("plot ");
        
        sim_graph.append("set title 'Simulated Failure rates - Green:Initial Red:Smart Blue:Random'\n");
        sim_graph.append("set xlabel '# Servers down'\n");
        sim_graph.append("set ylabel 'Percentage of failed requests'\n");
        //sim_graph.append("set xrange [0:100]\n");
        //sim_graph.append("set yrange [45:75]\n");
        sim_graph.append("plot ");
        
        for (File xp_folder : root.listFiles()) {
            if (xp_folder.isDirectory()) {
                
                processExperiment(xp_folder);
            }
        }
        
        
        FileUtils.writeTextFile(root, "Evolution.plt", evol_graph.toString());
        FileUtils.writeTextFile(root, "Simulation.plt", sim_graph.toString());
        
    }
    
    
    class InitialModel {
    
        String name;
        
        public InitialModel(String name){
            this.name = name;
        }
    
    }
    
    class EvolutionSimulation {
        String name;
        
        double[] robustness;
        
        public EvolutionSimulation(String name){
            this.name = name;
        }
    }
    
    
}
