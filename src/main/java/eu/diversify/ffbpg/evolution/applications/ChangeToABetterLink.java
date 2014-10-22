package eu.diversify.ffbpg.evolution.applications;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.collections.Population;
import eu.diversify.ffbpg.random.UniformIntegerSetGenerator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public class ChangeToABetterLink extends ApplicationEvolutionOperator {

    private int platform_neightboorhood_ratio = 10; // 10% of the platforms are randomly explored
    private UniformIntegerSetGenerator random_set_generator = new UniformIntegerSetGenerator();
    
    @Override
    public boolean execute(BPGraph graph, Application a) {
        
        // Discover X percent of the platform
        // Try to swap a link for a better one (in terms of service population)
        // Stop as soon as we find something better
        // do not do anything if nothing is better
        
        int nb_plat = graph.getPlatforms().size() * platform_neightboorhood_ratio / 100;
        // pick randomly the set of platforms to consider
        int[] ids = random_set_generator.getRandomIntegerSet(graph.getPlatforms().size(), nb_plat);
        ArrayList<Platform> platforms = new ArrayList<Platform>();
        for (int i=0; i<ids.length; i++) {
            platforms.add(graph.getPlatforms().get(ids[i]));
        }
        
        
        
        if (a.getCapacity() <= a.getLinkedPlatforms().size()) return false; // do not exeed app capacity
        
        // Create the candidate applications
        Hashtable<Platform, Population> candidates = new Hashtable<Platform, Population>();
        for(Platform p : a.getPlatformNeighborhoods(graph)) {
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
