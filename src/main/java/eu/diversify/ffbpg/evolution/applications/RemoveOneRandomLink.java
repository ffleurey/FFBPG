package eu.diversify.ffbpg.evolution.applications;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author ffl
 */
public class RemoveOneRandomLink extends ApplicationEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Application a) {

        ArrayList<Platform> candidates = AppLinksHelper.getValidLinksToRemove(graph, a);

        Collections.shuffle(candidates, RandomUtils.getRandom());

        if (candidates.size() > 0) {
            a.removeLinkToPlatform(candidates.get(0));
            return true;
        }
        return false;

    }

}
