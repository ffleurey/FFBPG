package eu.diversify.ffbpg.evolution.applications;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.random.UniformIntegerSetGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ffl
 */
public class AppLinksHelper {

    private static UniformIntegerSetGenerator random_set_generator = new UniformIntegerSetGenerator();
    
    public static Set<Platform> getRandomNeighborhoodForApp(BPGraph graph, Application a, int ratio) {
        
        // Check if the graph already contains the Neighborhood for that application
        Set<Platform> platforms = graph.getNeighborhoodForApplication(a);
        if (platforms != null) return platforms;
        
        // Create a new random Neighborhood  
        int nb_plat = graph.getPlatforms().size() * ratio / 100;
        // pick randomly the set of platforms to consider
        int[] ids = random_set_generator.getRandomIntegerSet(graph.getPlatforms().size(), nb_plat);
        platforms = new HashSet<Platform>();
        for (int i=0; i<ids.length; i++) {
            platforms.add(graph.getPlatforms().get(ids[i]));
        }
        
        // Store the Neighborhood in the graph
        graph.setNeighborhoodForApplication(a, platforms);
        return platforms;
    }
    
    public static ArrayList<Platform> getValidLinksToRemove(BPGraph graph, Application a) {
        ArrayList<Platform> removable = new ArrayList<Platform>();
        for(Platform p : a.getLinkedPlatforms()) {
            HashSet<Platform> remaining = (HashSet<Platform>)a.getLinkedPlatforms().clone();
            remaining.remove(p);
            if(a.dependenciesSatisfied(remaining)) // Do not include options which break the app
                removable.add(p);
        }
        return removable;
    }
    
    public static ArrayList<Platform> getUnusedLinks(BPGraph graph, Application a) {
        ArrayList<Platform> removable = new ArrayList<Platform>();
        for(Platform p : a.getLinkedPlatforms()) {
           
           if (!p.getProvidedServices().containsSome(a.getRequiredServices())) {
                removable.add(p);
           }
        }
        return removable;
    }
    
    public static ArrayList<Platform> getValidLinksToAdd(BPGraph graph, Application a, Collection<Platform> candidates) {
        ArrayList<Platform> result = new ArrayList<Platform>();
        for(Platform p : candidates) {
            if (a.getLinkedPlatforms().contains(p)) continue; // Eliminate already linked platforms
            if (!p.getProvidedServices().containsSome(a.getRequiredServices())) continue; // Eliminate useless platforms
            if (!p.hasRemainingCapacity()) continue; // Eliminate saturated platforms
            result.add(p); // We found a suitable candidate
        }
        return result;
    }
    
}
