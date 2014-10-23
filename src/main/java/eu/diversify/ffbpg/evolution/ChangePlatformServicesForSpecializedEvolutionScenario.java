/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.evolution.applications.ApplicationEvolutionOperator;
import eu.diversify.ffbpg.evolution.applications.ChangeForBetterEquitability;
import eu.diversify.ffbpg.evolution.platforms.ChangeForMorePopularService;
import eu.diversify.ffbpg.evolution.platforms.ChangeForMoreSpecializedService;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ffl
 */
public class ChangePlatformServicesForSpecializedEvolutionScenario extends PlatformServicesEvolutionScenario {

    public ChangePlatformServicesForSpecializedEvolutionScenario() {
        super("Change To Specialized Services", null, null);
    }
    
    ChangeForMoreSpecializedService operator = new ChangeForMoreSpecializedService();
    
    @Override
    public void step(BPGraph graph) {
      List<Platform> plats = (ArrayList<Platform>)graph.getPlatforms().clone();
        
       
       int op_count = 0;
       
       Collections.shuffle(plats, RandomUtils.getRandom());
       for(Platform p : plats) {
            if (operator.execute(graph, p)) op_count++;
       }
       
        System.out.println("Step complete. Operator applied on " + plats.size() +" platforms and swaped " + op_count + " services for more specialized ones.");
       
    }
    
}
