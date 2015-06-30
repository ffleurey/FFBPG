package eu.diversify.ffbpg.sgh;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.Service;
import eu.diversify.ffbpg.random.PoissonIntegerGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;

/**
 *
 * @author ffl
 */
public class SGHGenerator {
    
     
    public ArrayList<VariationPoint> getSGHUrbanVariants() {
        
        ArrayList<VariationPoint> result = new ArrayList<VariationPoint>();
        
        VariationPoint v = new VariationPoint("Map", null, false);
        v.addAlternative(new Alternative("Dublin", 200));
        v.addAlternative(new Alternative("Rennes", 200));
        v.addAlternative(new Alternative("Oslo", 200));
        v.addAlternative(new Alternative("La Rochelle", 20));
        v.addAlternative(new Alternative("Thouars", 5));
        v.addAlternative(new Alternative("Sandvika", 5));
        v.addAlternative(new Alternative("Trondheim", 20));
        v.addAlternative(new Alternative("Cesson", 20));
        v.addAlternative(new Alternative("Redon", 5));
        v.addAlternative(new Alternative("Aytre", 5));
        v.addAlternative(new Alternative("Sanzay", 5));
        result.add(v);
        
        v = new VariationPoint("Vehicule", new PoissonIntegerGenerator(2), false);
        v.addAlternative(new Alternative("Car", 200));
        v.addAlternative(new Alternative("Bike", 200));
        v.addAlternative(new Alternative("Walk", 100));
        v.addAlternative(new Alternative("Motorcycle", 20));
        v.addAlternative(new Alternative("Scooter", 5));
        v.addAlternative(new Alternative("Monocycle", 5));
        result.add(v);
        
        v = new VariationPoint("Algorithm", null, false);
        v.addAlternative(new Alternative("Diksjtra", 200));
        v.addAlternative(new Alternative("A*", 20));
        result.add(v);
        
        v = new VariationPoint("Traffic", new PoissonIntegerGenerator(2), true);
        v.addAlternative(new Alternative("Google", 200));
        v.addAlternative(new Alternative("Waze", 200));
        v.addAlternative(new Alternative("PublicService", 20));
        v.addAlternative(new Alternative("TrafficApp", 20));
        result.add(v);
        
        v = new VariationPoint("Polution", new PoissonIntegerGenerator(2), true);
        v.addAlternative(new Alternative("Air_AVG", 200));
        v.addAlternative(new Alternative("Air_RT", 50));
        v.addAlternative(new Alternative("Noize_AVG", 200));
        v.addAlternative(new Alternative("Noize_RT", 200));
        v.addAlternative(new Alternative("Polen", 20));
        v.addAlternative(new Alternative("Particules", 20));
        v.addAlternative(new Alternative("Ozone", 20));
        result.add(v);
        
        v = new VariationPoint("Road", new PoissonIntegerGenerator(2), true);
        v.addAlternative(new Alternative("POI", 200));
        v.addAlternative(new Alternative("Toll", 50));
        v.addAlternative(new Alternative("Slope", 50));
        v.addAlternative(new Alternative("Scenic", 50));
        v.addAlternative(new Alternative("Cost", 50));
        result.add(v);
        
        return result;
        
    }
    
    public BPGraph buildBPGraph(ArrayList<VariationPoint> model, int nb_apps, int nb_plats) {

        Hashtable<Alternative, Service> services = new Hashtable<Alternative, Service>();
        
        for (VariationPoint v : model) {
            for(Alternative a : v.alternatives) {
                services.put(a, new Service(v.getName() + "_" + a.getName()));
            }
        }

        ArrayList<ArrayList<Service>> selections = new ArrayList<ArrayList<Service>>();
        
        for (int i=0; i<nb_apps + nb_plats; i++) {
            ArrayList<Service> selection = new ArrayList<Service>();
            for (VariationPoint v : model) {
                for (Alternative a : v.chooseAlternatives()) {
                    selection.add(services.get(a));
                    services.get(a).incrementUsage();
                }
            }
            selections.add(selection);
            
        }
        
        ArrayList<Service> sorted_services = new ArrayList<Service>(services.values());
        Collections.sort(sorted_services);
        
        Hashtable<Service, Integer> srv_idx = new Hashtable<Service, Integer>();
        
        for (int i=0; i<sorted_services.size(); i++) {
            srv_idx.put(sorted_services.get(i), i);
        }
        
        
        
        
        Iterator<ArrayList<Service>> it = selections.iterator();
        
        ArrayList<Application> apps = new ArrayList<Application>();
        for (int i=0; i<nb_apps; i++) {
            Application a = new Application("A_" + i, nb_plats);
            for (Service s : it.next()) {
                a.getRequiredServices().add(srv_idx.get(s));
            }
            apps.add(a);
        }
        
        ArrayList<Platform> plats = new ArrayList<Platform>();
        for (int i=0; i<nb_plats; i++) {
            Platform p = new Platform("P_" + i, 18);
            for (Service s : it.next()) {
                p.getProvidedServices().add(srv_idx.get(s));
            }
            plats.add(p);
        }

        BPGraph result = new BPGraph(sorted_services);
        result.setApplications(apps);
        result.setPlatforms(plats);
        result.addRandomLinksToSatisfyDeps();
        result.purgeDeadApplications();
        return result;
    }
    
    
}
