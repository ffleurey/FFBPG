package eu.diversify.ffbpg;

import eu.diversify.ffbpg.collections.Population;
import eu.diversify.ffbpg.collections.SortedIntegerSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

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
    
    
     public void clearAllCachedData() {
         linked_apps = null;
         all_known_services_not_offered = null;
         //servicesUsagePopulation = null;
         //servicesRedondancyPopulation = null;
         notProvidedServicesMinRedondancyPopulation = null;
         providedServicesMinRedondancyPopulation = null;
         
     }
     
     // Calcullated data:
     ArrayList<Application> linked_apps;
     
     Hashtable<Integer, Integer> services_requests;
     
     SortedIntegerSet all_known_services_not_offered;
     //Population servicesUsagePopulation;
     //Population servicesRedondancyPopulation;
     Population notProvidedServicesMinRedondancyPopulation;
     Population providedServicesMinRedondancyPopulation;
     
     Population notProvidedServicesNumberRequests;
     Population providedServicesNumberRequests;

    public ArrayList<Application> getLinked_apps(BPGraph graph) {
        if (linked_apps == null) computeServicePopulation(graph);
        return linked_apps;
    }
    
     public Hashtable<Integer, Integer> getAllServicesNumberOfRequests(BPGraph graph) {
        if (linked_apps == null) computeServicePopulation(graph);
        return services_requests;
    }

    public SortedIntegerSet getAll_known_services_not_offered(BPGraph graph) {
        if (linked_apps == null) computeServicePopulation(graph);
        return all_known_services_not_offered;
    }

//    public Population getServicesUsagePopulation(BPGraph graph) {
//        if (linked_apps == null) computeServicePopulation(graph);
//        return servicesUsagePopulation;
//    }
//
//    public Population getServicesRedondancyPopulation(BPGraph graph) {
//        if (linked_apps == null) computeServicePopulation(graph);
//        return servicesRedondancyPopulation;
//    }

    public Population getNotOfferedServicesMinRedondancyPopulation(BPGraph graph) {
        if (linked_apps == null) computeServicePopulation(graph);
        return notProvidedServicesMinRedondancyPopulation;
    }
    
    public Population getProvidedServicesMinRedondancyPopulation(BPGraph graph) {
        if (linked_apps == null) computeServicePopulation(graph);
        return providedServicesMinRedondancyPopulation;
    }
    
     public Population getNotOfferedServicesNumberRequests(BPGraph graph) {
        if (linked_apps == null) computeServicePopulation(graph);
        return notProvidedServicesNumberRequests;
    }
    
    public Population getProvidedServicesNumberRequests(BPGraph graph) {
        if (linked_apps == null) computeServicePopulation(graph);
        return providedServicesNumberRequests;
    }
    
     
     private void computeServicePopulation(BPGraph graph) {
         
         linked_apps = graph.getLinkedApplicationsForPlatform(this);
         services_requests = new Hashtable<Integer, Integer>();
         // Collect all services
         all_known_services_not_offered = providedServices.clone();
         for (Application a : linked_apps) {
             all_known_services_not_offered.addAll(a.getRequiredServices());
         }
         // remove the services which are offered by p:
         for (int i=0; i<providedServices.size(); i++) {
             all_known_services_not_offered.remove(providedServices.get(i));
         }
         
         //int[] usage_pop = new int[all_known_services_not_offered.size()]; // # of apps using the service
         //int[] total_red_pop = new int[all_known_services_not_offered.size()]; // Min # of redondancy for apps
         int[] min_red_pop = new int[all_known_services_not_offered.size()]; // Min # of redondancy for apps
         
         int[] req_pop = new int[all_known_services_not_offered.size()];
         
         for (int i=0; i<all_known_services_not_offered.size(); i++) {
             Integer srv = all_known_services_not_offered.get(i);
             min_red_pop[i] = -1; // Theoritical absolute maximum
             req_pop[i] = 0;
             //usage_pop[i] = 0;
             //total_red_pop[i] = 0;
             for (Application a : linked_apps) {
                 if (a.requiredServices.contains(srv)) {
                     req_pop[i]++;
                     //usage_pop[i]++;
                     int r = a.getServicesRedondancy(srv);
                     //total_red_pop[i] += r;
                     if (min_red_pop[i] < 0 || r < min_red_pop[i]) min_red_pop[i] = r;
                 }
             }
             services_requests.put(srv, req_pop[i]);
         }
         //servicesUsagePopulation = new Population(usage_pop);
         //servicesRedondancyPopulation = new Population(total_red_pop);
         notProvidedServicesMinRedondancyPopulation = new Population(min_red_pop);
         notProvidedServicesNumberRequests = new Population(req_pop);
         
         min_red_pop = new int[getProvidedServices().size()]; // Min # of redondancy for apps
         req_pop = new int[getProvidedServices().size()];
         
         for (int i=0; i<getProvidedServices().size(); i++) {
             Integer srv = getProvidedServices().get(i);
             min_red_pop[i] = -1; // Theoritical absolute maximum
             req_pop[i] = 0;
             //usage_pop[i] = 0;
             //total_red_pop[i] = 0;
             for (Application a : linked_apps) {
                 if (a.requiredServices.contains(srv)) {
                     //usage_pop[i]++;
                     req_pop[i]++;
                     int r = a.getServicesRedondancy(srv);
                     //total_red_pop[i] += r;
                     if (min_red_pop[i] < 0 || r < min_red_pop[i]) min_red_pop[i] = r;
                 }
                 services_requests.put(srv, req_pop[i]);
             }
         }

         providedServicesMinRedondancyPopulation = new Population(min_red_pop);
         providedServicesNumberRequests = new Population(req_pop);
     }
    
}
