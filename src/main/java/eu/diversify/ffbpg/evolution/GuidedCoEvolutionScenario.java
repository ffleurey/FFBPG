/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.applications.AddTheMostUsefulLink;
import eu.diversify.ffbpg.evolution.applications.RemoveOneUnusedLinks;

/**
 *
 * @author franck
 */
public class GuidedCoEvolutionScenario extends EvolutionScenario {

    GuidedPlatformServicesEvolutionScenario ps = new GuidedPlatformServicesEvolutionScenario();
    GuidedApplicationLinksEvolutionScenario as = new GuidedApplicationLinksEvolutionScenario();
    AddTheMostUsefulLink add_links = new AddTheMostUsefulLink();
    RemoveOneUnusedLinks rm_links = new RemoveOneUnusedLinks();
    
    @Override
    public String getName() {
        return "Guided Co-Evolution";
    }

    @Override
    public void step(BPGraph graph) {
        // Evolve platforms
        ps.step(graph);
        
       // Remove unused links
        for (Application a : graph.getApplications()) {
            rm_links.execute(graph, a);
        }
        
        // Evolve application links
        as.step(graph);
        
        // add additonal links for application which may have been droped because of platform changes
        for (Application a : graph.getApplications()) {
            if (a.getCapacity() > a.getLinkedPlatforms().size()) {
                while (add_links.execute(graph, a));
            }
        }
    }
    
}
