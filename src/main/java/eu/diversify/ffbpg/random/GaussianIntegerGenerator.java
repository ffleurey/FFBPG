/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg.random;

/**
 *
 * @author ffl
 */
public class GaussianIntegerGenerator implements IntegerGenerator {

    private double mean = 10.0;
    private double variance = 3.0;
    
    public GaussianIntegerGenerator(double mean, double variance) {
        this.mean = mean;
        this.variance = variance;
    }
    
    
    public int getNextInteger(int min, int max) {
        int result;
        int attempts = 0;
        do {
            result = RandomUtils.getGaussian(mean, variance);
            attempts++;
            if (attempts > 5) { // 5 is arbitrary, this should not happen.
                System.err.println("GaussianIntegerGenerator: the specified min/max range does not match the specified distribution, returning min.");
                return min;
            }
        } while (result < min || result > max);
        return result;
    }
    
}
