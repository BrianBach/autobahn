/**
 * 
 */
package net.geant2.jra3.oscars;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneAdministrativeGroup;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneLinkContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNodeContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePortContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneAddressContent;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.ProvisioningDomain;
import net.geant2.jra3.network.AdminDomain;
import net.geant2.jra3.network.Node;
import net.geant2.jra3.network.Port;

/**
 * Performs topology conversion 
 * @author Michal
 *
 */

public class OscarsConverter {
	
	private OscarsConverter() { }
	
	private static class CtrlPlaneFindResults
	{
		public CtrlPlaneDomainContent domain;
		public CtrlPlaneNodeContent node;
		public CtrlPlanePortContent port;
		public CtrlPlaneLinkContent link;
	}
	
	private static CtrlPlaneFindResults findRemote(String domainId, String nodeId, 
			String portId, String linkId, CtrlPlaneDomainContent[] domains) {
		
		if (domains == null)
			return null;
		
		for (CtrlPlaneDomainContent domain : domains) {
			if (domain.getId().split(":")[3].equals(domainId)) {
				if (domain.getNode() == null)
					return null;
				for (CtrlPlaneNodeContent node : domain.getNode()) {
					if (node.getId().split(":")[4].equals(nodeId)) {
						if (node.getPort() == null)
							return null;
						for (CtrlPlanePortContent port : node.getPort()) {
							if (port.getId().split(":")[5].equals(portId)) {
								if (port.getLink() == null)
									return null;
								for (CtrlPlaneLinkContent link : port.getLink()) {
									if (link.getId().split(":")[6].equals(linkId)) {
										CtrlPlaneFindResults find = new CtrlPlaneFindResults();
										find.domain = domain;
										find.node = node;
										find.port = port;
										find.link = link;
										return find;
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	public static List<Link> getGeantTopology(CtrlPlaneDomainContent[] domains) {
		
		Map<String, AdminDomain> ads = new HashMap<String, AdminDomain>();
		Map<String, ProvisioningDomain> pds = new HashMap<String, ProvisioningDomain>();
		Map<String, Node> nodes = new HashMap<String, Node>();
		Map<String, Port> ports = new HashMap<String, Port>();
		
		final int LINK_NUM_SPLITS = 7;
		List<Link> links = new ArrayList<Link>();
		
		if (domains == null)
			return links;
		
		for (CtrlPlaneDomainContent domain : domains) {
			if (domain.getNode() == null)  
				continue;
			for (CtrlPlaneNodeContent node : domain.getNode()) {
				if (node.getPort() == null)
					continue;
				for (CtrlPlanePortContent port : node.getPort()) {
					if (port.getLink() == null)
						continue;
					for (CtrlPlaneLinkContent link : port.getLink()) {

						String[] split = link.getRemoteLinkId().split(":");
						if (split.length != LINK_NUM_SPLITS)
							continue;
						
						CtrlPlaneFindResults find = findRemote(split[3], split[4], split[5], split[6], domains);
						if (find != null) {
							
							split = link.getId().split(":");
							
							String domainID = split[3].replace("domain=", "");
							String nodeID = split[4].replace("node=", "");
							String portID = split[5].replace("port=", "");
							String linkID = split[6].replace("link=", "");
							String fullLinkID = "urn:ogf:network:" + domainID
									+ ":" + nodeID + ":" + portID + ":" + linkID;
							
							// set start port
							AdminDomain aDomain = ads.get(domainID);
							if(aDomain == null) {
								aDomain = new AdminDomain();
								
								aDomain.setBodID(domainID);
								aDomain.setName(domainID);
								
								ads.put(domainID, aDomain);
							}
							
							ProvisioningDomain pDomain = pds.get(domainID);
							if(pDomain == null) {
								pDomain = new ProvisioningDomain();
								pDomain.setAdminDomain(aDomain);
								pDomain.setBodID(domainID);

								pds.put(domainID, pDomain);
							}
							
							Node startNode = nodes.get(nodeID);
							if(startNode == null) {
								startNode = new Node();
								
								startNode.setProvisioningDomain(pDomain);
								startNode.setBodID(nodeID);
								if (node.getAddress() != null) {
									startNode.setAddress(node.getAddress().getString());
									startNode.setType(node.getAddress().getType());
								}
								
								nodes.put(nodeID, startNode);
							}
							
							Port startPort = ports.get(portID);
							if(startPort == null) {
								startPort = new Port();
							
								startPort.setNode(startNode);
								startPort.setBodID(portID);
								
								ports.put(portID, startPort);
							}

							// set end port
							String[] split2 = find.link.getId().split(":");
							domainID = split2[3].replace("domain=", "");
							nodeID = split2[4].replace("node=", "");
							portID = split2[5].replace("port=", "");
							linkID = split2[6].replace("link=", "");
							
							aDomain = ads.get(domainID);
							
							if(aDomain == null) {
								aDomain = new AdminDomain();
								aDomain.setBodID(domainID);
								aDomain.setName(domainID);
								
								ads.put(domainID, aDomain);
							}
							
							pDomain = pds.get(domainID);
							if(pDomain == null) {
								pDomain = new ProvisioningDomain();
								pDomain.setAdminDomain(aDomain);
								pDomain.setBodID(domainID);
	
								pds.put(domainID, pDomain);
							}
							
							Node endNode = nodes.get(nodeID);
							if(endNode == null) {
								endNode = new Node();
								
								endNode.setProvisioningDomain(pDomain);
								endNode.setBodID(nodeID);
								if (node.getAddress() != null) {
									endNode.setAddress(find.node.getAddress().getString());
									endNode.setType(find.node.getAddress().getType());
								}
								
								nodes.put(nodeID, endNode);
							}
							
							Port endPort = ports.get(portID);
							if(endPort == null) {
								endPort = new Port();
							
								endPort.setNode(endNode);
								endPort.setBodID(portID);
								
								ports.put(portID, endPort);
							}
						
							// dump values
							//System.out.println("port cap: " + port.getCapacity() + ", " + find.port.getCapacity());
							//System.out.println("link cap: " + link.getCapacity() + ", " + find.link.getCapacity());
							
							// decide if inter or intra link
							Link addLink;
							if (domain.getId().equals(find.domain.getId())) {
								addLink = Link.createVirtualLink(startPort, endPort, 0);
								//System.out.println("created virtual link: " + addLink.getBodID());
							} else { 
								addLink = Link.createInterDomainLink(startPort, endPort, 0);
								//System.out.println("created inter link: " + addLink.getBodID());
							}
							// set link properties from link
							addLink.setBodID(fullLinkID);
							addLink.setCapacity(Long.valueOf(link.getCapacity()));
							addLink.setGranularity(Long.valueOf(link.getGranularity()));
							addLink.setMaxResCapacity(Long.valueOf(link.getMaximumReservableCapacity()));
							addLink.setMinResCapacity(Long.valueOf(link.getMinimumReservableCapacity()));
														
							links.add(addLink);
						} else {
							//System.out.println("remote link not found for: " + link.getId());
						}
					}
				}
			}
		}
				
		return links;
	}
	
	public static void dumpGeantTopology(List<Link> links) {
		
		for (Link link : links) {
			
			System.out.println("linkId: " + link.getBodID());
			System.out.println("cap: " + link.getCapacity() + ", gran: " + link.getGranularity());
			System.out.println(link);
		}
	}
		
	public static CtrlPlaneDomainContent[] getOscarsTopology(List<Link> links) {
		
		if (links == null)
			return new CtrlPlaneDomainContent[1];
		
		// sort links acording to domain
		Map<ProvisioningDomain, List<Link>> sortLinks = new HashMap<ProvisioningDomain, List<Link>>();
		
		for (Link link : links) {
			
			ProvisioningDomain provDomain = link.getStartPort().getNode().getProvisioningDomain();
			if (!sortLinks.containsKey(provDomain)) 
				sortLinks.put(provDomain, new ArrayList<Link>());
			
			sortLinks.get(provDomain).add(link);
		}
		int numDomains = sortLinks.keySet().size();
		//System.out.println("num domains: " + sortLinks.keySet().size());
		CtrlPlaneDomainContent[] domains = new CtrlPlaneDomainContent[numDomains];
		ProvisioningDomain[] provDomains = sortLinks.keySet().toArray(new ProvisioningDomain[0]);
		
		for (int i=0; i < numDomains; i++) {

			CtrlPlaneDomainContent domain = new CtrlPlaneDomainContent();
			List<Link> itLinks = sortLinks.get(provDomains[i]);
			for (Link l : itLinks) {
			
				domain.setId(provDomains[i].getAdminDomain().getBodID());

				final String prefix = "urn:org.network";
				
				// set start link
				CtrlPlaneLinkContent link = new CtrlPlaneLinkContent();
				link.setId(prefix + ":domain=" + provDomains[i].getAdminDomainID() + ":node=" + l.getStartPort().getNode().getBodID() + ":port=" + l.getStartPort().getBodID() + ":link=" + l.getBodID());
				link.setCapacity(String.valueOf(l.getCapacity()));
				link.setGranularity(String.valueOf(l.getGranularity()));
				link.setMaximumReservableCapacity(String.valueOf(l.getMaxResCapacity()));
				link.setMinimumReservableCapacity(String.valueOf(l.getMinResCapacity()));
				
				link.setRemoteLinkId(prefix + ":domain=" + provDomains[i].getAdminDomainID() + 
						":node=" + l.getEndPort().getNode().getBodID() + ":port=" + 
						l.getEndPort().getBodID() + ":link=" + l.getBodID());
				
				CtrlPlanePortContent port = new CtrlPlanePortContent();
				port.setId(prefix + ":domain=" + provDomains[i].getAdminDomainID() + ":node=" + l.getStartPort().getNode().getBodID() + ":port=" + l.getStartPort().getBodID());
				port.setCapacity(String.valueOf(l.getCapacity()));
				port.setGranularity(String.valueOf(l.getGranularity()));
				port.setMaximumReservableCapacity(String.valueOf(l.getMaxResCapacity()));
				port.setMinimumReservableCapacity(String.valueOf(l.getMinResCapacity()));
				port.addLink(link);
			
				CtrlPlaneNodeContent node = new CtrlPlaneNodeContent();
				node.setId(prefix + ":domain=" + provDomains[i].getAdminDomainID() + ":node=" + l.getStartPort().getNode().getBodID());
				CtrlPlaneAddressContent addr = new CtrlPlaneAddressContent();
				addr.setType(l.getStartPort().getNode().getType());
				addr.setString(l.getStartPort().getNode().getAddress());
				addr.setValue(l.getStartPort().getNode().getAddress());
				node.setAddress(addr);
				node.addPort(port);
				
				domain.addNode(node);
				domain.setId(prefix + ":domain=" + provDomains[i].getAdminDomainID());
			
				// set end link
				/*
				link = new CtrlPlaneLinkContent();
				link.setId(l.getBodID());
				link.setCapacity(String.valueOf(l.getCapacity()));
				link.setGranularity(String.valueOf(l.getGranularity()));
				link.setMaximumReservableCapacity(String.valueOf(l.getMaxResCapacity()));
				link.setMinimumReservableCapacity(String.valueOf(l.getMinResCapacity()));
				
				if (l.isVirtual())
					link.setRemoteLinkId("urn:org:network:" + provDomains[i].getAdminDomainID() + ":" + l.getEndPort().getNode().getBodID() + 
						":" + l.getEndPort().getBodID() + ":" + l.getBodID());
				else 
					link.setRemoteLinkId("urn:org:network:cloud");
				
				port = new CtrlPlanePortContent();
				port.setId(l.getStartPort().getBodID());
				port.setCapacity(String.valueOf(l.getCapacity()));
				port.setGranularity(String.valueOf(l.getGranularity()));
				port.setMaximumReservableCapacity(String.valueOf(l.getMaxResCapacity()));
				port.setMinimumReservableCapacity(String.valueOf(l.getMinResCapacity()));
				port.addLink(link);
			
				node = new CtrlPlaneNodeContent();
				node.setId(l.getStartPort().getNode().getBodID());
				addr = new CtrlPlaneAddressContent();
				addr.setType(l.getStartPort().getNode().getType());
				addr.setString(l.getStartPort().getNode().getAddress());
				addr.setValue(l.getStartPort().getNode().getAddress());
				node.setAddress(addr);
				node.addPort(port);
			
				domain.addNode(node);
				*/
			}
			domains[i] = domain;
		}
		return domains;
	}
	
	public static void dumpOscarsTopology(CtrlPlaneDomainContent[] domains) {
		
		System.out.println("numDomains: " + domains.length);
    	for (CtrlPlaneDomainContent domain : domains) {
    	
    		System.out.println("domain: " + domain.getId());
    		
    		System.out.println("nodes: ");
    		CtrlPlaneNodeContent[] nodes = domain.getNode();
    		if (nodes != null) {
    			for (CtrlPlaneNodeContent node : nodes) {
    			
    				System.out.println("node: " + node.getId());
    				
    				System.out.println("node ports: ");
    				if (node.getPort() != null) {
    					for (CtrlPlanePortContent port : node.getPort()) {
    					
    						System.out.println("node port: " + port.getId());
    						
    						System.out.println("node port links: ");
    						
    						if (port.getLink() != null) {
    							for (CtrlPlaneLinkContent link : port.getLink()) {
    								
    								System.out.println("node port link: " + link.getId() + " - " + link.getRemoteLinkId());
    								//System.out.println("remote port: " + link.getRemotePortId() + ", remote node: "+ link.getRemoteNodeId());
    								
    								//System.out.println("admin domains: ");
    								if (link.getAdministrativeGroups() != null) {
    									
    									for (CtrlPlaneAdministrativeGroup admin : link.getAdministrativeGroups()) {
    										//System.out.println("admin: " + admin.getGroupID());
    									}
    								}
    							}
    						}
    					}
    				}
    			}
    		}
    		
    		System.out.println("links: ");
    		CtrlPlaneLinkContent[] links = domain.getLink();
    		if (links != null) {
    			for (CtrlPlaneLinkContent link : links) {
    				
    				System.out.println("link: " + link.getId());
    			}
    		}
    		
    		System.out.println("ports: ");
    		CtrlPlanePortContent[] ports = domain.getPort();
    		if (ports != null) {
    			for (CtrlPlanePortContent port : ports) {
    			
    				System.out.println("port: " + port.getId());
    			}
    		}
    	}
	}
}
