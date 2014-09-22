package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.applications.AddOneRandomLink;
import eu.diversify.ffbpg.evolution.applications.ApplicationEvolutionOperator;
import eu.diversify.ffbpg.evolution.applications.RemoveOneRandomLink;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class RandomApplicationLinksEvolutionScenario extends ApplicationLinksEvolutionScenario {

    public RandomApplicationLinksEvolutionScenario() {
        super("Random Application Links Evolution", 
                    new RemoveOneRandomLink(), new AddOneRandomLink());
    }

    
    
}
