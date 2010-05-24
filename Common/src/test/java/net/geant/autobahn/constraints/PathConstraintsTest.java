/*
 * PathConstraintsTest.java
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
public class PathConstraintsTest {

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
     * Test method for {@link net.geant.autobahn.constraints.PathConstraints#addAdditiveConstraint(net.geant.autobahn.constraints.ConstraintsNames, net.geant.autobahn.constraints.AdditiveConstraint)}.
     */
    @Test
    public void testAddAdditiveConstraint() {
        PathConstraints p = new PathConstraints();
        p.addAdditiveConstraint(ConstraintsNames.VLANS, new AdditiveConstraint(10.0));
        // Override the previous constraint
        p.addAdditiveConstraint(ConstraintsNames.VLANS, new AdditiveConstraint(15.0));
        // Override the previous constraint
        p.addAdditiveConstraint(ConstraintsNames.VLANS, new AdditiveConstraint(25.0));

        p.addAdditiveConstraint(ConstraintsNames.TIMESLOTS, new AdditiveConstraint(1.0));
        // Override the previous constraint
        p.addAdditiveConstraint(ConstraintsNames.TIMESLOTS, new AdditiveConstraint(-4.0));
        
        TestCase.assertEquals(25.0, p.getAdditiveConstraint(ConstraintsNames.VLANS).getValue(), 0);
        TestCase.assertEquals(-4.0, p.getAdditiveConstraint(ConstraintsNames.TIMESLOTS).getValue(), 0);
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.PathConstraints#addBooleanConstraint(net.geant.autobahn.constraints.ConstraintsNames, net.geant.autobahn.constraints.BooleanConstraint)}.
     */
    @Test
    public void testAddBooleanConstraint() {
        PathConstraints p = new PathConstraints();
        p.addBooleanConstraint(ConstraintsNames.VLANS, new BooleanConstraint(true));
        // Override the previous constraint
        p.addBooleanConstraint(ConstraintsNames.VLANS, new BooleanConstraint(false));
        // Override the previous constraint
        p.addBooleanConstraint(ConstraintsNames.VLANS, new BooleanConstraint(true));

        p.addBooleanConstraint(ConstraintsNames.TIMESLOTS, new BooleanConstraint(false));
        // Override the previous constraint
        p.addBooleanConstraint(ConstraintsNames.TIMESLOTS, new BooleanConstraint(true));
        
        TestCase.assertEquals(true, p.getBooleanConstraint(ConstraintsNames.VLANS).getValue().booleanValue());
        TestCase.assertEquals(true, p.getBooleanConstraint(ConstraintsNames.TIMESLOTS).getValue().booleanValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.PathConstraints#addRangeConstraint(net.geant.autobahn.constraints.ConstraintsNames, net.geant.autobahn.constraints.RangeConstraint)}.
     */
    @Test
    public void testAddRangeConstraint() {
        PathConstraints p = new PathConstraints();
        p.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint());
        // Override the previous constraint
        p.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint("100-120"));
        // Override the previous constraint
        p.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint(100, 120));

        p.addRangeConstraint(ConstraintsNames.TIMESLOTS, new RangeConstraint());
        // Override the previous constraint
        p.addRangeConstraint(ConstraintsNames.TIMESLOTS, new RangeConstraint("1-50,40-49"));
        
        TestCase.assertEquals(100, p.getRangeConstraint(ConstraintsNames.VLANS).getFirstValue());
        TestCase.assertEquals(1, p.getRangeConstraint(ConstraintsNames.TIMESLOTS).getFirstValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.PathConstraints#addMinValueConstraint(net.geant.autobahn.constraints.ConstraintsNames, net.geant.autobahn.constraints.MinValueConstraint)}.
     */
    @Test
    public void testAddMinValueConstraint() {
        PathConstraints p = new PathConstraints();
        p.addMinValueConstraint(ConstraintsNames.VLANS, new MinValueConstraint(10.0));
        // Override the previous constraint
        p.addMinValueConstraint(ConstraintsNames.VLANS, new MinValueConstraint(15.0));
        // Override the previous constraint
        p.addMinValueConstraint(ConstraintsNames.VLANS, new MinValueConstraint(25.0));

        p.addMinValueConstraint(ConstraintsNames.TIMESLOTS, new MinValueConstraint(1.0));
        // Override the previous constraint
        p.addMinValueConstraint(ConstraintsNames.TIMESLOTS, new MinValueConstraint(-4.0));
        
        TestCase.assertEquals(25.0, p.getMinValueConstraint(ConstraintsNames.VLANS).getValue());
        TestCase.assertEquals(-4.0, p.getMinValueConstraint(ConstraintsNames.TIMESLOTS).getValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.PathConstraints#intersect(net.geant.autobahn.constraints.PathConstraints)}.
     */
    @Test
    public void testIntersect_Additive() {
        PathConstraints p1 = new PathConstraints();
        p1.addAdditiveConstraint(ConstraintsNames.VLANS, new AdditiveConstraint(10.0));

        PathConstraints p2 = new PathConstraints();
        p2.addAdditiveConstraint(ConstraintsNames.VLANS, new AdditiveConstraint(15.0));
        
        PathConstraints p3 = p1.intersect(p2);
        AdditiveConstraint ac = p3.getAdditiveConstraint(ConstraintsNames.VLANS);
        TestCase.assertEquals(25.0, ac.getValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.PathConstraints#intersect(net.geant.autobahn.constraints.PathConstraints)}.
     */
    @Test
    public void testIntersect_Additive_Negative() {
        PathConstraints p1 = new PathConstraints();
        p1.addAdditiveConstraint(ConstraintsNames.VLANS, new AdditiveConstraint(10.0));

        PathConstraints p2 = new PathConstraints();
        p2.addAdditiveConstraint(ConstraintsNames.VLANS, new AdditiveConstraint(-15.0));
        
        PathConstraints p3 = p1.intersect(p2);
        AdditiveConstraint ac = p3.getAdditiveConstraint(ConstraintsNames.VLANS);
        TestCase.assertEquals(-5.0, ac.getValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.PathConstraints#intersect(net.geant.autobahn.constraints.PathConstraints)}.
     */
    @Test
    public void testIntersect_Boolean_true() {
        PathConstraints p1 = new PathConstraints();
        p1.addBooleanConstraint(ConstraintsNames.VLANS, new BooleanConstraint(true));

        PathConstraints p2 = new PathConstraints();
        p2.addBooleanConstraint(ConstraintsNames.VLANS, new BooleanConstraint(true));
        
        PathConstraints p3 = p1.intersect(p2);
        BooleanConstraint bc = p3.getBooleanConstraint(ConstraintsNames.VLANS);
        TestCase.assertEquals(true, bc.getValue().booleanValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.PathConstraints#intersect(net.geant.autobahn.constraints.PathConstraints)}.
     */
    @Test
    public void testIntersect_Boolean_false() {
        PathConstraints p1 = new PathConstraints();
        p1.addBooleanConstraint(ConstraintsNames.VLANS, new BooleanConstraint(true));

        PathConstraints p2 = new PathConstraints();
        p2.addBooleanConstraint(ConstraintsNames.VLANS, new BooleanConstraint(false));
        
        PathConstraints p3 = p1.intersect(p2);
        BooleanConstraint bc = p3.getBooleanConstraint(ConstraintsNames.VLANS);
        TestCase.assertEquals(false, bc.getValue().booleanValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.PathConstraints#intersect(net.geant.autobahn.constraints.PathConstraints)}.
     */
    @Test
    public void testIntersect_Range() {
        PathConstraints p1 = new PathConstraints();
        p1.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint("100-110,120,200-210"));

        PathConstraints p2 = new PathConstraints();
        p2.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint(120,150));
        
        PathConstraints p3 = p1.intersect(p2);
        RangeConstraint rc = p3.getRangeConstraint(ConstraintsNames.VLANS);
        TestCase.assertEquals(120, rc.getFirstValue());
        List<Range> rlist = rc.getRanges();  
        TestCase.assertEquals(1, rlist.size());
        TestCase.assertEquals(120, rlist.get(0).getMin().intValue());
        TestCase.assertEquals(120, rlist.get(0).getMax().intValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.PathConstraints#intersect(net.geant.autobahn.constraints.PathConstraints)}.
     */
    @Test
    public void testIntersect_Range_Empty() {
        PathConstraints p1 = new PathConstraints();
        p1.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint("100-110,120,200-210"));

        PathConstraints p2 = new PathConstraints();
        p2.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint(121,150));
        
        PathConstraints p3 = p1.intersect(p2);
        TestCase.assertNull(p3);
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.PathConstraints#intersect(net.geant.autobahn.constraints.PathConstraints)}.
     */
    @Test
    public void testIntersect_MinValue() {
        PathConstraints p1 = new PathConstraints();
        p1.addMinValueConstraint(ConstraintsNames.VLANS, new MinValueConstraint(10.0));

        PathConstraints p2 = new PathConstraints();
        p2.addMinValueConstraint(ConstraintsNames.VLANS, new MinValueConstraint(15.0));
        
        PathConstraints p3 = p1.intersect(p2);
        MinValueConstraint mc = p3.getMinValueConstraint(ConstraintsNames.VLANS);
        TestCase.assertEquals(10.0, mc.getValue());
    }

    /**
     * Test method for {@link net.geant.autobahn.constraints.PathConstraints#intersect(net.geant.autobahn.constraints.PathConstraints)}.
     */
    @Test
    public void testIntersect_MinValue_Negative() {
        PathConstraints p1 = new PathConstraints();
        p1.addMinValueConstraint(ConstraintsNames.VLANS, new MinValueConstraint(10.0));

        PathConstraints p2 = new PathConstraints();
        p2.addMinValueConstraint(ConstraintsNames.VLANS, new MinValueConstraint(-15.0));
        
        PathConstraints p3 = p1.intersect(p2);
        MinValueConstraint mc = p3.getMinValueConstraint(ConstraintsNames.VLANS);
        TestCase.assertEquals(-15.0, mc.getValue());
    }

}
