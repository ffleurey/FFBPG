package eu.diversify.ffbpg.evolution.applications;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class RemoveOneUnusedLinks extends ApplicationEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Application a) {
        ArrayList<Platform> toremove = AppLinksHelper.getUnusedLinks(graph, a);
                
        Collections.shuffle(toremove, RandomUtils.getRandom());
        
        if (toremove.isEmpty()) {
            return false;
        }
        else {
            a.removeLinkToPlatform(toremove.get(0));
             if (!a.dependenciesSatisfied()) {
                System.err.println("ERROR removing useless link: dependancies of " + a.toString() + " not satisfied after removing " + toremove.get(0).getName());
                System.err.println("Requested = " + a.getRequiredServices().toString() + " Offered = " + toremove.get(0).getProvidedServices());
            }
            return true;
        }
    }
    
}
