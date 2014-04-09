package eu.diversify.ffbpg.random;

/**
 *
 * @author ffl
 */
public interface IntegerSetGenerator {
    /**
     * Generate a random integer set. 
     * Each integer in the sequence is unique.
     * Each integer is between 0 and max_value.
     * 
     * @param max_value The maximum value for the integers
     * @param size The size of the sequence to generate
     * @return The generated random sequence
     */
    int[] getRandomIntegerSet(int max_value, int size);
}
