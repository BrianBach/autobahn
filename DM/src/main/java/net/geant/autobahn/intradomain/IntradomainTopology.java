package net.geant.autobahn.intradomain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.dao.DmDAOFactory;
import net.geant.autobahn.dao.hibernate.DmHibernateUtil;
import net.geant.autobahn.dao.hibernate.HibernateDmDAOFactory;
import net.geant.autobahn.intradomain.common.GenericInterface;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.common.dao.GenericInterfaceDAO;
import net.geant.autobahn.intradomain.common.dao.GenericLinkDAO;
import net.geant.autobahn.intradomain.common.dao.NodeDAO;
import net.geant.autobahn.intradomain.ethernet.EthLink;
import net.geant.autobahn.intradomain.ethernet.EthPhysicalPort;
import net.geant.autobahn.intradomain.ethernet.SpanningTree;
import net.geant.autobahn.intradomain.ethernet.Vlan;
import net.geant.autobahn.intradomain.ethernet.VlanPort;
import net.geant.autobahn.intradomain.ethernet.dao.EthLinkDAO;
import net.geant.autobahn.intradomain.ethernet.dao.SpanningTreeDAO;
import net.geant.autobahn.intradomain.ethernet.dao.VlanDAO;
import net.geant.autobahn.intradomain.sdh.SdhDevice;
import net.geant.autobahn.intradomain.sdh.StmLink;
import net.geant.autobahn.intradomain.sdh.StmType;
import net.geant2.cnis.autobahn.CnisClient;
import net.geant2.cnis.autobahn.xml.CnisToAutobahnResponse;
import net.geant2.cnis.autobahn.xml.ethernet.IDLink;
import net.geant2.cnis.autobahn.xml.ethernet.Link;
import net.geant2.cnis.autobahn.xml.ethernet.PhysicalPort;
import net.geant2.cnis.autobahn.xml.ethernet.Range;
import net.geant2.cnis.autobahn.xml.sdh.PhyInterface;
import net.geant2.cnis.autobahn.xml.sdh.PhyLink;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * The class holds the intradomain topology information. Contains the methods to
 * retrieve it either from a database or a cNIS instance.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="IntradomainTopology", namespace="intradomain.autobahn.geant.net", propOrder={
        "genericLinks", "nodes", "sptrees", "ethpp", "vlaps",
        "stmLinks", "sdhDevices", "type", "domainName"
})
public class IntradomainTopology {

	private final Logger log = Logger.getLogger(IntradomainTopology.class);

	@XmlType(name="Type", namespace="intradomain.autobahn.geant.net")
	public enum Type {ETH, SDH};

    private List<GenericLink> genericLinks = null;
    private List<Node> nodes = null;

    // eth specific
	private List<SpanningTree> sptrees = new ArrayList<SpanningTree>();
	private List<EthPhysicalPort> ethpp = null;
	private List<VlanPort> vlaps = null;

	// sdh specific
	private List<StmLink> stmLinks = null;
	private List<SdhDevice> sdhDevices = null;
    
	private Type type;
	private String domainName;

    // A public default constructor is required for JAX-RPC marshalling
    public IntradomainTopology(){}
    
	/**
	 * Creates an instance of the topology class.
	 * 
	 * @param cnisAddress
	 *            String url of the cNIS service, if it's "none" or null then
	 *            database is used
	 * @param domainId
	 *            String name of the domain
	 * @param topologyType
	 *            String type of the topology, "eth" or "sdh"
	 */
    public IntradomainTopology(String cnisAddress, String domainId, String topologyType) {
    	if("sdh".equals(topologyType))
    		type = Type.SDH;
    	else  if(topologyType.startsWith("eth"))
    		type = Type.ETH;

        domainName = domainId;

    	// cNIS is not going to be used (even if it is available), in case
    	// the database is not empty, because this might create consistency problems.
    	// For example, the cNIS topology could been updated while our database
    	// contains reservations on the old topology.
    	if(cnisAddress == null || "none".equals(cnisAddress)) {
    		// read it from db
    	    log.info("cNIS address is set to none. Topology will be retrieved from the database.");
			readFromDatabase();
    	} else if(!isDatabaseEmpty()) {
    	    // read it from db
    	    log.info("Reservations found in the database. Topology will be retrieved from the database and not from cNIS.");
    	    readFromDatabase();
    	} else {
    		try {
				readFromCnis(cnisAddress);
			} catch (Exception e) {
				log.info("Unable to use cNIS: " + e.getMessage() + " - Autobahn reads topology from DB");
				e.printStackTrace();
				readFromDatabase();
			}
    	}
    }
    
    // Checks if Database is empty by searching for Intradomain Reservations
    private boolean isDatabaseEmpty() {
        DmDAOFactory daos = HibernateDmDAOFactory.getInstance();
        List<IntradomainReservation> rsv_list = daos.getIntradomainReservationDAO().getAll();
        if (rsv_list == null || rsv_list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
    
 	private void readFromDatabase() {
		DmDAOFactory daos = HibernateDmDAOFactory.getInstance();
		
		if(isSDH()) {
			stmLinks = daos.getStmLinkDAO().getAll();
			genericLinks = new ArrayList<GenericLink>();
			
			for(StmLink link : stmLinks) {
				genericLinks.add(link.getStmLink());
			}

			nodes = new ArrayList<Node>();
			sdhDevices = daos.getSdhDeviceDAO().getAll();
			for(SdhDevice device : sdhDevices) {
				nodes.add(device.getNode());
			}
		} else if (isEthernet()){
			sptrees = daos.getSpanningTreeDAO().getAll();

			genericLinks = new ArrayList<GenericLink>();
			
			for(SpanningTree st: sptrees) {
				genericLinks.add(st.getEthLink().getGenericLink());
			}
			
			nodes = daos.getNodeDAO().getAll();
			ethpp = daos.getEthPhysicalPortDAO().getAll();
			vlaps = daos.getVlanPortDAO().getAll();
		}
	}

    /**
     * Mainly used for debugging purposes
     * author: Kostas
     * 
     * @return String contains topology 1-level deep
     */
    public String TopologyString() {
        StringBuffer sb = new StringBuffer("\n----Intradomain Topology: "+this.toString());
        try {
            sb.append("\n|-domainaddress:"+domainName);
            sb.append("\n|-genericLinks:");
            if (genericLinks!=null) {
                sb.append(" size:"+genericLinks.size()+"\n");
                for (GenericLink gl: genericLinks) {
                    sb.append(gl.toString()+"\n");
                }
            }
            sb.append("\n|-nodes:");
            if (nodes!=null) {
                sb.append(" size:"+nodes.size()+"\n");
                for (Node nd: nodes) {
                    sb.append(nd.toString()+",id:"+nd.getNodeId()+"\n");
                }
            }
            //sb.append("\n**"+genericLinks.toString()+nodes.toString()+"**\n");
            if (isSDH()) {
                sb.append("\n|-Is SDH.\n|-stmLinks:");
                if (stmLinks!=null) {
                    sb.append(" size:"+stmLinks.size()+"\n");
                    for (StmLink stml: stmLinks) {
                        sb.append(stml.toString()+"\n");
                    }
                }
                sb.append("\n|-sdhDevices:");
                if (sdhDevices!=null) {
                    sb.append(" size:"+sdhDevices.size()+"\n");
                    for (SdhDevice sdhd: sdhDevices) {
                        sb.append(sdhd.toString()+"\n");
                    }
                }
                //sb.append("\n**"+stmLinks.toString()+sdhDevices.toString()+"**\n");
            }
            if (isEthernet()) {
                sb.append("\n|-Is Ethernet.\n|-ethpp:");
                if (ethpp!=null) {
                    sb.append(" size:"+ethpp.size()+"\n");
                    for (EthPhysicalPort ethp: ethpp) {
                        sb.append(ethp.toString()+"\n");
                    }
                }
                sb.append("\n|-vlaps:");
                if (vlaps!=null) {
                    sb.append(" size:"+vlaps.size()+"\n");
                    for (VlanPort vp: vlaps) {
                        sb.append(vp.toString()+"\n");
                    }
                }
                sb.append("\n|-sptrees:");
                if (sptrees!=null) {
                    sb.append(" size:"+sptrees.size()+"\n");
                    for (SpanningTree st: sptrees) {
                        sb.append("\n  st vlan id:"+st.getVlan().getVlanId()+"\n");
                        sb.append("\n  st ethlink native vlan id:"+st.getEthLink().getNativeVlan()+"\n");
                        sb.append("\n  st ethlink generic link:"+st.getEthLink().getGenericLink()+"\n");
                        //sb.append(st.toString()+"\n");
                    }
                }
                //sb.append("\n**"+ethpp.toString()+vlaps.toString()+sptrees.toString()+"**\n");
            }
        }
        catch (NullPointerException ne) {
            sb.append(ne.getMessage());
            ne.printStackTrace();
        }
        sb.append("\n----End of Intradomain Topology object");
        return sb.toString();
    }
    
    private void readFromCnis(String address) throws Exception {
		// read it from cNIS via Web Service
		CnisClient cnis = new CnisClient(address);
		
		log.info("Connecting to cNIS instance: " + address);
		CnisToAutobahnResponse resp = cnis.getIntradomainTopology();
		
		if(isSDH()) {
			nodes = new ArrayList<Node>();
			
			//STM-1 = 150336000
			Map<String, GenericInterface> ports = new HashMap<String, GenericInterface>();
			
			for(net.geant2.cnis.autobahn.xml.sdh.Node n : resp.getSdhTopology().getNodes().getNode()) {
				Node node = new Node();
				node.setNodeId(0);
				
				node.setName(n.getName());
				
				
				for(PhyInterface p : n.getPhyInterfaces().getInterface()) {
					GenericInterface port = new GenericInterface();
					
					port.setInterfaceId(0);
					port.setNode(node);
					port.setDomainId(domainName);
					port.setClientPort(false);
					port.setName(p.getName());
	                //mtu info added
                    for (net.geant2.cnis.autobahn.xml.common.Tag tag: p.getTags().getTag()) {
                        if (tag.getKey().equals("mtu")) {
                            port.setMtu(Integer.parseInt(tag.getValue()));
                        }
                    }

					ports.put(p.getName(), port);
				}
				
				nodes.add(node);
				
			}
			
			genericLinks = new ArrayList<GenericLink>();
			
			for(PhyLink l : resp.getSdhTopology().getIntradomainLinks().getLink()) {
				GenericLink glink = new GenericLink();
				
				GenericInterface sport = ports.get(l.getStartInterface().getName());
				glink.setStartInterface(sport);
				
				GenericInterface dport = ports.get(l.getEndInterface().getName());
				glink.setEndInterface(dport);

				sport.setBandwidth(l.getBandwidth().longValue());
				dport.setBandwidth(l.getBandwidth().longValue());
				
				genericLinks.add(glink);
			}

			int id = 0;
			for(net.geant2.cnis.autobahn.xml.sdh.IDLink l : resp.getSdhTopology().getInterdomainLinks().getLink()) {
				GenericLink glink = new GenericLink();
				
				GenericInterface sport = ports.get(l.getStartPort().getName());
				sport.setBandwidth(l.getBandwidth().longValue());
				glink.setStartInterface(sport);
				
				Node dnode = new Node();
				dnode.setNodeId(0);
				dnode.setName("external-node-" + ++id);
				nodes.add(dnode);

				GenericInterface dport = new GenericInterface();
				dport.setName(l.getEndPortId());
				dport.setInterfaceId(0);
				dport.setNode(dnode);
				dport.setBandwidth(l.getBandwidth().longValue());
				String domainId = l.getExternalDomain().getId();
				dport.setDomainId(domainId);
				dport.setClientPort(isClientDomain(l.getExternalDomain()));
				dport.setDescription(getExternalDomainDescription(l.getExternalDomain()));

				glink.setEndInterface(dport);
				
				String idcpLink = getIdcpLink(l.getExternalDomain());
				if (idcpLink != null) 
					dport.setDescription(dport.getDescription() + "\n" + "idcplink=" + idcpLink);

				genericLinks.add(glink);
			}

			// SDH specific
			sdhDevices = new ArrayList<SdhDevice>();

			for(Node n : nodes) {
				SdhDevice dev = new SdhDevice();
				dev.setNode(n);
				dev.setName(n.getName());
				
				sdhDevices.add(dev);
			}

			stmLinks = new ArrayList<StmLink>();
			StmType stmType = new StmType();
			stmType.setBandwidth(64 * 150336000);
			stmType.setName("STM-64");
			
			for(GenericLink gl : genericLinks) {
				StmLink stmLink = new StmLink();
				stmLink.setStmLink(gl);
				stmLink.setStmType(stmType);

				log.debug(gl + " " + gl.getLinkId());
				
				stmLinks.add(stmLink);
			}
			
	    	Transaction t = DmHibernateUtil.getInstance().beginTransaction();
	    	
	    	clearIntradomainTopologyDatabase();
	    	saveTopology();
	        
	    	t.commit();
			
		} else if (isEthernet()) {
			
			Map<String, GenericInterface> ports = new HashMap<String, GenericInterface>();
			
			nodes = new ArrayList<Node>();
			Map<String, List<Range>> n_to_vlans = new HashMap<String, List<Range>>(); 
			
			int id = 0;
			for(net.geant2.cnis.autobahn.xml.ethernet.Node n : resp.getEthTopology().getNodes().getNode()) {
				Node node = new Node();
				node.setNodeId(0);
				node.setName(n.getName());
				n_to_vlans.put(node.getName(), n.getVlanRanges().getRange());
				
				for(PhysicalPort p : n.getPhysicalPorts().getPort()) {
					GenericInterface port = new GenericInterface();
					
					port.setInterfaceId(0);
					port.setNode(node);
					port.setBandwidth(p.getBandwidth().longValue());
					port.setDomainId(domainName);
					port.setClientPort(false);
					port.setName(p.getName());
					for (net.geant2.cnis.autobahn.xml.common.Tag tag: p.getTags().getTag()) {
                        if (tag.getKey().equals("mtu")) {
                            port.setMtu(Integer.parseInt(tag.getValue()));
                        }
                    }

					ports.put(p.getName(), port);
				}
				
				nodes.add(node);
			}

			sptrees = new ArrayList<SpanningTree>(); 
			List<Link> links = resp.getEthTopology().getIntradomainLinks().getLink();
			
			for(Link link : links) {
				GenericLink glink = new GenericLink();
				
				GenericInterface sport = ports.get(link.getStartPort().getName());
				glink.setStartInterface(sport);
				
				GenericInterface dport = ports.get(link.getEndPort().getName());
				glink.setEndInterface(dport);
				
				glink.setLinkId(++id);

				RangeConstraint vlansStartNode = createRangeConstraint(n_to_vlans.get(sport.getNode().getName()));
				RangeConstraint vlansEndNode = createRangeConstraint(n_to_vlans.get(dport.getNode().getName()));
				
				RangeConstraint vlans = vlansStartNode.intersect(vlansEndNode);
				
				SpanningTree st = new SpanningTree();
				st.setEthLink(new EthLink(glink, "", false, false, 0));
				st.setVlan(new Vlan(++id, "vlan-x", 
						vlans.getRanges().get(0).getMin(), 
						vlans.getRanges().get(0).getMax()));
				
				sptrees.add(st);
			}
			
			List<IDLink> id_links = resp.getEthTopology().getInterdomainLinks().getLink();
			
			for(IDLink l : id_links) {
				GenericLink glink = new GenericLink();
				
				GenericInterface sport = ports.get(l.getStartPort().getName());
				glink.setStartInterface(sport);

				Node dnode = new Node();
				dnode.setNodeId(0);
				dnode.setName("external-node-" + id);
				nodes.add(dnode);
				
				GenericInterface dport = new GenericInterface();
				dport.setName(l.getEndPortId());
				dport.setInterfaceId(0);
				dport.setNode(dnode);
				dport.setBandwidth(l.getBandwidth().longValue());
				String domainId = l.getExternalDomain().getId();
				dport.setDomainId(domainId);
				dport.setClientPort(isClientDomain(l.getExternalDomain()));						
				dport.setDescription(getExternalDomainDescription(l.getExternalDomain()));
				
				glink.setEndInterface(dport);
				
				String idcpLink = getIdcpLink(l.getExternalDomain());
				if (idcpLink != null) 
					dport.setDescription(dport.getDescription() + "\n" + "idcplink=" + idcpLink);
				
				SpanningTree st = new SpanningTree();
				st.setEthLink(new EthLink(glink, "", false, true, 1));
				st.setVlan(new Vlan(id++, "vlan-ext", 0, 4096));
				
				sptrees.add(st);
			}
			
			
			genericLinks = new ArrayList<GenericLink>();
			for(SpanningTree st: sptrees) {
				genericLinks.add(st.getEthLink().getGenericLink());
			}

	    	Transaction t = DmHibernateUtil.getInstance().beginTransaction();
	    	
	    	clearIntradomainTopologyDatabase();
			saveTopology();
			
	    	t.commit();
		}
    }

    private void mergeTheNodes(List<SpanningTree> sptrees) {
    	Map<String, Node> tmp = new HashMap<String, Node>();
    	
    	for(SpanningTree st : sptrees) {
    		// Start node
    		Node snode = st.getEthLink().getGenericLink().getStartInterface().getNode();
    		String sname = snode.getName();
    		if(tmp.containsKey(sname)) {
    			st.getEthLink().getGenericLink().getStartInterface().setNode(tmp.get(sname));
    		} else {
    			tmp.put(snode.getName(), snode);	
    		}

    		// End node
    		Node enode = st.getEthLink().getGenericLink().getEndInterface().getNode();
    		String ename = enode.getName();
    		if(tmp.containsKey(ename)) {
    			st.getEthLink().getGenericLink().getEndInterface().setNode(tmp.get(ename));
    		} else {
    			tmp.put(enode.getName(), enode);	
    		}
    	}
    }
    
    private void zeroTheIdentifiers(List<SpanningTree> sptrees) {
    	for(SpanningTree st : sptrees) {
    		st.getEthLink().getGenericLink().setLinkId(0);
    		st.getEthLink().getGenericLink().getEndInterface().setInterfaceId(0);
    		st.getEthLink().getGenericLink().getStartInterface().setInterfaceId(0);
    		st.getEthLink().getGenericLink().getStartInterface().getNode().setNodeId(0);
    		st.getEthLink().getGenericLink().getEndInterface().getNode().setNodeId(0);
    		st.getVlan().setVlanId(0);
    	}
    }
    
    public void saveTopology() {
    	DmDAOFactory daos = HibernateDmDAOFactory.getInstance();
    	
    	if(isEthernet()) {
        	mergeTheNodes(sptrees);
        	zeroTheIdentifiers(sptrees);
    		
	        for(SpanningTree st : sptrees) {
	        	daos.getGenericLinkDAO().create(st.getEthLink().getGenericLink());
	        	daos.getEthLinkDAO().create(st.getEthLink());
	        	daos.getVlanDAO().create(st.getVlan());
	        	daos.getSpanningTreeDAO().create(st);
	        }
    	} else if(isSDH()) {
	        for(StmLink link : stmLinks) {
	        	daos.getGenericLinkDAO().create(link.getStmLink());
	        	daos.getStmLinkDAO().update(link);
	        }

	        for(SdhDevice dev : sdhDevices) {
	            daos.getNodeDAO().create(dev.getNode());
	        	daos.getSdhDeviceDAO().update(dev);
	        }
    	}
    }
    
	/**
	 * Helper method that checks whether a domain provided by cNIS is a client domain.
	 */
    private boolean isClientDomain(net.geant2.cnis.autobahn.xml.common.Domain d) {
		// Check whether this is a client port by looking at the domain tags
		// and search for a tag named "client" set to true.
		net.geant2.cnis.autobahn.xml.common.Tags dTags = d.getTags();
		for (net.geant2.cnis.autobahn.xml.common.Tag tag: dTags.getTag()) {
			if ( (tag.getKey().equals("client")) && (tag.getValue().equals("true")) ) {
				return true;						
			}
		}
		return false;
    }
    
	/**
	 * Helper method that retrieves description tag from an interdomain link.
	 */
    private String getExternalDomainDescription(net.geant2.cnis.autobahn.xml.common.Domain d) {
		// Check whether this is a client port by looking at the domain tags
		// and search for a tag named "client" set to true.
		net.geant2.cnis.autobahn.xml.common.Tags dTags = d.getTags();
		for (net.geant2.cnis.autobahn.xml.common.Tag tag: dTags.getTag()) {
			if (tag.getKey().equals("description")) {
				return tag.getValue();
			}
		}
		return null;
    }
    
    /**
     * Helper method that retrieves idcp link mapping for idcp link
     * Link mapping should be provided in a form of:
     * idcplink=identifier of the link that is directed towards autobahn (this identifier must be provided by idcp side)
     * @param d
     * @return
     */
    private String getIdcpLink(net.geant2.cnis.autobahn.xml.common.Domain d) {
    	
    	net.geant2.cnis.autobahn.xml.common.Tags dTags = d.getTags();
		for (net.geant2.cnis.autobahn.xml.common.Tag tag: dTags.getTag()) {
			if (tag.getKey().contains("idcplink")) {
				return tag.getValue().replace("!", "="); // we must restore original identifier as cnis gui does not allow multiple '='
			}
		}
		return null;
    }

    
	/**
	 * Helper method useful for clearing from the database all the information
	 * on intradomain topology.
	 */
    public static void clearIntradomainTopologyDatabase() {
    	DmDAOFactory daos = HibernateDmDAOFactory.getInstance();

    	SpanningTreeDAO st_dao = daos.getSpanningTreeDAO();
    	EthLinkDAO eth_dao = daos.getEthLinkDAO();
    	GenericLinkDAO gldao = daos.getGenericLinkDAO();
    	VlanDAO vldao = daos.getVlanDAO();
    	GenericInterfaceDAO gifdao = daos.getGenericInterfaceDAO();
    	NodeDAO ndao = daos.getNodeDAO();

    	daos.getStmLinkDAO().deleteAll();
    	daos.getSdhDeviceDAO().deleteAll();
    	
    	st_dao.deleteAll();
    	eth_dao.deleteAll();
    	gldao.deleteAll();
    	vldao.deleteAll();
    	gifdao.deleteAll();
    	ndao.deleteAll();
    }
	
    private RangeConstraint createRangeConstraint(List<Range> ranges) {
		RangeConstraint rcon = new RangeConstraint();
		
		for(Range r : ranges) {
			rcon.addRange(r.getFrom().intValue(), r.getTo().intValue());
		}
		
		return rcon;
    }
    
	/**
	 * @return List of all generic links of the domain
	 */
	public List<GenericLink> getGenericLinks() {
		return genericLinks;
	}

	/**
	 * @return List of all nodes of the domain
	 */
	public List<Node> getNodes() {
		return nodes;
	}

	/**
	 * @return List of all spanning trees of the domain - only ethernet domains
	 */
	public List<SpanningTree> getSpanningTrees() {
		return sptrees;
	}

	/**
	 * @return List of all ethernet physical ports of the domain - only ethernet domains
	 */
	public List<EthPhysicalPort> getEthPhysicalPorts() {
		return ethpp;
	}

	/**
	 * @return  List of all vlan ports of the domain - only ethernet domains
	 */
	public List<VlanPort> getVlanPorts() {
		return vlaps;
	}

	/**
	 * @return  List of all STM link of the domain - only sdh
	 */
	public List<StmLink> getStmLinks() {
		return stmLinks;
	}

	/**
	 * @return  List of all sdh devices of the domain - only sdh
	 */
	public List<SdhDevice> getSdhDevices() {
		return sdhDevices;
	}
    
	/**
	 * 
	 * @return True if the topology is of ethernet type
	 */
    public boolean isEthernet() {
    	return type == Type.ETH;
    }
    
    /**
     * 
     * @return True if the topology is of sdh type
     */
    public boolean isSDH() {
    	return type == Type.SDH;
    }
    
    public boolean isEmpty() {
    	if(isEthernet()) {
    		return getSpanningTrees() == null || getSpanningTrees().size() == 0;
    	} else if(isSDH()) {
    		return getSdhDevices() == null || getSdhDevices().size() == 0;
    	}
    	
    	return true;
    }
    
    // The following functions were added for adherence to 
    // Javabean conventions (XML serialization requirement)
    
    public void setGenericLinks(List<GenericLink> genericLinks){
        this.genericLinks = genericLinks;
    }
    
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
    
    public void setSptrees(List<SpanningTree> sptrees) {
        this.sptrees = sptrees;
    }
    
    public void setEthpp(List<EthPhysicalPort> ethpp) {
        this.ethpp = ethpp;
    }
    
    public void setVlaps(List<VlanPort> vlaps) {
        this.vlaps = vlaps;
    }
    
    public void setStmLinks(List<StmLink> stmLinks) {
        this.stmLinks = stmLinks;
    }
    
    public void setSdhDevices(List<SdhDevice> sdhDevices) {
        this.sdhDevices = sdhDevices;
    }
    
    public void setType(Type type) {
        this.type = type;
    }
    
    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
    
    public String getDomainName() {
        return domainName;
    }
    
    public List<SpanningTree> getSptrees() {
        return sptrees;
    }
    
    public List<EthPhysicalPort> getEthpp() {
        return ethpp;
    }
    
    public List<VlanPort> getVlaps() {
        return vlaps;
    }
    
    public Type getType() {
        return type;
    }
}
