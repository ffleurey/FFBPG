/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.evolution;

import eu.diversify.ffbpg.BPGraph;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public class InitializationEvolutionScenario extends EvolutionScenario {

    protected static Hashtable<String, InitializationEvolutionScenario> prototypes = new Hashtable<String, InitializationEvolutionScenario>();
    protected static ArrayList<String> prototypes_names = new ArrayList<String>();
    
    static {
        InitializationEvolutionScenario s;
        s = new InitializationEvolutionScenario(); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
        s = new AddExtraRandomLinksEvolutionScenario(1); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
        s = new AddExtraRandomLinksEvolutionScenario(2); prototypes.put(s.getName(), s);prototypes_names.add(s.getName());
       
    }
    
    public static Object[] getAllScenarioNames() {
        return prototypes_names.toArray();
    }
    
    public static InitializationEvolutionScenario getScenarioByName(String name) {
        return prototypes.get(name);
    }
    
    @Override
    public String getName() {
        return "NONE";
    }

    @Override
    public void step(BPGraph graph) {
        // DO NOTHING
    }
    
    
    
    
}
