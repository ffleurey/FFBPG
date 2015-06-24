package eu.diversify.ffbpg;

import eu.diversify.ffbpg.collections.Population;
import eu.diversify.ffbpg.collections.SortedIntegerSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @author ffl
 */
public class Application {
    
    protected String name;
    protected SortedIntegerSet requiredServices;
    
    protected HashSet<Platform> platforms;
    
    /*
    
    protected HashSet<Platform> __neighborhood_platforms;
    protected HashSet<Application> __neighborhood_applications;
    
    private void computeNeighborhoods(BPGraph graph) {

            __neighborhood_applications = new HashSet<Application>();
            for (Platform p : getLinkedPlatforms()) {
                __neighborhood_applications.addAll(p.getLinked_apps(graph));
            }
            __neighborhood_platforms = new HashSet<Platform>();
            for (Application a : __neighborhood_applications) {
                __neighborhood_platforms.addAll(a.getLinkedPlatforms());
            }

    }
    
    public HashSet<Application> getApplicationNeighborhoods(BPGraph graph) {
        if (__neighborhood_applications == null) computeNeighborhoods(graph);
        return __neighborhood_applications;
    }
    
    public HashSet<Platform> getPlatformNeighborhoods(BPGraph graph) {
        if (__neighborhood_platforms == null) computeNeighborhoods(graph);
        return __neighborhood_platforms;
    }
    
    /*
    public void setNeighborhood(HashSet<Platform> platforms) {
        neighborhood = platforms;
    }
    */
    public void clearAllCachedData() {
        resetServicesPopulation();
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
    
    public String getNeighborhoodPlatformNames(BPGraph graph) {
        Set<Platform> n = graph.getNeighborhoodForApplication(this);
        if (n == null) return ""; // No neigborhood has been calculated and used
        StringBuffer result = new StringBuffer("{");
        for(Platform p : n) {
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
    
    private Application(String name, int capacity, SortedIntegerSet requiredServices, HashSet<Platform> new_platforms) {
        this.name = name;
        this.capacity = capacity;
        this.requiredServices = requiredServices;
        this.platforms = new_platforms;
        //this.neighborhood = neighborhood;
    }
    
    public Application deep_clone(Hashtable<String, Platform> new_platforms) {
        HashSet<Platform> nplatforms = new HashSet<Platform>();   
        for (Platform p : platforms) {
            nplatforms.add(new_platforms.get(p.getName()));
        }
        
        //HashSet<Platform> nneighborhood = new HashSet<Platform>();
        /*
        if (neighborhood != null) {
            for (Platform p : neighborhood) {
                nneighborhood.add(new_platforms.get(p.getName()));
            }
        }
                */
        
        return new Application(name, capacity, requiredServices.clone(), nplatforms);
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
            addLinkToPlatform(p);
        }
    }
    
    public void addLinkToPlatform(Platform p) {
            platforms.add(p);
            p.incrementLoad();
    }
    
    public void removeLinkToPlatform(Platform p) {
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
