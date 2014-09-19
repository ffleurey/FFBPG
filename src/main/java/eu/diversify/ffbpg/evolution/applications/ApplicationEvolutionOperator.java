package eu.diversify.ffbpg.evolution.applications;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;

/**
 *
 * @author ffl
 */
public abstract class ApplicationEvolutionOperator {
    
    public abstract boolean execute(BPGraph graph, Application a);
    
}
