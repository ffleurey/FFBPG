package eu.diversify.ffbpg.sgh.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ffl
 */
public class SGHOptionSet {

    public static Set<SGHOptionSet> createOptionSets(SGHVariationPoint variationPoint, ArrayList<SGHFeature> availableOptions) {
        Set<SGHOptionSet> result = new HashSet<SGHOptionSet>();
        
        
        return result;
        
    }
    
    SGHVariationPoint variationPoint;
    ArrayList<SGHFeature> selectedOptions;
            
    public SGHOptionSet(SGHVariationPoint variationPoint, ArrayList<SGHFeature> selectedOptions) {
        this.variationPoint = variationPoint;
        this.selectedOptions = selectedOptions;
    }
    
    
}
