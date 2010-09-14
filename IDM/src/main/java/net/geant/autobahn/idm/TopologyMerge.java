package net.geant.autobahn.idm;

import java.util.HashMap;
import java.util.Map;

import net.geant.autobahn.network.AdminDomain;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Node;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.network.ProvisioningDomain;
import net.geant.autobahn.reservation.AutobahnReservation;

public class TopologyMerge {

	private Map<String, AdminDomain> adomains = new HashMap<String, AdminDomain>();
	private Map<String, ProvisioningDomain> pdomains = new HashMap<String, ProvisioningDomain>();
	private Map<String, Node> nodes = new HashMap<String, Node>();
	private Map<String, Port> ports = new HashMap<String, Port>();
	
	public TopologyMerge() {
		
	}
	
	public AdminDomain getAdminDomain(AdminDomain ad) {
		AdminDomain adomain = adomains.get(ad.getBodID());
		if(adomain != null) {
		    // idcpServer value may have been set at a later stage, so make
		    // sure it is not overwritten be earlier admin domain data
		    adomain.setIdcpServer(ad.getIdcpServer());
			return adomain;
		}
		
		adomains.put(ad.getBodID(), ad);
		
		return ad;
	}
	
	public ProvisioningDomain getProvDomain(ProvisioningDomain pd) {
		ProvisioningDomain pdomain = pdomains.get(pd.getBodID());
		if(pdomain != null) {
            // idcpServer value may have been set at a later stage, so make
            // sure it is not overwritten be earlier admin domain data
            pdomain.getAdminDomain().setIdcpServer(pd.getAdminDomain().getIdcpServer());
			return pdomain;
		}
		
		pdomains.put(pd.getBodID(), pd);
		pd.setAdminDomain(getAdminDomain(pd.getAdminDomain()));
		
		return pd;
	}
	
	public Node getNode(Node n) {
		Node node = nodes.get(n.getBodID());
		if(node != null) {
            // idcpServer value may have been set at a later stage, so make
            // sure it is not overwritten be earlier admin domain data
            node.getProvisioningDomain().getAdminDomain().setIdcpServer(n.getProvisioningDomain().getAdminDomain().getIdcpServer());
			return node;
		}
		
		nodes.put(n.getBodID(), n);
		n.setProvisioningDomain(getProvDomain(n.getProvisioningDomain()));
		
		return n;
	}
	
	public Port getPort(Port p) {
		Port port = ports.get(p.getBodID());
		if(port != null) {
            // idcpServer value may have been set at a later stage, so make
            // sure it is not overwritten be earlier admin domain data
            port.getNode().getProvisioningDomain().getAdminDomain().setIdcpServer(p.getNode().getProvisioningDomain().getAdminDomain().getIdcpServer());
			return port;
		}
		
		ports.put(p.getBodID(), p);
		p.setNode(getNode(p.getNode()));
		
		return p;
	}
	
	public AutobahnReservation merge(AutobahnReservation r) {
		r.setStartPort(getPort(r.getStartPort()));
		r.setEndPort(getPort(r.getEndPort()));
		
		Path p = r.getPath();
		for(Link l : p.getLinks()) {
			l.setStartPort(getPort(l.getStartPort()));
			l.setEndPort(getPort(l.getEndPort()));
		}		
		
		return r;
	}
	
	public Link merge(Link l) {
		l.setStartPort(getPort(l.getStartPort()));
		l.setEndPort(getPort(l.getEndPort()));
		
		return l;
	}
}
