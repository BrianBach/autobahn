package net.geant.autobahn.intradomain;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import net.geant.autobahn.intradomain.mpls.MplsLink;
import net.geant.autobahn.intradomain.sdh.SdhDevice;
import net.geant.autobahn.intradomain.sdh.StmLink;
import net.geant.autobahn.intradomain.sdh.StmType;
import net.geant.autobahn.utils.MyProperties;
import net.geant2.cnis.autobahn.CnisClient;
import net.geant2.cnis.autobahn.xml.CnisToAutobahnResponse;
import net.geant2.cnis.autobahn.xml.ethernet.IDLink;
import net.geant2.cnis.autobahn.xml.ethernet.Link;
import net.geant2.cnis.autobahn.xml.ethernet.PhysicalPort;
import net.geant2.cnis.autobahn.xml.ethernet.Range;
import net.geant2.cnis.autobahn.xml.mpls.InterdomainLink;
import net.geant2.cnis.autobahn.xml.mpls.IntradomainLink;
import net.geant2.cnis.autobahn.xml.sdh.PhyInterface;
import net.geant2.cnis.autobahn.xml.sdh.PhyLink;

import org.apache.commons.lang.StringEscapeUtils;
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
        "stmLinks", "sdhDevices", "mplsLinks", "type", "domainName"
})
public class IntradomainTopology {

	private final Logger log = Logger.getLogger(IntradomainTopology.class);

	@XmlType(name="Type", namespace="intradomain.autobahn.geant.net")
	public enum Type {ETH, SDH, MPLS};

    private List<GenericLink> genericLinks = null;
    private List<Node> nodes = null;

    // eth specific
	private List<SpanningTree> sptrees = new ArrayList<SpanningTree>();
	private List<EthPhysicalPort> ethpp = null;
	private List<VlanPort> vlaps = null;

	// sdh specific
	private List<StmLink> stmLinks = null;
	private List<SdhDevice> sdhDevices = null;
	
	// mpls specific
	private List<MplsLink> mplsLinks = null;
    
	private Type type;
	private String domainName;

	// Delimiter used to create GenericInterface name
	// (by concatanating it with port name)
	public static final String INTERFACE_DELIM = ";;";
	
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
	 *            String type of the topology, "eth" or "sdh" or "mpls"
	 */
    public IntradomainTopology(String cnisAddress, String domainId, String topologyType) {
    	if("sdh".equalsIgnoreCase(topologyType))
    		type = Type.SDH;
    	else if (topologyType.equalsIgnoreCase("eth") || topologyType.equalsIgnoreCase("ethernet"))
    		type = Type.ETH;
    	else if (topologyType.equalsIgnoreCase("mpls"))
    		type = Type.MPLS;

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
				log.info("Unable to use cNIS: " + e.getMessage() + " - Autobahn reads " +
						"topology from DB. If you want to use cNIS, try to resolve this " +
						"issue. Also make sure that the cnis.address property in the " +
						"properties file is correct.");
				log.debug("cNIS error info: ", e);
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

            // Edge links will probably be Ethernet (possibly also vlan tagged)
            sptrees = daos.getSpanningTreeDAO().getAll();
            for (SpanningTree st: sptrees) {
                genericLinks.add(st.getEthLink().getGenericLink());
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
		} else if (isMpls()) {
			
			mplsLinks = daos.getMplsLinkDAO().getAll();
			genericLinks = new ArrayList<GenericLink>();
			
			for (MplsLink ml : mplsLinks) {
				genericLinks.add(ml.getGenericLink());
			}
			
			nodes = daos.getNodeDAO().getAll();
		}
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("\n----Intradomain Topology [log="
                + log + ", genericLinks=" + genericLinks + ", nodes=" + nodes
                + ", sptrees=" + sptrees + ", ethpp=" + ethpp + ", vlaps="
                + vlaps + ", stmLinks=" + stmLinks + ", sdhDevices="
                + sdhDevices + ", mplsLinks=" + mplsLinks + ", type=" + type
                + ", domainName=" + domainName + "]");

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
                sb.append("\n|-sptrees:");
                if (sptrees!=null) {
                    sb.append(" size:"+sptrees.size()+"\n");
                    for (SpanningTree st: sptrees) {
                        sb.append("    st ethlink generic link:"+st.getEthLink().getGenericLink()+"\n");
                        sb.append("    st vlans               :"+st.getVlan().getLowNumber() + " " + st.getVlan().getHighNumber() +"\n");
                    }
                }
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
            if (isMpls()) {
                sb.append("\n|-Is MPLS.\n|-mplsLinks:");
                if (mplsLinks != null) {
                    sb.append(" size: " + mplsLinks.size() + "\n");
                    for (MplsLink ml: mplsLinks) {
                        sb.append(ml.toString() + "\n");
                    }
                }
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
		
    	InputStream is = getClass().getClassLoader().getResourceAsStream("etc/public_ids.properties");
    	MyProperties publicIds = null;
    	if(is != null) {
    		publicIds = new MyProperties(is);
    	}
		
        List<GenericInterface> duplicateExceptions = new ArrayList<GenericInterface>();
        
		if(isSDH()) {
			nodes = new ArrayList<Node>();
			
			//STM-1 = 150336000
			Map<String, GenericInterface> ports = new HashMap<String, GenericInterface>();
			
			for(net.geant2.cnis.autobahn.xml.sdh.Node n : resp.getSdhTopology().getNodes().getNode()) {
				Node node = new Node();
				node.setNodeId(0);
				
				node.setName(unescHtml(n.getName()));
				// We assume that all the nodes in SDh have the vlan translation support enabled
				node.setVlanTranslationSupport(true);
				
				for(PhyInterface p : n.getPhyInterfaces().getInterface()) {
					GenericInterface port = new GenericInterface();
					
					port.setInterfaceId(0);
					port.setNode(node);
					port.setDomainId(domainName);
					port.setClientPort(false);
                    String unescapedPortName = unescHtml(n.getName())
                            + INTERFACE_DELIM + unescHtml(p.getName());
					port.setName(unescapedPortName);
					
					String pub = unescHtml(getPublicName(p));
					if(pub != null) {
						log.info("Received public identifier from cNIS: " + unescapedPortName + " " + pub);
						publicIds.setProperty(unescapedPortName, pub);
					}
					
                    for (net.geant2.cnis.autobahn.xml.common.Tag tag: p.getTags().getTag()) {
                        //mtu info added
                        if (tag.getKey().equalsIgnoreCase("mtu")) {
                            port.setMtu(Integer.parseInt(tag.getValue()));
                        }
                        
                        // Check for ports to be excepted from "multiple links
                        // on same port not allowed" rule
                        if ((tag.getKey().equalsIgnoreCase("multiple-links-allowed"))
                                && (tag.getValue().equalsIgnoreCase("true"))) {
                                duplicateExceptions.add(port);
                        }
                    }

					ports.put(unescapedPortName, port);
				}
				
				nodes.add(node);
				
			}
			
			genericLinks = new ArrayList<GenericLink>();
			
			for(PhyLink l : resp.getSdhTopology().getIntradomainLinks().getLink()) {
				GenericLink glink = new GenericLink();
				
                String sname = unescHtml(l.getStartNode().getName())
                        + INTERFACE_DELIM
                        + unescHtml(l.getStartInterface().getName());
                GenericInterface sport = ports.get(sname);
				glink.setStartInterface(sport);
				
                String ename = unescHtml(l.getEndNode().getName())
                        + INTERFACE_DELIM
                        + unescHtml(l.getEndInterface().getName());
                GenericInterface dport = ports.get(ename);
				glink.setEndInterface(dport);

				sport.setBandwidth(l.getBandwidth().longValue());
				dport.setBandwidth(l.getBandwidth().longValue());
				
				genericLinks.add(glink);
			}

			int id = 0;
			for(net.geant2.cnis.autobahn.xml.sdh.IDLink l : resp.getSdhTopology().getInterdomainLinks().getLink()) {
				GenericLink glink = new GenericLink();
				
                String sname = unescHtml(l.getStartNode().getName())
                        + INTERFACE_DELIM
                        + unescHtml(l.getStartPort().getName());
                GenericInterface sport = ports.get(sname);
				sport.setBandwidth(l.getBandwidth().longValue());
				glink.setStartInterface(sport);

				Node dnode = new Node();
				dnode.setNodeId(0);
				dnode.setName("external-node-" + ++id);
				nodes.add(dnode);

				GenericInterface dport = new GenericInterface();
				dport.setName(unescHtml(l.getEndPortId()));
				dport.setInterfaceId(0);
				dport.setNode(dnode);
				dport.setBandwidth(l.getBandwidth().longValue());
                String domainId = unescHtml(l.getExternalDomain().getId());
				dport.setDomainId(domainId);
				dport.setClientPort(isClientDomain(l.getExternalDomain()));
                dport.setDescription(unescHtml(getExternalDomainDescription(
                        l.getExternalDomain())));

				glink.setEndInterface(dport);
				
                String idcpLink = unescHtml(getIdcpLink(l.getExternalDomain()));
				if (idcpLink != null) 
					dport.setDescription(dport.getDescription() + "\n" + "idcplink=" + idcpLink);

				EthLink elink = new EthLink(glink, "", false, true, 1);
				String vlan = getVlansForSDH(l.getExternalDomain());
				
				if (vlan != null) {
					RangeConstraint rcon = new RangeConstraint(vlan);
					for(net.geant.autobahn.constraints.Range r : rcon.getRanges()) {
						SpanningTree st = new SpanningTree();
						
						st.setEthLink(elink);
						st.setVlan(new Vlan(id++, "vlan-ext", r.getMin(), r.getMax()));
						
						sptrees.add(st);
					}
				} else {
					SpanningTree st = new SpanningTree();
					
					st.setEthLink(elink);
					st.setVlan(new Vlan(id++, "vlan-ext", 0, 4096));
					
					sptrees.add(st);
				}
			}

			// Create a list with all generic links to test for duplicates
			List <GenericLink> glinkList = new ArrayList<GenericLink>(genericLinks);
			for (SpanningTree st : sptrees) {
			    glinkList.add(st.getEthLink().getGenericLink());
			}
            GenericInterface duplicateIf = getDuplicateInterfaces(glinkList, duplicateExceptions);
            if (duplicateIf != null) {
                nodes.clear();
                genericLinks.clear();
                sptrees.clear();
                throw new Exception("cNIS sdh topology is not valid, interface "
                        + duplicateIf + " is associated with more than 1 link");
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
				node.setName(unescHtml(n.getName()));
				node.setIpAddress(n.getIpAddress());
				node.setVlanTranslationSupport(n.isVlanTranslation());
				n_to_vlans.put(node.getName(), n.getVlanRanges().getRange());
				
				for(PhysicalPort p : n.getPhysicalPorts().getPort()) {
					GenericInterface port = new GenericInterface();

                    port.setName(unescHtml(n.getName())
                            + INTERFACE_DELIM
                            + unescHtml(p.getName()));
					
					String pub = unescHtml(getPublicName(p));
					if(pub != null) {
						log.info("Received public identifier from cNIS: " + port.getName() + " " + pub);
						publicIds.setProperty(port.getName(), pub);
					}
					
					port.setInterfaceId(0);
					port.setNode(node);
					if(p.getBandwidth() == null || p.getBandwidth().longValue() == 0) {
						log.info("Bandwidth for port: "
								+ port.getName()
								+ " is not set. This may be a mistake in configuration. Please check the settings in cNIS.");
					} else {
						port.setBandwidth(p.getBandwidth().longValue());
					}
					
					port.setDomainId(domainName);
					port.setClientPort(false);
					for (net.geant2.cnis.autobahn.xml.common.Tag tag: p.getTags().getTag()) {
                        if (tag.getKey().equalsIgnoreCase("mtu")) {
                            port.setMtu(Integer.parseInt(tag.getValue()));
                        }
                        
                        // Check for ports to be excepted from "multiple links
                        // on same port not allowed" rule
                        if ((tag.getKey().equalsIgnoreCase("multiple-links-allowed"))
                                && (tag.getValue().equalsIgnoreCase("true"))) {
                                duplicateExceptions.add(port);
                        }
                    }

					ports.put(port.getName(), port);
				}
				
				nodes.add(node);
			}

			sptrees = new ArrayList<SpanningTree>(); 
			List<Link> links = resp.getEthTopology().getIntradomainLinks().getLink();
			
			for(Link link : links) {
				GenericLink glink = new GenericLink();
                String sname = unescHtml(link.getStartNode().getName())
                        + INTERFACE_DELIM
                        + unescHtml(link.getStartPort().getName());
				GenericInterface sport = ports.get(sname);
				glink.setStartInterface(sport);
				
                String ename = unescHtml(link.getEndNode().getName())
                        + INTERFACE_DELIM
                        + unescHtml(link.getEndPort().getName());
				GenericInterface dport = ports.get(ename);
				glink.setEndInterface(dport);
				
				glink.setLinkId(++id);

				RangeConstraint vlansStartNode = createRangeConstraint(n_to_vlans.get(sport.getNode().getName()));
				RangeConstraint vlansEndNode = createRangeConstraint(n_to_vlans.get(dport.getNode().getName()));
				
				RangeConstraint vlans = vlansStartNode.intersect(vlansEndNode);
				
				EthLink ethLink = new EthLink(glink, "", false, false, 0);
				
				if (vlans == null || vlans.getRanges() == null) {
				    throw new Exception("No vlan range for link " + glink);
				}
                for (net.geant.autobahn.constraints.Range r : vlans.getRanges()) {
                    SpanningTree st = new SpanningTree();
                    st.setEthLink(ethLink);
                    st.setVlan(new Vlan(++id, "vlan-x", r.getMin(), r.getMax()));

                    sptrees.add(st);
                }
			}
			
			List<IDLink> id_links = resp.getEthTopology().getInterdomainLinks().getLink();
			
			for(IDLink l : id_links) {
				GenericLink glink = new GenericLink();
				
                String sname = unescHtml(l.getStartNode().getName())
                        + INTERFACE_DELIM
                        + unescHtml(l.getStartPort().getName());
				GenericInterface sport = ports.get(sname);
				glink.setStartInterface(sport);

				Node dnode = new Node();
				dnode.setNodeId(0);
				dnode.setName("external-node-" + id);
				nodes.add(dnode);
				
				GenericInterface dport = new GenericInterface();
				dport.setName(unescHtml(l.getEndPortId()));
				dport.setInterfaceId(0);
				dport.setNode(dnode);
				dport.setBandwidth(l.getBandwidth().longValue());
				String domainId = unescHtml(l.getExternalDomain().getId());
				dport.setDomainId(domainId);
				dport.setClientPort(isClientDomain(l.getExternalDomain()));						
                dport.setDescription(unescHtml(getExternalDomainDescription(
                        l.getExternalDomain())));
				
				glink.setEndInterface(dport);
				
				String idcpLink = getIdcpLink(l.getExternalDomain());
				if (idcpLink != null) 
					dport.setDescription(dport.getDescription() + "\n" + "idcplink=" + idcpLink);
				
				EthLink ethLink = new EthLink(glink, "", false, true, 1);
				
				for(Range r : l.getVlanRanges().getRange()) {
					SpanningTree st = new SpanningTree();
					
					st.setEthLink(ethLink);
					st.setVlan(new Vlan(id++, "vlan-ext", Integer.valueOf("" + r.getFrom()), Integer.valueOf("" + r.getTo())));
					
					sptrees.add(st);
				}
			}
			
			genericLinks = new ArrayList<GenericLink>();
			for(SpanningTree st: sptrees) {
				genericLinks.add(st.getEthLink().getGenericLink());
			}

            GenericInterface duplicateIf = getDuplicateInterfaces(genericLinks, duplicateExceptions);
            if (duplicateIf != null) {
                nodes.clear();
                genericLinks.clear();
                sptrees.clear();
                throw new Exception("cNIS ethernet topology is not valid, interface "
                        + duplicateIf + " is associated with more than 1 link");
            }
            
	    	Transaction t = DmHibernateUtil.getInstance().beginTransaction();
	    	
	    	clearIntradomainTopologyDatabase();
			saveTopology();
			
	    	t.commit();
	    	
		} else if (isMpls()) {  

			int id = 0;
			nodes = new ArrayList<Node>();
			Map<String, GenericInterface> ports = new HashMap<String, GenericInterface>();
			
			List<net.geant2.cnis.autobahn.xml.mpls.Node> mplsNodes = resp.getMplsTopology().getNodes().getNode();

			for (net.geant2.cnis.autobahn.xml.mpls.Node n : mplsNodes) {
				
				Node node = new Node();
				node.setNodeId(0);
				node.setName(unescHtml(n.getName()));
				node.setIpAddress(n.getIpAddress());
				
				List<net.geant2.cnis.autobahn.xml.mpls.Port> mplsPorts = n.getPorts().getPort();
				for (net.geant2.cnis.autobahn.xml.mpls.Port p : mplsPorts) {
					
					GenericInterface port = new GenericInterface();
                    port.setName(unescHtml(n.getName())
                            + INTERFACE_DELIM
                            + unescHtml(p.getName()));
					
					String pub = unescHtml(getPublicName(p));
					if(pub != null) {
						log.info("Received public identifier from cNIS: " + port.getName() + " " + pub);
						publicIds.setProperty(port.getName(), pub);
					}

					port.setBandwidth(10000000); 
					port.setInterfaceId(0);
					port.setNode(node);
					port.setDomainId(domainName);
					port.setClientPort(false);
					
					ports.put(port.getName(), port);
				}
				nodes.add(node);
			}
			
			mplsLinks = new ArrayList<MplsLink>();
			
			List<IntradomainLink> intraLinks = resp.getMplsTopology().getIntradomainLinks().getIntradomainLink();
			for (IntradomainLink intraLink : intraLinks) { 
				
				GenericLink link = new GenericLink();
				link.setLinkId(++id);
                String startName = unescHtml(intraLink.getStartNode().getName())
                        + INTERFACE_DELIM
                        + unescHtml(intraLink.getStartPort().getName());
				GenericInterface startPort = ports.get(startName);
				link.setStartInterface(startPort);
				
                String endName = unescHtml(intraLink.getEndNode().getName())
                        + INTERFACE_DELIM
                        + unescHtml(intraLink.getEndPort().getName());
				GenericInterface endPort = ports.get(endName);
				link.setEndInterface(endPort);
				
				MplsLink mplsLink = new MplsLink();
				mplsLink.setGenericLink(link);
				mplsLinks.add(mplsLink);
			}

			List<InterdomainLink> interLinks = resp.getMplsTopology().getInterdomainLinks().getInterdomainLink();
			for (InterdomainLink interLink : interLinks) {
				
				GenericLink link = new GenericLink();
                String startName = unescHtml(interLink.getStartNode().getName())
                        + INTERFACE_DELIM
                        + unescHtml(interLink.getStartPort().getName());
				GenericInterface startPort = ports.get(startName);
				link.setStartInterface(startPort);
				
				Node endNode = new Node();
				endNode.setNodeId(0L);
				endNode.setName("external-node-" + id);
				nodes.add(endNode);
				
				GenericInterface endPort = new GenericInterface();
				endPort.setName(unescHtml(interLink.getExternalPortId()));
				endPort.setInterfaceId(0);
				endPort.setBandwidth(interLink.getBandwidth().longValue());
				endPort.setNode(endNode);
				String domainId = unescHtml(interLink.getExternalDomain().getId());
				endPort.setDomainId(domainId);
				endPort.setClientPort(isClientDomain(interLink.getExternalDomain()));
                endPort.setDescription(unescHtml(getExternalDomainDescription(
                        interLink.getExternalDomain())));
				link.setEndInterface(endPort);
				
				String idcpLink = getIdcpLink(interLink.getExternalDomain());
				if (idcpLink != null) 
					endPort.setDescription(endPort.getDescription() + "\n" + "idcplink=" + idcpLink);
				
				MplsLink mplsLink = new MplsLink();
				mplsLink.setGenericLink(link);
				mplsLinks.add(mplsLink);
			}
			
	    	Transaction t = DmHibernateUtil.getInstance().beginTransaction();
	    	
	    	clearIntradomainTopologyDatabase();
			saveTopology();
			
	    	t.commit();
		}
		
		publicIds.save(new File("etc/public_ids.properties"));
    }
    
    private String unescHtml(String str) {
        return StringEscapeUtils.unescapeHtml(str);
    }

    /**
     * Checks if the list of generic links contains interfaces that are used in
     * multiple links
     * 
     * @param glinkList
     *            - the list of links to be checked for duplicate ports
     * @param exceptions
     *            - the ports that are allowed to be attached to multiple links.
     *            If one of these ports is found, it will not be returned
     * @return the first duplicate interface found, or null if none found
     */
    private GenericInterface getDuplicateInterfaces(
            List<GenericLink> glinkList, List<GenericInterface> exceptions) {
        if (glinkList == null) {
            return null;
        }
        // Use a Set to avoid duplicate GenericLink entries
        Set<GenericLink> glinkSet = new HashSet<GenericLink>(glinkList);

        List<GenericInterface> gifList = new ArrayList<GenericInterface>();
        
        for (GenericLink gl : glinkSet) {
            GenericInterface startIf = gl.getStartInterface();
            GenericInterface endIf = gl.getEndInterface();
            if (gifList.contains(startIf)) {
                if (exceptions != null && !exceptions.contains(startIf)) {
                    return startIf;
                }
            }
            if (gifList.contains(endIf)) {
                if (exceptions != null && !exceptions.contains(endIf)) {
                    return endIf;
                }
            }
            gifList.add(startIf);
            gifList.add(endIf);
        }
        
        return null;
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
        	zeroTheIdentifiers(sptrees);
    		
	        for(SpanningTree st : sptrees) {
	        	daos.getGenericLinkDAO().create(st.getEthLink().getGenericLink());
	        	daos.getEthLinkDAO().create(st.getEthLink());
	        	daos.getVlanDAO().create(st.getVlan());
	        	daos.getSpanningTreeDAO().create(st);
	        }
    		
	        for(StmLink link : stmLinks) {
	        	daos.getGenericLinkDAO().create(link.getStmLink());
	        	daos.getStmLinkDAO().update(link);
	        }

	        for(SdhDevice dev : sdhDevices) {
	            daos.getNodeDAO().create(dev.getNode());
	        	daos.getSdhDeviceDAO().update(dev);
	        }
    	} else if (isMpls()) {
    		
    		for (MplsLink link : mplsLinks) {
    			daos.getGenericLinkDAO().create(link.getGenericLink());
    			daos.getMplsLinkDAO().update(link);
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
			if ( (tag.getKey().equalsIgnoreCase("client")) && (tag.getValue().equalsIgnoreCase("true")) ) {
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
			if (tag.getKey().equalsIgnoreCase("description")) {
				return tag.getValue();
			}
		}
		return null;
    }

	/**
	 * Helper method that retrieves description tag from an interdomain link.
	 */
    private String getVlansForSDH(net.geant2.cnis.autobahn.xml.common.Domain d) {
		// Check whether this is a client port by looking at the domain tags
		// and search for a tag named "client" set to true.
		net.geant2.cnis.autobahn.xml.common.Tags dTags = d.getTags();
		for (net.geant2.cnis.autobahn.xml.common.Tag tag: dTags.getTag()) {
			if (tag.getKey().equalsIgnoreCase("vlans")) {
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
     * @param d
     * @return
     */
    private String getPublicName(net.geant2.cnis.autobahn.xml.sdh.PhyInterface p) {
    	net.geant2.cnis.autobahn.xml.common.Tags dTags = p.getTags();
    	
    	if(dTags == null)
    		return null;
    	
		for (net.geant2.cnis.autobahn.xml.common.Tag tag: dTags.getTag()) {
			if(tag.getKey().contains("public-name")) {
				return tag.getValue();
			}
		}
		
		return null;
    }
    
    /**
     * @param d
     * @return
     */
    private String getPublicName(net.geant2.cnis.autobahn.xml.ethernet.PhysicalPort p) {
    	net.geant2.cnis.autobahn.xml.common.Tags dTags = p.getTags();
    	
    	if(dTags == null)
    		return null;
    	
		for (net.geant2.cnis.autobahn.xml.common.Tag tag: dTags.getTag()) {
			if(tag.getKey().contains("public-name")) {
				return tag.getValue();
			}
		}
		
		return null;
    }
    
    /**
     * 
     * @param p
     * @return
     */
    private String getPublicName(net.geant2.cnis.autobahn.xml.mpls.Port p) {
    	net.geant2.cnis.autobahn.xml.common.Tags dTags = p.getTags();
    	
    	if(dTags == null)
    		return null;
    	
		for (net.geant2.cnis.autobahn.xml.common.Tag tag: dTags.getTag()) {
			if(tag.getKey().contains("public-name")) {
				return tag.getValue();
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
    	
    	daos.getMplsLinkDAO().deleteAll();
    	
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
	 * @return List of all MplsLink of the domain - only mpls
	 */
	public List<MplsLink> getMplsLinks() {
		return mplsLinks;
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
    
    /**
     * 
     * @return True if the topology is of mpls type
     */
    public boolean isMpls() {
    	return type == Type.MPLS;
    }
    
    public boolean isEmpty() {
    	if(isEthernet()) {
    		return getSpanningTrees() == null || getSpanningTrees().size() == 0;
    	} else if(isSDH()) {
    		return getSdhDevices() == null || getSdhDevices().size() == 0;
    	} else if (isMpls()) {
    		return getMplsLinks() == null || getMplsLinks().size() == 0;
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
    
    public void setMplsLinks(List<MplsLink> mplsLinks) { 
    	this.mplsLinks = mplsLinks;
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
