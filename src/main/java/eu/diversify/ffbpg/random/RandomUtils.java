package eu.diversify.ffbpg.random;

import java.util.Random;

/**
 *
 * @author ffl
 */
public class RandomUtils {
    
    private static Random rand = new Random(System.currentTimeMillis());
    
    /**
     * Return a random integer between 0 and max inclusive
     * @param max
     * @return 
     */
    public static int getUniform(int max) {
        int result = rand.nextInt(max+1);
        return result;
    }
    
    /**
     * Get random integers with a Normal distribution (Gaussian)
     * @param mean
     * @param variance
     * @return 
     */
    public static int getGaussian(double mean, double variance) {
        int result = (int) (mean + rand.nextGaussian() * variance);
        return result;
    }

    /**
     * Get random integers with a Poisson distribution 
     * This is not an optimal implementation. It is O(lambda). 
     * There are some more complex constant time algorithms.
     * @param lambda
     * @return 
     */
    public static int getPoisson(double lambda) {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;

        do {
            k++;
            p *= rand.nextDouble();
        } while (p > L);

        return k - 1;
    }

    /**
     * Get random integers with a Binomial distribution 
     * This is not an optimal implementation. It is O(n). 
     * There are some more complex constant time algorithms.
     * @param n
     * @param p
     * @return 
     */
    public static int getBinomial(int n, double p) {
        int x = 0;
        for (int i = 0; i < n; i++) {
            if (rand.nextDouble() < p) {
                x++;
            }
        }
        return x;
    }
    
    /**
     * Get random integers with a Negative Exponential distribution 
     * @param lambda
     * @return 
     */
    public static int getNegExponential(double lambda) {
        int x = (int)(Math.log(1-rand.nextDouble()) / (-lambda));
        return x;
    }
    
}
