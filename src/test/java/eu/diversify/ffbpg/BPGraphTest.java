package eu.diversify.ffbpg;

import eu.diversify.ffbpg.ExtinctionSequence;
import eu.diversify.ffbpg.BPGraph;
import eu.diversify.ffbpg.collections.SortedIntegerCollection;
import eu.diversify.ffbpg.random.GaussianIntegerGenerator;
import eu.diversify.ffbpg.random.IntegerGenerator;
import eu.diversify.ffbpg.random.IntegerSetGenerator;
import eu.diversify.ffbpg.random.NegExpIntegerSetGenerator;
import eu.diversify.ffbpg.random.PoissonIntegerGenerator;
import eu.diversify.ffbpg.random.UniformIntegerGenerator;
import eu.diversify.ffbpg.random.UniformIntegerSetGenerator;
import java.io.File;
import java.util.ArrayList;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple the platform generator.
 */
public class BPGraphTest 
    extends TestCase
{
    
    File out_dir;
    int n_services = 50;
    int n_applications = 75;
    int n_run = 25;
    
    IntegerGenerator uniform_app_size_generator = new UniformIntegerGenerator();
    IntegerSetGenerator uniform_service_sets_generator = new UniformIntegerSetGenerator();
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BPGraphTest( String testName )
    {
        super( testName );
        out_dir = new File(new File("."), "BPGraphTest");
        if (out_dir.exists()) out_dir.delete();
        out_dir.mkdir();
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( BPGraphTest.class );
    }

    public void run_experiment(String name, IntegerGenerator app_size_generator, IntegerSetGenerator service_sets_generator, boolean all_links)
    {
        BPGraph g = null;
        int[][] apps_srv = new int[n_run][];
        int[][] apps_link = new int[n_run][];
        int[][] srv_dist = new int[n_run][];
        double[] robusness = new double[n_run];
        
        for(int i=0; i<n_run; i++) {
            g = new BPGraph(n_services);
            SortedIntegerCollection[] ssets = g.getRandomGenerator().createRandomServiceSets(g.getServices(), n_applications, app_size_generator, service_sets_generator);
            g.createGraphWithOnePlatformPerApplicationAndSingleLink(ssets);
            if (all_links) g.addLinksToAllPlatformsProvidingAtLeastOneSrv();
            apps_srv[i] = g.applicationsServicesCountsDistribution();
            apps_link[i] = g.applicationsLinksCountsDistribution();
            srv_dist[i] = g.servicesDistributionInApplications();
            
            ExtinctionSequence[] seqs = g.performRandomExtinctionSequences(n_run);
            robusness[i] = 100 * ExtinctionSequence.averageRobustnessIndex(ExtinctionSequence.averageExtinctionSequences(seqs));
        }
        
        BPGraph.writeGNUPlotScriptForData(srv_dist, out_dir, name + "_srv_dist");
        BPGraph.writeGNUPlotScriptForData(apps_link, out_dir, name + "_apps_link");
        BPGraph.writeGNUPlotScriptForData(apps_srv, out_dir, name + "_apps_srv");
        
        ExtinctionSequence.writeGNUPlotScriptForDouble(robusness, out_dir, name + "_robusness");
        
        ExtinctionSequence[] seqs = g.performRandomExtinctionSequences(n_run);
        ExtinctionSequence.averageRobustnessIndex(ExtinctionSequence.averageExtinctionSequences(seqs));
        ExtinctionSequence.writeGNUPlotScriptForAll(seqs, out_dir, name + "_extinctions");
        
    }
    
    public void testUniformUniformSL()
    {
        run_experiment("1_UniformUniformSL", uniform_app_size_generator, uniform_service_sets_generator, false);
    }
    
    public void testUniformUniformAL()
    {
        run_experiment("2_UniformUniformAL", uniform_app_size_generator, uniform_service_sets_generator, true);
    }
    
    public void testUniformNExp_2_1_AL()
    {
        run_experiment("3_UniformNExp_2_1_AL", uniform_app_size_generator, new NegExpIntegerSetGenerator(2.0, 0.01), true);
    }
    
    public void testGaussian_6_3_UniformAL()
    {
        run_experiment("4_Gaussian_6_3_UniformAL", new  GaussianIntegerGenerator(6.0, 3.0), uniform_service_sets_generator, true);
    }
    
    public void testGaussian_6_3_NExp_2_1_AL()
    {
        run_experiment("5_Gaussian_6_3_NExp_2_1_AL", new  GaussianIntegerGenerator(6.0, 3.0), new NegExpIntegerSetGenerator(2.0, 0.01), true);
    }
    
    public void testPoisson_6_NExp_2_1_AL()
    {
        run_experiment("6_Poisson_6_NExp_2_1_AL", new  PoissonIntegerGenerator(6.0), new NegExpIntegerSetGenerator(2.0, 0.01), true);
    }
   
}
