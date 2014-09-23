/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.evolution.platforms;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.Service;
import eu.diversify.ffbpg.collections.Population;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * This operator selects a random platform and makes it drop one service.
 * The removed services is either an unused ser
 * 
 * @author ffl
 */
public class DropTheMostRedondantService extends PlatformEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Platform p) {
        
        Population pop = p.getProvidedServicesMinRedondancyPopulation(graph);
        
        // Fine the higest redondancy value
        int max_red = 0;
        int[] data = pop.getData();
                
        for (int i=0; i<data.length; i++) {
            if (data[i] > max_red) max_red = data[i];
        }
        if (max_red < 2) return false; // There are no services we can remove
        
        // Select all services with this index
        ArrayList<Integer> candidates = new ArrayList<Integer>();
        for (int i=0; i<data.length; i++) {
            if (data[i] == max_red) candidates.add(i);
        }
        
        Collections.shuffle(candidates, RandomUtils.getRandom());
        
        int selected = p.getProvidedServices().get(candidates.get(0));
        
        // Remove the service
        p.getProvidedServices().remove(selected);
        
        return true;
        
    }
    
}
