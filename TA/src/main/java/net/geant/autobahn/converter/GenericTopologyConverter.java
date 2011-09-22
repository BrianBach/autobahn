package net.geant.autobahn.converter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.common.GenericInterface;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.converter.Stats;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder;
import net.geant.autobahn.lookup.LookupService;
import net.geant.autobahn.lookup.LookupServiceException;
import net.geant.autobahn.network.AdminDomain;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.LinkIdentifiers;
import net.geant.autobahn.network.LinkType;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.network.ProvisioningDomain;
import net.geant.autobahn.network.StateAdmin;
import net.geant.autobahn.network.StateOper;
import net.geant.autobahn.topologyabstraction.ExternalIdentifiersSource;

import org.apache.log4j.Logger;


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
    private Map<String, net.geant.autobahn.network.Node> absNodes = new 
        HashMap<String, net.geant.autobahn.network.Node>();
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

    public enum NeighborStatus {CONNECTED, PENDING, FAILED};
    private Map<String, NeighborStatus> neighbors = new HashMap<String, NeighborStatus>();
    
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
	 * @see net.geant.autobahn.intradomain.converter.TopologyConverter#abstractInternalPartOfTopology()
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
        	
        	if(!startDomainID.equalsIgnoreCase(endDomainID)) {
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
	 * @see net.geant.autobahn.intradomain.converter.TopologyConverter#abstractExternalPartOfTopology(net.geant.autobahn.intradomain.converter.ExternalIdentifiersSource)
	 */
	public List<Link> abstractExternalPartOfTopology(ExternalIdentifiersSource externalIds) {
		
		this.externalIds = externalIds;
		
		// Remove all old entries of edge ports from the LS
        if (isLSavailable(lookuphost)) {
            LookupService lookup = new LookupService(lookuphost);
            try {
                // Find a link with the same start and end domain Id, that
                // is an internal link and therefore our home domain Id
                if (genericLinks != null) {
                    for (GenericLink gl : genericLinks) {
                        if (gl != null) {
                            String candidateDomain = gl.getStartInterface().getDomainId();
                            if (candidateDomain.equals(gl.getEndInterface().getDomainId())) {
                                log.debug("Removing old edge port entries from the lookup" +
                                		" for domain " + candidateDomain);
                                lookup.RemoveAllEdgePorts(candidateDomain);
                                break;
                            }
                        }
                    }
                }
            } catch (LookupServiceException lse) {
                log.error("Old edge port entries could not be removed from LS");
                lse.printStackTrace();
            }
        }
		
		for(GenericLink glink : eLinks) {
			// Skip client devices
    		if(glink.getEndInterface().isClientPort()) {
    			continue;
    		}
			if (refreshInterdomainLink(glink)) {
			    log.info(glink.getStartInterface().getDomainId() + "-" + 
			            glink.getEndInterface().getDomainId() + " connectivity successful.");
			}
		}
		
		return getAbstractLinks();
	}
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.intradomain.converter.TopologyConverter#createAbstractLinks()
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

    /* (non-Javadoc)
	 * @see net.geant.autobahn.converter.TopologyConverter#getNeighborsStatus()
	 */
	@Override
	public Map<String, NeighborStatus> getNeighborsStatus() {
		return neighbors;
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
    
    private net.geant.autobahn.network.Node createNode(GenericInterface gif,
			String interName) {
    	
    	String nodeName = gif.getNode().getName();
        log.debug("Creating abstract node for node " + nodeName + "(" +gif.getNode().getNodeId() + ") where generic interface " + gif.getInterfaceId() + " is attached");
    	net.geant.autobahn.network.Node res = absNodes.get(nodeName);
    	
    	if(res == null) {
    		if(interName == null)
    			interName = internalIds.generateNodeID();

            res = new net.geant.autobahn.network.Node();
            
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
    		Port sport = new Port(bodID, "Ethernet", false, absNodes.get(nodename));

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

		neighbors.put(externalDomain, NeighborStatus.PENDING);
		
        String sportname = gl.getStartInterface().getName();
        // Register the local (start) port of the interdomain link at the LS
        if (isLSavailable(lookuphost)) {
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
        if (dportname == null && isLSavailable(lookuphost)) {
            LookupService lookup = new LookupService(lookuphost);
            ArrayList<String> edgePortIds;
            try {
                edgePortIds = lookup.QueryEdgePort(externalDomain, homeDomain);
            } catch (LookupServiceException e) {
                // Can't get identifier for remote edge port (LS is not working)
                log.info("Domain " + externalDomain + " is down, " +
                		"Add to waiting remote edge port of link: " + gl);
                AwaitingIdentifiers wait = getWaiting(gl.getEndInterface().getDomainId());
                wait.addLink(gl);
                return false;
            }

            if (edgePortIds == null || edgePortIds.size() ==0) {
                // Can't get identifier for remote edge port
                log.info("Domain " + externalDomain + " is down, " +
                		"Add to waiting remote edge port of link: " + gl);
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
			info.add("Mapping interdomain link " + gl + "\t to " + l.getBodID());
			edgeLinks.put(l, gl);
			return true;
		}

		l = tmpLinks.get(sportname);

		
		LinkIdentifiers identifiers = externalIds.getIdentifiers(externalDomain, dportname, l.getBodID());
		if(identifiers == null) {
			// Cannot get identifier for port or node
			log.info("Domain " + externalDomain + " is down, " +
					"Add to waiting: " + gl);
			neighbors.put(externalDomain, NeighborStatus.FAILED);
			AwaitingIdentifiers wait = getWaiting(gl.getEndInterface().getDomainId());
			wait.addLink(gl);
			return false;
		}
		
		//Create abs node
		net.geant.autobahn.network.Node dnode = createNode(gl.getEndInterface(), identifiers.getNodeId());

		//Create abs port
		String dportBodId = identifiers.getPortId();
		Port dport = new Port(dportBodId, "Ethernet", false, dnode);
		absPorts.put(dportname, dport);
		info.add("Mapping port " + dportname + "\t to " + dportBodId);

		l.setEndPort(dport);

		// Found id for links try to get waiting ids
		AwaitingIdentifiers wait = waiting.get(gl.getEndInterface().getDomainId());
		if(wait != null)
			wait.retry();
		
		l.setBodID(identifiers.getLinkId());

		neighbors.put(externalDomain, NeighborStatus.CONNECTED);
		
		// Add to links
		absLinks.put(sportname, l);
		edgeLinks.put(l, gl);

		info.add("Mapping interdomain link " + gl + "\t to " + identifiers.getLinkId());
		
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
    			String ddesc = glink.getEndInterface().getDescription();
    			if (ddesc == null || ddesc.equals("") || ddesc.equalsIgnoreCase("null")) {
    			    ddesc = bodID;
    			}
            	Port eport = new Port(bodID, "Ethernet", false, absNodes.get(dnode), ddesc);
            	
        		info.add("Mapping client port " + dname + "\t to " + bodID);
    		    
    		    // Register end port to LS
    			if (isLSavailable(lookuphost)) {
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
            	
            	info.add("Mapping client link " + sname + " - " + dname + "\t to " + bodID);
            	
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
            	Port sport = new Port(portname, "Ethernet", false, absNodes.get(snode.getName()));

        		info.add("Mapping port " + snode.getName() + "\t to " + portname);
            	
            	portname = internalIds.generatePortID();
            	Port eport = new Port(portname, "Ethernet", false, absNodes.get(dnode.getName()));
            	
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
	 * @see net.geant.autobahn.intradomain.converter.TopologyConverter#getEdgeLink(net.geant.autobahn.network.Link)
	 */
	public GenericLink getEdgeLink(Link l) {
		log.debug("Get edge link for link:"+l);
		
		return edgeLinks.get(l);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.intradomain.converter.IdentifiersSource#getIdentifiers(java.lang.String, java.lang.String)
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
		
		if (sport ==  null) {
		    log.warn("Public identifiers mapping: No identifier could be found for " + portName);
		}
		
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
		
		log.info("Searching for the waiting ids for domain: " + externalDomain);
		
		AwaitingIdentifiers wait = waiting.get(externalDomain);
		if(wait != null && !wait.isEmpty()) {
			log.info("Domain: " + externalDomain + " is up, retrieving ids...");
			wait.retry();
			
			// If awaiting identifier list for this domain was emptied, we have
			// successful connection to that domain
			if (wait.isEmpty()) {
				neighbors.put(externalDomain, NeighborStatus.CONNECTED);
			    log.info("Domain: " + externalDomain + " successfully attached");
			}
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
	
	private boolean isLSavailable(String ls) {
	    if ((ls == null) || ls.equalsIgnoreCase("none") || ls.equals("")) {
	        return false;
	    }
        // Check if it is a proper URL
        try {
            new URL(ls);
        } catch (MalformedURLException e) {
            log.debug(ls + " is not a proper URL for LS");
            return false;
        }
	    return true;
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
