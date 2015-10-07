/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.sgh.model;

import java.util.ArrayList;

/**
 *
 * @author ffl
 */
public class MainGeneratorStats {
    
    public static void main(String[] args) {
        SGHSystem graph = SGHSystem.generateSGHSystem(100, 100);
        
        System.out.println(graph.dumpClients());
        
    }
    
    
}
