/**
 * 
 */
package net.geant.autobahn.idcp;


import org.apache.log4j.Logger;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneHopContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneLinkContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneSwcapContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneSwitchingCapabilitySpecificInfo;

import net.geant.autobahn.network.Link;
import net.geant.autobahn.reservation.AutobahnReservation;
import net.geant.autobahn.reservation.ReservationErrors;

/**
 * Responsible for translating autobahn to idcp parameters
 * 
 * @author PCSS
 */
public final class ToIdcp {
	
	private static Logger log = Logger.getLogger(ToIdcp.class);
	
	private final String domainName;
	private final IdcpDomain domain;
	private final IdcpClient idcp;
	
	public ToIdcp(String domainName) {
		
		this.domainName = domainName;
		this.domain = IdcpManager.getIdcpDomain(domainName);
		String url = null;
		if (domain.isPeered()) {
			url = domain.getIdcpUrl();
		} else {
			IdcpDomain peered = IdcpManager.getIdcpDomain(domain.getStaticRoute());
			url = peered.getIdcpUrl();
		}
		
		System.out.println("ToIdcp created with " + url);
		this.idcp = new IdcpClient(url);
	}
	
	private static CtrlPlaneHopContent createHop(String hopId, String linkId, String remoteId, String vlan) { 
		
		CtrlPlaneLinkContent link = new CtrlPlaneLinkContent();
		link.setId(linkId);
		link.setRemoteLinkId(remoteId);
		link.setTrafficEngineeringMetric("10");
		
		CtrlPlaneSwitchingCapabilitySpecificInfo switching = new CtrlPlaneSwitchingCapabilitySpecificInfo();
		switching.setInterfaceMTU(9000);
		switching.setVlanTranslation(true);
		switching.setVlanRangeAvailability(vlan);
		switching.setSuggestedVLANRange(vlan);
		switching.setCapability("");
		
		CtrlPlaneSwcapContent swcaps = new CtrlPlaneSwcapContent();
		swcaps.setEncodingType("ethernet");
		swcaps.setSwitchingcapType("l2sc");
		swcaps.setSwitchingCapabilitySpecificInfo(switching);
		link.setSwitchingCapabilityDescriptors(swcaps);
		
		CtrlPlaneHopContent hop = new CtrlPlaneHopContent();
		hop.setId(hopId);
		hop.setLink(link);
		
		return hop;
	}
	
	/**
	 * Send create message to idcp domain
	 * @param reservation
	 * @return
	 */
	public int forwardCreate(AutobahnReservation reservation) {
		
		if (!reservation.isIdcpReservation())
			return ReservationErrors.RESERVATION_NOTSUPPORTED;
			
		// Reservations starting from an idcp domain and terminating at autobahn domain cannot be supported as the idcp side is not aware of autobahn 
    	// topology. Still we should allow this kind of reservations, however exceptions thrown by the idcp is less than descriptive so we quit here  
	    if (reservation.isIdcp2AbReservation() && !reservation.isAb2IdcpReservation()) 
	    	return ReservationErrors.RESERVATION_NOTSUPPORTED;
	    

	    final String startPort = reservation.getStartPort().getBodID();
        final String idcpStartPort = Idcp.portToIdcpLink(startPort);
        if (idcpStartPort == null) {
        	log.info("could not convert " + startPort + " to idcp link");
        	return ReservationErrors.WRONG_DOMAIN;
        }
	    final String endPort = reservation.getEndPort().getBodID();
	    final String idcpEndPort = Idcp.restorePortId(endPort);
	    
	    //IdcpDomain nextIdcpDomain = domain.isPeered() ? domain : IdcpManager.getIdcpDomain(domain.getStaticRoute());
	    String idcpAutobahnEgress, idcpIngress;
	    if (!domain.isPeered()) {
	    	
			String domName = domain.getStaticRoute();
			IdcpDomain peered = IdcpManager.getIdcpDomain(domName);
	    	String[] linkMapping = peered.getLinkMapping();
	    	if (linkMapping == null) {
	    		log.info("link maping not found for domain " + peered.getDomainName());
	    		return ReservationErrors.WRONG_DOMAIN;
	    	}
	    	idcpAutobahnEgress = linkMapping[0];
	    	idcpAutobahnEgress = Idcp.portToIdcpLink(idcpAutobahnEgress);
	        if (idcpAutobahnEgress == null) {
	        	log.info("could not convert " + idcpAutobahnEgress + " to idcp link");
	        	return ReservationErrors.WRONG_DOMAIN;
	        }
	    	idcpIngress = linkMapping[1];
	    	
	    } else {
	    
		    Link autobahnToIdcp = reservation.getPath().getIngress(domain.getDomainName());
		    if (autobahnToIdcp == null) {
		    	log.info("autobahn egress not found for path " + reservation.getPath());
		    	return ReservationErrors.WRONG_DOMAIN;
		    }
		    if (!autobahnToIdcp.getEndPort().isIdcpPort()) {
		    	log.info("autobahn egress not idcp");
		    	return ReservationErrors.WRONG_DOMAIN;
		    }
		    
		    String autobahnEgress = autobahnToIdcp.getEndPort().getBodID();
		    if (autobahnEgress.equals(endPort)) {
		    	log.info("dest port is the same as autobahn egress");
		    	return ReservationErrors.WRONG_DOMAIN;
		    }
		    idcpAutobahnEgress = Idcp.portToIdcpLink(autobahnEgress);
	        if (idcpAutobahnEgress == null) {
	        	log.info("could not convert " + autobahnEgress + " to idcp link");
	        	return ReservationErrors.WRONG_DOMAIN;
	        }
	    	idcpIngress = domain.getProperties().getProperty(autobahnEgress);
	        if (idcpIngress == null) { 
	        	log.info("could not find mapping for " + autobahnEgress + ", please ensure it is set either in property file or cnis database");
	        	return ReservationErrors.WRONG_DOMAIN;
	        }
	    }
               
        /*
        
	    // check if link mapping (autobahn's egress to idcp ingress) has been provided by cnis (port desc property)
	    String portDesc = reservation.getEndPort().getDescription();
	    if (portDesc != null && portDesc.contains("idcplink")) {
	    	System.out.println("port desc contains: " + portDesc);
	    	String linkMapping = portDesc.substring(portDesc.indexOf("idcplink"));
	    	src = linkMapping.split("\\=")[1];
	    } else {
	    	
	     	if (properties.containsKey(src)) {
	     		src = properties.getProperty(src);
	     	} else
	     		log.info("ToIdcp - source port " + src + " with no link mapping");
	    }
	    
	    String dst = reservation.getEndPort().getBodID();
	    dst = ToIdcp.restorePortId(dst);
	    System.out.println("src: " + src + ", dst: " + dst);
	    */
	    /*
	    GlobalConstraints globalCons = reservation.getGlobalConstraints();
	    DomainConstraints domainCons = globalCons.getDomainConstraints().get(globalCons.getDomainConstraints().size() - 1);
	    
	    PathConstraints pathCons = domainCons.getPathConstraints().get(domainCons.getPathConstraints().size() - 1);
	    List<Range> ranges = pathCons.getRangeConstraint(ConstraintsNames.VLANS).getRanges();
	    Range vlans = ranges.get(ranges.size() - 1);
	    */    
	    int vlanNumber = reservation.getGlobalConstraints().getDomainConstraints().get(0).getFirstPathConstraints().getRangeConstraints().get(0).getFirstValue();
	    final String vlan = vlanNumber == 0 ? "any" : String.valueOf(vlanNumber);
	    final String resId = Idcp.toIdcpReservationId(reservation.getBodID());
	    final String desc =  reservation.getDescription();
	    final long startTime = reservation.getStartTime().getTimeInMillis() / Idcp.TIME_SCALE;
	    final long endTime = reservation.getEndTime().getTimeInMillis() / Idcp.TIME_SCALE;
	    final int bandwidth = ((int)(reservation.getCapacity() / Idcp.BANDWIDTH_SCALE));
	    
	    CtrlPlanePathContent pathContent = new CtrlPlanePathContent();
		pathContent.setId(startPort + " - " + endPort);
		pathContent.setDirection("");
		pathContent.setLifetime(null);
		
		// create 4-hops path content
		pathContent.getHop().add(createHop("0", idcpStartPort, idcpStartPort, vlan));
		pathContent.getHop().add(createHop("1", idcpAutobahnEgress, idcpIngress, vlan));
		pathContent.getHop().add(createHop("2", idcpIngress, idcpAutobahnEgress, vlan));
		pathContent.getHop().add(createHop("3", idcpEndPort, idcpEndPort, vlan));
	    
		PathInfo pathInfo = new PathInfo();
		
		Layer2Info l2 = new Layer2Info();
		VlanTag srcVtag = new VlanTag();
		srcVtag.setValue(vlan);
		srcVtag.setTagged(true);
		l2.setSrcEndpoint(idcpAutobahnEgress);
		l2.setSrcVtag(srcVtag);
		VlanTag dstVtag = new VlanTag();
		dstVtag.setValue(vlan);
		dstVtag.setTagged(true);
		l2.setDestEndpoint(idcpEndPort);
		l2.setDestVtag(dstVtag);
		
		pathInfo.setLayer2Info(l2);
		pathInfo.setLayer3Info(null);
		pathInfo.setMplsInfo(null);
		pathInfo.setPathSetupMode(Idcp.PATH_MODE_AUTOMATIC);
		pathInfo.setPathType(Idcp.PATH_TYPE_LOOSE);
		pathInfo.setPath(pathContent);
		
		if (IdcpManager.isDebugging())
			Idcp.printPathInfo(pathInfo);
		
	    // add res listener
	    reservation.addStatusListener(new IdcpReservation(resId, reservation, pathInfo));
		
		try {
	    	idcp.forwardCreate(resId, desc, idcpStartPort, idcpEndPort, startTime, endTime, bandwidth, vlan, pathInfo);
	    	return 0;
	    } catch (IdcpException e) {
	    	log.info("ToIdcp - schedule failed - " + e.getMessage());
	    	String error = e.getMessage().toLowerCase();
	    	if (error.contains("vlan"))
        		return ReservationErrors.CONSTRAINTS_NOT_AGREED;
        	else if (error.contains("oversubscribed"))
        		return ReservationErrors.NOT_ENOUGH_CAPACITY;
        	else 
        		return ReservationErrors.COMMUNICATION_ERROR;
	    }
	}
	
	/**
	 * Sends cancel message to idcp domain
	 * @param resId
	 */
	public void forwardCancel(String resId) { 
		
		final String rid = Idcp.toIdcpReservationId(resId);
		
		try {
			idcp.forwardCancel(rid);
		} catch (IdcpException e) {
			log.info("ToIdcp - forward cancel failed - " + e.getMessage());
		}
	}
}
