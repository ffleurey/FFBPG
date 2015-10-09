package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public class SGHServerAdaptation implements Comparable<SGHServerAdaptation> {

    public static SGHServerAdaptation pickRandomAdaptation(ArrayList<SGHServerAdaptation> list) {
        assert !list.isEmpty();
        Collections.shuffle(list, RandomUtils.getRandom());
        return list.get(0);
    }
    
    public static SGHServerAdaptation pickAdaptationWeightedByFeaturesPopularity(ArrayList<SGHServerAdaptation> list) {
        assert !list.isEmpty();
        // Sort the list by popularity
        Collections.sort(list);
        // Popularity can be negative so we need to calculate some weights
        int[] weights = new int[list.size()];
        // Find the lowest value
        int min = list.get(list.size()-1).popularity;
        int total = 0;
        for (int i=0; i<list.size(); i++) {
            int w = list.get(i).popularity - min + 1;
            w = w * w; // square the popularity to accentuate the differences
            total += w;
            weights[i] = total;
        }
        // Pick a random integer
        int picked = RandomUtils.getUniform(total);
        
        for (int i=0; i<list.size(); i++) {
            if (picked <= weights[i]) return list.get(i);
        }
        // This can never happen unless there is a bug in the few lines above
        return null;
    }
    
    
    SGHServer server;
    Hashtable<SGHFeature, Integer> popularities;
    
    SGHFeature feature_to_remove = null;
    SGHFeature feature_to_add = null;

    
    int popularity;
    
    public int computePopularity() {
        int result = 0;
        if (feature_to_add != null && popularities.containsKey(feature_to_add)) {
            result += popularities.get(feature_to_add);
        }
        if (feature_to_remove != null && popularities.containsKey(feature_to_remove)) {
            result -= popularities.get(feature_to_remove);
        }
        return result;
    }
    

    public SGHServerAdaptation(SGHServer server, SGHFeature feature_to_remove, SGHFeature feature_to_add, Hashtable<SGHFeature, Integer> popularities) {
        this.server = server;
        this.feature_to_add = feature_to_add;
        this.feature_to_remove = feature_to_remove;
        this.popularities = popularities;
        popularity = computePopularity();
    }
    
    public int execute() {
        int size = server.featureSet.size();
        if (feature_to_remove != null) server.removeFeature(feature_to_remove);
        if (feature_to_add != null) server.addFeature(feature_to_add);
        return server.featureSet.size()-size;
    }

    public SGHFeature getFeature_to_remove() {
        return feature_to_remove;
    }

    public void setFeature_to_remove(SGHFeature feature_to_remove) {
        this.feature_to_remove = feature_to_remove;
    }

    public SGHFeature getFeature_to_add() {
        return feature_to_add;
    }

    public void setFeature_to_add(SGHFeature feature_to_add) {
        this.feature_to_add = feature_to_add;
    }

    @Override
    public int compareTo(SGHServerAdaptation o) {
        return o.popularity - popularity;
    }
    
}
