package eu.diversify.ffbpg.evolution.applications;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.collections.Population;
import eu.diversify.ffbpg.random.RandomUtils;
import eu.diversify.ffbpg.random.UniformIntegerSetGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ffl
 */
public class ChangeForBetterShannon extends ApplicationEvolutionOperator {

    private int platform_neightboorhood_ratio = 10; // 10% of the platforms are randomly explored
    private UniformIntegerSetGenerator random_set_generator = new UniformIntegerSetGenerator();
    
    @Override
    public boolean execute(BPGraph graph, Application a) {
        
        Set<Platform> environment = AppLinksHelper.getRandomNeighborhoodForApp(graph,a,  10); 
        ArrayList<Platform> can_be_removed = AppLinksHelper.getValidLinksToRemove(graph, a);
        ArrayList<Platform> can_be_added = AppLinksHelper.getValidLinksToAdd(graph, a, environment);
        
        // Test possibilities and keep the one that improves the diversity
        Collections.shuffle(can_be_removed, RandomUtils.getRandom());
        Collections.shuffle(can_be_added, RandomUtils.getRandom());
        
        Population init_pop = a.getServicesPopulation();
        double base_shannon = init_pop.getShannonIndex();
        
        for (Platform toAdd : can_be_added) {
            for (Platform toRemove : can_be_removed) {
                HashSet<Platform> new_links = (HashSet<Platform>)a.getLinkedPlatforms().clone();
                new_links.add(toAdd);
                new_links.remove(toRemove);
                     
                Population pop = a.getServicesPopulation(new_links);
                double new_shannon = pop.getShannonIndex();
            
            if (new_shannon > base_shannon) {
                a.removeLinkToPlatform(toRemove);
                a.addLinkToPlatform(toAdd);
                return true;
            }
            }
        }
        return false;
    }
    
}
