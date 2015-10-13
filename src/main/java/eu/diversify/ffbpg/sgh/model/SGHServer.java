package eu.diversify.ffbpg.sgh.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public class SGHServer extends SGHNode {
    
    int CAPACITY = 15;
    int load = 0;
    
    public int getCapacity() {
        return CAPACITY;
    }
    
    public int getLoad() {
        return load;
    }
    
    public void increaseLoad() {
        assert hasCapacity();
        load++;
    }
    
    public void decreaseLoad() {
        assert load > 0;
        load--;
    }
    
    public boolean hasCapacity() {
        return load < CAPACITY;
    }
    
    public int loadPercentage() {
        return (load*100)/CAPACITY;
    }
    
    public int probabilityToAdapt() {
        if (!hasCapacity()) return 5;
        else return 50;
    }
    
    public SGHServer(HashMap<SGHVariationPoint, ArrayList<SGHFeature>> features) {
        this.features = features;
        computeFeatureSet();
    }
    
    public SGHServer deep_clone() {
        HashMap<SGHVariationPoint, ArrayList<SGHFeature>> clone = new HashMap<SGHVariationPoint, ArrayList<SGHFeature>>();
        for(SGHVariationPoint vp : features.keySet()) {
            clone.put(vp, (ArrayList<SGHFeature>)features.get(vp).clone());
        }
        SGHServer result = new SGHServer(clone);
        result.setName("c" + this.name);
        result.load = load;
        return result;
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
    
    protected void removeFeature(SGHFeature f) {
        featureSet.remove(f);
        features.get(f.getVariationPoint()).remove(f);
    }
    
    protected void addFeature(SGHFeature f) {
        featureSet.add(f);
        features.get(f.getVariationPoint()).add(f);
    } 
    
    public ArrayList<SGHServerAdaptation> all_valid_add_feature_adaptations(ArrayList<SGHClientApp> connected_clients, boolean smart) {
        
        ArrayList<SGHServerAdaptation> result = new ArrayList<SGHServerAdaptation>();
        Hashtable<SGHFeature, Integer> popularities = computePopularities(connected_clients);
        // Find any features which may be added
        
        if (!connected_clients.isEmpty() && smart) { // Li,it features to be added to the ones used by the clients
        
            for (SGHFeature candidate : popularities.keySet()) {
                SGHVariationPoint vp = candidate.getVariationPoint();
                if (!vp.isMultiple() && features.get(vp).size() == 1) continue; // nothing can be added
                if (!featureSet.contains(candidate)) {
                        result.add(new SGHServerAdaptation(this, null, candidate, popularities));
                    }
            }
        }
        else {  // Find any features which may be added
           
            for(SGHVariationPoint vp : features.keySet()) {

                if (!vp.isMultiple() && features.get(vp).size() == 1) continue; // nothing can be added

                for (SGHFeature candidate : vp.getAlternatives()) {
                    if (!featureSet.contains(candidate)) {
                        result.add(new SGHServerAdaptation(this, null, candidate, popularities));
                    }
                }
            
            }
        }
        return result;
    }
    
    public int removeUselessFeatures(ArrayList<SGHClientApp> connected_clients) {
        int result = 0;
        
        // Keep the same if there are no connected clients
        if (connected_clients.isEmpty()) return result;
        
        // if a features is in none of the clients it is totally useless
        for (SGHFeature f : (HashSet<SGHFeature>)featureSet.clone()) {
            boolean found = false;
            for (SGHClientApp c : connected_clients) {
                if (c.featureSet.contains(f)) {
                    found = true;
                    break;
                }   
            }
            if (!found) {
                removeFeature(f);
                result++;
            }
        }
        return result;
    }
    
    public Hashtable<SGHFeature, Integer> computePopularities(ArrayList<SGHClientApp> connected_clients) {
        Hashtable<SGHFeature, Integer> result = new Hashtable<SGHFeature, Integer>();
        for (SGHClientApp c : connected_clients) {
            for (SGHFeature f : c.featureSet) {
                if (!result.containsKey(f)) result.put(f, 1);
                else result.put(f, result.get(f)+1);
            }
        }
        return result;
    }
    
    
    public ArrayList<SGHServerAdaptation> all_valid_remove_feature_adaptations(ArrayList<SGHClientApp> connected_clients) {
        
        ArrayList<SGHServerAdaptation> result = new ArrayList<SGHServerAdaptation>();
        
        // Keep the same if there are no connected clients
        if (connected_clients.isEmpty()) return result;
        
        Hashtable<SGHFeature, Integer> popularities = computePopularities(connected_clients);
        
        // Compute the set of feature which cannot be removed because of clients
        HashSet<SGHFeature> critical_features = new HashSet<SGHFeature>();
        
        for (SGHClientApp c : connected_clients) {
            ArrayList<SGHRequest> reqs = c.getRequests();
            for (SGHServer s : c.getLinks()) {
                if (s == this) continue; // skip the current server
                reqs = s.filterRequestsWhichCanHandle(reqs);
            }
            for (SGHRequest r : reqs) {
                for (SGHFeature f : r.features) {
                    critical_features.add(f);
                }
            }
        }
        
        // find out if some features can be removed
        for(SGHVariationPoint vp : features.keySet()) {
            
            if (features.get(vp).size() == 0) continue; // nothing can be removed
            else if (features.get(vp).size() == 1) {
                if (vp.isOptional() && !critical_features.contains(features.get(vp).get(0))) {
                    result.add(new SGHServerAdaptation(this, features.get(vp).get(0), null, popularities));
                }
            }
            else { // we have more than 1 elements to work with. 
                for(SGHFeature candidate: features.get(vp)) {
                    if (!critical_features.contains(candidate)) {
                        result.add(new SGHServerAdaptation(this, candidate, null, popularities));
                    }
                }
            }
        }
        
        return result;
    }
    
    
}
