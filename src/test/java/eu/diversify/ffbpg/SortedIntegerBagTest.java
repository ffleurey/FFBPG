package eu.diversify.ffbpg;

import eu.diversify.ffbpg.collections.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple the platform generator.
 */
public class SortedIntegerBagTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SortedIntegerBagTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SortedIntegerBagTest.class );
    }

    public void testCreate1()
    {
        SortedIntegerBag l1 = new SortedIntegerBag();
        l1.add(5);
        l1.add(2);
        l1.add(3);
        l1.add(12);
        l1.add(12);
        l1.add(100);
        l1.add(50);
        l1.add(1);
        assertEquals(l1.size(), 8);
        assertEquals(l1.toString(), "{1, 2, 3, 5, 12, 12, 50, 100}");
    }
    
    public void testCreate2()
    {
        SortedIntegerBag l1 = new SortedIntegerBag("{1, 1, 2, 3, 5, 12, 12, 12, 50, 100, 100}");
        assertEquals(l1.size(), 11);
        assertEquals(l1.toString(), "{1, 1, 2, 3, 5, 12, 12, 12, 50, 100, 100}");
    }
    
    public void testCreate3()
    {
        SortedIntegerBag l1 = new SortedIntegerBag("{5, 1, 2, 100, 3, 50, 12, 2, 2}");
        assertEquals(l1.size(), 9);
        assertEquals(l1.toString(), "{1, 2, 2, 2, 3, 5, 12, 50, 100}");
    }
    
     public void testCreate4()
    {
        SortedIntegerBag l1 = new SortedIntegerBag("{5, 1, 1, 2, 100, 3, 50, 1, 12, 2, 2, 100}");
        assertEquals(l1.size(), 12);
        assertEquals(l1.toString(), "{1, 1, 1, 2, 2, 2, 3, 5, 12, 50, 100, 100}");
    }
    
}
