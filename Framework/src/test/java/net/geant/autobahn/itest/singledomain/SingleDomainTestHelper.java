package net.geant.autobahn.itest.singledomain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.idm2dm.OversubscribedException;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.ResourcesReservation;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.ethernet.SpanningTree;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.reservation.ReservationParams;
import net.geant.autobahn.utils.IntraTopologyBuilder;

public class SingleDomainTestHelper {
    public final static long _1Mb = (long) 1000 * 1000;
    public final static long _1Gb = (long) 1 * 1000 * 1000 * 1000;
    public final static long _10Gb = (long) 10 * 1000 * 1000 * 1000;
    
    
    // -------------------- HELPERS -----------------
    public static DomainConstraints[] check(ResourcesReservation dm, Link[] links, long capacity, Calendar start,
            Calendar end) throws OversubscribedException, AAIException {
        
        ReservationParams params = new ReservationParams();
        
        params.setCapacity(capacity);
        params.setStartTime(start);
        params.setEndTime(end);
            
        return dm.checkResources(links, params);
    }

    public static DomainConstraints[] check(ResourcesReservation dm, Link[] links, long capacity, String sdate,
            String edate) throws OversubscribedException, AAIException {

        return check(dm, links, capacity, cal(sdate), cal(edate));
    }
    
    public static void reserve(ResourcesReservation dm, String resID, Link[] links, long capacity, int vlan,
            String sdate, String edate)
            throws ConstraintsAlreadyUsedException, OversubscribedException {

        reserve(dm, resID, links, capacity, vlan, vlan, cal(sdate), cal(edate));
    }

    public static void reserve(ResourcesReservation dm, String resID, Link[] links, long capacity, int vlan, int vlan2,
            String sdate, String edate)
            throws ConstraintsAlreadyUsedException, OversubscribedException {

        reserve(dm, resID, links, capacity, vlan, vlan2, cal(sdate), cal(edate));
    }
    
    public static void reserve(ResourcesReservation dm, String resID, Link[] links, long capacity, int vlan1, int vlan2,
            Calendar start, Calendar end)
            throws ConstraintsAlreadyUsedException, OversubscribedException {
        
        ReservationParams params = new ReservationParams();
        
        params.setCapacity(capacity);
        params.setStartTime(start);
        params.setEndTime(end);
        
        PathConstraints pcon = new PathConstraints();
        pcon.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint(vlan1, vlan1));
        params.setPathConstraintsIngress(pcon);
        
        PathConstraints pcon2 = new PathConstraints();
        pcon2.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint(vlan2, vlan2));
        params.setPathConstraintsEgress(pcon2);
        
        dm.addReservation(resID, links, params);
    }
    
    public static Calendar cal(String sdate) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        
        Date date = null;
        try {
            date = df.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        } 
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        return cal;
    }

    public static IntradomainTopology buildTopology(IntraTopologyBuilder builder) {
        // Build topology
        IntradomainTopology topology = new IntradomainTopology();
        topology.setNodes(builder.getNodes());
        topology.setSptrees(builder.getSpanningTrees());
        List<SpanningTree> sptrees = topology.getSpanningTrees();
        List<GenericLink> genericLinks = new ArrayList<GenericLink>();
        for(SpanningTree st: sptrees) {
            genericLinks.add(st.getEthLink().getGenericLink());
        }
        topology.setGenericLinks(genericLinks);
        topology.setType(IntradomainTopology.Type.ETH);
        
        return topology;
    }
    
    public static IntradomainPath buildIntradomainPath(IntraTopologyBuilder builder, String... s_names) {
    	
    	IntradomainPath pth = new IntradomainPath();
    	Arrays.sort(s_names);
    	
    	for(SpanningTree st : builder.getIntradomainTopology().getSpanningTrees()) {
    		String name = st.getEthLink().getGenericLink().toString();
    		
    		System.out.println(name);
    		
        	if(Arrays.binarySearch(s_names, name) > -1) {
        		System.out.println("Found: " + name);
        		
        		PathConstraints pcon = new PathConstraints();
        		pcon.addRangeConstraint(ConstraintsNames.VLANS, 
        				new RangeConstraint(st.getVlan().toString()));
        		
        		pth.addGenericLink(st.getEthLink().getGenericLink(), pcon);
        	}
    	}
    	
    	return pth;
    }
    
	public static String getVlan(PathConstraints pcon) {
		return pcon.getRangeConstraint(ConstraintsNames.VLANS).toString();
	}

	public static String getVlan(DomainConstraints dcon) {
		return dcon.getFirstPathConstraints().getRangeConstraint(ConstraintsNames.VLANS).toString();
	}
}
