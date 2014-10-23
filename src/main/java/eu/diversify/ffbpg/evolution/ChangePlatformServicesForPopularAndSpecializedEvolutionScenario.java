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
public class ChangePlatformServicesForPopularAndSpecializedEvolutionScenario extends PlatformServicesEvolutionScenario {

    int popular_rate = 75;
    
    ChangeForMorePopularService popular_operator = new ChangeForMorePopularService();
    ChangeForMoreSpecializedService specialized_operator = new ChangeForMoreSpecializedService();
    
    Hashtable<String, PlatformEvolutionOperator> memory = new Hashtable<String, PlatformEvolutionOperator>();
    
    public ChangePlatformServicesForPopularAndSpecializedEvolutionScenario(int popular_rate) {
        super("Popular/Specialized Platforms ("+ popular_rate + "% popular)", null, null);
        this.popular_rate = popular_rate;
    }
    
    @Override
    public void step(BPGraph graph) {
      List<Platform> plats = (ArrayList<Platform>)graph.getPlatforms().clone();
        
       
       int op_count = 0;
       
       Collections.shuffle(plats, RandomUtils.getRandom());
       for(Platform p : plats) {
           
           PlatformEvolutionOperator operator = memory.get(p.getName());
           if (operator == null) {
               if (RandomUtils.getUniform(100) <= popular_rate) {
                   operator = popular_operator;
               }
               else {
                   operator = specialized_operator;
               }
               memory.put(p.getName(), operator);
           }
           
            if (operator.execute(graph, p)) op_count++;
       }
       
        System.out.println("Step complete. Operator applied on " + plats.size() +" platforms and swaped " + op_count + " services for more different ones.");
       
    }
    
}
