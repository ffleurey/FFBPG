package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.random.PoissonIntegerGenerator;
import java.util.ArrayList;

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
    
    ArrayList<SGHVariationPoint> variationPoints;
    
    private void initializeModel() {
        variationPoints = createSGHVariationPoints();
    }
    
    private ArrayList<SGHVariationPoint> createSGHVariationPoints() {
        
        ArrayList<SGHVariationPoint> result = new ArrayList<SGHVariationPoint>();
        
        SGHVariationPoint v = new SGHVariationPoint("0", new PoissonIntegerGenerator(2), false);
        v.addAlternative(new SGHFeature("Car", 200));
        v.addAlternative(new SGHFeature("Bike", 200));
        v.addAlternative(new SGHFeature("Walk", 100));
        v.addAlternative(new SGHFeature("Motorcycle", 20));
        v.addAlternative(new SGHFeature("Scooter", 5));
        v.addAlternative(new SGHFeature("Monocycle", 5));
        result.add(v);
        
        v = new SGHVariationPoint("Algorithm", null, false);
        v.addAlternative(new SGHFeature("Diksjtra", 200));
        v.addAlternative(new SGHFeature("A*", 20));
        result.add(v);
        
        v = new SGHVariationPoint("Traffic", new PoissonIntegerGenerator(2), true);
        v.addAlternative(new SGHFeature("Google", 200));
        v.addAlternative(new SGHFeature("Waze", 200));
        v.addAlternative(new SGHFeature("PublicService", 20));
        v.addAlternative(new SGHFeature("TrafficApp", 20));
        result.add(v);
        
        v = new SGHVariationPoint("Polution", new PoissonIntegerGenerator(2), true);
        v.addAlternative(new SGHFeature("Air_AVG", 200));
        v.addAlternative(new SGHFeature("Air_RT", 50));
        v.addAlternative(new SGHFeature("Noize_AVG", 200));
        v.addAlternative(new SGHFeature("Noize_RT", 200));
        v.addAlternative(new SGHFeature("Polen", 20));
        v.addAlternative(new SGHFeature("Particules", 20));
        v.addAlternative(new SGHFeature("Ozone", 20));
        result.add(v);
        
        v = new SGHVariationPoint("Road", new PoissonIntegerGenerator(2), true);
        v.addAlternative(new SGHFeature("POI", 200));
        v.addAlternative(new SGHFeature("Toll", 50));
        v.addAlternative(new SGHFeature("Slope", 50));
        v.addAlternative(new SGHFeature("Scenic", 50));
        v.addAlternative(new SGHFeature("Cost", 50));
        result.add(v);
        
        return result;
        
    }
    
}
