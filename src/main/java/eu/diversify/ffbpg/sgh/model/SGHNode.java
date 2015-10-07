/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.sgh.model;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class SGHNode {
        HashMap<SGHVariationPoint, ArrayList<SGHFeature>> features;
    
    

    public HashMap<SGHVariationPoint, ArrayList<SGHFeature>> getFeatures() {
        return features;
    }
}
