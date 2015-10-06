
package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.random.IntegerGenerator;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class SGHVariationPoint {
    
    String name;
    
    ArrayList<SGHFeature> alternatives = new ArrayList<SGHFeature>();
    IntegerGenerator serverMultGenerator = null;
    IntegerGenerator clientMultGenerator = null;
    boolean optional = true;
    
    
    public String getName() {
        return name;
    }
    
    public SGHVariationPoint(String name) {
        this.name = name;
    }
    
    public SGHVariationPoint(String name, IntegerGenerator multiplicityGenerator, boolean optional) {
        this.name = name;
        this.serverMultGenerator = multiplicityGenerator;
        this.clientMultGenerator = multiplicityGenerator;
        this.optional = optional;
    }
    
    public SGHVariationPoint(String name, IntegerGenerator serverMultGenerator, IntegerGenerator clientMultGenerator, boolean optional) {
        this.name = name;
        this.serverMultGenerator = serverMultGenerator;
        this.clientMultGenerator = clientMultGenerator;
        this.optional = optional;
    }
    
    public void addAlternative(SGHFeature a) {
        alternatives.add(a);
    }
    
    public ArrayList<SGHFeature> chooseAlternatives(IntegerGenerator multiplicityGenerator) {
        
        if (optional && RandomUtils.getUniform(100) < 50) return new ArrayList<SGHFeature>();
        
        int nb_alternatives = 1;
        
        if (multiplicityGenerator != null) {
            nb_alternatives = multiplicityGenerator.getNextInteger(1, alternatives.size());
        }
        
        return chooseASetOfNRandomAlternative(nb_alternatives);
    }
    
    // Choose a set of N random alternatives according to the weights.
    public ArrayList<SGHFeature> chooseASetOfNRandomAlternative(int n) {
        ArrayList<SGHFeature> result = new ArrayList<SGHFeature>();
        
        ArrayList<SGHFeature> options = (ArrayList<SGHFeature>)alternatives.clone();
        
        for (int i=0; i<n; i++) {
            int total = 0;
            Collections.sort(options);
            for(SGHFeature a : options) total += a.weight;
            int selected = RandomUtils.getUniform(total);
            int current = 0;
            for(SGHFeature a : options) {
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
