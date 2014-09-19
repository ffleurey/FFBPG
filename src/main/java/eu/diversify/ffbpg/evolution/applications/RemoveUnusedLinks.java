package eu.diversify.ffbpg.evolution.applications;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import java.util.ArrayList;

/**
 *
 * @author ffl
 */
public class RemoveUnusedLinks extends ApplicationEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Application a) {
        ArrayList<Platform> toremove = new ArrayList<Platform>();
        for (Platform p : a.getLinkedPlatforms()) {
            if (!p.getProvidedServices().containsSome(a.getRequiredServices())) {
                toremove.add(p);
            }
        }
        if (toremove.isEmpty()) {
            return false;
        }
        else {
            a.getLinkedPlatforms().removeAll(toremove);
            return true;
        }
    }
    
}
