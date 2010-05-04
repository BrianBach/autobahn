/*
 * IntraTopologySaver.java
 *
 * 2007-04-03
 */
package net.geant.autobahn.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericInterface;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.ethernet.EthLink;
import net.geant.autobahn.intradomain.ethernet.SpanningTree;
import net.geant.autobahn.intradomain.ethernet.Vlan;
import net.geant.autobahn.intradomain.sdh.SdhDevice;
import net.geant.autobahn.intradomain.sdh.StmLink;
import net.geant.autobahn.intradomain.sdh.StmType;

import org.hibernate.Session;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class IntraTopologyBuilder {

    private Session session = null;
    private Map<String, Node> nodes = new HashMap<String, Node>();
    private Map<String, GenericInterface> gifs = new HashMap<String, GenericInterface>();
    private List<SpanningTree> strees = new ArrayList<SpanningTree>();
    
    private List<StmLink> stmLinks = new ArrayList<StmLink>();
    
    private boolean useDb;
    private int counter = 1;
    
    public IntraTopologyBuilder(boolean useDb) {
    	this.useDb = useDb;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
    private GenericInterface createRouterIf(String nodeID, String interfaceID, String domainId, boolean client, long bandwidth) {

        GenericInterface gi = gifs.get(interfaceID);
        
        if(gi == null) {
            Node node = nodes.get(nodeID);
            
            if(node == null) {
                node = new Node();
            	if(!useDb)
            		node.setNodeId(counter++);

                node.setIpAddress(nodeID);
                node.setName(nodeID);
                nodes.put(nodeID, node);
                
                if(session != null)
                	session.save(node);
            }
            
            gi = new GenericInterface();
            
        	if(!useDb)
        		gi.setInterfaceId(counter++);
            
            gi.setNode(node);
            gi.setBandwidth(bandwidth);
            if(domainId != null)
            	gi.setDomainId(domainId);
            gi.setName(interfaceID);
            gi.setClientPort(client);
            
            gifs.put(gi.getName(), gi);
            
            if(session != null)
            	session.save(gi);
        }      

        return gi;
    }

    public GenericInterface createClientIf(String nodeID, String interfaceID, String domainId, long bandwidth) {
    	return createRouterIf(nodeID, interfaceID, domainId, true, bandwidth);
    }
    
    public GenericInterface createRouterIf(String nodeID, String interfaceID, String domainId, long bandwidth) {
    	return createRouterIf(nodeID, interfaceID, domainId, false, bandwidth);
    }
    
    public GenericInterface createNodeIf(String nodeID, String interfaceID, long bandwidth) {

    	return createRouterIf(nodeID, interfaceID, null, false, bandwidth);
    }
    
    public void addStmLink(GenericInterface start, GenericInterface end, StmType type) {
    	GenericLink glink = new GenericLink();
    	glink.setStartInterface(start);
    	glink.setEndInterface(end);
    	if(!useDb)
    		glink.setLinkId(counter++);
		
        if(session != null)
        	session.save(glink);
    		
        StmLink stmLink = new StmLink();
        stmLink.setStmLink(glink);
        
        if(session != null)
        	session.save(type);
        
        stmLink.setStmType(type);

        if(session != null)
        	session.save(stmLink);
        
        stmLinks.add(stmLink);
    }
    
    public void addSpanningTree(GenericInterface start, GenericInterface end, int low, int high) {
        
        GenericLink link = new GenericLink();
        link.setStartInterface(start);
        link.setEndInterface(end);
        link.setLinkId(counter++);
        if(session != null)
        	session.save(link);
        
        EthLink ethLink = new EthLink();
        ethLink.setGenericLink(link);
        if(session != null)
        	session.save(ethLink);
        
        Vlan vlan = new Vlan();
        vlan.setLowNumber(low);
        vlan.setHighNumber(high);
        if(session != null)
        	session.save(vlan);
        
        SpanningTree st = new SpanningTree();
        st.setEthLink(ethLink);
        st.setVlan(vlan);
        if(session != null)
        	session.save(st);
        
        strees.add(st);
    }
    
    public List<SpanningTree> getSpanningTrees() {
    	return strees;
    }

    public List<StmLink> getStmLinks() {
    	return stmLinks;
    }
    
    public List<SdhDevice> getSdhDevices() {
    	List<SdhDevice> devices = new ArrayList<SdhDevice>();
    	
    	for(Node n : nodes.values()) {
    		SdhDevice dev = new SdhDevice();
    		dev.setNode(n);
    		dev.setName(n.getName());
    		
    		devices.add(dev);
    	}
    	
    	return devices;
    }

    public void saveSdhDevices() {
    	
    	if(session == null)
    		return;
    	
    	for(SdhDevice dev : getSdhDevices()) {
           	session.save(dev);
    	}
    }
    
    public List<Node> getNodes() {
    	return new ArrayList<Node>(nodes.values());
    }
    
    public IntradomainTopology getTopology() {
    	IntradomainTopology topo = new IntradomainTopology();

    	if(strees.size() > 0) {
	    	topo.setSptrees(strees);
	    	topo.setNodes(getNodes());
	    	topo.setType(IntradomainTopology.Type.ETH);

	    	List<GenericLink> glinks = new ArrayList<GenericLink>();
	    	for(SpanningTree st : strees) {
	    		glinks.add(st.getEthLink().getGenericLink());
	    	}
    		topo.setGenericLinks(glinks);
    	} else {
	    	topo.setStmLinks(stmLinks);
	    	topo.setSdhDevices(getSdhDevices());
	    	topo.setType(IntradomainTopology.Type.SDH);
	    	
	    	List<GenericLink> glinks = new ArrayList<GenericLink>();
	    	for(StmLink slink : stmLinks) {
	    		glinks.add(slink.getStmLink());
	    	}
    		topo.setGenericLinks(glinks);
    	}
    	
    	return topo;
    }
}
