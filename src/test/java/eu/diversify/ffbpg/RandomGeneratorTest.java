package eu.diversify.ffbpg;

import eu.diversify.ffbpg.Service;
import eu.diversify.ffbpg.RandomGenerator;
import eu.diversify.ffbpg.collections.SortedIntegerSet;
import eu.diversify.ffbpg.random.GaussianIntegerGenerator;
import eu.diversify.ffbpg.random.UniformIntegerGenerator;
import eu.diversify.ffbpg.random.UniformIntegerSetGenerator;
import java.util.ArrayList;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple the platform generator.
 */
public class RandomGeneratorTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RandomGeneratorTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RandomGeneratorTest.class );
    }

     
    public void testCreateServices()
    {
        int nb_srv = 50;
        RandomGenerator f = new RandomGenerator();
        ArrayList<Service> services = f.createServices(nb_srv);
        assertEquals(services.size(), nb_srv);
        for (int i=0; i<services.size(); i++)
            assertNotNull(services.get(i));
    }
    
    public void testCreateRandomPlatformTypes()
    {
        int nb_srv = 50;
        int nb_pt = 500;
        RandomGenerator f = new RandomGenerator();
        ArrayList<Service> services = f.createServices(nb_srv);
        assertEquals(services.size(), nb_srv);
        SortedIntegerSet[] species = f.createRandomServiceSets(services, nb_pt, new UniformIntegerGenerator(), new UniformIntegerSetGenerator());
       
        assertEquals(species.length, nb_pt);
        
        // basic check for the uniform distribution of nb of srv
        double avg_srv = 0;
        for (int i=0; i<species.length; i++) {
            avg_srv += species[i].size();
        }
        avg_srv/= species.length; // one average nb_srv/2 is expected here
        assertEquals(nb_srv/2.0, avg_srv, nb_srv/20.0); // allow for a 10% error
        
    }
    
    public void runCreateGaussianPlatformTypes(double mean, double variance)
    {
        int nb_srv = 100;
        int nb_pt = 1000;
        RandomGenerator f = new RandomGenerator();
        ArrayList<Service> services = f.createServices(nb_srv);
        assertEquals(services.size(), nb_srv);
       // SortedIntegerSet[] species = f.createGaussianServiceSet(services, nb_pt, mean, variance);
        SortedIntegerSet[] species = f.createRandomServiceSets(services, nb_pt, new GaussianIntegerGenerator(mean, variance), new UniformIntegerSetGenerator());
        assertEquals(species.length, nb_pt);
        
        // basic check for the uniform distribution of nb of srv
        double avg_srv = 0;
        for (int i=0; i<species.length; i++) {
            avg_srv += species[i].size();
        }
        avg_srv/= species.length;
        double var_srv = 0;
        for (int i=0; i<species.length; i++) {
            var_srv += (species[i].size() - avg_srv)*(species[i].size() - avg_srv);
        }
        var_srv/= species.length;
        var_srv = Math.sqrt(var_srv);
        
        assertEquals(mean, avg_srv, mean/100.0*15.0); // allow for a 15% error
        assertEquals(variance, var_srv, variance/100.0*10.0); // allow for a 10% error
        
    }
    
    public void testCreateGaussianPlatformTypes_10_2()
    {
        runCreateGaussianPlatformTypes(10.0, 2.0);
    }
    public void testCreateGaussianPlatformTypes_50_10()
    {
        runCreateGaussianPlatformTypes(50.0, 10.0);
    }
    public void testCreateGaussianPlatformTypes_5_1()
    {
        runCreateGaussianPlatformTypes(5.0, 1.0);
    }
    public void testCreateGaussianPlatformTypes_20_2()
    {
        runCreateGaussianPlatformTypes(20.0, 2.0);
    }
    public void testCreateGaussianPlatformTypes_6_3()
    {
        runCreateGaussianPlatformTypes(6.0, 3.0);
    }
}
