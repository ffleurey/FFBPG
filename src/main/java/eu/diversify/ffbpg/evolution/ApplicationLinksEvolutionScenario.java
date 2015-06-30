package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.evolution.applications.AddTheMostUsefulLink;
import eu.diversify.ffbpg.evolution.applications.ApplicationEvolutionOperator;
import eu.diversify.ffbpg.evolution.applications.EvolveAppNeighborhood;
import eu.diversify.ffbpg.evolution.applications.RemoveTheLeastUsefulLink;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public abstract class ApplicationLinksEvolutionScenario extends EvolutionScenario{
    
    
    protected static Hashtable<String, ApplicationLinksEvolutionScenario> prototypes = new Hashtable<String, ApplicationLinksEvolutionScenario>();
    protected static ArrayList<String> prototypes_names = new ArrayList<String>();
    
    static {
        ApplicationLinksEvolutionScenario s;
        s = new NoApplicationLinksEvolutionScenario(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
        s = new RandomApplicationLinksEvolutionScenario(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
        //s = new GuidedRemoveApplicationLinksEvolutionScenario(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
        //s = new GuidedAddApplicationLinksEvolutionScenario(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
        //s = new GuidedApplicationLinksEvolutionScenario(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
        //s = new GuidedAddApplicationLinksEvolutionScenarioCheating(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
        //s = new GuidedApplicationLinksEvolutionScenarioCheats(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
        s = new ChangeLinksForEquitabilityEvolutionScenario(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
        s = new ChangeLinksForEquitabilityAndLowerLoadEvolutionScenario(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
        //s = new ChangeLinksForShannonEvolutionScenario1(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
        //s = new ChangeLinksForShannonWithNoiseEvolutionScenario(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
        
    }
    
    public static Object[] getAllScenarioNames() {
        return prototypes_names.toArray();
    }
    
    public static ApplicationLinksEvolutionScenario getScenarioByName(String name) {
        return prototypes.get(name);
    }
    
    ApplicationEvolutionOperator remove_link_op;
    ApplicationEvolutionOperator add_link_op;
    
    String name;
    
    public ApplicationLinksEvolutionScenario(String name, ApplicationEvolutionOperator remove_link_op, ApplicationEvolutionOperator add_link_op) {
        this.remove_link_op = remove_link_op;
        this.add_link_op = add_link_op;
        this.name = name;
    }
    
    public void step(BPGraph graph) {
 
        ArrayList<Application> apps = (ArrayList<Application>)graph.getApplications().clone();
        //Collections.shuffle(apps);
        
        int removed = 0;
        int added = 0;
        
        // Try to remove a set of links for each app
        Collections.shuffle(apps);
        for(Application a : apps) {

             if (remove_link_op.execute(graph, a)) removed++;

             if (!a.dependenciesSatisfied()) {
                System.err.println("ERROR removing links: dependancies of " + a.toString() + " not satisfied after removing" );
            }
        }
        
        System.out.println("Removed links = " + removed);
         // POST CONDITION: All apps should still be running! 
        /*
        for (Application a : graph.getApplications()) {
        if (!a.dependenciesSatisfied()) {
                System.err.println("ERROR after remove: dependancies of " + a.getName() + " not satisfied after evolution step \n" );
            }
        }
        */
        
        // Add just as many links as has been removed
        boolean finished = (removed == 0);
            while (!finished) {
            Collections.shuffle(apps);
            for(Application a : apps) {
                    if (add_link_op.execute(graph, a)) {
                        added++;
                        if (added == removed) {
                            finished = true;
                            break;
                        }
                    }
            }
        }
        System.out.println("Step complete removed links = " + removed + " added links = " + added);
        
        // POST CONDITION: All apps should still be running!
        
        for (Application a : graph.getApplications()) {
            /*
        if (!a.dependenciesSatisfied()) {
                System.err.println("ERROR in ApplicationLinksEvolutionScenario: dependancies of " + a.getName() + " not satisfied after evolution step \n" );
            }
                    */
        }
                    
    }

    @Override
    public String getName() {
        return name;
    }
    
}
