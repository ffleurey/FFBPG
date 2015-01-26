package eu.diversify.ffbpg.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ffl
 */
public class NegExpIntegerGenerator implements IntegerGenerator {

    protected double lambda = 1.0;
    
    public NegExpIntegerGenerator(double lambda) {
        this.lambda = lambda;
    }
    
    Random rand = new Random(System.currentTimeMillis());

    // Clever way of implementing the distribution found from:
    // http://math.stackexchange.com/questions/28004/random-exponential-like-distribution/28010#28010
    @Override
    public int getNextInteger(int min, int max) {
        double u = rand.nextDouble();
        double e = -Math.log(u) / lambda;
        e = e - (int)e; // Using the fractional part of e instead of a more costly acceptance-rejection method.
        int result = (int) (min + e * (max - min));
        return result;
    }
    
}
