/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.evolution.platforms;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.collections.Population;
import eu.diversify.ffbpg.collections.SortedIntegerSet;
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
public class AddTheMostUsefulService extends PlatformEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Platform p) {
        
        
        Population pop = p.getNotOfferedServicesMinRedondancyPopulation(graph);
   
        // Fine the higest redondancy value
        int min_red = -1;
        int[] data = pop.getData();
        
        if (data.length == 0) return false; // We do no know any services!
        
        
        for (int i=0; i<data.length; i++) {
            if ( min_red<0 || data[i] < min_red) min_red = data[i];
        }
         
        // Select all services with this index
        ArrayList<Integer> candidates = new ArrayList<Integer>();
        for (int i=0; i<data.length; i++) {
            if (data[i] == min_red) candidates.add(i);
        }
        
        Collections.shuffle(candidates, RandomUtils.getRandom());
        
        int selected = p.getAll_known_services_not_offered(graph).get(candidates.get(0));
        
              // Remove the service
        p.getProvidedServices().add(selected);
        
        return true;
        
    }
    
}
