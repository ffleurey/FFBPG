/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg;

/**
 *
 * @author ffl
 */
public class Service implements Comparable<Service> {
    
    String name;
    Integer usage = 0;

    public String getName() {
        return name;
    }
    
    public Service(String name) {
        this.name = name;
    }
    
    public void incrementUsage() {
        usage++;
    }

    @Override
    public int compareTo(Service o) {
        return o.usage.compareTo(usage);
    }
    
}
