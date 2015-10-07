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
    
    
    @Override
    public String toString() {
        ArrayList<String> strs = new ArrayList<String>();
        for (SGHFeature f : features) {
            strs.add(f.getName());
        }
        Collections.sort(strs);
        StringBuffer b = new StringBuffer();
        for (String s : strs) { b.append(s); b.append(" "); }
        return b.toString().trim();
    }
    
    public SGHRequest() {
        this.features = new HashSet<SGHFeature>();
    }
    
    public SGHRequest(SGHRequest toClone) {
        this.features = (HashSet<SGHFeature>)toClone.features.clone();
    }
    
}
