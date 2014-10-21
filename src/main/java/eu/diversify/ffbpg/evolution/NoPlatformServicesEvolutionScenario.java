
package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.BPGraph;

/**
 *
 * @author ffl
 */
public class NoPlatformServicesEvolutionScenario extends PlatformServicesEvolutionScenario {

    public NoPlatformServicesEvolutionScenario() {
        super("NONE", null, null);
    }
    
    @Override
    public void step(BPGraph g) {
        // Nothing
    }
    
}
