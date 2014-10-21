/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.applications.ApplicationEvolutionOperator;

/**
 *
 * @author ffl
 */
public class NoApplicationLinksEvolutionScenario extends ApplicationLinksEvolutionScenario{

    public NoApplicationLinksEvolutionScenario() {
        super("NONE", null, null);
    }
    
    @Override
    public void step(BPGraph g) {
        // Nothing
    }
    
}
