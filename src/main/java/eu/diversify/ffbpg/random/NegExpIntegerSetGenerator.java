package eu.diversify.ffbpg.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ffl
 */
public class NegExpIntegerSetGenerator implements IntegerSetGenerator {

    protected double lambda = 1.0;
    protected double uniform = 0.0;
    
    public NegExpIntegerSetGenerator(double lambda, double uniform) {
        assert (uniform >= 0.0 && uniform <= 1.0);
        this.lambda = lambda;
        this.uniform = uniform;
    }
    
    Random rand = new Random(System.currentTimeMillis());
    
    private class Pair {
        Pair(int i, double p) {
            index = i;
            proba = p;
        }
        int index;
        double proba;
    }
    
    public int[] getRandomIntegerSet(int max_value, int size) {
        assert size <= max_value; // otherwize there won't be enough values
        
        List<Pair> dataList = new ArrayList<Pair>();
        double total = 0;
        for (int i = 0; i < max_value; i++) {
            
            double x = i;//(i+0.5)/(max_value+1.0);
            double p = lambda * Math.exp(-lambda*x);
            
            p = p * (1.0 - uniform) + uniform; // combine the exp distribution with a uniform component
            
            //System.out.print(" " + p);
            dataList.add(new Pair(i, p));
            total += p;
        }
        //System.out.println("\ntotal = " + total);
        int[] num = new int[size];
        
        for (int i = 0; i < size; i++) {
            double r = rand.nextDouble() * total;
            double cumul = 0;
            int j=0;
            do {
                cumul +=  dataList.get(j).proba;
                j++;
            } while(r > cumul && j<dataList.size());
            num[i] = dataList.get(j-1).index;
            total -= dataList.get(j-1).proba;
            dataList.remove(j-1);
        }
        
        return num;
    }
    
}
