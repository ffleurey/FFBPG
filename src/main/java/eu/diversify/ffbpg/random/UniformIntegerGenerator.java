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
public class UniformIntegerGenerator implements IntegerGenerator {

    public int getNextInteger(int min, int max) {
        return RandomUtils.getUniform(max-min) + min;
    }
    
}
