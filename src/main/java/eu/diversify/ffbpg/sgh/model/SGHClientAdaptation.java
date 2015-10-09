/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.collections.Population;
import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class SGHClientAdaptation implements Comparable<SGHClientAdaptation>{
    
    
    public static SGHClientAdaptation pickRandomAdaptation(ArrayList<SGHClientAdaptation> list) {
        assert !list.isEmpty();
        Collections.shuffle(list, RandomUtils.getRandom());
        return list.get(0);
    }
    
    public static SGHClientAdaptation pickAdaptationWeightedByFitness(ArrayList<SGHClientAdaptation> list) {
        assert !list.isEmpty();
        // Sort the list by fitness
        Collections.sort(list);
        
        double best_fitness = list.get(0).adaptation_fitness;
        double current_fitness = list.get(0).client_fitness;
        // Do not adapt to somthing worse
        if(current_fitness > best_fitness) return null;
        else if (current_fitness == best_fitness) {
            if (RandomUtils.getUniform(100) < 75) return null;
        }
        
        int[] weights = new int[list.size()];
        
        int current_weight = 100;
        double previous_eq = -1;
        int total = 0;
        for (int i=0; i<list.size(); i++) {
            SGHClientAdaptation a = list.get(i);
            if (previous_eq > 0 && previous_eq > a.adaptation_fitness) {
                current_weight /= 10;
            }
            int w = current_weight;
            previous_eq = a.adaptation_fitness;
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
    
    
    
    SGHClientApp client;
    
    SGHServer link_to_remove = null;
    SGHServer link_to_add = null;
    
    
    Population pop;
    
    double adaptation_fitness;
    double client_fitness;
    
    private void computeAdaptationFitness() {
        int[] p = new int[client.getRequests().size()];
        int i=0;
        for (SGHRequest r : client.getRequests()) {
            int nserv = 0;
            for(SGHServer s : client.getLinks()) {
                if (s == link_to_remove) continue;
                if (s.canHandle(r)) nserv++;
            }
            if (link_to_add != null && link_to_add.canHandle(r)) nserv++;
            p[i] = nserv;
            i++;
        }
        pop = new Population(p);
        adaptation_fitness = pop.getShannonEquitability();
    }

    public SGHClientApp getClient() {
        return client;
    }

    public SGHServer getLink_to_remove() {
        return link_to_remove;
    }

    public void setLink_to_remove(SGHServer link_to_remove) {
        this.link_to_remove = link_to_remove;
    }

    public SGHServer getLink_to_add() {
        return link_to_add;
    }

    public void setLink_to_add(SGHServer link_to_add) {
        this.link_to_add = link_to_add;
    }
    
    public SGHClientAdaptation(SGHClientApp client) {
        this.client = client;
    }
    
    public SGHClientAdaptation(SGHClientApp client, SGHServer link_to_remove, SGHServer link_to_add, double client_fit) {
        this.client = client;
        this.link_to_add = link_to_add;
        this.link_to_remove = link_to_remove;
        this.client_fitness = client_fit;
        computeAdaptationFitness();
    }
    
    public int execute() {
        int size = client.getLinks().size();
        if (link_to_remove != null) client.getLinks().remove(link_to_remove);
        if (link_to_add != null) client.getLinks().add(link_to_add);
        return client.getLinks().size() - size;
    }

    @Override
    public int compareTo(SGHClientAdaptation o) {
        double diff = o.adaptation_fitness - adaptation_fitness;
        if (diff > 0) return 1;
        else if (diff < 0) return -1;
        else return 0;
    }
    
}
