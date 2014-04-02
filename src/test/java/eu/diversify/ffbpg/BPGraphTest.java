package eu.diversify.ffbpg;

import eu.diversify.ffbpg.ExtinctionSequence;
import eu.diversify.ffbpg.BPGraph;
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
    int n_services = 10;
    int n_applications = 25;
    int n_run = 25;
    
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

    public void testBPGraphRandomSingleLinks()
    {
        BPGraph g = null;
        int[][] apps_srv = new int[n_run][];
        int[][] apps_link = new int[n_run][];
        int[][] srv_dist = new int[n_run][];
        for(int i=0; i<n_run; i++) {
            g = new BPGraph(n_services);
            g.createGraphWithOnePlatformPerApplicationAndSingleLink(g.getRandomGenerator().createRandomServiceSets(g.getServices(), n_applications));
            apps_srv[i] = g.applicationsServicesCountsDistribution();
            apps_link[i] = g.applicationsLinksCountsDistribution();
            srv_dist[i] = g.servicesDistributionInApplications();
        }
        
        BPGraph.writeGNUPlotScriptForData(srv_dist, out_dir, "RandomSingleLinks_srv_dist");
        BPGraph.writeGNUPlotScriptForData(apps_link, out_dir, "RandomSingleLinks_apps_link");
        BPGraph.writeGNUPlotScriptForData(apps_srv, out_dir, "RandomSingleLinks_apps_srv");
        
        ExtinctionSequence[] seqs = g.performRandomExtinctionSequences(n_run);
        ExtinctionSequence.writeGNUPlotScriptForAll(seqs, out_dir, "RandomSingleLinks_extinctions");
        
    }
    
    public void testBPGraphRandomAllLinks()
    {
        BPGraph g = null;
        int[][] apps_srv = new int[n_run][];
        int[][] apps_link = new int[n_run][];
        int[][] srv_dist = new int[n_run][];
        for(int i=0; i<n_run; i++) {
            g = new BPGraph(n_services);
            g.createGraphWithOnePlatformPerApplicationAndSingleLink(g.getRandomGenerator().createRandomServiceSets(g.getServices(), n_applications));
            g.addAllPotentialLinks();
            apps_srv[i] = g.applicationsServicesCountsDistribution();
            apps_link[i] = g.applicationsLinksCountsDistribution();
            srv_dist[i] = g.servicesDistributionInApplications();
        }
        
        BPGraph.writeGNUPlotScriptForData(srv_dist, out_dir, "RandomAllLinks_srv_dist");
        BPGraph.writeGNUPlotScriptForData(apps_link, out_dir, "RandomAllLinks_apps_link");
        BPGraph.writeGNUPlotScriptForData(apps_srv, out_dir, "RandomAllLinks_apps_srv");
        
        ExtinctionSequence[] seqs = g.performRandomExtinctionSequences(n_run);
        ExtinctionSequence.writeGNUPlotScriptForAll(seqs, out_dir, "RandomAllLinks_extinctions");
        
    }
    
    public void testBPGraphGaussianAllLinks()
    {
        BPGraph g = null;
        int[][] apps_srv = new int[n_run][];
        int[][] apps_link = new int[n_run][];
        int[][] srv_dist = new int[n_run][];
        for(int i=0; i<n_run; i++) {
            g = new BPGraph(n_services);
            g.createGraphWithOnePlatformPerApplicationAndSingleLink(g.getRandomGenerator().createGaussianPlatformTypes(g.getServices(), n_applications, 6.0, 3.0));
            g.addAllPotentialLinks();
            apps_srv[i] = g.applicationsServicesCountsDistribution();
            apps_link[i] = g.applicationsLinksCountsDistribution();
            srv_dist[i] = g.servicesDistributionInApplications();
        }
        
        BPGraph.writeGNUPlotScriptForData(srv_dist, out_dir, "GaussianAllLinks_srv_dist");
        BPGraph.writeGNUPlotScriptForData(apps_link, out_dir, "GaussianAllLinks_apps_link");
        BPGraph.writeGNUPlotScriptForData(apps_srv, out_dir, "GaussianAllLinks_apps_srv");
        
        ExtinctionSequence[] seqs = g.performRandomExtinctionSequences(n_run);
        ExtinctionSequence.writeGNUPlotScriptForAll(seqs, out_dir, "GaussianAllLinks_extinctions");
        
    }
    
    public void testBPGraphRandomMultiLinks()
    {
        BPGraph g = new BPGraph(100);
        g.createGraphWithOnePlatformPerApplicationAndSingleLink(g.getRandomGenerator().createRandomServiceSets(g.getServices(), 50));
        g.addAllPotentialLinks();
        g.performRandomExtinctionSequence();
    }
    
    public void testBPGraphGaussianSingleLinks()
    {
        BPGraph g = new BPGraph(100);
        g.createGraphWithOnePlatformPerApplicationAndSingleLink(g.getRandomGenerator().createGaussianPlatformTypes(g.getServices(), 50, 5.0, 2.0));
        g.performRandomExtinctionSequence();
    }
    
    public void testBPGraphGaussianMultiLinks()
    {
        BPGraph g = new BPGraph(100);
        g.createGraphWithOnePlatformPerApplicationAndSingleLink(g.getRandomGenerator().createGaussianPlatformTypes(g.getServices(), 50, 5.0, 2.0));
        g.addAllPotentialLinks();
        g.performRandomExtinctionSequence();
    }
    
    public void testBPGraphGaussianMultiLinksAvg()
    {
        BPGraph g = new BPGraph(100);
        g.createGraphWithOnePlatformPerApplicationAndSingleLink(g.getRandomGenerator().createGaussianPlatformTypes(g.getServices(), 50, 6.0, 3.0));
        g.addAllPotentialLinks();
        ExtinctionSequence[] seqs = g.performRandomExtinctionSequences(200);
        ExtinctionSequence.writeGNUPlotScriptForAll(seqs, out_dir, "testBPGraphGaussianMultiLinksAvg");
        
    }
    
   
}
