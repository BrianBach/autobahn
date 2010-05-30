package net.geant.autobahn.calendar;

import java.util.GregorianCalendar;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.icu.util.Calendar;

public class AdditiveCalendarTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddReservation() {
        System.out.println(" ---Running testAddReservation");

        AdditiveCalendar ac = new AdditiveCalendar();
        
        ac.addReservation(100,
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00));
        
        ac.addReservation(100,
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00));

        ac.addReservation(100,
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00));

        ac.addReservation(100,
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00));
        
        long mu = ac.getMaxUsage(
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00),
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00));
        TestCase.assertEquals(400, mu);
        
        mu = ac.getMaxUsage(
                new GregorianCalendar(2010, Calendar.FEBRUARY, 01, 22, 00, 00),
                new GregorianCalendar(2010, Calendar.FEBRUARY, 02, 22, 00, 00));
        TestCase.assertEquals(400, mu);
        
        mu = ac.getMaxUsage(
                new GregorianCalendar(2011, Calendar.FEBRUARY, 01, 22, 00, 00),
                new GregorianCalendar(2011, Calendar.FEBRUARY, 02, 22, 00, 00));
        TestCase.assertEquals(0, mu);
        
        mu = ac.getMaxUsage(
                new GregorianCalendar(2009, Calendar.FEBRUARY, 01, 22, 00, 00),
                new GregorianCalendar(2011, Calendar.FEBRUARY, 02, 22, 00, 00));
        TestCase.assertEquals(400, mu);
    }

    @Test
    public void testAddReservation2() {
        System.out.println(" ---Running testAddReservation2");

        AdditiveCalendar ac = new AdditiveCalendar();
        
        ac.addReservation(100,
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00));
        
        ac.addReservation(100,
                new GregorianCalendar(2010, Calendar.JANUARY, 02, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.FEBRUARY, 01, 22, 00, 00));

        ac.addReservation(2000,
                new GregorianCalendar(2010, Calendar.JANUARY, 04, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.JANUARY, 04, 22, 00, 01));

        long mu = ac.getMaxUsage(
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00),
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00));
        TestCase.assertEquals(2200, mu);
        
        mu = ac.getMaxUsage(
                new GregorianCalendar(2010, Calendar.FEBRUARY, 01, 22, 00, 00),
                new GregorianCalendar(2010, Calendar.FEBRUARY, 02, 22, 00, 00));
        TestCase.assertEquals(100, mu);
        
        mu = ac.getMaxUsage(
                new GregorianCalendar(2010, Calendar.JANUARY, 05, 22, 00, 00),
                new GregorianCalendar(2011, Calendar.FEBRUARY, 02, 22, 00, 00));
        TestCase.assertEquals(200, mu);
    }

    @Test
    public void testAddRemoveReservation() {
        System.out.println(" ---Running testAddRemoveReservation");

        AdditiveCalendar ac = new AdditiveCalendar();
        
        ac.addReservation(100,
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00));
        
        ac.removeReservation(100,
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00));
        
        ac.removeReservation(100,
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00));

        long mu = ac.getMaxUsage(
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00),
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00));
        TestCase.assertEquals(0, mu);
        
        mu = ac.getMaxUsage(
                new GregorianCalendar(2010, Calendar.FEBRUARY, 01, 22, 00, 00),
                new GregorianCalendar(2010, Calendar.FEBRUARY, 02, 22, 00, 00));
        TestCase.assertEquals(0, mu);
        
        mu = ac.getMaxUsage(
                new GregorianCalendar(2011, Calendar.FEBRUARY, 01, 22, 00, 00),
                new GregorianCalendar(2011, Calendar.FEBRUARY, 02, 22, 00, 00));
        TestCase.assertEquals(0, mu);
        
        mu = ac.getMaxUsage(
                new GregorianCalendar(2009, Calendar.FEBRUARY, 01, 22, 00, 00),
                new GregorianCalendar(2011, Calendar.FEBRUARY, 02, 22, 00, 00));
        TestCase.assertEquals(0, mu);
    }

    @Test
    public void testRemoveReservation() {
        System.out.println(" ---Running testRemoveReservation");

        AdditiveCalendar ac = new AdditiveCalendar();
        
        ac.removeReservation(100,
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00));
        
        ac.removeReservation(100,
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00), 
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00));

        long mu = ac.getMaxUsage(
                new GregorianCalendar(2010, Calendar.JANUARY, 01, 22, 00, 00),
                new GregorianCalendar(2010, Calendar.MARCH, 01, 22, 00, 00));
        TestCase.assertEquals(0, mu);
        
        mu = ac.getMaxUsage(
                new GregorianCalendar(2010, Calendar.FEBRUARY, 01, 22, 00, 00),
                new GregorianCalendar(2010, Calendar.FEBRUARY, 02, 22, 00, 00));
        TestCase.assertEquals(0, mu);
        
        mu = ac.getMaxUsage(
                new GregorianCalendar(2011, Calendar.FEBRUARY, 01, 22, 00, 00),
                new GregorianCalendar(2011, Calendar.FEBRUARY, 02, 22, 00, 00));
        TestCase.assertEquals(0, mu);
        
        mu = ac.getMaxUsage(
                new GregorianCalendar(2009, Calendar.FEBRUARY, 01, 22, 00, 00),
                new GregorianCalendar(2011, Calendar.FEBRUARY, 02, 22, 00, 00));
        TestCase.assertEquals(0, mu);
    }

}
