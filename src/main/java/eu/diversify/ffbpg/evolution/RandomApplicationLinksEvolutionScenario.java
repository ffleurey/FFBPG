package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.applications.AddOneRandomLink;
import eu.diversify.ffbpg.evolution.applications.ApplicationEvolutionOperator;
import eu.diversify.ffbpg.evolution.applications.RemoveOneRandomLink;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class RandomApplicationLinksEvolutionScenario extends EvolutionScenario{
    
    public void step(BPGraph graph) {
        
        // First remove some links
        ApplicationEvolutionOperator op = new RemoveOneRandomLink();
        ArrayList<Application> apps = (ArrayList<Application>)graph.getApplications().clone();
        
        // Try to remove 1 link per app
        Collections.shuffle(apps);
        for(Application a : apps) op.execute(graph, a);
        
        // Try to remove 1 link per app
        Collections.shuffle(apps);
        for(Application a : apps) op.execute(graph, a);
        
        op = new AddOneRandomLink();
        
        // Try to add 1 link per app
        Collections.shuffle(apps);
        for(Application a : apps) op.execute(graph, a);
        
        // Try to add 1 link per app
        Collections.shuffle(apps);
        for(Application a : apps) op.execute(graph, a);
    }

    @Override
    public String getName() {
        return "Random Application Links Evolution";
    }
    
}
