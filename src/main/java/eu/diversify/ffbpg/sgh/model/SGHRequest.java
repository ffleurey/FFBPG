package eu.diversify.ffbpg.sgh.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author ffl
 */
public class SGHRequest {
    
    HashSet<SGHFeature> features;
    
    String to_string = null;
    
    @Override
    public String toString() {
        if (to_string == null) {
            ArrayList<String> strs = new ArrayList<String>();
            for (SGHFeature f : features) {
                strs.add(f.getName());
            }
            Collections.sort(strs);
            StringBuffer b = new StringBuffer();
            for (String s : strs) { b.append(s); b.append(" "); }
            to_string = b.toString().trim();
        }
        return to_string;
    }
    
    public SGHRequest() {
        this.features = new HashSet<SGHFeature>();
    }
    
    public SGHRequest(SGHRequest toClone) {
        this.features = (HashSet<SGHFeature>)toClone.features.clone();
    }
    
}
