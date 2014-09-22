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
             
        // calculate Services usage
        Hashtable<Integer, Integer> service_min_redondancy = new Hashtable<Integer, Integer>();
        
        int min_min_redondancy = graph.getPlatforms().size();
        
        for (int i=0; i<candidates.size(); i++) {
            Integer srv = candidates.get(i);
            int min_redondancy = graph.getPlatforms().size();
            for (Application a : linked_apps) {
                if (a.getRequiredServices().contains(srv)) {
                    int redondancy = a.getServicesRedondancy(srv);
                    if (redondancy < min_redondancy) min_redondancy = redondancy;
                }
            }
            service_min_redondancy.put(srv, min_redondancy);
            if (min_redondancy < min_min_redondancy) min_min_redondancy = min_redondancy;
        }
        
        // shuffle the candidates and select one
        ArrayList<Integer> rc = new ArrayList<Integer>();
        for (int i=0; i<candidates.size(); i++) rc.add(candidates.get(i));
        Collections.shuffle(rc, RandomUtils.getRandom());
        
        for (Integer srv : rc) {
            if (service_min_redondancy.get(srv) == min_min_redondancy) {
                p.getProvidedServices().add(srv);
                return true;
            }
        }
        
        return false;
      
    }
    
}
