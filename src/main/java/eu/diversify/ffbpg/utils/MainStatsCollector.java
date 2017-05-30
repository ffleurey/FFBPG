/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.utils;

import java.io.File;

/**
 *
 * @author franck
 */
public class MainStatsCollector {
    
    public static String root_folder = "/home/franck/tmp/paper_out";
    
    public static void processExperiment(File xp_folder) {
        
    }
    
    
    public static void main(String[] args) {
        
        File root = new File(root_folder);
        if (!root.isDirectory()) {
            System.err.println("Invalid root directory: " + root_folder);
            return;
        }
        
        for (File xp_folder : root.listFiles()) {
            if (xp_folder.isDirectory()) {
                
            }
        }
        
        
        
        
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
