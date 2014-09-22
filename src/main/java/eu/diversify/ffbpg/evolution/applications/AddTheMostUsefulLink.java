package eu.diversify.ffbpg.evolution.applications;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.collections.Population;
import java.util.HashSet;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public class AddTheMostUsefulLink extends ApplicationEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Application a) {
        
        // Create the candidate applications
        Hashtable<Platform, Population> candidates = new Hashtable<Platform, Population>();
        for(Platform p : a.getNeighborhood()) {
            HashSet<Platform> new_links = (HashSet<Platform>)a.getLinkedPlatforms().clone();
            new_links.add(p);
            if (a.getLinkedPlatforms().contains(p)) continue; // Eliminate already linked platforms
            if (!p.getProvidedServices().containsSome(a.getRequiredServices())) continue; // Eliminate useless platforms
            if (!p.hasRemainingCapacity()) continue; // Eliminate saturated platforms
            candidates.put(p, a.getServicesPopulation(new_links)); // We found a suitable candidate
        }
        
        Platform toAdd = null;
      
        double max_equi = 0;
        for (Platform p : candidates.keySet()) {
            double equi = candidates.get(p).getShannonEquitability();
            if (p == null || equi > max_equi) {
                toAdd = p;
                max_equi = equi;
            }
        }
        
        if (toAdd != null) {
            a.getLinkedPlatforms().add(toAdd);
            toAdd.incrementLoad();
            return true;
        }
        
        return false;
   
    }
    
}
