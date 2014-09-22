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
public class DropOneRandomService extends PlatformEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Platform p) {
        // Calculate the links for this platform
        ArrayList<Application> linked_apps = graph.getLinkedApplicationsForPlatform(p);
     
        ArrayList<Integer> unused_services = new ArrayList<Integer>();
        
        for (int i=0; i<p.getProvidedServices().size(); i++) {
            Integer srv = p.getProvidedServices().get(i);
            boolean used = false;
            for (Application a : linked_apps) {
                if (a.getRequiredServices().contains(srv)) {
                    used = true; break;
                }
            }
            if (!used) { // Collect unused services
                unused_services.add(srv);
            }
        }
        Collections.shuffle(unused_services, RandomUtils.getRandom()); // randomize the order of the services
        
        if(!unused_services.isEmpty()) {
             p.getProvidedServices().remove(unused_services.get(0));
            return true;
        }
        else {
            return false;
        }
      
    }
    
}
