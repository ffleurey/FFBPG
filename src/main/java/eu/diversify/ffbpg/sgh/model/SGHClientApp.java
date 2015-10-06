package eu.diversify.ffbpg.sgh.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ffl
 */
public class SGHClientApp {
    
    HashMap<SGHVariationPoint, ArrayList<SGHFeature>> features;
    
    ArrayList<SGHRequest> requests;
    
    public SGHClientApp(HashMap<SGHVariationPoint, ArrayList<SGHFeature>> features) {
        this.features = features;
        initializeResquests();
    }
    
    private void initializeResquests() {
        
    }
    
    public static Set<Set<SGHFeature>> cartesianProduct(Set<SGHFeature>... sets) {
    if (sets.length < 2)
        throw new IllegalArgumentException(
                "Can't have a product of fewer than two sets (got " +
                sets.length + ")");

    return _cartesianProduct(0, sets);
}

private static Set<Set<SGHFeature>> _cartesianProduct(int index, Set<SGHFeature>... sets) {
    Set<Set<SGHFeature>> ret = new HashSet<Set<SGHFeature>>();
    if (index == sets.length) {
        ret.add(new HashSet<SGHFeature>());
    } else {
        for (SGHFeature obj : sets[index]) {
            for (Set<SGHFeature> set : _cartesianProduct(index+1, sets)) {
                set.add(obj);
                ret.add(set);
            }
        }
    }
    return ret;
}
    
    public boolean hasAllRequestsSatisfied(Collection<SGHServer> srvs) {
        return (countSatisfiedRequests(srvs) == requests.size());
    }
    
    public int countSatisfiedRequests(Collection<SGHServer> srvs) {
        int result = 0;
        for (SGHRequest r : requests) {
            for (SGHServer s : srvs) {
                if (s.canHandle(r)) {
                    result++;
                    break;
                }
            }
        }
        return result;
    }
    
}
