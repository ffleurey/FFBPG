
package eu.diversify.ffbpg.sgh.model;

import java.util.ArrayList;

/**
 *
 * @author ffl
 */
public class SGHFeature implements Comparable<SGHFeature>{
    
    String name;
    Integer weight;
    SGHVariationPoint variationPoint;

    public SGHVariationPoint getVariationPoint() {
        return variationPoint;
    }
    
    public String getName() {
        return name;
    }

    public Integer getWeight() {
        return weight;
    }
    
    public SGHFeature(String name, int weight, SGHVariationPoint variationPoint) {
        this.name = name;
        this.weight = weight;
        this.variationPoint = variationPoint;
    }

    @Override
    public int compareTo(SGHFeature o) {
        return weight.compareTo(o.weight);
    }
}
