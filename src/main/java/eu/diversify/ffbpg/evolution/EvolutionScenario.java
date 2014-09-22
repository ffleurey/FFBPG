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
    
    protected static Hashtable<String, EvolutionScenario> prototypes = new Hashtable<String, EvolutionScenario>();
    static {
        EvolutionScenario s;
        s = new GuidedApplicationLinksEvolutionScenario(); prototypes.put(s.getName(), s);
        s = new RandomApplicationLinksEvolutionScenario(); prototypes.put(s.getName(), s);
    }
    
    public static Object[] getAllScenarioNames() {
        return prototypes.keySet().toArray();
    }
    
    public static EvolutionScenario getScenarioByName(String name) {
        return prototypes.get(name);
    }
    
    public abstract String getName();
    
    public abstract void step(BPGraph graph);
    
}
