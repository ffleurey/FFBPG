package eu.diversify.ffbpg;

import eu.diversify.ffbpg.collections.SortedIntegerCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author ffl
 */
public class Application {
    
    protected String name;
    protected SortedIntegerCollection requiredServices;
    
    protected HashSet<Platform> platforms;
    
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
    

    public SortedIntegerCollection getRequiredServices() {
        return requiredServices;
    }
    
    public Application(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        requiredServices = new SortedIntegerCollection();
        platforms = new HashSet<Platform>();
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
        SortedIntegerCollection all_provided = new SortedIntegerCollection();
        for(Platform p : platforms) {
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
        SortedIntegerCollection all_provided = new SortedIntegerCollection();
        for(Platform p : alive_platforms) {
            all_provided.addAll(p.getProvidedServices());
        }
        return all_provided.containsAll(requiredServices);
    }
    
}
