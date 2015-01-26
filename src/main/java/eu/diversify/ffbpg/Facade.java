
package eu.diversify.ffbpg;

import eu.diversify.ffbpg.collections.SortedIntegerSet;
import eu.diversify.ffbpg.random.GaussianIntegerGenerator;
import eu.diversify.ffbpg.random.IntegerGenerator;
import eu.diversify.ffbpg.random.IntegerSetGenerator;
import eu.diversify.ffbpg.random.NegExpIntegerGenerator;
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
    
    public static IntegerGenerator getPoissonIntegerGenerator(double lambda) {
        return new PoissonIntegerGenerator(lambda);
    }
    
    public static IntegerGenerator getNegExpIntegerGenerator(double lambda) {
        return new NegExpIntegerGenerator(lambda);
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
     * @param app_min_size Minimum size of the applications to generate (should probably be 1 in most cases)
     * @return 
     */
    public static BPGraph createBPgraph(int n_applications, int n_services, IntegerGenerator app_size_generator, IntegerSetGenerator service_sets_generator) {
        BPGraph g = null;
        g = new BPGraph(n_services);
        SortedIntegerSet[] ssets = g.getRandomGenerator().createRandomServiceSets(g.getServices(), n_applications, app_size_generator, service_sets_generator, 1);
        g.createGraphWithOnePlatformPerApplicationAndSingleLink(ssets,n_applications, n_applications);
        return g;
    }
    
    public static BPGraph createRandomBPGraph(int n_applications, int n_platforms, int n_services, IntegerGenerator node_size_generator, IntegerSetGenerator service_sets_generator, int applications_capacity, int platforms_capacity, int app_min_size) {
        BPGraph g = null;
        g = new BPGraph(n_services);
        SortedIntegerSet[] apps = g.getRandomGenerator().createRandomServiceSets(g.getServices(), n_applications, node_size_generator, service_sets_generator, app_min_size);
        SortedIntegerSet[] plats = g.getRandomGenerator().createRandomServiceSets(g.getServices(), n_platforms, node_size_generator, service_sets_generator, app_min_size);
        g.createRandomizedGraphWithoutLinks(apps, plats, applications_capacity, platforms_capacity);
        g.addRandomLinksToSatisfyDeps();
        g.purgeDeadApplications();
        return g;
    }
    
    
}
