package eu.diversify.ffbpg.evolution.platforms;

import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;

/**
 *
 * @author ffl
 */
public abstract class PlatformEvolutionOperator {
    
    public abstract boolean execute(BPGraph graph, Platform p);
    
}
