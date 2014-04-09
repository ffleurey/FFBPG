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
public interface IntegerGenerator {
    /**
     * Return a random integer between min and max (both inclusive)
     * @param min
     * @param max
     * @return 
     */
    public int getNextInteger(int min, int max);
}
