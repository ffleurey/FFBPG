/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.evolution.platforms;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.Service;
import eu.diversify.ffbpg.collections.SortedIntegerSet;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * This operator selects a random platform and makes it drop one service.
 * The removed services is either an unused ser
 * 
 * @author ffl
 */
public class DropOneRandomService extends PlatformEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Platform p) {
     
        ArrayList<Integer> unused_services = PlatformSrvHelper.getValidServicesToRemove(graph, p);
                
        if(!unused_services.isEmpty()) {
             p.getProvidedServices().remove(unused_services.get(0));
             p.clearAllCachedData();
            return true;
        }
        else {
            return false;
        }
      
    }
    
}
