package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.applications.AddOneRandomLink;
import eu.diversify.ffbpg.evolution.applications.AddTheMostUsefulLink;
import eu.diversify.ffbpg.evolution.applications.ApplicationEvolutionOperator;
import eu.diversify.ffbpg.evolution.applications.RemoveOneRandomLink;
import eu.diversify.ffbpg.evolution.applications.RemoveTheLeastUsefulLink;
import eu.diversify.ffbpg.evolution.platforms.AddTheMostUsefulService;
import eu.diversify.ffbpg.evolution.platforms.DropTheMostRedondantService;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class GuidedPlatformServicesEvolutionScenario extends PlatformServicesEvolutionScenario {

    public GuidedPlatformServicesEvolutionScenario() {
        super("Guided Platform Service Evolution", 
                    new DropTheMostRedondantService(), new AddTheMostUsefulService());
    }

    
    
}
