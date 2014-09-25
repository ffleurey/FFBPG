package eu.diversify.ffbpg;

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
    
   
    
}
