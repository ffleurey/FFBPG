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
import eu.diversify.ffbpg.evolution.platforms.AddOneRandomService;
import eu.diversify.ffbpg.evolution.platforms.ChangeForMorePopularService;
import eu.diversify.ffbpg.evolution.platforms.ChangeForMoreSpecializedService;
import eu.diversify.ffbpg.evolution.platforms.DropOneRandomService;
import eu.diversify.ffbpg.evolution.platforms.PlatformEvolutionOperator;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author ffl
 */
public class BalancePlatformServicesEvolutionScenario1 extends PlatformServicesEvolutionScenario {

  
    public BalancePlatformServicesEvolutionScenario1() {
        super("Balance Platform services", new DropOneRandomService(), new AddOneRandomService());
        
    }
    
    @Override
    public void step(BPGraph graph) {
      List<Platform> plats = (ArrayList<Platform>)graph.getPlatforms().clone();
        Collections.shuffle(plats, RandomUtils.getRandom());

        //plats = plats.subList(0, plats.size()/10); // Only 1 of 10 platforms is evolved
        
        int removed = 0;
        int added = 0;
        
        // Remove a random service on staturated platforms
        for(Platform p : plats) {
            if (p.hasRemainingCapacity()) continue;
            if (remove_srv_op.execute(graph, p)) {
                removed++;
            }
        }
        
        Collections.shuffle(plats, RandomUtils.getRandom());
        
        // Add just as many links as has been removed but on platforms with some capacity left
        boolean finished = (removed == 0);
            while (!finished) {
            Collections.shuffle(plats, RandomUtils.getRandom());
            for(Platform p : plats) {
                    if (!p.hasRemainingCapacity()) continue;
                    if (add_srv_op.execute(graph, p)) {
                        added++;
                        if (added == removed) {
                            finished = true;
                            break;
                        }
                    }
            }
        }
        

        System.out.println("Balance complete removed services = " + removed + " added services = " + added);   
    }
    
}
