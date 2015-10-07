package eu.diversify.ffbpg.sgh.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author ffl
 */
public class SGHServer extends SGHNode {
    
    
    
    public SGHServer(HashMap<SGHVariationPoint, ArrayList<SGHFeature>> features) {
        this.features = features;
        computeFeatureSet();
    }
    
     public String getOneLineString() {
        StringBuilder b = new StringBuilder();
        b.append(getName()); b.append("\t");
        b.append(featureSet.size());b.append("\t");
        //b.append(requests.size());b.append("\t");
        //b.append(links.size());b.append("\t");
        //b.append(isAlive());b.append("\t");
        b.append("{");b.append(featuresAsString());b.append("}");b.append("\t");
        //b.append("[");b.append(linksAsString());b.append("]");b.append("\t");
        return b.toString();
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
