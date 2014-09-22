package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.applications.AddTheMostUsefulLink;
import eu.diversify.ffbpg.evolution.applications.ApplicationEvolutionOperator;
import eu.diversify.ffbpg.evolution.applications.RemoveTheLeastUsefulLink;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public abstract class ApplicationLinksEvolutionScenario extends EvolutionScenario{
    
    ApplicationEvolutionOperator remove_link_op;
    ApplicationEvolutionOperator add_link_op;
    String name;
    
    public ApplicationLinksEvolutionScenario(String name, ApplicationEvolutionOperator remove_link_op, ApplicationEvolutionOperator add_link_op) {
        this.remove_link_op = remove_link_op;
        this.add_link_op = add_link_op;
        this.name = name;
    }
    
    public void step(BPGraph graph) {
        
        // First remove some links
        
        ArrayList<Application> apps = (ArrayList<Application>)graph.getApplications().clone();
        
        int removed = 0;
        int added = 0;
        
        // Try to remove a set of links for each app
        Collections.shuffle(apps);
        for(Application a : apps) {
            // try to remove at least one links and no more than half the max number of links 
            int n = 1;//RandomUtils.getUniform(a.getCapacity()/2) + 1; 
            for (int i=0; i<n; i++) {
                if (remove_link_op.execute(graph, a)) removed++;
                else break; // we cannot remove more links
            }
        }
        
        
        
        // Add as many links as we can
        for(Application a : apps) {
           
            //while (true) {
                if (add_link_op.execute(graph, a)) added++;
                else break; // we cannot remove more links
            //}
        }
        
        System.out.println("Step complete removed links = " + removed + " added links = " + added);
    }

    @Override
    public String getName() {
        return name;
    }
    
}
