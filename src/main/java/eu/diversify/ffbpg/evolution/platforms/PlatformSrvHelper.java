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

/**
 *
 * @author ffl
 */
public class PlatformSrvHelper {
    
    public static ArrayList<Integer> getValidServicesToAdd(BPGraph graph, Platform p) {
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
        return rc;
    }
    
    public static ArrayList<Integer> getValidServicesToRemove(BPGraph graph, Platform p) {
        
        // Services can be removed if their min redondancy is lower than 0
        
        Population pop = p.getProvidedServicesMinRedondancyPopulation(graph);
        ArrayList<Integer> candidates = new ArrayList<Integer>();
       

        int[] data = pop.getData();
                
        for (int i=0; i<data.length; i++) {
            if (data[i] >= 2) candidates.add(p.getProvidedServices().get(i));
        }
        
        return candidates;

    }
    
    public static ArrayList<Integer> getUnUsedServices(BPGraph graph, Platform p) {
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
        return unused_services;
    }
    
}
