package eu.diversify.ffbpg.sgh.model;

/**
 *
 * @author ffl
 */
public class SGHServerAdaptation {
    
    SGHServer server;
    
    SGHFeature feature_to_remove = null;
    SGHFeature feature_to_add = null;


    public SGHServerAdaptation(SGHServer server, SGHFeature feature_to_remove, SGHFeature feature_to_add) {
        this.server = server;
        this.feature_to_add = feature_to_add;
        this.feature_to_remove = feature_to_remove;
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
    
}
