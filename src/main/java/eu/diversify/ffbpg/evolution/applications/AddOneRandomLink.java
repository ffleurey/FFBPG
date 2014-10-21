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
        
        String old_links = a.getLinkedPlatformNames();
        /*
        if (!a.dependenciesSatisfied()) {
            System.err.println("ERROR PRE in AddOneRandomLink: dependancies of " + a.getName()+ " not satisfied!\n" );
        }
        */
        if (a.getCapacity() <= a.getLinkedPlatforms().size()) return false; // do not exeed app capacity
        // Create the candidate applications
        ArrayList<Platform> candidates = new ArrayList<Platform>();
        for(Platform p : graph.getPlatforms()) {
            if (a.getLinkedPlatforms().contains(p)) continue; // Eliminate already linked platforms
            if (!p.getProvidedServices().containsSome(a.getRequiredServices())) continue; // Eliminate useless platforms
            if (!p.hasRemainingCapacity()) continue; // Eliminate saturated platforms
            candidates.add(p); // We found a suitable candidate
        }
        
        Collections.shuffle(candidates, RandomUtils.getRandom());
        
        if (candidates.size() > 0) {
            a.addLinkToPlatform(candidates.get(0));
             /*
             if (!a.dependenciesSatisfied()) {
                System.err.println("ERROR POST in AddOneRandomLink: dependancies of " + a.getName() + " not satisfied after adding " + candidates.get(0).getName() + "!\n" );
                String new_links = a.getLinkedPlatformNames();
                System.err.println("ERROR POST old links = " + old_links);
                System.err.println("ERROR POST new links = " + new_links);
             }
             */
            return true;
        }
        
        return false;
   
    }
    
}
