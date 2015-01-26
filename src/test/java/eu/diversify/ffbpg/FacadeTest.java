package eu.diversify.ffbpg;

import eu.diversify.ffbpg.random.IntegerGenerator;
import eu.diversify.ffbpg.random.IntegerSetGenerator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple the platform generator.
 */
public class FacadeTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FacadeTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( FacadeTest.class );
    }
    
    public void testGenerateBPGraph()
    {
        // Here is the default parameters I am using
        BPGraph g = Facade.createBPgraph(100, 80, Facade.getPoissonIntegerGenerator(6), Facade.getNegExpIntegerSetGenerator(0.25, 0.005));
     
        assertEquals(g.getServices().size(), 80);
        assertEquals(g.getApplications().size(), 100);
        assertEquals(g.getPlatforms().size(), 100);
    }
    
    public void testGenerateBPGraphRandom()
    {
        int number_platforms = 100;
        int number_services = 75;
        int number_applications = 150;
        
        int platforms_capacity = 10;
        int application_capacity = number_services; // No Limit
        
        // Random distribution generators used both for apps and plats
        IntegerGenerator sizes_generator = Facade.getPoissonIntegerGenerator(6); 
        IntegerSetGenerator srv_generator = Facade.getNegExpIntegerSetGenerator(0.25, 0.005);
        
        
        BPGraph g = Facade.createRandomBPGraph(number_applications, number_platforms, number_services,
                    sizes_generator, srv_generator, application_capacity, platforms_capacity, 1);
     
        assertEquals(g.getPlatforms().size(), 100);
    }
    
   
    
}
