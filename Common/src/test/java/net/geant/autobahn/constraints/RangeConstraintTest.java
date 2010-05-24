/*
 * RangeConstraintTest.java
 *
 */
package net.geant.autobahn.constraints;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public class RangeConstraintTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.String)}.
     */
    @Test
    public void testRangeConstraintString() {
        RangeConstraint r = new RangeConstraint("100-110,120,130-140");
        TestCase.assertEquals(100, r.getFirstValue());
        List<Range> rlist = r.getRanges();  
        TestCase.assertEquals(3, rlist.size());
        TestCase.assertEquals(100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(110, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(120, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(120, rlist.get(1).getMax().intValue());
        TestCase.assertEquals(130, rlist.get(2).getMin().intValue());
        TestCase.assertEquals(140, rlist.get(2).getMax().intValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRangeConstraintString_WrongOrder() {
        RangeConstraint r = new RangeConstraint("200-110,120,130-140");
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRangeConstraintString_IllegalChars_Spaces() {
        RangeConstraint r = new RangeConstraint(" 100 - 110, 120 , 130 -140");
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRangeConstraintString_IllegalChars_InsteadOfDash() {
        RangeConstraint r = new RangeConstraint("200&&110,120,130-140");
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRangeConstraintString_IllegalChars_StartingComma() {
        RangeConstraint r = new RangeConstraint(",100-110,120,130-140");
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRangeConstraintString_IllegalChars_ManyCommas() {
        RangeConstraint r = new RangeConstraint("100-110,,120,130-140");
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRangeConstraintString_IllegalChars_InsteadOfNumber() {
        RangeConstraint r = new RangeConstraint("abc100-110,120,130-140");
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.String)}.
     */
    @Test
    public void testRangeConstraintString_IllegalChars_EndingComma() {
        RangeConstraint r = new RangeConstraint("100-110,120,130-140,");
        TestCase.assertEquals(100, r.getFirstValue());
        List<Range> rlist = r.getRanges();
        TestCase.assertEquals(3, rlist.size());
        TestCase.assertEquals(100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(110, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(120, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(120, rlist.get(1).getMax().intValue());
        TestCase.assertEquals(130, rlist.get(2).getMin().intValue());
        TestCase.assertEquals(140, rlist.get(2).getMax().intValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRangeConstraintString_IllegalChars_NoDash() {
        RangeConstraint r = new RangeConstraint("abcd");
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRangeConstraintString_IllegalChars_OnlyDash() {
        RangeConstraint r = new RangeConstraint("-");
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRangeConstraintString_IllegalChars_MissingMaxVal() {
        RangeConstraint r = new RangeConstraint("100-");
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRangeConstraintString_IllegalChars_MissingMinVal() {
        RangeConstraint r = new RangeConstraint("-100");
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRangeConstraintString_IllegalChars_TwoDashes() {
        RangeConstraint r = new RangeConstraint("100-110-120");
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.Integer, java.lang.Integer)}.
     */
    @Test
    public void testRangeConstraintIntegerInteger() {
        RangeConstraint r = new RangeConstraint(new Integer(100), new Integer(200));
        TestCase.assertEquals(100, r.getFirstValue());
        List<Range> rlist = r.getRanges();  
        TestCase.assertEquals(1, rlist.size());
        TestCase.assertEquals(100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(200, rlist.get(0).getMax().intValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.Integer, java.lang.Integer)}.
     */
    @Test
    public void testRangeConstraintIntegerInteger_Negative() {
        RangeConstraint r = new RangeConstraint(new Integer(-100), new Integer(200));
        TestCase.assertEquals(-100, r.getFirstValue());
        List<Range> rlist = r.getRanges();  
        TestCase.assertEquals(1, rlist.size());
        TestCase.assertEquals(-100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(200, rlist.get(0).getMax().intValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#RangeConstraint(java.lang.Integer, java.lang.Integer)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRangeConstraintIntegerInteger_WrongOrder() {
        RangeConstraint r = new RangeConstraint(new Integer(200), new Integer(100));
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#addRange(net.geant.autobahn.constraints.Range)}.
     */
    @Test
    public void testAddRange() {
        RangeConstraint r = new RangeConstraint("100-110,120,130-140");
        TestCase.assertEquals(100, r.getFirstValue());
        List<Range> rlist = r.getRanges();  
        TestCase.assertEquals(3, rlist.size());
        TestCase.assertEquals(100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(110, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(120, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(120, rlist.get(1).getMax().intValue());
        TestCase.assertEquals(130, rlist.get(2).getMin().intValue());
        TestCase.assertEquals(140, rlist.get(2).getMax().intValue());

        r.addRange(new Integer(112), new Integer(118));
        TestCase.assertEquals(100, r.getFirstValue());
        rlist = r.getRanges();  
        TestCase.assertEquals(4, rlist.size());
        TestCase.assertEquals(100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(110, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(112, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(118, rlist.get(1).getMax().intValue());
        TestCase.assertEquals(120, rlist.get(2).getMin().intValue());
        TestCase.assertEquals(120, rlist.get(2).getMax().intValue());
        TestCase.assertEquals(130, rlist.get(3).getMin().intValue());
        TestCase.assertEquals(140, rlist.get(3).getMax().intValue());

        r.addRange(new Range (new Integer(119), new Integer(131)));
        TestCase.assertEquals(100, r.getFirstValue());
        rlist = r.getRanges();
        TestCase.assertEquals(3, rlist.size());
        TestCase.assertEquals(100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(110, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(112, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(118, rlist.get(1).getMax().intValue());
        TestCase.assertEquals(119, rlist.get(2).getMin().intValue());
        TestCase.assertEquals(140, rlist.get(2).getMax().intValue());

        r.addRange(new Range (new Integer(118), new Integer(119)));
        TestCase.assertEquals(100, r.getFirstValue());
        rlist = r.getRanges();
        TestCase.assertEquals(2, rlist.size());
        TestCase.assertEquals(100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(110, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(112, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(140, rlist.get(1).getMax().intValue());
    }
    
    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#addRange(net.geant.autobahn.constraints.Range)}.
     */
    @Test
    public void testAddRange2() {
        RangeConstraint r = new RangeConstraint("100-110,102,98-99");
        TestCase.assertEquals(98, r.getFirstValue());
        List<Range> rlist = r.getRanges();  
        TestCase.assertEquals(2, rlist.size());
        TestCase.assertEquals(98, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(99, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(100, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(110, rlist.get(1).getMax().intValue());

        r.addRange(new Integer(1), new Integer(120));
        TestCase.assertEquals(1, r.getFirstValue());
        rlist = r.getRanges();  
        TestCase.assertEquals(1, rlist.size());
        TestCase.assertEquals(1, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(120, rlist.get(0).getMax().intValue());

        r.addRange(new Range (new Integer(119), new Integer(131)));
        TestCase.assertEquals(1, r.getFirstValue());
        rlist = r.getRanges();
        TestCase.assertEquals(1, rlist.size());
        TestCase.assertEquals(1, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(131, rlist.get(0).getMax().intValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#removeRange(java.lang.Integer, java.lang.Integer)}.
     */
    @Test
    public void testRemoveRange() {
        RangeConstraint r = new RangeConstraint("100-110,121-200");
        TestCase.assertEquals(100, r.getFirstValue());
        List<Range> rlist = r.getRanges();  
        TestCase.assertEquals(2, rlist.size());
        TestCase.assertEquals(100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(110, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(121, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(200, rlist.get(1).getMax().intValue());

        r.removeRange(new Integer(105), new Integer(108));
        rlist = r.getRanges();  
        TestCase.assertEquals(3, rlist.size());
        TestCase.assertEquals(100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(104, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(109, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(110, rlist.get(1).getMax().intValue());
        TestCase.assertEquals(121, rlist.get(2).getMin().intValue());
        TestCase.assertEquals(200, rlist.get(2).getMax().intValue());

        r.removeRange(new Integer(101), new Integer(109));
        rlist = r.getRanges();  
        TestCase.assertEquals(3, rlist.size());
        TestCase.assertEquals(100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(100, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(110, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(110, rlist.get(1).getMax().intValue());
        TestCase.assertEquals(121, rlist.get(2).getMin().intValue());
        TestCase.assertEquals(200, rlist.get(2).getMax().intValue());

        r.removeRange(new Integer(95), new Integer(199));
        rlist = r.getRanges();  
        TestCase.assertEquals(1, rlist.size());
        TestCase.assertEquals(200, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(200, rlist.get(0).getMax().intValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#removeRange(java.lang.Integer, java.lang.Integer)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRemoveRange_Invalid() {
        RangeConstraint r = new RangeConstraint("100-110,121-200");
        TestCase.assertEquals(100, r.getFirstValue());
        List<Range> rlist = r.getRanges();  
        TestCase.assertEquals(2, rlist.size());
        TestCase.assertEquals(100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(110, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(121, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(200, rlist.get(1).getMax().intValue());

        r.removeRange(new Integer(200), new Integer(108));
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#removeRange(java.lang.Integer, java.lang.Integer)}.
     */
    @Test
    public void testRemoveRange_Unnecessary() {
        RangeConstraint r = new RangeConstraint("100-110,121-200");
        TestCase.assertEquals(100, r.getFirstValue());
        List<Range> rlist = r.getRanges();  
        TestCase.assertEquals(2, rlist.size());
        TestCase.assertEquals(100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(110, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(121, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(200, rlist.get(1).getMax().intValue());

        r.removeRange(new Integer(1), new Integer(50));
        rlist = r.getRanges();  
        TestCase.assertEquals(2, rlist.size());
        TestCase.assertEquals(100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(110, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(121, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(200, rlist.get(1).getMax().intValue());

        r.removeRange(new Integer(201), new Integer(1000));
        rlist = r.getRanges();  
        TestCase.assertEquals(2, rlist.size());
        TestCase.assertEquals(100, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(110, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(121, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(200, rlist.get(1).getMax().intValue());

        r.removeRange(new Integer(-4), new Integer(400));
        rlist = r.getRanges();
        TestCase.assertEquals(0, rlist.size());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#intersect(net.geant.autobahn.constraints.RangeConstraint)}.
     */
    @Test
    public void testIntersect() {
        RangeConstraint r1 = new RangeConstraint("1-25,121-200");
        RangeConstraint r2 = new RangeConstraint("50-99,130-180");
        RangeConstraint r3 = r1.intersect(r2);

        TestCase.assertEquals(130, r3.getFirstValue());
        List<Range> rlist = r3.getRanges();  
        TestCase.assertEquals(1, rlist.size());
        TestCase.assertEquals(130, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(180, rlist.get(0).getMax().intValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#intersect(net.geant.autobahn.constraints.RangeConstraint)}.
     */
    @Test
    public void testIntersect_SubSet() {
        RangeConstraint r1 = new RangeConstraint("1-25,121-200");
        RangeConstraint r2 = new RangeConstraint("1-2,4-24,122,123,124,126-129,130-180");
        RangeConstraint r3 = r1.intersect(r2);

        TestCase.assertEquals(1, r3.getFirstValue());
        List<Range> rlist = r3.getRanges();  
        TestCase.assertEquals(4, rlist.size());
        TestCase.assertEquals(1, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(2, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(4, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(24, rlist.get(1).getMax().intValue());
        TestCase.assertEquals(122, rlist.get(2).getMin().intValue());
        TestCase.assertEquals(124, rlist.get(2).getMax().intValue());
        TestCase.assertEquals(126, rlist.get(3).getMin().intValue());
        TestCase.assertEquals(180, rlist.get(3).getMax().intValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#intersect(net.geant.autobahn.constraints.RangeConstraint)}.
     */
    @Test
    public void testIntersect_Empty() {
        RangeConstraint r1 = new RangeConstraint("1-25,121-200");
        RangeConstraint r2 = new RangeConstraint("50-99,230-280");
        
        TestCase.assertNull(r1.intersect(r2));
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#difference(net.geant.autobahn.constraints.RangeConstraint)}.
     */
    @Test
    public void testDifference() {
        RangeConstraint r1 = new RangeConstraint("1-25,121-200");
        RangeConstraint r2 = new RangeConstraint("23-25,25,121-199");
        RangeConstraint r3 = r1.difference(r2);

        TestCase.assertEquals(1, r3.getFirstValue());
        List<Range> rlist = r3.getRanges();  
        TestCase.assertEquals(2, rlist.size());
        TestCase.assertEquals(1, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(22, rlist.get(0).getMax().intValue());
        TestCase.assertEquals(200, rlist.get(1).getMin().intValue());
        TestCase.assertEquals(200, rlist.get(1).getMax().intValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#contains(net.geant.autobahn.constraints.RangeConstraint)}.
     */
    @Test
    public void testContains() {
        RangeConstraint r1 = new RangeConstraint("1-25,121-200");
        RangeConstraint r2 = new RangeConstraint("23-25,25,121-199");
        
        TestCase.assertEquals(true, r1.contains(r2));
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.RangeConstraint#contains(net.geant.autobahn.constraints.RangeConstraint)}.
     */
    @Test
    public void testContains_Not() {
        RangeConstraint r1 = new RangeConstraint("1-25,121-198");
        RangeConstraint r2 = new RangeConstraint("23-25,25,121-199");
        
        TestCase.assertEquals(false, r1.contains(r2));
    }

}
