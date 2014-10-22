/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.applications.ApplicationEvolutionOperator;
import eu.diversify.ffbpg.evolution.applications.ChangeForBetterEquitability;
import eu.diversify.ffbpg.evolution.applications.ChangeForBetterShannon;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class ChangeLinksForShannonEvolutionScenario1 extends ApplicationLinksEvolutionScenario{

    public ChangeLinksForShannonEvolutionScenario1() {
        super("Change To Better Shannon Links", null, null);
    }
    
    ChangeForBetterShannon operator = new ChangeForBetterShannon();
    
    @Override
    public void step(BPGraph graph) {
       ArrayList<Application> apps = (ArrayList<Application>)graph.getApplications().clone();
       
       int op_count = 0;
       
       // Try to remove a set of links for each app
       Collections.shuffle(apps);
       for(Application a : apps) {
            if (operator.execute(graph, a)) op_count++;
       }
       
        System.out.println("Step complete. Operator applied on " + apps.size() +" applications and swaped " + op_count + " links.");
       
    }
    
}
