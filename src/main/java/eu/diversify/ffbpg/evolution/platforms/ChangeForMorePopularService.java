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
public class ChangeForMorePopularService extends PlatformEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Platform p) {

        ArrayList<Integer> can_be_removed = PlatformSrvHelper.getValidServicesToRemove(graph, p);
        ArrayList<Integer> can_be_added = PlatformSrvHelper.getValidServicesToAdd(graph, p);
        
        // This is an index of how many times each service is requested by connected apps
        Hashtable<Integer, Integer> requests = p.getAllServicesNumberOfRequests(graph);
        
        // We try to find a more popular service and swap it
        for(Integer toRemove : can_be_removed) {
            for (Integer toAdd : can_be_added) {
                if (requests.get(toRemove) < requests.get(toAdd)) {
                    // Do it
                    p.getProvidedServices().remove(toRemove);
                    p.getProvidedServices().add(toAdd);
                    p.clearAllCachedData();
                    return true;
                }
            }
        }
        
        return false;
      
    }
    
}
