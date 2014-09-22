package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.applications.AddOneRandomLink;
import eu.diversify.ffbpg.evolution.applications.AddTheMostUsefulLink;
import eu.diversify.ffbpg.evolution.applications.ApplicationEvolutionOperator;
import eu.diversify.ffbpg.evolution.applications.RemoveOneRandomLink;
import eu.diversify.ffbpg.evolution.applications.RemoveTheLeastUsefulLink;
import eu.diversify.ffbpg.evolution.platforms.AddOneRandomService;
import eu.diversify.ffbpg.evolution.platforms.AddTheMostUsefulService;
import eu.diversify.ffbpg.evolution.platforms.DropOneRandomService;
import eu.diversify.ffbpg.evolution.platforms.DropTheMostRedondantService;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class RandomPlatformServicesEvolutionScenario1 extends PlatformServicesEvolutionScenario {

    public RandomPlatformServicesEvolutionScenario1() {
        super("Random Platform Service Evolution", 
                    new DropOneRandomService(), new AddOneRandomService());
    }

    
    
}
