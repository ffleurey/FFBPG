package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.collections.Population;
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
    
    public SGHClientApp deep_clone(HashMap<SGHServer, SGHServer> old_new_map) {
        HashMap<SGHVariationPoint, ArrayList<SGHFeature>> clone = new HashMap<SGHVariationPoint, ArrayList<SGHFeature>>();
        for(SGHVariationPoint vp : features.keySet()) {
            clone.put(vp, (ArrayList<SGHFeature>)features.get(vp).clone());
        }
        SGHClientApp result = new SGHClientApp(clone);
        for (SGHServer s : links) {
            result.getLinks().add(old_new_map.get(s));
        }
        result.setName(this.name);
        return result;
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
    
    public int probabilityToAdapt() {
        return 50;
    }
    
    public int dropUselessLinks() {
        int result = 0;
        for (SGHServer s : (ArrayList<SGHServer>)links.clone()) {
            if (s.filterRequestsWhichCanHandle(requests).size() == requests.size()) {
                result++;
                links.remove(s);
                s.decreaseLoad();
            }
        }
        return result;
    }
    
    public ArrayList<SGHClientAdaptation> all_valid_swap_link_adaptations(ArrayList<SGHServer> neighbors) {
        ArrayList<SGHClientAdaptation> result = new ArrayList<SGHClientAdaptation>();
        Population p = computeRequestsPopulation();
        double fitness = p.getSGHSimulationFitness();
        ArrayList<SGHServer> valid_candidates = new ArrayList<SGHServer>();
        
        for (SGHServer s : neighbors) {
            if (links.contains(s)) continue;
            if (!s.hasCapacity()) continue;
            // the new link has to be useful for something
            if (s.filterRequestsWhichCanHandle(requests).size() == requests.size()) continue;
            valid_candidates.add(s);
        }
        
        if (valid_candidates.isEmpty()) return result; // do not go further if there are no candidates
        
        for (SGHServer old_link : links) {
            
            ArrayList<SGHRequest> remaining_reqs = getRequests();
            for (SGHServer s : links) {
                if (s != old_link) remaining_reqs = s.filterRequestsWhichCanHandle(remaining_reqs);
            }
            
            for(SGHServer new_link : valid_candidates) {
                if (!new_link.hasCapacity()) continue;
                if (new_link.filterRequestsWhichCanHandle(remaining_reqs).isEmpty()) {
                    result.add(new SGHClientAdaptation(this, old_link, new_link, fitness));
                }
            }
        }
        return result;
    }
    
     public ArrayList<SGHClientAdaptation> all_valid_add_link_adaptations(ArrayList<SGHServer> neighbors) {
        ArrayList<SGHClientAdaptation> result = new ArrayList<SGHClientAdaptation>();
        double fitness = computeRequestsPopulation().getSGHSimulationFitness();
        ArrayList<SGHServer> valid_candidates = new ArrayList<SGHServer>();
        
        for (SGHServer s : neighbors) {
            if (links.contains(s)) continue;
            if (!s.hasCapacity()) continue;
            // the new link has to be useful for something
            if (s.filterRequestsWhichCanHandle(requests).size() == requests.size()) continue;
            result.add(new SGHClientAdaptation(this, null, s, fitness));
        }
        
        return result; // do not go further if there are no candidates
     }
     
     public ArrayList<SGHClientAdaptation> all_valid_remove_link_adaptations(boolean smart) {
        ArrayList<SGHClientAdaptation> result = new ArrayList<SGHClientAdaptation>();
        
        // Refuse to have less that 2 links
        if (getLinks().size()<3 && smart) return result;
        
        double fitness = computeRequestsPopulation().getSGHSimulationFitness();
       for (SGHServer old_link : links) {
            
            ArrayList<SGHRequest> remaining_reqs = getRequests();
            for (SGHServer s : links) {
                if (s != old_link) remaining_reqs = s.filterRequestsWhichCanHandle(remaining_reqs);
            }
            
            if (remaining_reqs.isEmpty()) result.add(new SGHClientAdaptation(this, old_link, null, fitness));
        }
        
        return result; // do not go further if there are no candidates
     }
     
     public boolean is_server_adaption_acceptable(SGHServer current, SGHServer new_candidate) {
         
        ArrayList<SGHRequest> remaining_reqs = getRequests();
        for (SGHServer s : links) {
            if (s != current) remaining_reqs = s.filterRequestsWhichCanHandle(remaining_reqs);
        }
        remaining_reqs = new_candidate.filterRequestsWhichCanHandle(remaining_reqs);
        return remaining_reqs.isEmpty();
    }
     
    private Population computeRequestsPopulation() {
        int[] p = new int[getRequests().size()];
        int i=0;
        for (SGHRequest r : getRequests()) {
            int nserv = 0;
            for(SGHServer s : getLinks()) {
                if (s.canHandle(r)) nserv++;
            }
            p[i] = nserv;
            i++;
        }
        return new Population(p);
    }
     
}
