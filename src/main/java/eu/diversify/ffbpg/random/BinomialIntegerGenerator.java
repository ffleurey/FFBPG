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
public class BinomialIntegerGenerator implements IntegerGenerator {

    private int n = 10;
    private double p = 3.0;
    
    public BinomialIntegerGenerator(int n, double p) {
        this.n = n;
        this.p = p;
    }
    
    
    public int getNextInteger(int min, int max) {
        int result;
        int attempts = 0;
        do {
            result = RandomUtils.getBinomial(n, p);
            attempts++;
            if (attempts > 5) { // 5 is arbitrary, this should not happen.
                System.err.println("BinomialIntegerGenerator: the specified min/max range does not match the specified distribution, returning min.");
                return min;
            }
        } while (result < min || result > max);
       
        return result;
    }
    
}
