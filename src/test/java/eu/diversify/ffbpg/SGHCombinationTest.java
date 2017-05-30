package eu.diversify.ffbpg;

import eu.diversify.ffbpg.sgh.model.SGHClientApp;
import eu.diversify.ffbpg.sgh.model.SGHFeature;
import eu.diversify.ffbpg.sgh.model.SGHModel;
import eu.diversify.ffbpg.sgh.model.SGHVariationPoint;
import java.util.ArrayList;
import java.util.HashMap;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple the platform generator.
 */
public class SGHCombinationTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SGHCombinationTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SGHCombinationTest.class );
    }
    /*
    public void testSGHRequestsGenerator1()
    {
        SGHModel m = SGHModel.getInstance();
        
        // Create a client "manually"
        HashMap<SGHVariationPoint, ArrayList<SGHFeature>> selection = new HashMap<SGHVariationPoint, ArrayList<SGHFeature>>();
        
        ArrayList<SGHFeature> features;
        
        SGHVariationPoint vehicule = m.getVariationPoint("Vehicule");
        features = new ArrayList<SGHFeature>();
        features.add(vehicule.getAlternative("Car"));
        selection.put(vehicule, features);
        
        SGHVariationPoint algo = m.getVariationPoint("Algorithm");
        features = new ArrayList<SGHFeature>();
        features.add(algo.getAlternative("Diksjtra"));
        selection.put(algo, features);
        
        SGHVariationPoint traffic = m.getVariationPoint("Traffic");
        features = new ArrayList<SGHFeature>();
        features.add(traffic.getAlternative("Waze"));
        selection.put(traffic, features);
        
        SGHVariationPoint polution = m.getVariationPoint("Polution");
        features = new ArrayList<SGHFeature>();
        features.add(polution.getAlternative("Particules"));
        features.add(polution.getAlternative("Noize_RT"));
        selection.put(polution, features);
        
        SGHVariationPoint road = m.getVariationPoint("Road");
        features = new ArrayList<SGHFeature>();
        features.add(road.getAlternative("Cost"));
        selection.put(road, features);
        
        SGHClientApp cli = new SGHClientApp(selection);
        
        System.out.println(cli.getStringDump());
        
        assertEquals(16, cli.getRequests().size());
        
    }
    
    public void testSGHRequestsGenerator2()
    {
        SGHModel m = SGHModel.getInstance();
        
        // Create a client "manually"
        HashMap<SGHVariationPoint, ArrayList<SGHFeature>> selection = new HashMap<SGHVariationPoint, ArrayList<SGHFeature>>();
        
        ArrayList<SGHFeature> features;
        
        SGHVariationPoint vehicule = m.getVariationPoint("Vehicule");
        features = new ArrayList<SGHFeature>();
        features.add(vehicule.getAlternative("Car"));
        features.add(vehicule.getAlternative("Bike"));
        features.add(vehicule.getAlternative("Walk"));
        selection.put(vehicule, features);
        
        SGHVariationPoint algo = m.getVariationPoint("Algorithm");
        features = new ArrayList<SGHFeature>();
        features.add(algo.getAlternative("Diksjtra"));
        selection.put(algo, features);
     
 
        SGHClientApp cli = new SGHClientApp(selection);
        
        System.out.println(cli.getStringDump());
        
        assertEquals(7, cli.getRequests().size());
        
    }
    */
    public void testRandomGenerator1()
    {
        SGHModel m = SGHModel.getInstance();
        
        SGHClientApp c1 = m.createRandomClient();
        System.out.println(c1.getStringDump());
    }

   
}
