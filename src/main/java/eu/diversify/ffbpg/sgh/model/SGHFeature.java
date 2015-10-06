
package eu.diversify.ffbpg.sgh.model;

import java.util.ArrayList;

/**
 *
 * @author ffl
 */
public class SGHFeature implements Comparable<SGHFeature>{
    
    String name;
    Integer weight;
    
    public String getName() {
        return name;
    }
    
    public SGHFeature(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public int compareTo(SGHFeature o) {
        return weight.compareTo(o.weight);
    }
}
