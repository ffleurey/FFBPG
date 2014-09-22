package eu.diversify.ffbpg;

import eu.diversify.ffbpg.collections.SortedIntegerSet;

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
    
    protected SortedIntegerSet providedServices;

    public SortedIntegerSet getProvidedServices() {
        return providedServices;
    }
    
    public Platform(String name, int capacity) {
        this.capacity = capacity;
        this.name = name;
        providedServices = new SortedIntegerSet();
    }
    
    private Platform(String name, int capacity, int load, SortedIntegerSet providedServices) {
        this.capacity = capacity;
        this.load = load;
        this.name = name;
        this.providedServices = providedServices;
    }
    
    public Platform deep_clone() {
        return new Platform(name, capacity, load, providedServices.clone());
    }
    
}
