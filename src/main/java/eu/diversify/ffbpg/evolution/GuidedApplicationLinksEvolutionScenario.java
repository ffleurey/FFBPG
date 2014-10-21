package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.applications.AddOneRandomLink;
import eu.diversify.ffbpg.evolution.applications.AddTheMostUsefulLink;
import eu.diversify.ffbpg.evolution.applications.ApplicationEvolutionOperator;
import eu.diversify.ffbpg.evolution.applications.RemoveOneRandomLink;
import eu.diversify.ffbpg.evolution.applications.RemoveTheLeastUsefulLink;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class GuidedApplicationLinksEvolutionScenario extends ApplicationLinksEvolutionScenario {

    public GuidedApplicationLinksEvolutionScenario() {
        super("Smart Remove, Smart Add", 
                    new RemoveTheLeastUsefulLink(), new AddTheMostUsefulLink());
    }

    
    
}
