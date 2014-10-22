/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.applications.AddOneRandomLink;
import eu.diversify.ffbpg.evolution.applications.ApplicationEvolutionOperator;
import eu.diversify.ffbpg.evolution.applications.ChangeForBetterEquitability;
import eu.diversify.ffbpg.evolution.applications.ChangeForBetterShannon;
import eu.diversify.ffbpg.evolution.applications.RemoveOneRandomLink;
import eu.diversify.ffbpg.evolution.applications.RemoveTheLeastUsefulLink;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class ChangeLinksForShannonWithNoiseEvolutionScenario extends ApplicationLinksEvolutionScenario{

    public ChangeLinksForShannonWithNoiseEvolutionScenario() {
        super("Change To Better Shannon With Noise", null, null);
    }
    
    ChangeForBetterShannon operator = new ChangeForBetterShannon();
    
    RemoveTheLeastUsefulLink op_remove = new RemoveTheLeastUsefulLink();
    AddOneRandomLink op_add = new AddOneRandomLink();
    
    @Override
    public void step(BPGraph graph) {
       ArrayList<Application> apps = (ArrayList<Application>)graph.getApplications().clone();
       
       int op_count = 0;
       
       // Try to remove a set of links for each app
       Collections.shuffle(apps);
       for(Application a : apps) {
            if (operator.execute(graph, a)) op_count++;
       }
       
       System.out.println("Swap link for better shannon applied on " + apps.size() +" applications and actually swaped " + op_count + " links.");
       
       // ADD NOISE by trying to remove 1 link on 10% of the apps and add the corresponding amount of links on other apps
       int removed = 0;
       Collections.shuffle(apps);
       for (int i=0; i<apps.size()/10; i++) {
           if (op_remove.execute(graph, apps.get(i))) removed++;
       }
       
       int added = 0;
       // Add just as many links as has been removed
        boolean finished = (removed == 0);
            while (!finished) {
            Collections.shuffle(apps);
            for(Application a : apps) {
                    if (op_add.execute(graph, a)) {
                        added++;
                        if (added == removed) {
                            finished = true;
                            break;
                        }
                    }
            }
        }
        System.out.println("Random noise removed links = " + removed + " added links = " + added);
       
    }
    
}
