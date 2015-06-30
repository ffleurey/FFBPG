package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
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
public abstract class PlatformServicesEvolutionScenario extends EvolutionScenario{
    
        protected static Hashtable<String, PlatformServicesEvolutionScenario> prototypes = new Hashtable<String, PlatformServicesEvolutionScenario>();
        protected static ArrayList<String> prototypes_names = new ArrayList<String>();
        
        static {
            PlatformServicesEvolutionScenario s;  
            s = new NoPlatformServicesEvolutionScenario(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
            //s = new GuidedPlatformServicesEvolutionScenario(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
            s = new RandomPlatformServicesEvolutionScenario1(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
            s = new ChangePlatformServicesForPopularEvolutionScenario(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
            //s = new ChangePlatformServicesForSpecializedEvolutionScenario(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
            //s = new ChangePlatformServicesForPopularAndSpecializedEvolutionScenario(90); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
            //s = new ChangePlatformServicesForPopularAndSpecializedEvolutionScenario(75); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
            //s = new ChangePlatformServicesForPopularAndSpecializedEvolutionScenario(50); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
        }
    
    public static Object[] getAllScenarioNames() {
        return prototypes_names.toArray();
    }
    
    public static PlatformServicesEvolutionScenario getScenarioByName(String name) {
        return prototypes.get(name);
    }
    
    
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
            }
        }
        
        Collections.shuffle(plats, RandomUtils.getRandom());
        
        // Add just as many links as has been removed
        boolean finished = (removed == 0);
            while (!finished) {
            Collections.shuffle(plats, RandomUtils.getRandom());
            for(Platform p : plats) {
                    if (add_srv_op.execute(graph, p)) {
                        added++;
                        if (added == removed) {
                            finished = true;
                            break;
                        }
                    }
            }
        }
        

        System.out.println("Step complete removed services = " + removed + " added services = " + added);
    }

    @Override
    public String getName() {
        return name;
    }
    
}
