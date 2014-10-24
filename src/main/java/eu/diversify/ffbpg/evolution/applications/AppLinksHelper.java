package eu.diversify.ffbpg.evolution.applications;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.random.UniformIntegerSetGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author ffl
 */
public class AppLinksHelper {

    private static UniformIntegerSetGenerator random_set_generator = new UniformIntegerSetGenerator();
    
    public static ArrayList<Platform> getRandomNeighborhood(BPGraph graph, int ratio) {
        int nb_plat = graph.getPlatforms().size() * ratio / 100;
        // pick randomly the set of platforms to consider
        int[] ids = random_set_generator.getRandomIntegerSet(graph.getPlatforms().size(), nb_plat);
        ArrayList<Platform> platforms = new ArrayList<Platform>();
        for (int i=0; i<ids.length; i++) {
            platforms.add(graph.getPlatforms().get(ids[i]));
        }
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
