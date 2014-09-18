package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.BPGraph;

/**
 *
 * @author ffl
 */
public abstract class AbstractEvolutionOperator {

    public abstract void step1_evolve_platforms(BPGraph graph);
    public abstract void step2_evolve_applications(BPGraph graph);
    public abstract void step3_evolve_links(BPGraph graph);
    
}
