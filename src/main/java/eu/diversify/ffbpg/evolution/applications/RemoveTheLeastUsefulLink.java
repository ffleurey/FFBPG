package eu.diversify.ffbpg.evolution.applications;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.collections.Population;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public class RemoveTheLeastUsefulLink extends ApplicationEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Application a) {
        
        ArrayList<Platform> valids = AppLinksHelper.getValidLinksToRemove(graph, a);
        
        // Create the candidate applications and compute the resulting Populations
        Hashtable<Platform, Population> candidates = new Hashtable<Platform, Population>();
        for(Platform p : valids) {
            HashSet<Platform> remaining = (HashSet<Platform>)a.getLinkedPlatforms().clone();
            remaining.remove(p);
            candidates.put(p, a.getServicesPopulation(remaining));
        }
        
        Platform toRemove = null;
        double max_equi = 0;
        for (Platform p : candidates.keySet()) {
            double equi = candidates.get(p).getShannonEquitability();
            if (p == null || equi > max_equi) {
                toRemove = p;
                max_equi = equi;
            }
        }
        
        if (toRemove != null) {
            a.removeLinkToPlatform(toRemove);
            return true;
        }
        
        return false;
    
    }
    
}
