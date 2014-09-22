/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.evolution.platforms;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.collections.SortedIntegerSet;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This operator selects a random platform and makes it drop one service.
 * The removed services is either an unused ser
 * 
 * @author ffl
 */
public class AddOneRandomService extends PlatformEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Platform p) {
        // Calculate the links for this platform
        ArrayList<Application> linked_apps = graph.getLinkedApplicationsForPlatform(p);
        // Get a list of  the services which are known by the apps but not offered.
        SortedIntegerSet candidates = new SortedIntegerSet();
       for (Application a : linked_apps) {
            candidates.addAll(a.getRequiredServices());
        }
        for (int i=0; i<p.getProvidedServices().size(); i++) {
            candidates.remove(p.getProvidedServices().get(i));
        }
        
        // shuffle the candidates and select one
        ArrayList<Integer> rc = new ArrayList<Integer>();
        for (int i=0; i<candidates.size(); i++) rc.add(candidates.get(i));
        Collections.shuffle(rc, RandomUtils.getRandom());
        
        if (rc.size()>0) {
            p.getProvidedServices().add(rc.get(0));
            return true;
        }
        
        return false;
      
    }
    
}
