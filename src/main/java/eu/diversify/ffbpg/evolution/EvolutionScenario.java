package eu.diversify.ffbpg.evolution;


import eu.diversify.ffbpg.BPGraph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public abstract class EvolutionScenario {
    
    public abstract String getName();
    
    public abstract void step(BPGraph graph);
    
}
