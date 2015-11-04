package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.random.PoissonIntegerGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public class SGHModel {
    
    private static SGHModel _instance = null;
    
    public static SGHModel getInstance() {
        if (_instance == null) _instance = new SGHModel();
        return _instance;
    }
    
    private SGHModel() {
        initializeModel();
    }
    
    Hashtable<String, SGHVariationPoint> variationPoints;
    
    public Collection<SGHVariationPoint> getAllVariationPoints() {
        return variationPoints.values();
    }
    
    public SGHVariationPoint getVariationPoint(String name) {
        return variationPoints.get(name);
    }
    
    private void initializeModel() {
        variationPoints = createSGHVariationPoints();
    }
    
    public int totalNbFeatures() {
        int result = 0;
        for (SGHVariationPoint vp : variationPoints.values()) result += vp.alternatives.size();
        return result;
    }
    
    private Hashtable<String, SGHVariationPoint> createSGHVariationPoints() {
        
        Hashtable<String, SGHVariationPoint> result = new Hashtable<String, SGHVariationPoint>();
        
        SGHVariationPoint v = new SGHVariationPoint("vehicle", new PoissonIntegerGenerator(1), new PoissonIntegerGenerator(2), false);
        v.addAlternative(new SGHFeature("car", 500, v));
        v.addAlternative(new SGHFeature("bike", 100, v));
        v.addAlternative(new SGHFeature("foot", 50, v));
        v.addAlternative(new SGHFeature("motorcycle", 20, v));
        v.addAlternative(new SGHFeature("scooter", 5, v));
        result.put(v.getName(), v);
        
        v = new SGHVariationPoint("algorithm", null, false);
        v.addAlternative(new SGHFeature("diksjtra", 200, v));
        v.addAlternative(new SGHFeature("astar", 20, v));
        result.put(v.getName(), v);
        
        v = new SGHVariationPoint("weighting", new PoissonIntegerGenerator(4), new PoissonIntegerGenerator(2), false);
        v.addAlternative(new SGHFeature("default", 250, v));
        v.addAlternative(new SGHFeature("fastest", 500, v));
        v.addAlternative(new SGHFeature("shortest", 100, v));
        v.addAlternative(new SGHFeature("least_polluted", 50, v));
        v.addAlternative(new SGHFeature("least_noisy", 50, v));
        v.addAlternative(new SGHFeature("least_pollen", 10, v));
        v.addAlternative(new SGHFeature("most_ozonic", 10, v));
        v.addAlternative(new SGHFeature("most_scenic", 5, v));
        v.addAlternative(new SGHFeature("least_congested", 100, v));
        result.put(v.getName(), v);
        
        
        return result;
        
    }
    /*
        private Hashtable<String, SGHVariationPoint> createSGHVariationPoints() {
        
        Hashtable<String, SGHVariationPoint> result = new Hashtable<String, SGHVariationPoint>();
        
        SGHVariationPoint v = new SGHVariationPoint("Vehicle", new PoissonIntegerGenerator(1), new PoissonIntegerGenerator(2), false);
        v.addAlternative(new SGHFeature("Car", 500, v));
        v.addAlternative(new SGHFeature("Bike", 100, v));
        v.addAlternative(new SGHFeature("Walk", 50, v));
        v.addAlternative(new SGHFeature("Motorcycle", 20, v));
        v.addAlternative(new SGHFeature("Scooter", 5, v));
        v.addAlternative(new SGHFeature("Monocycle", 5, v));
        result.put(v.getName(), v);
        
        v = new SGHVariationPoint("Algorithm", null, false);
        v.addAlternative(new SGHFeature("Diksjtra", 200, v));
        v.addAlternative(new SGHFeature("A*", 20, v));
        result.put(v.getName(), v);
        
        v = new SGHVariationPoint("Traffic", new PoissonIntegerGenerator(3), new PoissonIntegerGenerator(1), true);
        v.addAlternative(new SGHFeature("Google", 200, v));
        v.addAlternative(new SGHFeature("Waze", 50, v));
        v.addAlternative(new SGHFeature("PublicService", 10, v));
        v.addAlternative(new SGHFeature("TrafficApp", 5, v));
        result.put(v.getName(), v);
        
        v = new SGHVariationPoint("Pollution", new PoissonIntegerGenerator(3), new PoissonIntegerGenerator(1), true);
        v.addAlternative(new SGHFeature("Air_AVG", 500, v));
        v.addAlternative(new SGHFeature("Air_RT", 50, v));
        v.addAlternative(new SGHFeature("Noize_AVG", 20, v));
        v.addAlternative(new SGHFeature("Noize_RT", 100, v));
        v.addAlternative(new SGHFeature("Polen", 10, v));
        v.addAlternative(new SGHFeature("Particules", 5, v));
        v.addAlternative(new SGHFeature("Ozone", 5, v));
        result.put(v.getName(), v);
        
        v = new SGHVariationPoint("Road", new PoissonIntegerGenerator(3), new PoissonIntegerGenerator(1), false);
        v.addAlternative(new SGHFeature("POI", 200, v));
        v.addAlternative(new SGHFeature("Toll", 50, v));
        v.addAlternative(new SGHFeature("Slope", 20, v));
        v.addAlternative(new SGHFeature("Scenic", 10, v));
        v.addAlternative(new SGHFeature("Cost", 10, v));
        result.put(v.getName(), v);
        
        return result;
        
    }
    */
    
    public SGHClientApp createRandomClient() {
        SGHClientApp result = null;
        while (result==null || result.requests.size()<2){
            HashMap<SGHVariationPoint, ArrayList<SGHFeature>> selection = new HashMap<SGHVariationPoint, ArrayList<SGHFeature>>();
            for (SGHVariationPoint vp : getAllVariationPoints()) {
                selection.put(vp, vp.chooseAlternatives(vp.getClientMultGenerator()));
            }
            result =  new SGHClientApp(selection);
        }
        return result;
    }
    
    public SGHServer createRandomServer() {
        
        HashMap<SGHVariationPoint, ArrayList<SGHFeature>> selection = new HashMap<SGHVariationPoint, ArrayList<SGHFeature>>();
        for (SGHVariationPoint vp : getAllVariationPoints()) {
            selection.put(vp, vp.chooseAlternatives(vp.getServerMultGenerator()));
        }
        return new SGHServer(selection);
        
    }
    
    
}
