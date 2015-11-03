package eu.diversify.ffbpg.sgh;

import eu.diversify.ffbpg.sgh.model.SGHVariationPoint;
import eu.diversify.ffbpg.sgh.model.SGHFeature;
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
    
    
    public String getSGHDescription(ArrayList<SGHVariationPoint> model) {
        StringBuilder b = new StringBuilder();
        for (SGHVariationPoint v : model) {
            b.append("> " + v.getName() + " [");
            if (v.isOptional()) b.append("0..");
            else b.append("1..");
            
            if (!v.isMultiple()) {
                b.append("1]");
            }
            else {
                b.append("*] C:" + v.getClientMultGenerator().toString() + " / S:" + v.getServerMultGenerator());
            }
            b.append("\n");
            
            for (SGHFeature a : v.getAlternatives()) {
                b.append("    -> " + a.getName() + " (" + a.getWeight() + ")\n");
            }
            b.append("\n");
        }
        
        return b.toString();
    }
     
    public ArrayList<SGHVariationPoint> getSGHUrbanVariants() {
        
        ArrayList<SGHVariationPoint> result = new ArrayList<SGHVariationPoint>();
        
        SGHVariationPoint v = new SGHVariationPoint("Map", null, false);
        v.addAlternative(new SGHFeature("Dublin", 200, null));
        v.addAlternative(new SGHFeature("Rennes", 200, null));
        v.addAlternative(new SGHFeature("Oslo", 200, null));
        v.addAlternative(new SGHFeature("La Rochelle", 20, null));
        v.addAlternative(new SGHFeature("Thouars", 5, null));
        v.addAlternative(new SGHFeature("Sandvika", 5, null));
        v.addAlternative(new SGHFeature("Trondheim", 20, null));
        v.addAlternative(new SGHFeature("Cesson", 20, null));
        v.addAlternative(new SGHFeature("Redon", 5, null));
        v.addAlternative(new SGHFeature("Aytre", 5, null));
        v.addAlternative(new SGHFeature("Sanzay", 5, null));
        result.add(v);
        
        v = new SGHVariationPoint("Vehicule", new PoissonIntegerGenerator(2), false);
        v.addAlternative(new SGHFeature("Car", 200, null));
        v.addAlternative(new SGHFeature("Bike", 200, null));
        v.addAlternative(new SGHFeature("Walk", 100, null));
        v.addAlternative(new SGHFeature("Motorcycle", 20, null));
        v.addAlternative(new SGHFeature("Scooter", 5, null));
        v.addAlternative(new SGHFeature("Monocycle", 5, null));
        result.add(v);
        
        v = new SGHVariationPoint("Algorithm", null, false);
        v.addAlternative(new SGHFeature("Diksjtra", 200, null));
        v.addAlternative(new SGHFeature("A*", 20, null));
        result.add(v);
        
        v = new SGHVariationPoint("Traffic", new PoissonIntegerGenerator(2), true);
        v.addAlternative(new SGHFeature("Google", 200, null));
        v.addAlternative(new SGHFeature("Waze", 200, null));
        v.addAlternative(new SGHFeature("PublicService", 20, null));
        v.addAlternative(new SGHFeature("TrafficApp", 20, null));
        result.add(v);
        
        v = new SGHVariationPoint("Polution", new PoissonIntegerGenerator(2), true);
        v.addAlternative(new SGHFeature("Air_AVG", 200, null));
        v.addAlternative(new SGHFeature("Air_RT", 50, null));
        v.addAlternative(new SGHFeature("Noize_AVG", 200, null));
        v.addAlternative(new SGHFeature("Noize_RT", 200, null));
        v.addAlternative(new SGHFeature("Polen", 20, null));
        v.addAlternative(new SGHFeature("Particules", 20, null));
        v.addAlternative(new SGHFeature("Ozone", 20, null));
        result.add(v);
        
        v = new SGHVariationPoint("Road", new PoissonIntegerGenerator(2), true);
        v.addAlternative(new SGHFeature("POI", 200, null));
        v.addAlternative(new SGHFeature("Toll", 50, null));
        v.addAlternative(new SGHFeature("Slope", 50, null));
        v.addAlternative(new SGHFeature("Scenic", 50, null));
        v.addAlternative(new SGHFeature("Cost", 50, null));
        result.add(v);
        
        return result;
        
    }
    
    
    public ArrayList<SGHVariationPoint> getSGHValidCombinations() {
        
        ArrayList<SGHVariationPoint> result = new ArrayList<SGHVariationPoint>();
        
        SGHVariationPoint v = new SGHVariationPoint("0", new PoissonIntegerGenerator(2), false);
        v.addAlternative(new SGHFeature("Car", 200, null));
        v.addAlternative(new SGHFeature("Bike", 200, null));
        v.addAlternative(new SGHFeature("Walk", 100, null));
        v.addAlternative(new SGHFeature("Motorcycle", 20, null));
        v.addAlternative(new SGHFeature("Scooter", 5, null));
        v.addAlternative(new SGHFeature("Monocycle", 5, null));
        result.add(v);
        
        v = new SGHVariationPoint("Algorithm", null, false);
        v.addAlternative(new SGHFeature("Diksjtra", 200, null));
        v.addAlternative(new SGHFeature("A*", 20, null));
        result.add(v);
        
        v = new SGHVariationPoint("Traffic", new PoissonIntegerGenerator(2), true);
        v.addAlternative(new SGHFeature("Google", 200, null));
        v.addAlternative(new SGHFeature("Waze", 200, null));
        v.addAlternative(new SGHFeature("PublicService", 20, null));
        v.addAlternative(new SGHFeature("TrafficApp", 20, null));
        result.add(v);
        
        v = new SGHVariationPoint("Polution", new PoissonIntegerGenerator(2), true);
        v.addAlternative(new SGHFeature("Air_AVG", 200, null));
        v.addAlternative(new SGHFeature("Air_RT", 50, null));
        v.addAlternative(new SGHFeature("Noize_AVG", 200, null));
        v.addAlternative(new SGHFeature("Noize_RT", 200, null));
        v.addAlternative(new SGHFeature("Polen", 20, null));
        v.addAlternative(new SGHFeature("Particules", 20, null));
        v.addAlternative(new SGHFeature("Ozone", 20, null));
        result.add(v);
        
        v = new SGHVariationPoint("Road", new PoissonIntegerGenerator(2), true);
        v.addAlternative(new SGHFeature("POI", 200, null));
        v.addAlternative(new SGHFeature("Toll", 50, null));
        v.addAlternative(new SGHFeature("Slope", 50, null));
        v.addAlternative(new SGHFeature("Scenic", 50, null));
        v.addAlternative(new SGHFeature("Cost", 50, null));
        result.add(v);
        
        return result;
        
    }
    
    public BPGraph buildBPGraph(ArrayList<SGHVariationPoint> model, int nb_apps, int nb_plats) {
/*
        Hashtable<SGHFeature, Service> services = new Hashtable<SGHFeature, Service>();
        
        for (SGHVariationPoint v : model) {
            for(SGHFeature a : v.alternatives) {
                services.put(a, new Service(v.getName() + "_" + a.getName()));
            }
        }

        ArrayList<ArrayList<Service>> selections = new ArrayList<ArrayList<Service>>();
        
        for (int i=0; i<nb_apps + nb_plats; i++) {
            ArrayList<Service> selection = new ArrayList<Service>();
            for (SGHVariationPoint v : model) {
                for (SGHFeature a : v.chooseAlternatives()) {
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
        
        */
        return null;
    }
    
    
}
