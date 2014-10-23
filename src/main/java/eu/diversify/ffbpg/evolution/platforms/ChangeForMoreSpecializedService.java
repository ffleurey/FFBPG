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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 * This operator selects a random platform and makes it drop one service.
 * The removed services is either an unused ser
 * 
 * @author ffl
 */
public class ChangeForMoreSpecializedService extends PlatformEvolutionOperator {

    
    //Hashtable<String, SpecializedPlatformMemory> memory = new Hashtable<String, SpecializedPlatformMemory>();
    
    @Override
    public boolean execute(BPGraph graph, Platform p) {
/*
        SpecializedPlatformMemory mem = memory.get(p.getName());
        if (mem == null) {
            mem = new SpecializedPlatformMemory();
            memory.put(p.getName(), mem);
        }
  */      
        ArrayList<Integer> can_be_removed = PlatformSrvHelper.getValidServicesToRemove(graph, p);
        ArrayList<Integer> can_be_added = PlatformSrvHelper.getValidServicesToAdd(graph, p);
        
        Collections.shuffle(can_be_added, RandomUtils.getRandom());
        Collections.shuffle(can_be_removed, RandomUtils.getRandom());
        
        // This is an index of how many times each service is requested by connected apps
        Hashtable<Integer, Integer> requests = p.getAllServicesNumberOfRequests(graph);
        
        int max_popularity = 0;
        for (int r : requests.values()) if (r>max_popularity) max_popularity = r;
        
        int threshold = max_popularity / 2;
        
        // We try to find a more popular service and swap it
        for(Integer toRemove : can_be_removed) {
           
            for (Integer toAdd : can_be_added) {
                if (requests.get(toRemove) > threshold && requests.get(toAdd) < threshold) { // Remove the most popular
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
    /*
    class SpecializedPlatformMemory {
        Hashtable<Integer, Integer> stategic_services = new Hashtable<Integer, Integer>();
        
        protected void addStategicService(Integer srv) {
            int strategic_time = RandomUtils.getGaussian(50, 25); // The service is startegic for an average of 50 steps 
            stategic_services.put(srv, strategic_time);
        }
        
        protected boolean isStategic(Integer srv) {
            return stategic_services.containsKey(srv);
        }
        
        protected Set<Integer> step() {
            Set<Integer> result = new HashSet<Integer>();
            Set<Integer> services = stategic_services.keySet();
            for (Integer srv : services) {
                int ttl = stategic_services.get(srv);
                ttl --;
                if (ttl > 0) stategic_services.put(srv, ttl);
                else {
                    stategic_services.remove(srv);
                    result.add(srv);
                }
            }
            return result;
        }
    }
    */
}
