/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.platforms.AddOneRandomService;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class AddExtraRandomServicesEvolutionScenario extends InitializationEvolutionScenario {

    int nb_srv_per_plat = 0;
    AddOneRandomService add_service = new AddOneRandomService();
    
    public AddExtraRandomServicesEvolutionScenario(int l) {
        nb_srv_per_plat = l;
    }
    
    @Override
    public void step(BPGraph graph) {
        
        ArrayList<Platform> plats = (ArrayList<Platform>)graph.getPlatforms().clone();
        int added = 0;
        int toAdd = graph.getPlatforms().size() * nb_srv_per_plat;
        
        // Add just as many links as has been removed
        boolean finished = (toAdd == 0);
            while (!finished) {
            Collections.shuffle(plats);
            for(Platform a : plats) {
                    if (add_service.execute(graph, a)) {
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
        return "Add Random Services (avg" + nb_srv_per_plat + " per platform)";
    }
    
    
    
}
