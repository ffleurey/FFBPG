package eu.diversify.ffbpg;

import java.util.ArrayList;

/**
 *
 * @author ffl
 */
public class Application {
    
    protected String name;
    protected SortedIntegerCollection requiredServices;
    
    protected ArrayList<Platform> platforms;
    
    public String getName() {
        return name;
    }

    public SortedIntegerCollection getRequiredServices() {
        return requiredServices;
    }
    
    public Application(String name) {
        this.name = name;
        requiredServices = new SortedIntegerCollection();
        platforms = new ArrayList<Platform>();
    }
    
    public void createAllPlatformLinks(ArrayList<Platform> available_platforms) {
        platforms = new ArrayList<Platform>();
        for (Platform p : available_platforms) {
            if(p.getProvidedServices().containsSome(requiredServices)) platforms.add(p);
        }
    }
    
    public int linksCount() {
        return platforms.size();
    }
    
    public void updateLinkforAddedPlatform(Platform p) {
        if (p.getProvidedServices().containsSome(requiredServices)) platforms.add(p);
    }
    
    public void updateLinkforRemovedPlatform(Platform p) {
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
