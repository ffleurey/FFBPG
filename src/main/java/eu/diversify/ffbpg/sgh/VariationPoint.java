
package eu.diversify.ffbpg.sgh;

import eu.diversify.ffbpg.random.IntegerGenerator;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class VariationPoint {
    
    String name;
    
    ArrayList<Alternative> alternatives = new ArrayList<Alternative>();
    
    IntegerGenerator multiplicityGenerator = null;
    
    boolean optional = false;
    
    public String getName() {
        return name;
    }
    
    public VariationPoint(String name) {
        this.name = name;
    }
    
    public VariationPoint(String name, IntegerGenerator multiplicityGenerator, boolean optional) {
        this.name = name;
        this.multiplicityGenerator = multiplicityGenerator;
    }
    
    public void addAlternative(Alternative a) {
        alternatives.add(a);
    }
    
    public ArrayList<Alternative> chooseAlternatives() {
        
        if (optional && RandomUtils.getUniform(100) < 50) return new ArrayList<Alternative>();
        
        int nb_alternatives = 1;
        
        if (multiplicityGenerator != null) {
            nb_alternatives = multiplicityGenerator.getNextInteger(1, alternatives.size());
        }
        
        return chooseASetOfNRandomAlternative(nb_alternatives);
    }
    
    // Choose a set of N random alternatives according to the weights.
    public ArrayList<Alternative> chooseASetOfNRandomAlternative(int n) {
        ArrayList<Alternative> result = new ArrayList<Alternative>();
        
        ArrayList<Alternative> options = (ArrayList<Alternative>)alternatives.clone();
        
        for (int i=0; i<n; i++) {
            int total = 0;
            Collections.sort(options);
            for(Alternative a : options) total += a.weight;
            int selected = RandomUtils.getUniform(total);
            int current = 0;
            for(Alternative a : options) {
                current += a.weight;
                if (selected < current) {
                    result.add(a);
                    options.remove(a);
                    break;
                }
            }
        }
        
        return result;
    }
    
}
