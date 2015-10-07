/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.sgh.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ffl
 */
public class MainGeneratorStats {
    
    public static void main(String[] args) {
        SGHSystem graph = SGHSystem.generateSGHSystem(100, 100);
        
        System.out.println(graph.dumpData());
        
        SGHExtinctionSequence[] eseqs = graph.computeRandomExtinctionSequence(200);
        
        double[] avg_seq = SGHExtinctionSequence.averageExtinctionSequences(eseqs);
        
        System.out.println(Arrays.toString(avg_seq));
        
        double robustness = SGHExtinctionSequence.averageRobustnessIndex(avg_seq);
        
        System.out.println("Robustness = " + robustness);
    }
    
    
}
