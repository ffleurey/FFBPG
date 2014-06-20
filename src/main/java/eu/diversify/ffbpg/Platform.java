package eu.diversify.ffbpg;

import eu.diversify.ffbpg.collections.SortedIntegerCollection;

/**
 *
 * @author ffl
 */
public class Platform {
    
    protected String name;
    
    public String getName() {
        return name;
    }
    
    protected int capacity = 10;
    protected int load = 0;

    public int getLoad() {
        return load;
    }
    
    public void resetLoad() {
        load = 0;
    }
    
    public void incrementLoad() {
        load++;
    }
    
    public void decrementLoad() {
        load--;
    }
    
    public void setLoad(int load) {
        this.load = load;
    }
    
    public boolean hasRemainingCapacity() {
        return load < capacity;
    }
    
    protected SortedIntegerCollection providedServices;

    public SortedIntegerCollection getProvidedServices() {
        return providedServices;
    }
    
    public Platform(String name, int capacity) {
        this.capacity = capacity;
        this.name = name;
        providedServices = new SortedIntegerCollection();
    }
}
