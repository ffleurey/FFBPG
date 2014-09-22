package eu.diversify.ffbpg;

import eu.diversify.ffbpg.collections.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple the platform generator.
 */
public class populationTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public populationTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( populationTest.class );
    }
    
    public void testAvgMed()
    {
        int[] pop = {9,1,1,3};
        Population p = new Population(pop);
        assertEquals(p.getPopulationMedianSize(), 2.0);
        assertEquals(p.getPopulationMeanSize(), 3.5);
    }
    
    public void testAvgMed1()
    {
        int[] pop = {9,1};
        Population p = new Population(pop);
        assertEquals(p.getPopulationMedianSize(), 5.0);
        assertEquals(p.getPopulationMeanSize(), 5.0);
    }

    public void testAvgMed2()
    {
        int[] pop = {9};
        Population p = new Population(pop);
        assertEquals(p.getPopulationMedianSize(), 9.0);
        assertEquals(p.getPopulationMeanSize(), 9.0);
    }
    
    public void testAvgMed3()
    {
        int[] pop = {9,1,1,3, 1, 1, 1};
        Population p = new Population(pop);
        assertEquals(p.getPopulationMedianSize(), 1.0);
        assertEquals(p.getPopulationMeanSize(), 17.0/7.0);
    }
    
}
