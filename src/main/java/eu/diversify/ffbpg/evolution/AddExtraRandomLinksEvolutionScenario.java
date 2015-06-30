/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.applications.AddOneRandomLink;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class AddExtraRandomLinksEvolutionScenario extends InitializationEvolutionScenario {

    int nb_link_per_app = 0;
    AddOneRandomLink add_link = new AddOneRandomLink(false);
    
    public AddExtraRandomLinksEvolutionScenario(int l) {
        nb_link_per_app = l;
    }
    
    @Override
    public void step(BPGraph graph) {
        
        ArrayList<Application> apps = (ArrayList<Application>)graph.getApplications().clone();
        int added = 0;
        int toAdd = graph.getApplications().size() * nb_link_per_app;
        
        // Add just as many links as has been removed
        boolean finished = (toAdd == 0);
            while (!finished) {
            Collections.shuffle(apps);
            for(Application a : apps) {
                    if (add_link.execute(graph, a)) {
                        added++;
                        if (added == toAdd) {
                            finished = true;
                            break;
                        }
                    }
            }
        }
    }

    @Override
    public String getName() {
        return "Add Random Links (avg" + nb_link_per_app + " per app)";
    }
    
    
    
}
