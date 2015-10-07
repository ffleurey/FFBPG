package eu.diversify.ffbpg.sgh.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author ffl
 */
public class SGHServer extends SGHNode {
    
    HashSet<SGHFeature> featureSet;
    
    public SGHServer(HashMap<SGHVariationPoint, ArrayList<SGHFeature>> features) {
        this.features = features;
        computeFeatureSet();
    }
    
    public void computeFeatureSet() {
        featureSet = new HashSet<SGHFeature>();
        for(ArrayList<SGHFeature> fs : features.values()) {
            featureSet.addAll(fs);
        }
    }
    
    public ArrayList<SGHRequest> filterRequestsWhichCanHandle(ArrayList<SGHRequest> reqs) {
        ArrayList<SGHRequest> result = new ArrayList<SGHRequest>();
        for (SGHRequest r : reqs) {
            if (!canHandle(r)) {
                result.add(r);
            }
        }
        return result;
    }
    
    public boolean canHandle(SGHRequest r) {
        return featureSet.containsAll(r.features);
    }
}
