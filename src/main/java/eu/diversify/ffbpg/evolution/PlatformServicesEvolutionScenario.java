package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.evolution.platforms.PlatformEvolutionOperator;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ffl
 */
public abstract class PlatformServicesEvolutionScenario extends EvolutionScenario{
    
    PlatformEvolutionOperator remove_srv_op;
    PlatformEvolutionOperator add_srv_op;
   
    
    String name;
    
    public PlatformServicesEvolutionScenario(String name, PlatformEvolutionOperator remove_srv_op, PlatformEvolutionOperator add_srv_op) {
        this.remove_srv_op = remove_srv_op;
        this.add_srv_op = add_srv_op;
        this.name = name;
    }
    
    public void step(BPGraph graph) {
 
        List<Platform> plats = (ArrayList<Platform>)graph.getPlatforms().clone();
        Collections.shuffle(plats, RandomUtils.getRandom());

        //plats = plats.subList(0, plats.size()/10); // Only 1 of 10 platforms is evolved
        
        int removed = 0;
        int added = 0;
        
        for(Platform p : plats) {
            if (remove_srv_op.execute(graph, p)) {
                removed++;
                if (add_srv_op.execute(graph, p)) added++;
            }
               
        }

        System.out.println("Step complete removed services = " + removed + " added services = " + added);
    }

    @Override
    public String getName() {
        return name;
    }
    
}
