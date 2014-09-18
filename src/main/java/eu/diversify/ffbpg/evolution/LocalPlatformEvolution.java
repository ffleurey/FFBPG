/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.Service;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public class LocalPlatformEvolution extends AbstractEvolutionOperator {

    @Override
    public void step1_evolve_platforms(BPGraph graph) {
        // Select a random platform which will evolve
        Platform p = graph.getPlatforms().get(RandomUtils.getUniform(graph.getPlatforms().size()));
        // Calculate the links for this platform
        ArrayList<Application> linked_apps = graph.getLinkedApplicationsForPlatform(p);
        // calculate Services usage
        Hashtable<Integer, ArrayList<Application>> service_usage = new Hashtable<Integer, ArrayList<Application>>();
        Hashtable<Integer, Integer> service_min_redondancy = new Hashtable<Integer, Integer>();
        for (int i=0; i<p.getProvidedServices().size(); i++) {
            Integer srv = p.getProvidedServices().get(i);
            ArrayList<Application> apps = new ArrayList<Application>();
            int min_redondancy = graph.getPlatforms().size();
            for (Application a : linked_apps) {
                if (a.getRequiredServices().contains(srv)) {
                    apps.add(a);
                    int redondancy = a.getServicesRedondancy(srv);
                    if (redondancy < min_redondancy) min_redondancy = redondancy;
                }
            }
            service_usage.put(srv, apps);
            service_min_redondancy.put(srv, min_redondancy);
        }
        // Select the service with the higest "min_redondancy"
        int max_r = 1;
        int selected_service = -1;
        for (int srv : service_min_redondancy.keySet()) {
            if (service_min_redondancy.get(srv) > max_r) {
                selected_service = srv;
                max_r = service_min_redondancy.get(srv);
            }
        }
        if (selected_service > 0 && max_r > 1) {
            // The selected service can be removed without breaking any applications
            p.getProvidedServices().remove(selected_service);
            
            // TODO: Instantiate a replacement service
            
        }
        else {
            // No service can be removed without breaking some applications
        }
    }

    @Override
    public void step2_evolve_applications(BPGraph graph) {
        
    }

    @Override
    public void step3_evolve_links(BPGraph graph) {
        
    }
    
}
