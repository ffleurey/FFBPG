package eu.diversify.ffbpg.evolution.applications;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.collections.Population;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public class AddOneRandomLink extends ApplicationEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Application a) {
        
        // Create the candidate applications
        ArrayList<Platform> candidates = new ArrayList<Platform>();
        for(Platform p : a.getNeighborhood()) {
            if (a.getLinkedPlatforms().contains(p)) continue; // Eliminate already linked platforms
            if (!p.getProvidedServices().containsSome(a.getRequiredServices())) continue; // Eliminate useless platforms
            if (!p.hasRemainingCapacity()) continue; // Eliminate saturated platforms
            candidates.add(p); // We found a suitable candidate
        }
        
        Collections.shuffle(candidates, RandomUtils.getRandom());
        
        if (candidates.size() > 0) {
            a.getLinkedPlatforms().add(candidates.get(0));
            candidates.get(0).incrementLoad();
            return true;
        }
        
        return false;
   
    }
    
}
