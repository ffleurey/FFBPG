package eu.diversify.ffbpg.sgh.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import sun.net.www.content.audio.aiff;

/**
 *
 * @author ffl
 */
public class SGHOptionSet {

    public static SGHOptionSet createOptionSets(SGHVariationPoint variationPoint, ArrayList<SGHFeature> availableOptions) {
        
        Hashtable<Integer, ArrayList<ArrayList<SGHFeature>>> comb = new Hashtable<Integer, ArrayList<ArrayList<SGHFeature>>>();
        
        // Empty set if valid
        if (variationPoint.isOptional()) {
            comb.put(0, new ArrayList<ArrayList<SGHFeature>>());
            comb.get(0).add(new ArrayList<SGHFeature>());
        }
        
        // Single choice (always possible)
        comb.put(1, new ArrayList<ArrayList<SGHFeature>>());
        for (SGHFeature f : availableOptions) {
            ArrayList<SGHFeature> set = new ArrayList<SGHFeature>();
            set.add(f);
            comb.get(1).add(set);
        }
        
        Hashtable<SGHFeature, Integer> index = new Hashtable<SGHFeature, Integer>();
        int i=0;
        for (SGHFeature f : availableOptions) {
            index.put(f, i); i++;
        }
        
        // All possible combinations from 2 to N
        if (variationPoint.isMultiple()) {
            
            for (int n=2; n<=availableOptions.size(); n++) {
                ArrayList<ArrayList<SGHFeature>> nm1 = comb.get(n-1);
                comb.put(n, new ArrayList<ArrayList<SGHFeature>>());
                for (ArrayList<SGHFeature> s : nm1) {
                    for (SGHFeature f : availableOptions) {
                        if (index.get(s.get(s.size()-1)) < index.get(f)) {
                            ArrayList<SGHFeature> set = (ArrayList<SGHFeature>)s.clone();
                            set.add(f);
                            comb.get(n).add(set);
                        }
                    }
                }
            }
        }

        ArrayList<ArrayList<SGHFeature>> res = new ArrayList<ArrayList<SGHFeature>>();
        
        for (ArrayList<ArrayList<SGHFeature>> o : comb.values()) {
            res.addAll(o);
        }
        
        return new SGHOptionSet(variationPoint, res);
        
    }
    
    SGHVariationPoint variationPoint;
    ArrayList<ArrayList<SGHFeature>> options;
    
    
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(variationPoint.getName());  b.append(" : ");
        for (ArrayList<SGHFeature> opts : options) {
            b.append("{ ");
            for (SGHFeature o : opts) {
                b.append(o.getName());b.append(" ");
            }
            b.append("} ");
        }
        return b.toString();
    }
    
    
    public SGHVariationPoint getVariationPoint() {
        return variationPoint;
    }
    
    public ArrayList<ArrayList<SGHFeature>> getOptionSets() {
        return options;
    }
            
    public SGHOptionSet(SGHVariationPoint variationPoint, ArrayList<ArrayList<SGHFeature>> options) {
        this.variationPoint = variationPoint;
        this.options = options;
    }
    
}
