/*
 * CalendarEntryTest.java
 *
 * 30 May 2010
 */
package net.geant.autobahn.calendar;

import java.util.GregorianCalendar;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.icu.util.Calendar;

/**
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public class CalendarEntryTest {

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
     * Test method for {@link net.geant.autobahn.calendar.CalendarEntry#overlaps(net.geant.autobahn.calendar.CalendarEntry)}.
     */
    @Test
    public void testOverlaps() {
        System.out.println(" ---Running testOverlaps");
        CalendarEntry ce1 = new CalendarEntry(
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00), 
                100);
        
        CalendarEntry ce2 = new CalendarEntry(
                new GregorianCalendar(2010, Calendar.FEBRUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.FEBRUARY, 03, 22, 00, 00), 
                100);
        
        TestCase.assertEquals(true, ce1.overlaps(ce2));
    }

    /**
     * Test method for {@link net.geant.autobahn.calendar.CalendarEntry#overlaps(net.geant.autobahn.calendar.CalendarEntry)}.
     */
    @Test
    public void testOverlaps2() {
        System.out.println(" ---Running testOverlaps2");
        CalendarEntry ce1 = new CalendarEntry(
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00), 
                100);
        
        CalendarEntry ce2 = new CalendarEntry(
                new GregorianCalendar(2010, Calendar.FEBRUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.APRIL, 01, 22, 00, 00), 
                100);
        
        TestCase.assertEquals(true, ce1.overlaps(ce2));
    }

    /**
     * Test method for {@link net.geant.autobahn.calendar.CalendarEntry#overlaps(net.geant.autobahn.calendar.CalendarEntry)}.
     */
    @Test
    public void testOverlaps3() {
        System.out.println(" ---Running testOverlaps3");
        CalendarEntry ce1 = new CalendarEntry(
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00), 
                100);
        
        CalendarEntry ce2 = new CalendarEntry(
                new GregorianCalendar(2009, Calendar.FEBRUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2011, Calendar.APRIL, 01, 22, 00, 00), 
                100);
        
        TestCase.assertEquals(true, ce1.overlaps(ce2));
    }

    /**
     * Test method for {@link net.geant.autobahn.calendar.CalendarEntry#overlaps(net.geant.autobahn.calendar.CalendarEntry)}.
     */
    @Test
    public void testOverlaps_differentVlan() {
        System.out.println(" ---Running testOverlaps_differentVlan");
        CalendarEntry ce1 = new CalendarEntry(
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00), 
                100);
        
        CalendarEntry ce2 = new CalendarEntry(
                new GregorianCalendar(2009, Calendar.FEBRUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2011, Calendar.APRIL, 01, 22, 00, 00), 
                200);
        
        TestCase.assertEquals(true, ce1.overlaps(ce2));
    }

    /**
     * Test method for {@link net.geant.autobahn.calendar.CalendarEntry#overlaps(net.geant.autobahn.calendar.CalendarEntry)}.
     */
    @Test
    public void testOverlaps_Barely() {
        System.out.println(" ---Running testOverlaps_Barely");
        CalendarEntry ce1 = new CalendarEntry(
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00), 
                100);
        
        CalendarEntry ce2 = new CalendarEntry(
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.APRIL, 01, 22, 00, 00), 
                100);
        
        TestCase.assertEquals(true, ce1.overlaps(ce2));
    }
    
    /**
     * Test method for {@link net.geant.autobahn.calendar.CalendarEntry#overlaps(net.geant.autobahn.calendar.CalendarEntry)}.
     */
    @Test
    public void testOverlaps_Not() {
        System.out.println(" ---Running testOverlaps_Not");
        CalendarEntry ce1 = new CalendarEntry(
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00), 
                100);
        
        CalendarEntry ce2 = new CalendarEntry(
                new GregorianCalendar(2011, Calendar.FEBRUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2011, Calendar.FEBRUARY, 03, 22, 00, 00), 
                100);
        
        TestCase.assertEquals(false, ce1.overlaps(ce2));
    }

    /**
     * Test method for {@link net.geant.autobahn.calendar.CalendarEntry#overlaps(net.geant.autobahn.calendar.CalendarEntry)}.
     */
    @Test
    public void testOverlaps_BarelyNot() {
        System.out.println(" ---Running testOverlaps_BarelyNot");
        CalendarEntry ce1 = new CalendarEntry(
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00), 
                100);
        
        CalendarEntry ce2 = new CalendarEntry(
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 01), 
                new GregorianCalendar(2010, Calendar.APRIL, 01, 22, 00, 00), 
                100);
        
        TestCase.assertEquals(false, ce1.overlaps(ce2));
    }
    
}
