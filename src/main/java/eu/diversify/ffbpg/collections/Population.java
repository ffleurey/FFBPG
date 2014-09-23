package eu.diversify.ffbpg.collections;

/**
 *
 * @author ffl
 */
public class Population {
    
    int[] population;
    
    private double shannon = -1;
    private double equitability;
    private double mean = -1;
    private double median = -1;
    private int min;
    private int max;
    private String string = null;
    
    public Population(int[] pop) {
        population = pop;
    }
    
    public int[] getData() {
        return population;
    }
    
    public String toString() {
        if (string == null) {
            StringBuffer b = new StringBuffer();
            b.append('[');
            for (int i=0; i<population.length; i++) {
                b.append(population[i]);
                if (i < population.length-1) b.append(", ");
            }
            b.append(']');
            string = b.toString();
        }
        return string;
    }
    
    private void computeShannonIndex() {
        double result = 0;
        double sum = 0;
        for(int p : population) sum += p;
        double pi;
        for(int i = 0; i<population.length; i++) {
            pi = population[i] / sum;
            result -= pi * Math.log(pi);
        }
        shannon=result;
        if (population.length>1) equitability = result / Math.log(population.length);
        else equitability = result;
    }
    
    private void computeMedian(){
        SortedIntegerBag list = new SortedIntegerBag();
        for (int i=0; i<population.length; i++) {
            list.add(population[i]);
        }
        int med_index = population.length / 2;
        median = list.get(med_index);
        if (population.length % 2 == 0) { //  even number of values, need to average
            median += list.get(med_index-1);
            median /= 2;
        }
        min = list.get(0);
        max = list.get(list.size()-1);
    }
    
    
    public int getSpeciesCount() {
        return population.length;
    }
    
    public double getShannonIndex() {
        if(shannon<0) computeShannonIndex();
        return shannon;
    }
    
    public double getShannonEquitability() {
        if(shannon<0) computeShannonIndex();
        return equitability;
    }
    
    public double getPopulationMeanSize() {
        if (mean < 0) {
            double result = 0;
            for (int i=0; i<population.length; i++) {
                result += population[i];
            }
            mean = result / population.length;
        }
        return mean;
    }
    
    public double getPopulationMedianSize() {
        if (median<0) computeMedian();
        return median;
    }
    
    public int getPopulationMinSize() {
        if (median<0) computeMedian();
        return min;
    }
    
    public int getPopulationMaxSize() {
        if (median<0) computeMedian();
        return max;
    }
    
    
}
