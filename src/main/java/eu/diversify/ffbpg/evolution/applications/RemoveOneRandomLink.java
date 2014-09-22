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
public class RemoveOneRandomLink extends ApplicationEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Application a) {
        
        // Create the candidate applications
        ArrayList<Platform> candidates = new ArrayList<Platform>();
        for(Platform p : a.getLinkedPlatforms()) {
            HashSet<Platform> remaining = (HashSet<Platform>)a.getLinkedPlatforms().clone();
            remaining.remove(p);
            if(a.dependenciesSatisfied(remaining)) // Do not include options which break the app
                candidates.add(p);
        }
        
        Collections.shuffle(candidates, RandomUtils.getRandom());
        
        if (candidates.size() > 0) {
            a.getLinkedPlatforms().remove(candidates.get(0));
            candidates.get(0).decrementLoad();
            return true;
        }
        
        return false;
   
    }
    
}
