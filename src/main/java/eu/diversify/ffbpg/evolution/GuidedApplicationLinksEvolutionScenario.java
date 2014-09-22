package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.applications.AddTheMostUsefulLink;
import eu.diversify.ffbpg.evolution.applications.ApplicationEvolutionOperator;
import eu.diversify.ffbpg.evolution.applications.RemoveTheLeastUsefulLink;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class GuidedApplicationLinksEvolutionScenario extends EvolutionScenario{
    
    public void step(BPGraph graph) {
        
        // First remove some links
        ApplicationEvolutionOperator op = new RemoveTheLeastUsefulLink();
        ArrayList<Application> apps = (ArrayList<Application>)graph.getApplications().clone();
        
        int removed = 0;
        int added = 0;
        
        // Try to remove 1 link per app
        Collections.shuffle(apps);
        for(Application a : apps) {
            if (op.execute(graph, a)) removed++;
        }
        
        // Try to remove 1 link per app
        Collections.shuffle(apps);
        for(Application a : apps) {
            if (op.execute(graph, a)) removed++;
        }
        
        op = new AddTheMostUsefulLink();
        
        // Try to add 1 link per app
        Collections.shuffle(apps);
        for(Application a : apps) {
            if (op.execute(graph, a)) added++;
        }
        
        // Try to add 1 link per app
        Collections.shuffle(apps);
        for(Application a : apps) {
            if (op.execute(graph, a)) added++;
        }
        System.out.println("Step complete removed links = " + removed + " added links = " + added);
    }

    @Override
    public String getName() {
        return "Guided Application Links Evolution";
    }
    
}
