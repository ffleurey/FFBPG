package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.applications.AddAUsefulLink;
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
public class GuidedAddApplicationLinksEvolutionScenarioCheating extends ApplicationLinksEvolutionScenario {

    public GuidedAddApplicationLinksEvolutionScenarioCheating() {
        super("Random Remove, Smart+ Add", 
                    new RemoveOneRandomLink(), new AddTheMostUsefulLink());
    }

    
    
}
