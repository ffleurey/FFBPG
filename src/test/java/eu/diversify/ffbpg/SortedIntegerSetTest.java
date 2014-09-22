package eu.diversify.ffbpg;

import eu.diversify.ffbpg.collections.SortedIntegerSet;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple the platform generator.
 */
public class SortedIntegerSetTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SortedIntegerSetTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SortedIntegerSetTest.class );
    }

    public void testCreate1()
    {
        SortedIntegerSet l1 = new SortedIntegerSet();
        l1.add(5);
        l1.add(2);
        l1.add(3);
        l1.add(12);
        l1.add(12);
        l1.add(100);
        l1.add(50);
        l1.add(1);
        assertEquals(l1.size(), 7);
        assertEquals(l1.toString(), "{1, 2, 3, 5, 12, 50, 100}");
    }
    
    public void testCreate2()
    {
        SortedIntegerSet l1 = new SortedIntegerSet("{1, 2, 3, 5, 12, 50, 100}");
        assertEquals(l1.size(), 7);
        assertEquals(l1.toString(), "{1, 2, 3, 5, 12, 50, 100}");
    }
    
    public void testCreate3()
    {
        SortedIntegerSet l1 = new SortedIntegerSet("{5, 1, 2, 100, 3, 50, 12, 2, 2}");
        assertEquals(l1.size(), 7);
        assertEquals(l1.toString(), "{1, 2, 3, 5, 12, 50, 100}");
    }
    
    public void testClone()
    {
        SortedIntegerSet l1 = new SortedIntegerSet("{1, 2, 3, 5, 12, 50, 100}");
        SortedIntegerSet l2 = l1.clone();
        assertEquals(l1.size(), 7);
        assertEquals(l1.toString(), "{1, 2, 3, 5, 12, 50, 100}");
        assertEquals(l2.size(), 7);
        assertEquals(l2.toString(), "{1, 2, 3, 5, 12, 50, 100}");
        l2.add(600);
        l2.add(40);
        assertEquals(l1.size(), 7);
        assertEquals(l1.toString(), "{1, 2, 3, 5, 12, 50, 100}");
        assertEquals(l2.size(), 9);
        assertEquals(l2.toString(), "{1, 2, 3, 5, 12, 40, 50, 100, 600}");
    }
    
    public void testContainsAll1()
    {
        SortedIntegerSet l1 = new SortedIntegerSet("{1, 2, 3, 5, 12, 50, 100}");
        SortedIntegerSet l2 = l1.clone();
        l2.add(600);
        l2.add(40);
        l2.add(60);
        l2.add(0);
        assertTrue("l2 should contain itself", l2.containsAll(l2));
        assertTrue("l2 should contain l1", l2.containsAll(l1));
        assertFalse("l1 should not contain l2", l1.containsAll(l2));
    }
    
    public void testContainsAll2()
    {
        SortedIntegerSet l1 = new SortedIntegerSet("{1, 2, 3, 5, 12, 50, 100}");
        SortedIntegerSet l2 = new SortedIntegerSet("{}");
        assertTrue("l2 should contain itself", l2.containsAll(l2));
        assertTrue("l1 should contain l2", l1.containsAll(l2));
        assertFalse("l2 should not contain l1", l2.containsAll(l1));
    }
    
    public void testContainsAll3()
    {
        SortedIntegerSet l1 = new SortedIntegerSet("{1, 2, 3, 5, 12, 50, 100}");
        SortedIntegerSet l2 = new SortedIntegerSet("{12, 2}");
        assertTrue("l2 should contain itself", l2.containsAll(l2));
        assertTrue("l1 should contain l2", l1.containsAll(l2));
        assertFalse("l2 should not contain l1", l2.containsAll(l1));
    }
    
    public void testContainsAll4()
    {
        SortedIntegerSet l1 = new SortedIntegerSet("{1, 2, 3, 5, 12, 50, 100}");
        SortedIntegerSet l2 = new SortedIntegerSet("{1}");
        assertTrue("l2 should contain itself", l2.containsAll(l2));
        assertTrue("l1 should contain l2", l1.containsAll(l2));
        assertFalse("l2 should not contain l1", l2.containsAll(l1));
    }
    
    public void testContainsAll5()
    {
        SortedIntegerSet l1 = new SortedIntegerSet("{1, 2, 3, 5, 12, 50, 100}");
        SortedIntegerSet l2 = new SortedIntegerSet("{1, 2, 3, 5, 12, 50, 100, 500}");
        assertTrue("l2 should contain itself", l2.containsAll(l2));
        assertFalse("l1 should not contain l2", l1.containsAll(l2));
        assertTrue("l2 should contain l1", l2.containsAll(l1));
    }
    
    public void testContainsSome()
    {
        SortedIntegerSet l1 = new SortedIntegerSet("{1, 2, 3, 5, 12, 50, 100}");
        SortedIntegerSet l2 = l1.clone();
        l2.add(600);
        l2.add(40);
        l2.add(60);
        l2.add(0);
        SortedIntegerSet l3 = new SortedIntegerSet("{-10, 55, 500, 250}");
        SortedIntegerSet l4 = new SortedIntegerSet();
        assertTrue("l1 should contain some of itself", l1.containsSome(l1));
        assertTrue("l2 should contain some of itself", l2.containsSome(l2));
        assertTrue("l1 should contain some of l2", l1.containsSome(l2));
        assertTrue("l2 should contain some of l1", l2.containsSome(l1));
        assertFalse("l1 should not contain some of l3", l1.containsSome(l3));
        assertFalse("l2 should not contain some of l3", l2.containsSome(l3));
        assertFalse("l1 should not contain some of l4", l1.containsSome(l4));
        assertFalse("l2 should not contain some of l4", l2.containsSome(l4));
        assertFalse("l4 should not contain some of l4", l4.containsSome(l4));
        assertFalse("l4 should not contain some of l3", l4.containsSome(l3));
    }
  
     public void testIntersection()
     {
         SortedIntegerSet l1 = new SortedIntegerSet("{1, 2, 3, 5, 12, 50, 100}");
         SortedIntegerSet l2 = new SortedIntegerSet("{}");
         SortedIntegerSet l3 = new SortedIntegerSet("{1, 2, 3, 5, 12, 50, 100, 600, 1000}");
         SortedIntegerSet l4 = new SortedIntegerSet("{-10, 8, 12, 55}");
         SortedIntegerSet l5 = new SortedIntegerSet("{0, 14, 10, 550}");
         
         assertEquals(l1.intersection(l1).toString(), "{1, 2, 3, 5, 12, 50, 100}");
         assertEquals(l2.intersection(l2).toString(), "{}");
         assertEquals(l1.intersection(l2).toString(), "{}");
         assertEquals(l2.intersection(l1).toString(), "{}");
         assertEquals(l1.intersection(l3).toString(), "{1, 2, 3, 5, 12, 50, 100}");
         assertEquals(l3.intersection(l1).toString(), "{1, 2, 3, 5, 12, 50, 100}");
         assertEquals(l1.intersection(l4).toString(), "{12}");
         assertEquals(l4.intersection(l1).toString(), "{12}");
         assertEquals(l4.intersection(l5).toString(), "{}");
         assertEquals(l5.intersection(l4).toString(), "{}");
     }
     
     public void testUnion()
     {
         SortedIntegerSet l1 = new SortedIntegerSet("{}");
         SortedIntegerSet l2 = new SortedIntegerSet("{2, 4, 6}");
         SortedIntegerSet l3 = new SortedIntegerSet("{1, 3, 5, 7}");
         SortedIntegerSet l4 = new SortedIntegerSet("{1, 2, 3, 4, 5, 6, 7}");
         SortedIntegerSet l5 = new SortedIntegerSet("{4}");
         
         assertEquals(l1.union(l1).toString(), "{}");
         assertEquals(l2.union(l2).toString(), "{2, 4, 6}");
         assertEquals(l1.union(l2).toString(), "{2, 4, 6}");
         assertEquals(l2.union(l1).toString(), "{2, 4, 6}");
         assertEquals(l2.union(l3).toString(), "{1, 2, 3, 4, 5, 6, 7}");
         assertEquals(l3.union(l2).toString(), "{1, 2, 3, 4, 5, 6, 7}");
         assertEquals(l1.union(l4).toString(), "{1, 2, 3, 4, 5, 6, 7}");
         assertEquals(l4.union(l1).toString(), "{1, 2, 3, 4, 5, 6, 7}");
         assertEquals(l4.union(l5).toString(), "{1, 2, 3, 4, 5, 6, 7}");
         assertEquals(l5.union(l4).toString(), "{1, 2, 3, 4, 5, 6, 7}");
         assertEquals(l5.union(l2).toString(), "{2, 4, 6}");
         assertEquals(l2.union(l5).toString(), "{2, 4, 6}");
         assertEquals(l5.union(l3).toString(), "{1, 3, 4, 5, 7}");
         assertEquals(l3.union(l5).toString(), "{1, 3, 4, 5, 7}");
     }
    
}
