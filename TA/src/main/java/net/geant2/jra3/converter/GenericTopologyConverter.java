package net.geant2.jra3.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.geant2.jra3.intradomain.IntradomainPath;
import net.geant2.jra3.intradomain.common.GenericInterface;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.common.Node;
import net.geant2.jra3.intradomain.pathfinder.IntradomainPathfinder;
import net.geant2.jra3.lookup.LookupService;
import net.geant2.jra3.lookup.LookupServiceException;
import net.geant2.jra3.network.AdminDomain;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.LinkIdentifiers;
import net.geant2.jra3.network.LinkType;
import net.geant2.jra3.network.Port;
import net.geant2.jra3.network.ProvisioningDomain;
import net.geant2.jra3.network.StateAdmin;
import net.geant2.jra3.network.StateOper;
import net.geant2.jra3.topologyabstraction.ExternalIdentifiersSource;

import org.apache.log4j.Logger;

import net.geant2.jra3.intradomain.converter.Stats;

/**
 * Generic Implementation of the topology converter. Provides common operations
 * for abstracting heterogenous networks. Keeps mapping between intradomain and
 * interdomain network representation.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 */
public abstract class GenericTopologyConverter implements TopologyConverter {
	
	private final Logger log = Logger.getLogger(this.getClass());
	
    private static final int PATH_LIMIT = 32;

    private InternalIdentifiersSource internalIds;
    private ExternalIdentifiersSource externalIds;
    private PublicIdentifiersMapping idMappings;
    
    private IntradomainPathfinder pathFinder = null;
    
    // Intra topology
    protected List<GenericLink> genericLinks = new ArrayList<GenericLink>();
    protected List<Node> nodes = new ArrayList<Node>();
    
    private Set<Node> routers = new HashSet<Node>();
    private List<GenericLink> eLinks = new ArrayList<GenericLink>();
    
    // Inter topology
    private Map<String, AwaitingIdentifiers> waiting = new HashMap<String, AwaitingIdentifiers>();
    private Map<String, ProvisioningDomain> pDomains = new HashMap<String, ProvisioningDomain>();
    private Map<String, net.geant2.jra3.network.Node> absNodes = new 
        HashMap<String, net.geant2.jra3.network.Node>();
    private Map<String, Port> absPorts = new HashMap<String, Port>();
    private Map<String, Link> absLinks = new HashMap<String, Link>();
    private Map<String, Link> tmpLinks = new HashMap<String, Link>();
    
    // Mapping between intra-domain and inter-domain level
    private Map<Link, GenericLink> edgeLinks = new HashMap<Link, GenericLink>();
    private List<Link> virtualLinks = new ArrayList<Link>();

    //Used by EthMonitoring class in order to identify virtual links
    private Map<Link, Node> sNodetoVLinks = new HashMap<Link, Node>();
    private Map<Link, Node> dNodetoVLinks = new HashMap<Link, Node>();
    
    // Textual info about network devices mapping
    private List<String> info = new ArrayList<String>();
    
    // Location of Lookup Server
    private String lookuphost = null;

    private boolean internalCalculationEnded = false;

    /**
     * Creates topology converter.
     * 
     * @param pathfinder Intradomain pathfinder
     * @param internal Source of the abstract identifiers for internal devices 
     * @param mapping Mapping between edge ports real names and public names
     * @param lookuphost URL of the lookup service
     */
	public GenericTopologyConverter(IntradomainPathfinder pathfinder, 
			InternalIdentifiersSource internal, PublicIdentifiersMapping mapping, String lookuphost) {
		this.pathFinder = pathfinder;
		this.internalIds = internal;
		this.idMappings = mapping;
		this.lookuphost = lookuphost;
	}
	
    /**
     * Creates topology converter without Lookup Service.
     * 
     * @param pathfinder Intradomain pathfinder
     * @param internal Source of the abstract identifiers for internal devices 
     * @param mapping Mapping between edge ports real names and public names
     */
	public GenericTopologyConverter(IntradomainPathfinder pathfinder, 
			InternalIdentifiersSource internal, PublicIdentifiersMapping mapping) {
		this.pathFinder = pathfinder;
		this.internalIds = internal;
		this.idMappings = mapping;		
	}
	

	/* (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.converter.TopologyConverter#abstractInternalPartOfTopology()
	 */
	public Stats abstractInternalPartOfTopology() {
        long s = System.currentTimeMillis();

        eLinks.clear();
        routers.clear();
        
        // Find links between two different domains
        for(GenericLink gl : genericLinks) {
        	String startDomainID = gl.getStartInterface().getDomainId();
        	String endDomainID = gl.getEndInterface().getDomainId();
        	
        	if(startDomainID == null || endDomainID == null)
        		continue;
        	
        	if(!startDomainID.equals(endDomainID)) {
        		eLinks.add(gl);
        		
        		routers.add(gl.getStartInterface().getNode());
        		routers.add(gl.getEndInterface().getNode());
        	}
        }

        createInternalNodes();
        Stats stats = createVirtualLinks();
        createInternalPorts();
        
        createClientLinks();
		createInterdomainLinks();
        
        stats.calcTime = (System.currentTimeMillis() - s) / 1000.0f;
        
        // Notify that calculation is finished
        internalCalculationEnded = true;
        synchronized(this) {
        	this.notify();
        }
        
        return stats;
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.converter.TopologyConverter#abstractExternalPartOfTopology(net.geant2.jra3.intradomain.converter.ExternalIdentifiersSource)
	 */
	public List<Link> abstractExternalPartOfTopology(ExternalIdentifiersSource externalIds) {
		
		this.externalIds = externalIds;
		
		for(GenericLink glink : eLinks) {
			// Skip client devices
    		if(glink.getEndInterface().isClientPort()) {
    			continue;
    		}
			refreshInterdomainLink(glink);
		}
		
		return getAbstractLinks();
	}
	
	/* (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.converter.TopologyConverter#createAbstractLinks()
	 */
	public List<Link> getAbstractLinks() {
        List<Link> result = new ArrayList<Link>();

		result.addAll(edgeLinks.keySet());
		result.addAll(virtualLinks);

		for(Link l : result) {
			l.setAdministrativeState(StateAdmin.NORMAL_OPERATION);
			l.setOperationalState(StateOper.UP);
			l.setType(LinkType.ID_LINK);
		}
		
		return result;
	}

    private ProvisioningDomain createProvisioningDomain(String domainID, boolean isClient) {
        if(domainID == null)
            return null;
        
        ProvisioningDomain pd = pDomains.get(domainID);
        if(pd == null) {
            AdminDomain ad = new AdminDomain();
            ad.setBodID(domainID);
            ad.setName(domainID);
            
           	ad.setClientDomain(isClient);
            
            pd = new ProvisioningDomain();
            pd.setAdminDomain(ad);
            pd.setBodID(domainID);
            
            pDomains.put(domainID, pd);
        }
        
        return pd;
    }
    
    private net.geant2.jra3.network.Node createNode(GenericInterface gif,
			String interName) {
    	
    	String nodeName = gif.getNode().getName();
    	net.geant2.jra3.network.Node res = absNodes.get(nodeName);
    	
    	if(res == null) {
    		if(interName == null)
    			interName = internalIds.generateNodeID();

            res = new net.geant2.jra3.network.Node();
            
            res.setBodID(interName);
            res.setProvisioningDomain(createProvisioningDomain(
            		gif.getDomainId(), gif.isClientPort()));
            
            info.add("Mapping node: " + nodeName + "\t to " + interName);
            
            absNodes.put(nodeName, res);
    	}
    	
    	return res;
    }
    
    private void createInternalNodes() {
        
        for(GenericLink glink : eLinks) {
        	// Start interface belongs to our domain
        	createNode(glink.getStartInterface(), null);
        	
        	// Add also client nodes
        	if(glink.getEndInterface().isClientPort()) {
        		createNode(glink.getEndInterface(), null);
        	}
        }
    }
    
	private void createInternalPorts() {
    	for(GenericLink gl : eLinks) {
    		// Start port
    		String sportname = gl.getStartInterface().getName();
    		String nodename = gl.getStartInterface().getNode().getName();
    		String bodID = internalIds.generatePortID();
    		Port sport = new Port(bodID, bodID, "Ethernet", false, absNodes.get(nodename));

    		info.add("Mapping port " + sportname + "\t to " + bodID);
    		
    		absPorts.put(sportname, sport);
    	}
	}
	
    private void createInterdomainLinks() {
    	
    	for(GenericLink gl : eLinks) {

    		if(gl.getEndInterface().isClientPort())
    			continue;
    		
    		String sportname = gl.getStartInterface().getName();
    		Port sport = absPorts.get(sportname);
    		
    		String dportname = gl.getEndInterface().getName();
    		Port dport = absPorts.get(dportname);
    		
    		// Link
    		Link l = Link.createInterDomainLink(sport, dport, gl.getCapacity());
    		String bodID = internalIds.generateLinkID();
    		
    		l.setBodID(bodID);
    		
    		tmpLinks.put(sportname, l);
    	}
    }
    
    private boolean refreshInterdomainLink(GenericLink gl) {
        
    	String homeDomain = gl.getStartInterface().getDomainId();
		String externalDomain = gl.getEndInterface().getDomainId();

        String sportname = gl.getStartInterface().getName();
        // Register the local (start) port of the interdomain link at the LS
        if (lookuphost != null) {
            LookupService lookup = new LookupService(lookuphost);
	        try {
	    		String sportPublicName = idMappings.getIdentifierFor(sportname);
	    		if (sportPublicName == null) {
	    			// No mapping to a public name for this port exists, so 
	    			// we just register the internal port name
	    			log.debug("Registering internal port name to lookup: " + sportname);
		            lookup.RegisterEdgePort(homeDomain, externalDomain, sportname);
	    		} else {
	    			// Register the public name corresponding to the edge port
	    			log.debug("Registering public port name to lookup: " + sportPublicName);
		            lookup.RegisterEdgePort(homeDomain, externalDomain, sportPublicName);
	    		}
	        } catch (LookupServiceException lse) {
	            log.error("Edge port to domain " + externalDomain + " could not be registered to LS");
	            lse.printStackTrace();
	        }
        }
        
        String dportname = gl.getEndInterface().getName();
        // If Database did not contain the remote (end) port of the 
        // interdomain link port name, try to query it from the LS
        if (dportname == null && lookuphost != null) {
            LookupService lookup = new LookupService(lookuphost);
            ArrayList<String> edgePortIds;
            try {
                edgePortIds = lookup.QueryEdgePort(externalDomain, homeDomain);
            } catch (LookupServiceException e) {
                // Can't get identifier for remote edge port (LS is not working)
                log.info("Add to waiting remote edge port of link: " + gl);
                AwaitingIdentifiers wait = getWaiting(gl.getEndInterface().getDomainId());
                wait.addLink(gl);
                return false;
            }

            if (edgePortIds == null || edgePortIds.size() ==0) {
                // Can't get identifier for remote edge port
                log.info("Add to waiting remote edge port of link: " + gl);
                AwaitingIdentifiers wait = getWaiting(gl.getEndInterface().getDomainId());
                wait.addLink(gl);
                return false;
            } else if (edgePortIds.size() > 1) {
                // Multi-homed connection, we have no way to identify edge port name
                log.error("Multi-homed connection to domain " + externalDomain + ", please add information manually in the Database");
                
                // This is a fatal error
                return false;
            } else {
                dportname = edgePortIds.get(0);
            }
        }

		Link l = absLinks.get(sportname);
		
		if (l != null) {
			log.warn(" -- -- Already refreshed ! " + gl);
			
			// Link id already established
			info.add("Mapping link " + gl + "\t to " + l.getBodID());
			edgeLinks.put(l, gl);
			return true;
		}

		l = tmpLinks.get(sportname);

		
		LinkIdentifiers identifiers = externalIds.getIdentifiers(externalDomain, dportname, l.getBodID());
		if(identifiers == null) {
			// Cant get identifier for port or node
			log.info("Add to waiting: " + gl);
			AwaitingIdentifiers wait = getWaiting(gl.getEndInterface().getDomainId());
			wait.addLink(gl);
			return false;
		}
		
		//Create abs node
		net.geant2.jra3.network.Node dnode = createNode(gl.getEndInterface(), identifiers.getNodeId());

		//Create abs port
		String dportBodId = identifiers.getPortId();
		Port dport = new Port(dportBodId, dportBodId, "Ethernet", false, dnode);
		absPorts.put(dportname, dport);
		info.add("Mapping port " + dportname + "\t to " + dportBodId);

		l.setEndPort(dport);

		// Found id for links try to get waiting ids
		AwaitingIdentifiers wait = waiting.get(gl.getEndInterface().getDomainId());
		if(wait != null)
			wait.retry();
		
		l.setBodID(identifiers.getLinkId());

		// Add to links
		absLinks.put(sportname, l);
		edgeLinks.put(l, gl);

		info.add("Mapping link " + gl + "\t to " + identifiers.getLinkId());
		
		return true;
    }
	
    private void createClientLinks() {
    	
    	for(GenericLink glink : eLinks) {
    		if(glink.getEndInterface().isClientPort()) {
    			// Start port
    			String sname = glink.getStartInterface().getName();
            	Port sport = absPorts.get(sname);
            	
            	// End port
        		String dname = glink.getEndInterface().getName();
        		String dnode = glink.getEndInterface().getNode().getName();
    			String bodID = internalIds.generatePortID();
            	Port eport = new Port(bodID, bodID, "Ethernet", false, absNodes.get(dnode));
            	
        		info.add("Mapping client port " + dname + "\t to " + bodID);
    		    
    		    // Register end port to LS
    			if (lookuphost != null) {
	    		    String domain = glink.getEndInterface().getDomainId();
	    		    String friendlyName = glink.getEndInterface().getDescription();
	    		    LookupService lookup = new LookupService(lookuphost);
	    		    try {
	                    lookup.RegisterEndPort(friendlyName, bodID, domain);
	                } catch (LookupServiceException lse) {
	                    lse.printStackTrace();
	                    log.info("End port " + bodID + " with friendly name " 
	                            + friendlyName + " could not be registered to LS");
	                }
    			}

        		// Link
            	Link l = Link.createInterDomainLink(sport, eport, glink.getCapacity());
            	
            	bodID = internalIds.generateLinkID();
            	l.setBodID(bodID);
            	
            	info.add("Mapping link " + sname + " - " + dname + "\t to " + bodID);
            	
            	edgeLinks.put(l, glink);
    		}
    	}
    }
    
    private Stats createVirtualLinks() {
    	virtualLinks.clear();
    	sNodetoVLinks.clear();
    	dNodetoVLinks.clear();
    	
    	Set<Node> domainRouters = new HashSet<Node>();
    	for(GenericLink glink : eLinks) {
    		domainRouters.add(glink.getStartInterface().getNode());
    	}
    	
        // Create VIRTUAL Links
    	int totalPaths = 0;
        Node[] routers_array = domainRouters.toArray(new Node[domainRouters.size()]);

        for(int i = 0; i < routers_array.length - 1; i++) {
        	Node snode = routers_array[i];
        	
        	for(int j = i + 1; j < routers_array.length; j++) {
        		Node dnode = routers_array[j];
        		
            	List<IntradomainPath> paths = pathFinder.findPaths(snode,
						dnode, null, PATH_LIMIT);
            	
            	if(paths == null || paths.size() < 1)
            		continue;
            	
            	long maxCapacity = Long.MIN_VALUE;
            	
            	for(IntradomainPath path : paths) {
            		if(path.getCapacity() > maxCapacity)
            			maxCapacity = path.getCapacity();
            	}
            	
            	String portname = internalIds.generatePortID();
            	Port sport = new Port(portname, portname, "Ethernet", false, absNodes.get(snode.getName()));

        		info.add("Mapping port " + snode.getName() + "\t to " + portname);
            	
            	portname = internalIds.generatePortID();
            	Port eport = new Port(portname, portname, "Ethernet", false, absNodes.get(dnode.getName()));
            	
            	info.add("Mapping port " + dnode.getName() + "\t to " + portname);
            	
            	Link l = Link.createVirtualLink(sport, eport, maxCapacity);
            	
            	String bodID = internalIds.generateLinkID();
            	l.setBodID(bodID);
            	
            	info.add("Mapping link " + snode.getName() + " - " + dnode.getName()
            			+ "\t to " + bodID + " (" + paths.size() + " paths)");
            	
            	totalPaths += paths.size();
            	
            	virtualLinks.add(l);
            	sNodetoVLinks.put(l,snode);
            	dNodetoVLinks.put(l,dnode);
        	}
        }
        
        return new Stats(nodes.size(), routers.size(), genericLinks.size(),
				totalPaths, 0);
    }
    
	/* (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.converter.TopologyConverter#getEdgeLink(net.geant2.jra3.network.Link)
	 */
	public GenericLink getEdgeLink(Link l) {
		log.debug("Get edge link for link:"+l);
		return edgeLinks.get(l);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.converter.IdentifiersSource#getIdentifiers(java.lang.String, java.lang.String)
	 */
	public LinkIdentifiers getIdentifiers(String portName, String linkBodId) {
		
		// Wait for the calculation to end
		if(!internalCalculationEnded) {
			log.warn("WAITING FOR CALCULATION END...");
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			log.warn("WAITING FOR CALCULATION ... GO !");
		}
		
		String sport = idMappings.getIdentifierFor((portName==null) ? null : portName.trim());
		
		Link l = tmpLinks.get(sport);
		
		if(l == null) {
			log.warn("Abstraction: No such link for port: " + sport);

			return null;
		}
		
		IpAddress myAddress = new IpAddress(l.getBodID());
		IpAddress address2 = new IpAddress(linkBodId);
		
		if(address2.getValue() > myAddress.getValue()) {
			l.setBodID(linkBodId);
		}

		// Get waiting identifiers for the domain that asked
		String externalDomain = getExternalDomainIdForPort(sport);
		
		AwaitingIdentifiers wait = waiting.get(externalDomain);
		if(wait != null && !wait.isEmpty()) {
			log.info("Domain: " + externalDomain + " is up, retrieving ids...");
			wait.retry();
		}
		
		LinkIdentifiers result = new LinkIdentifiers();
		result.setNodeId(absPorts.get(sport).getNode().getBodID());
		result.setPortId(absPorts.get(sport).getBodID());
		result.setLinkId(l.getBodID());
		
		return result;
	}

	private String getExternalDomainIdForPort(String sport) {
		for(GenericLink gl : eLinks) {
			if(gl.getStartInterface().getName().equals(sport)) {
				return gl.getEndInterface().getDomainId();
			}
		}
		
		return null;
	}
	
	private AwaitingIdentifiers getWaiting(String domainId) {
		AwaitingIdentifiers res = waiting.get(domainId);
		if(res == null) {
			res = new AwaitingIdentifiers();
			waiting.put(domainId, res);
		}
		
		return res;
	}

	/**
	 * Class used to store information that some identifiers could not be
	 * retrieved from external source (i.e. other AutoBAHN instance is not
	 * operating). This class also provides a possibility of retrying retrieval
	 * of the identifier.
	 * 
	 * @author jacek
	 */
	class AwaitingIdentifiers {
		private List<GenericLink> links = new ArrayList<GenericLink>();
		
		/**
		 * Called when for some reasons DM can't get the identifier for a link.
		 * 
		 * @param glink GenericLink link object
		 */
		public void addLink(GenericLink glink) {
			if(!links.contains(glink))
				links.add(glink);
		}

		/**
		 * Repeats the trial to get identifiers that could not be retrieved
		 * previously.
		 */
		public void retry() {
			List<GenericLink> temp = new ArrayList<GenericLink>(links);
			links.clear();
			
			for(GenericLink glink : temp) {
				if(!refreshInterdomainLink(glink))
					links.add(glink);
			}
		}
		
		public boolean isEmpty() {
			return links.size() == 0;
		}
	}
	
     /**
	 * Used by EthMonitoring class.
	 * @author <a href="mailto:gkamas@cti.gr">Gkamas Apostolos</a>
	 */
	public Set<Link> getAllEdgeLinks() {
		return edgeLinks.keySet();
	}

	/**
	 * Used by EthMonitoring class.
	 * @author <a href="mailto:gkamas@cti.gr">Gkamas Apostolos</a>
	 */
	public List<Link> getAllVirtualLinks() {
		return virtualLinks;
	}

	/**
	 * Used by EthMonitoring class.
	 * @author <a href="mailto:gkamas@cti.gr">Gkamas Apostolos</a>
	 */
	public Node getsNodeLink(Link l) {
		return sNodetoVLinks.get(l);
	}

	/**
	 * Used by EthMonitoring class.
	 * @author <a href="mailto:gkamas@cti.gr">Gkamas Apostolos</a>
	 */
	public Node getdNodeLink(Link l) {
		return dNodetoVLinks.get(l);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Mapping information:\n");
		for(String inf : info) {
			sb.append(inf + "\n");
		}
		
		return sb.toString();
	}
}
