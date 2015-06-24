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
import java.util.Set;

/**
 *
 * @author ffl
 */
public class AddOneRandomLink extends ApplicationEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Application a) {
        
        if (a.getCapacity() <= a.getLinkedPlatforms().size()) return false; // do not exeed app capacity
        
        // Create the candidate applications
        Set<Platform> environment = AppLinksHelper.getRandomNeighborhoodForApp(graph,a,  10);
        ArrayList<Platform> candidates = AppLinksHelper.getValidLinksToAdd(graph, a, environment);
     
        Collections.shuffle(candidates, RandomUtils.getRandom());
        
        if (candidates.size() > 0) {
            a.addLinkToPlatform(candidates.get(0));
            return true;
        }
        
        return false;
   
    }
    
}
