package net.geant.autobahn.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.MinValueConstraint;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.Range;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.intradomain.common.Node;

import net.geant.autobahn.utils.IntraTopologyBuilder;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericLink;

public class CalendarTest {

    private Map<String, Node> nodes = null;

    private List<GenericLink> glinks = null;
    
    Properties props = new Properties();

    @Before
    public void setUp() {
              
        EthTopology1 topoSrc = new EthTopology1();
        IntraTopologyBuilder builder = new IntraTopologyBuilder(false);
        topoSrc.domain1(builder);
        
        IntradomainTopology topo = builder.getTopology();
        
        nodes = new HashMap<String, Node>();
        for(Node n : topo.getNodes()) {
            nodes.put(n.getName(), n);
        }

        glinks = new ArrayList<GenericLink>();
        for(GenericLink glink : topo.getGenericLinks()) {
            glinks.add(glink);
        }
        
    }
    
    @Test
    public void testaddReservationCapacity(){
        AccessPoint calendar = null;
        props.setProperty("db.type", "ethernet");
        props.setProperty("tool.time.setup", "60");
        props.setProperty("tool.time.teardown", "120");
        try {
            calendar = new AccessPoint(props);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Calendar start = Calendar.getInstance();
        
        Calendar end = Calendar.getInstance();
        System.out.println("Start at: " + start + "\n  End at: " + end);
        long capacity = 6;
        PathConstraints pcon = new PathConstraints();
        List<GenericLink> nonUsefulLinks = calendar.checkCapacity(glinks, capacity, start, end);
        TestCase.assertEquals(2,nonUsefulLinks.size());
        for (GenericLink link : nonUsefulLinks ){
            System.out.println("Links with no Capacity: " + link.getLinkId());
        }
        try {
            calendar.addReservation(glinks, capacity, pcon, start, end);
        } catch (ConstraintsAlreadyUsedException e) {
            System.out.println("Constraint problem!!!");
            e.printStackTrace();
        }
        nonUsefulLinks = calendar.checkCapacity(glinks, capacity, start, end);
        TestCase.assertEquals(7,nonUsefulLinks.size());
        
        for (GenericLink link : nonUsefulLinks ){
            System.out.println("Links with no Capacity: " + link.getLinkId());
        }
        try {
            calendar.addReservation(glinks, capacity, pcon, start, end);
        } catch (ConstraintsAlreadyUsedException e) {
            System.out.println("Constraint problem!!!");
            e.printStackTrace();
        }
        nonUsefulLinks = calendar.checkCapacity(glinks, capacity, start, end);
        TestCase.assertEquals(7,nonUsefulLinks.size());
        
        for (GenericLink link : nonUsefulLinks ){
            System.out.println("Links with no Capacity: " + link.getLinkId());
        }
    }
    
    @Test(expected=ConstraintsAlreadyUsedException.class)
    public void testaddReservationConstraints() throws ConstraintsAlreadyUsedException {
        AccessPoint calendar = null;
        props.setProperty("db.type", "ethernet");
        props.setProperty("tool.time.setup", "60");
        props.setProperty("tool.time.teardown", "120");
        try {
            calendar = new AccessPoint(props);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Calendar start = Calendar.getInstance();
        
        Calendar end = Calendar.getInstance();
        System.out.println("Start at: " + start + "\n  End at: " + end);
        long capacity = 6;
        PathConstraints pcon = new PathConstraints();
        List<RangeConstraint> vlansToBeReserved = new ArrayList<RangeConstraint>();
        
        RangeConstraint e1 = new RangeConstraint();
        Range newRange =  new Range();
        Integer min = 100;
        newRange.setRangeID(1121);
        newRange.setMin(min);
        Integer max = 0;
        newRange.setMax(max);
        e1.addRange(newRange);
        vlansToBeReserved.add(e1);
        
        ConstraintsNames conN =  ConstraintsNames.VLANS;
        MinValueConstraint minV = null;
        pcon.addMinValueConstraint(conN,minV);
        pcon.setRangeConstraints(vlansToBeReserved);
        ArrayList<ConstraintsNames> rangeNames = new ArrayList<ConstraintsNames>();
        rangeNames.add(conN);
        pcon.setRangeNames(rangeNames);
        List<GenericLink> nonUsefulLinks = calendar.checkCapacity(glinks, capacity, start, end);
        TestCase.assertEquals(2,nonUsefulLinks.size());
        for (GenericLink link : nonUsefulLinks ){
            System.out.println("Links with no Capacity: " + link.getLinkId());
        }
        
        try {
            calendar.addReservation(glinks, capacity, pcon, start, end);
        } catch (ConstraintsAlreadyUsedException e) {
            System.out.println("Constraint problem!!!");
            e.printStackTrace();
            TestCase.fail();
        }
        nonUsefulLinks = calendar.checkCapacity(glinks, capacity, start, end);
        TestCase.assertEquals(7,nonUsefulLinks.size());
        
        for (GenericLink link : nonUsefulLinks ){
            System.out.println("Links with no Capacity: " + link.getLinkId());
        }

        // Try an over-subscription
        calendar.addReservation(glinks, capacity, pcon, start, end);
    }

}