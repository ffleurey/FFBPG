
package eu.diversify.ffbpg.sgh;

import java.util.ArrayList;

/**
 *
 * @author ffl
 */
public class Alternative implements Comparable<Alternative>{
    
    String name;
    Integer weight;
    
    public String getName() {
        return name;
    }
    
    public Alternative(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public int compareTo(Alternative o) {
        return weight.compareTo(o.weight);
    }
}
