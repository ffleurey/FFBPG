package eu.diversify.ffbpg;

import eu.diversify.ffbpg.evolution.ApplicationLinksEvolutionScenario;
import eu.diversify.ffbpg.evolution.BalanceApplicationLinksEvolutionScenario;
import eu.diversify.ffbpg.evolution.BalancePlatformServicesEvolutionScenario1;
import eu.diversify.ffbpg.evolution.InitializationEvolutionScenario;
import eu.diversify.ffbpg.evolution.PlatformServicesEvolutionScenario;
import eu.diversify.ffbpg.random.IntegerGenerator;
import eu.diversify.ffbpg.random.IntegerSetGenerator;
import java.util.ArrayList;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple the platform generator.
 */
public class EvolutionTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public EvolutionTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( EvolutionTest.class );
    }
    
    public void testCloning()
    {
        BPGraph g = generateRandomInitialGraph();
        int number_platforms = g.getPlatforms().size();
        int number_services = g.getServices().size();
        int number_applications = g.getApplications().size();
        
        BPGraph gen1 = g.deep_clone();
        
        
        assertEquals(gen1.getPlatforms().size(), number_platforms);
        assertEquals(gen1.getServices().size(), number_services);
        assertEquals(gen1.getApplications().size(), number_applications);
        
        assertFalse(gen1.getPlatforms().contains(g.getPlatforms().get(0)));
        assertFalse(gen1.getApplications().contains(g.getApplications().get(0)));        
        
        for (Application a : gen1.getApplications()) {
            assertTrue(a.dependenciesSatisfied());
        }
                
        BPGraph gen2 = gen1.deep_clone();
        
        assertEquals(gen2.getPlatforms().size(), number_platforms);
        assertEquals(gen2.getServices().size(), number_services);
        assertEquals(gen2.getApplications().size(), number_applications);
        
        assertFalse(gen2.getPlatforms().contains(g.getPlatforms().get(0)));
        assertFalse(gen2.getApplications().contains(g.getApplications().get(0)));  
        assertFalse(gen2.getPlatforms().contains(gen1.getPlatforms().get(0)));
        assertFalse(gen2.getApplications().contains(gen1.getApplications().get(0)));  
        
        for (Application a : gen2.getApplications()) {
            assertTrue(a.dependenciesSatisfied());
        }
        
    }
    
    public void testEvolutionRandom()
    {
        BPGraph g = generateRandomInitialGraph();
        
        ApplicationLinksEvolutionScenario link_s = ApplicationLinksEvolutionScenario.getScenarioByName("Random Remove, Random Add");
        PlatformServicesEvolutionScenario serv_s = PlatformServicesEvolutionScenario.getScenarioByName("Random Platform Service Evolution");
        InitializationEvolutionScenario init_s = InitializationEvolutionScenario.getScenarioByName("NONE");
        //BalanceApplicationLinksEvolutionScenario balance_links = new BalanceApplicationLinksEvolutionScenario();
        //BalancePlatformServicesEvolutionScenario1 balance_services = new BalancePlatformServicesEvolutionScenario1();
        
        int sim_steps = 50;
        int sim_steps_size = 1;
        
        BPGraph initial = g;
            
            // run the simulation;
            System.out.println("Starting simulations... ");
            ArrayList<BPGraph> results = new  ArrayList<BPGraph>();
            BPGraph current = initial;
            results.add(current);
            
            System.out.println("Running initialization step...");
            
            current = current.deep_clone(); // Create the new bp graph
            current.clearAllCachedData();
            init_s.step(current);
            results.add(current);
            
            for (int i = 0; i<sim_steps; i++) {
                System.out.println("Running Simuation step #" + i);
                current = current.deep_clone(); // Create the new bp graph
                current.setlabel("[Simulation step " + (i+1) + "]");
                for (int j=0; j<sim_steps_size; j++) {
                    
                    current.clearAllCachedData();
                    if (serv_s != null) serv_s.step(current);
                    current.clearAllCachedData();
                    if (link_s != null) link_s.step(current);
                   //current.clearAllCachedData();
                   //balance_services.step(current);
                    //current.clearAllCachedData();
                    //balance_links.step(current);
                }
                results.add(current);
                
                // Check that all apps are still alive
                for (Application a : current.getApplications()) {
                    assertTrue(a.dependenciesSatisfied());
                }
                
            }
            System.out.println("End of simualtion.");
         
        
        
    }
    
    public void testEvolutionSmartSimple()
    {
        BPGraph g = generateRandomInitialGraph();
        
        ApplicationLinksEvolutionScenario link_s = ApplicationLinksEvolutionScenario.getScenarioByName("Change To Better Equitability Links");
        PlatformServicesEvolutionScenario serv_s = PlatformServicesEvolutionScenario.getScenarioByName("Change To Popular Services");
        InitializationEvolutionScenario init_s = InitializationEvolutionScenario.getScenarioByName("NONE");
        BalanceApplicationLinksEvolutionScenario balance_links = new BalanceApplicationLinksEvolutionScenario();
        BalancePlatformServicesEvolutionScenario1 balance_services = new BalancePlatformServicesEvolutionScenario1();
        
        int sim_steps = 50;
        int sim_steps_size = 1;
        
        BPGraph initial = g;
            
            // run the simulation;
            System.out.println("Starting simulations... ");
            ArrayList<BPGraph> results = new  ArrayList<BPGraph>();
            BPGraph current = initial;
            results.add(current);
            
            System.out.println("Running initialization step...");
            
            current = current.deep_clone(); // Create the new bp graph
            current.clearAllCachedData();
            init_s.step(current);
            results.add(current);
            
            for (int i = 0; i<sim_steps; i++) {
                System.out.println("Running Simuation step #" + i);
                current = current.deep_clone(); // Create the new bp graph
                current.setlabel("[Simulation step " + (i+1) + "]");
                for (int j=0; j<sim_steps_size; j++) {
                    
                    current.clearAllCachedData();
                    if (serv_s != null) serv_s.step(current);
                    current.clearAllCachedData();
                    if (link_s != null) link_s.step(current);
                   current.clearAllCachedData();
                   balance_services.step(current);
                   current.clearAllCachedData();
                   balance_links.step(current);
                }
                results.add(current);
                
                // Check that all apps are still alive
                for (Application a : current.getApplications()) {
                    assertTrue(a.dependenciesSatisfied());
                }
                
            }
            System.out.println("End of simualtion.");
         
        
        
    }
    
    public BPGraph generateRandomInitialGraph()
    {
        int number_platforms = 100;
        int number_services = 50;
        int number_applications = 300;
        
        int platforms_capacity = 18;
        int application_capacity = number_services; // No Limit
        
        // Random distribution generators used both for apps and plats
        IntegerGenerator sizes_generator = Facade.getPoissonIntegerGenerator(6); 
        IntegerSetGenerator srv_generator = Facade.getNegExpIntegerSetGenerator(0.25, 0.005);
        
        
        BPGraph g = Facade.createRandomBPGraph(number_applications, number_platforms, number_services,
                    sizes_generator, srv_generator, application_capacity, platforms_capacity);
     
        assertEquals(g.getPlatforms().size(), 100);
        
        for (Application a : g.getApplications()) {
            assertTrue(a.dependenciesSatisfied());
        }
        
        return g;
    }
    
   
    
}
