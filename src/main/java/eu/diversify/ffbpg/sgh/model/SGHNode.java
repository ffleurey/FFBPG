/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.collections.Population;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;


public abstract class SGHNode {
    
    HashMap<SGHVariationPoint, ArrayList<SGHFeature>> features;
    HashSet<SGHFeature> featureSet;
    
    public static HashMap<String, ArrayList<SGHNode>> getPopulation(ArrayList nodes) {
        HashMap<String, ArrayList<SGHNode>> result = new HashMap<String, ArrayList<SGHNode>>();
        for (int i=0; i<nodes.size(); i++) {
            SGHNode  n = (SGHNode)nodes.get(i);
            String sig = n.getSpeciesSignature();
            if (!result.containsKey(sig)) {
                result.put(sig, new ArrayList<SGHNode>());
            }
            result.get(sig).add(n);
        }
        return result;
    }
    
    public static double disparityOfSpercies(HashMap<String, ArrayList<SGHNode>> pop) {
        ArrayList<SGHNode> species = new ArrayList<SGHNode>();
        for (ArrayList<SGHNode> ind : pop.values()) {
            species.add(ind.get(0));
        }
        double result = 0;
        for (SGHNode s1 : species) {
            for (SGHNode s2 : species) {
                if (s2 == s1) break;
                result += s1.distance(s2);
            }
        }
        return result;
    }
    
    public static double diversity(HashMap<String, ArrayList<SGHNode>> pop) {
        ArrayList<SGHNode> species = new ArrayList<SGHNode>();
        HashMap<SGHNode, Integer> popsize = new HashMap<SGHNode, Integer>();
        for (ArrayList<SGHNode> ind : pop.values()) {
            species.add(ind.get(0));
            popsize.put(ind.get(0), ind.size());
        }
        double result = 0;
        for (SGHNode s1 : species) {
            for (SGHNode s2 : species) {
                if (s2 == s1) break;
                result += popsize.get(s1) * popsize.get(s2) * s1.distance(s2);
            }
        }
        return result;
    }
    
    public static Population getPopulationStats(HashMap<String, ArrayList<SGHNode>> pop) {
        int[] res = new int[pop.size()];
        int i = 0;
        for (String k : pop.keySet()) {
            res[i] = pop.get(k).size();
            i++;
        }
        return new Population(res);
    }
    
    public void computeFeatureSet() {
        featureSet = new HashSet<SGHFeature>();
        for(ArrayList<SGHFeature> fs : features.values()) {
            featureSet.addAll(fs);
        }
    }
    
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    public HashMap<SGHVariationPoint, ArrayList<SGHFeature>> getFeatures() {
        return features;
    }
    
    public String featuresAsString() {
        ArrayList<String> strs = new ArrayList<String>();
        for(SGHFeature f : featureSet) strs.add(f.getName());
        Collections.sort(strs);
        StringBuilder b = new StringBuilder();
        for (String s : strs) {b.append(s); b.append(" ");}
        return b.toString().trim();
    }
    
    public String getSpeciesSignature() {
        ArrayList<String> fea = new ArrayList<String>();
        for (SGHFeature f : featureSet) {
            fea.add(f.getName());
        }
        Collections.sort(fea);
        StringBuilder b = new StringBuilder();
        for (String s : fea) {
            b.append(s);
        }
        return b.toString();
    }
    
    public double distance(SGHNode other) {
        double result = 0;
        
        double inter = 0;
        double union = 0;
        
        for (SGHFeature f : featureSet) {
            union++;
            if (other.featureSet.contains(f)) {
                inter++;
            }
        }
        
        for (SGHFeature f : other.featureSet) {
            if (!featureSet.contains(f)) union++;
        }
        
        result = 1.0f - inter/union; // Jaquard distance
        
        return result;
    }
    
    
}
