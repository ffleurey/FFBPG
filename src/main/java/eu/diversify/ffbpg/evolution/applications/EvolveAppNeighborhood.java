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
public class EvolveAppNeighborhood extends ApplicationEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Application a) {
        
        ArrayList<Platform> rn = new ArrayList<Platform>(a.getNeighborhood());
        Collections.shuffle(rn, RandomUtils.getRandom());
        
        Platform toRemove = null;
        
        for (Platform p : rn) {
            if (!p.getProvidedServices().containsSome(a.getRequiredServices())) {
                toRemove = p; // We found a platform which does not affer any interesting service
                break;
            }
        }
        
        if (toRemove == null) return false;
        
        // Discover a new platform which affers some usefull stuff
        Platform toAdd = null;
        ArrayList<Platform> allp = new ArrayList<Platform>(graph.getPlatforms());
        Collections.shuffle(allp, RandomUtils.getRandom());
        
        for (Platform p : allp) {
            if (a.getNeighborhood().contains(p)) continue; // it is already in 
            if (!p.getProvidedServices().containsSome(a.getRequiredServices())) continue; // it has no interesting services
            toAdd = p;
            break;
        }
        
        if (toAdd == null) return false;
   
        a.getNeighborhood().remove(toRemove);
        a.getNeighborhood().add(toAdd);
        
        return true;
    }
    
}
