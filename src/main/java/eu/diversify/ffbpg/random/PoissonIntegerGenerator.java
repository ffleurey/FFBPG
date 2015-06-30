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
public class PoissonIntegerGenerator implements IntegerGenerator {

    private double lambda = 5.0;
    
    public String toString() {
        return "(Poisson l = " + lambda + ")";
    }
    
    public PoissonIntegerGenerator(double lambda) {
        this.lambda = lambda;
    }
    
    
    public int getNextInteger(int min, int max) {
        int result;
        int attempts = 0;
        do {
            result = RandomUtils.getPoisson(lambda);
            attempts++;
            if (attempts > 5) { // 5 is arbitrary, this should not happen.
                System.err.println("PoissonIntegerGenerator: the specified min/max range does not match the specified distribution, returning min.");
                return min;
            }
        } while (result < min || result > max);
        return result;
    }
    
}
