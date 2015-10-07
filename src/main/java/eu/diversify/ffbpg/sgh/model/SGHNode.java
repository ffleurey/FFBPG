/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.sgh.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;


public abstract class SGHNode {
    
    HashMap<SGHVariationPoint, ArrayList<SGHFeature>> features;
    HashSet<SGHFeature> featureSet;
    
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
}
