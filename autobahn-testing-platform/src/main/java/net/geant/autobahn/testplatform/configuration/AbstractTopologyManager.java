package net.geant.autobahn.testplatform.configuration;

import java.util.ArrayList;
import java.util.List;

import net.geant.autobahn.network.Link;
import net.geant.autobahn.testplatform.clients.AdministrationClient;
import net.geant.autobahn.testplatform.clients.TopologyAbstractionClient;
import net.geant.autobahn.testplatform.helpers.AutobahnManagement;
import net.geant.autobahn.topologyabstraction.TopologyAbstraction;

public class AbstractTopologyManager {

	public List<Link> getAbstractLinks(String[] addresses) {
		List<Link> links = new ArrayList<Link>();
		
		for(String domain : addresses) {
	        TopologyAbstractionClient ss = new TopologyAbstractionClient(domain + "/topologyabstraction");
	        TopologyAbstraction port = ss.getTopologyAbstractionPort();

	        links.addAll(port.getAbstractLinks());
		}
		
		return links;
	}
	
	public List<Link> getAbstractLinks(AutobahnManagement[] domains) {
		List<Link> links = new ArrayList<Link>();
		
		for(AutobahnManagement domain : domains) {
	        TopologyAbstractionClient ss = new TopologyAbstractionClient(
	        		domain.getDomainAddress() + "/topologyabstraction");
	        TopologyAbstraction port = ss.getTopologyAbstractionPort();

	        links.addAll(port.getAbstractLinks());
		}
		
		return links;
	}
	
	public void sendLinksToIDM(String address, List<Link> links) {
		AdministrationClient cli = new AdministrationClient(address);
		cli.getAdministrationPort().setTopology(links);
	}
	
	public void sendLinksToIDM(AutobahnManagement[] domains, List<Link> links) {
		for(AutobahnManagement domain : domains) {
			sendLinksToIDM(domain.getDomainAddress() + "/administration", links);
		}
	}
}
