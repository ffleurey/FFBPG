/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.evolution.platforms;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.collections.SortedIntegerSet;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This operator selects a random platform and makes it drop one service.
 * The removed services is either an unused ser
 * 
 * @author ffl
 */
public class AddOneRandomService extends PlatformEvolutionOperator {

    @Override
    public boolean execute(BPGraph graph, Platform p) {

        ArrayList<Integer> rc = PlatformSrvHelper.getValidServicesToAdd(graph, p);
        
        if (rc.size()>0) {
            p.getProvidedServices().add(rc.get(0));
            return true;
        }
        
        return false;
      
    }
    
}
