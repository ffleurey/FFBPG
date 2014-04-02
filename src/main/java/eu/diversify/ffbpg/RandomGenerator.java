package eu.diversify.ffbpg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Franck Fleurey
 */
public class RandomGenerator {
    
   
    /**************************************************************************
    * Randomizations and statistical distributions  
    *************************************************************************/
    Random rand = new Random(System.currentTimeMillis());
    
    int[] random_integer_seq_uniform(int max_value, int size) {
        assert size <= max_value; // otherwize there won't be enough values
        
        List<Integer> dataList = new ArrayList<Integer>();
        for (int i = 0; i < max_value; i++) {
            dataList.add(i);
        }
        Collections.shuffle(dataList, rand);
        int[] num = new int[dataList.size()];
        for (int i = 0; i < size; i++) {
            num[i] = dataList.get(i);
        }
        return num;
    }
    
    int random_integer_gaussian(int min, int max, double mean, double variance) {
        int result = (int) (mean + rand.nextGaussian()*variance);
        if (result < min) result = min;
        if (result >= max) result = max-1;
        return result;
    }
    
    /**************************************************************************
    * Factory to create pseudo-random applications, platforms and services
    *************************************************************************/
    public ArrayList<Service> createServices(int n_services) {
        ArrayList<Service> services = new ArrayList<Service>();
        for (int i=0; i<n_services; i++) {
            services.add(new Service("S" + i));
        }
        return services;
    }
    
    public SortedIntegerCollection[] createRandomServiceSets(ArrayList<Service> services, int n_platform) {
        SortedIntegerCollection[] result = new SortedIntegerCollection[n_platform];
        for (int i=0; i<n_platform; i++) {
            result[i] = new SortedIntegerCollection();
            // select the services for this platform
            int n_services = rand.nextInt(services.size()-1)+1;
            int[] ridx = random_integer_seq_uniform(services.size(), n_services);
            for (int j=0; j<n_services; j++) {
                result[i].add(ridx[j]);
            }
        }
        return result;
    }
    
    public SortedIntegerCollection[] createGaussianPlatformTypes(ArrayList<Service> services, int n_platform, double mean_services, double variance_services) {
        if (services == null) throw new Error("ERROR: Services not initialized");
        SortedIntegerCollection[] result = new SortedIntegerCollection[n_platform];
        for (int i=0; i<n_platform; i++) {
            result[i] = new SortedIntegerCollection();
            // select the services for this platform
            int n_services = random_integer_gaussian(1, services.size(), mean_services, variance_services);
            int[] ridx = random_integer_seq_uniform(services.size(), n_services);
            for (int j=0; j<n_services; j++) {
                result[i].add(ridx[j]);
            }
        }
        return result;
    }
}
