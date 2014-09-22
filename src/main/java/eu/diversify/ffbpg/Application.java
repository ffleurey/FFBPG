package eu.diversify.ffbpg;

import eu.diversify.ffbpg.collections.Population;
import eu.diversify.ffbpg.collections.SortedIntegerSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public class Application {
    
    protected String name;
    protected SortedIntegerSet requiredServices;
    
    protected HashSet<Platform> platforms;
    
    protected HashSet<Platform> neighborhood;
    
    public HashSet<Platform> getNeighborhood() {
        return neighborhood;
    }
    
    public void setNeighborhood(HashSet<Platform> platforms) {
        neighborhood = platforms;
    }
    
    
    protected int capacity = 10;

    public int getLoad() {
        return platforms.size();
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean hasRemainingCapacity() {
        return getLoad() < capacity;
    }
    
    public HashSet<Platform> getLinkedPlatforms() {
        return platforms;
    }
    
    public String getLinkedPlatformNames() {
        StringBuffer result = new StringBuffer("{");
        for(Platform p : platforms) {
            result.append(p.getName());
            result.append(" "); 
        }
        result.append("}");
        return result.toString();
    }
    
    public int getServicesRedondancy(Integer srv) {
        return getServicesRedondancy(srv, platforms);
    }
    
    public int getServicesRedondancy(Integer srv, Collection<Platform> platform_links) {
        int result = 0;
        if(requiredServices.contains(srv)) {
            for (Platform p : platform_links) {
                if (p.getProvidedServices().contains(srv)) result ++;
            }
        }
        return result;
    }
    
    public Population getServicesPopulation(Collection<Platform> platform_links) {
          int[] pop = new int[requiredServices.size()];
            for (int i = 0; i<pop.length; i++) {
                pop[i] = getServicesRedondancy(requiredServices.get(i), platform_links);
            }
            return new Population(pop);
    }
    
    protected Population servicesPop = null;
    public Population getServicesPopulation() {
        if (servicesPop == null) {
            servicesPop = getServicesPopulation(platforms);
        }
        return servicesPop;
    }
    public void resetServicesPopulation() {
        servicesPop = null;
    }

    public SortedIntegerSet getRequiredServices() {
        return requiredServices;
    }
    
    public Application(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        requiredServices = new SortedIntegerSet();
        platforms = new HashSet<Platform>();
    }
    
    private Application(String name, int capacity, SortedIntegerSet requiredServices, HashSet<Platform> new_platforms, HashSet<Platform> neighborhood) {
        this.name = name;
        this.capacity = capacity;
        this.requiredServices = requiredServices;
        this.platforms = new_platforms;
        this.neighborhood = neighborhood;
    }
    
    public Application deep_clone(Hashtable<String, Platform> new_platforms) {
        HashSet<Platform> nplatforms = new HashSet<Platform>();   
        for (Platform p : platforms) {
            nplatforms.add(new_platforms.get(p.getName()));
        }
        
        HashSet<Platform> nneighborhood = new HashSet<Platform>();
        for (Platform p : neighborhood) {
            nneighborhood.add(new_platforms.get(p.getName()));
        }
        
        return new Application(name, capacity, requiredServices.clone(), nplatforms, nneighborhood);
    }
    
    public void addLinksToPlatformsProvidingAtLeastOneSrv(ArrayList<Platform> available_platforms, ArrayList<AddLinkIfPossible> result) {
       
        for (Platform p : available_platforms) {
            if(p.getProvidedServices().containsSome(requiredServices)) {
                result.add(new AddLinkIfPossible(this, p));
            }
        }
    }
    
    public void addLinksToPlatformsProvidingAllSrv(ArrayList<Platform> available_platforms, ArrayList<AddLinkIfPossible> result) {

        for (Platform p : available_platforms) {
            if(p.getProvidedServices().containsAll(requiredServices)) {
               result.add(new AddLinkIfPossible(this, p));
            }
        }
    }
    
    
    public int linksCount() {
        return platforms.size();
    }
    
    public void updateLinkforAddedPlatform(Platform p) {
        if (p.hasRemainingCapacity() && this.hasRemainingCapacity() && p.getProvidedServices().containsSome(requiredServices)) {
            platforms.add(p);
            p.incrementLoad();
        }
    }
    
    public void updateLinkforRemovedPlatform(Platform p) {
        if (platforms.contains(p)) p.decrementLoad();
        platforms.remove(p);
    }
    
    public boolean dependenciesSatisfied() {
        return dependenciesSatisfied(platforms);
    }
    
    public boolean dependenciesSatisfied(Collection<Platform> linked_platforms) {
        SortedIntegerSet all_provided = new SortedIntegerSet();
        for(Platform p : linked_platforms) {
            all_provided.addAll(p.getProvidedServices());
        }
        return all_provided.containsAll(requiredServices);
    }
    
    protected ArrayList<Platform> alive_platforms;
    
    public void startExtinctionSequence() {
        alive_platforms = new ArrayList<Platform>();
        alive_platforms.addAll(platforms);
    }
    
    public void killPlatform(Platform p) {
        alive_platforms.remove(p);
    }
    
    public boolean isAlive() {
        SortedIntegerSet all_provided = new SortedIntegerSet();
        for(Platform p : alive_platforms) {
            all_provided.addAll(p.getProvidedServices());
        }
        return all_provided.containsAll(requiredServices);
    }
    
}
