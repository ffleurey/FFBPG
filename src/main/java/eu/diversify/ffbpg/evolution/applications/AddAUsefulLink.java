package eu.diversify.ffbpg.evolution.applications;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.collections.Population;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @author ffl
 */
public class AddAUsefulLink extends ApplicationEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Application a) {
        
        if (a.getCapacity() <= a.getLinkedPlatforms().size()) return false; // do not exeed app capacity
        
        Population init_pop = a.getServicesPopulation();
        double base_equitability = init_pop.getShannonEquitability();
        
        Set<Platform> environment = AppLinksHelper.getRandomNeighborhoodForApp(graph,a,  10);
        ArrayList<Platform> candidates = AppLinksHelper.getValidLinksToAdd(graph, a, environment);
     
        Collections.shuffle(candidates, RandomUtils.getRandom());
        
        for(Platform p : candidates) {
            HashSet<Platform> new_links = (HashSet<Platform>)a.getLinkedPlatforms().clone();
            new_links.add(p);
                     
            // This is a suitable candidate
            Population pop = a.getServicesPopulation(new_links);
            
            double new_equitability = pop.getShannonEquitability();
            
            if (new_equitability > base_equitability) {
                a.addLinkToPlatform(p);
                return true;
            }
        }
        return false;
   
    }
    
}
