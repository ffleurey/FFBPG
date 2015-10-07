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
public class SGHClientApp extends SGHNode {
    
    ArrayList<SGHServer> links = new ArrayList<SGHServer>();
    public ArrayList<SGHServer> getLinks() {
        return links;
    }
    
    ArrayList<SGHRequest> requests;
    public ArrayList<SGHRequest> getRequests() {
        return requests;
    }
    
    public boolean isAlive() {
        ArrayList<SGHRequest> reqs = (ArrayList<SGHRequest>)requests.clone();
        for (SGHServer s : getLinks()) {
            reqs = s.filterRequestsWhichCanHandle(reqs);
        }
        return reqs.isEmpty();
    }
    
    public SGHClientApp(HashMap<SGHVariationPoint, ArrayList<SGHFeature>> features) {
        this.features = features;
        initializeResquests();
    }
    
    public String getOneLineString() {
        StringBuilder b = new StringBuilder();
        b.append(hashCode()); b.append("\t");
        b.append(requests.size());b.append("\t");
        b.append(links.size());b.append("\t");
        b.append(isAlive());b.append("\t");
        return b.toString();
    }
    
    public String getStringDump() {
        StringBuilder b = new StringBuilder();
        b.append("*** Dump for SGHClientApp "); b.append(this.hashCode()); b.append(" ***\n");
        for (SGHVariationPoint vp : features.keySet()) {
             b.append("* "); b.append(vp.getName()); b.append("{ ");
             for (SGHFeature f : features.get(vp)) {
                 b.append(f.getName()); b.append(" ");
             }
             b.append("}\n");
        }
        b.append("**Combinations:\n");
        for(SGHOptionSet os : options) {
            b.append("* "); b.append(os.toString());b.append("\n");
        }
        b.append("**Requests:\n");
        for(SGHRequest r : getRequests()) {
            b.append("* > ");b.append(r.toString()); b.append("\n");
        }
        b.append("******\n");
        return b.toString();
    }
    
    private ArrayList<SGHOptionSet> options;
    
    private void initializeResquests() {
       
        options = new ArrayList<SGHOptionSet>();
        for (SGHVariationPoint vp : features.keySet()) {
            options.add(SGHOptionSet.createOptionSets(vp, features.get(vp)));
        }
       
        ArrayList<SGHRequest> preqs = new ArrayList<SGHRequest>();
        
        if (options.size() > 0) {
        
            SGHOptionSet opt = options.get(0);
            for (ArrayList<SGHFeature> o : opt.getOptionSets()) {
                SGHRequest nr = new SGHRequest();
                nr.features.addAll(o);
                preqs.add(nr);
            }

            for (int i=1; i<options.size(); i++) {
                ArrayList<SGHRequest> nreqs = new ArrayList<SGHRequest>();
                opt = options.get(i);
                for (ArrayList<SGHFeature> o : opt.getOptionSets()) {
                    for (SGHRequest r : preqs) {
                        SGHRequest nr = new SGHRequest(r); // This is not optimal in terms of memory allocation...
                        nr.features.addAll(o);
                        nreqs.add(nr);
                    }
                }
                preqs = nreqs;
            }
        }
        requests = preqs;
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
