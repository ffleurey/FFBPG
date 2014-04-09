package eu.diversify.ffbpg.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ffl
 */
public class UniformIntegerSetGenerator implements IntegerSetGenerator {

    Random rand = new Random(System.currentTimeMillis());
    
    public int[] getRandomIntegerSet(int max_value, int size) {
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
    
}
