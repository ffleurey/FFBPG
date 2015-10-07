package eu.diversify.ffbpg.sgh.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        computeFeatureSet();
        initializeResquests();
    }
    
    
    public String getOneLineString() {
        StringBuilder b = new StringBuilder();
        b.append(getName()); b.append("\t");
        b.append(featureSet.size());b.append("\t");
        b.append(requests.size());b.append("\t");
        b.append(links.size());b.append("\t");
        b.append(isAlive());b.append("\t");
        b.append("{");b.append(featuresAsString());b.append("}");b.append("\t");
        b.append("[");b.append(linksAsString());b.append("]");b.append("\t");
        return b.toString();
    }
    
    public String linksAsString() {
        ArrayList<String> strs = new ArrayList<String>();
        for(SGHServer s : links) strs.add(s.getName());
        Collections.sort(strs);
        StringBuilder b = new StringBuilder();
        for (String s : strs) {b.append(s); b.append(" ");}
        return b.toString().trim();
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
