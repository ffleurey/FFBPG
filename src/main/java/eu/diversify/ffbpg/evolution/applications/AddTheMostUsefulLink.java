package eu.diversify.ffbpg.evolution.applications;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.collections.Population;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @author ffl
 */
public class AddTheMostUsefulLink extends ApplicationEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Application a) {
        
        if (a.getCapacity() <= a.getLinkedPlatforms().size()) return false; // do not exeed app capacity
        
        Set<Platform> environment = AppLinksHelper.getRandomNeighborhoodForApp(graph,a,  10);
        ArrayList<Platform> valids = AppLinksHelper.getValidLinksToAdd(graph, a, environment);
        
        Hashtable<Platform, Population> candidates = new Hashtable<Platform, Population>();
        
        for(Platform p : valids) {
            HashSet<Platform> new_links = (HashSet<Platform>)a.getLinkedPlatforms().clone();
            new_links.add(p);
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
            a.addLinkToPlatform(toAdd);
            return true;
        }
        
        return false;
   
    }
    
}
