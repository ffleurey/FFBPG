
package eu.diversify.ffbpg;

import eu.diversify.ffbpg.collections.SortedIntegerSet;
import eu.diversify.ffbpg.random.GaussianIntegerGenerator;
import eu.diversify.ffbpg.random.IntegerGenerator;
import eu.diversify.ffbpg.random.IntegerSetGenerator;
import eu.diversify.ffbpg.random.NegExpIntegerSetGenerator;
import eu.diversify.ffbpg.random.PoissonIntegerGenerator;
import eu.diversify.ffbpg.random.UniformIntegerGenerator;
import eu.diversify.ffbpg.random.UniformIntegerSetGenerator;

/**
 *
 * @author ffl
 */
public class Facade {
    
    public static IntegerGenerator getUniformIntegerGenerator() {
        return new UniformIntegerGenerator();
    }
    
    public static IntegerGenerator getPoiIntegerGenerator(double lambda) {
        return new PoissonIntegerGenerator(lambda);
    }
    
    public static IntegerGenerator getGaussianIntegerGenerator(double mean, double variance) {
        return new GaussianIntegerGenerator(mean, variance);   
    }
    
    public static IntegerSetGenerator getUniformIntegerSetGenerator() {
        return new UniformIntegerSetGenerator();
    }
    
    public static IntegerSetGenerator getNegExpIntegerSetGenerator(double lambda, double uniform) {
        return new NegExpIntegerSetGenerator(lambda, uniform);
    }
    
    /***
     * Generate a random bi-partite graph according to the given distribution
     * @param n_applications Number of applications and platforms (currently always the same)
     * @param n_services Total number of services in the world
     * @param app_size_generator Distribution of the applications and servers sizes (1 app always matched 1 server)
     * @param service_sets_generator Distribution to use in order to pick the services
     * @return 
     */
    public static BPGraph createBPgraph(int n_applications, int n_services, IntegerGenerator app_size_generator, IntegerSetGenerator service_sets_generator) {
        BPGraph g = null;
        g = new BPGraph(n_services);
        SortedIntegerSet[] ssets = g.getRandomGenerator().createRandomServiceSets(g.getServices(), n_applications, app_size_generator, service_sets_generator);
        g.createGraphWithOnePlatformPerApplicationAndSingleLink(ssets,n_applications, n_applications);
        return g;
    }
    
    
    
}
