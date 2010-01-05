/*
 * Link.java
 *
 * 2006-08-30
 */
package net.geant2.jra3.network;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * A Link class represents a circuit between two ports. The nodes connected by a
 * link can be:
 * - neighbours belonging to the same administrative domain
 * - neighbours belonging to different administrative domains (interdomain
 * links)
 * - non-neighbouring nodes
 * 
 * In the last case two options are possible:
 * - The link entity represents a connection among two edge ports of a domain
 * in order to represent the intra-domain part of an end-to-end path as a single
 * intra-domain link. This kind of link will be called "virtual links" and will
 * be used by the IDM path finding module to compute end-to-end paths.
 * 
 * - The link entity represents the union of two or more contiguous local
 * links. This kind of link will be called "composite link". A composite link is
 * represented exactly as an ordinary link and is treated as such by the path
 * finding algorithms. Composite links can only be composed by intra-domain
 * links.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Link", namespace="network.jra3.geant2.net", propOrder={
		"bodID", "kind", "startPort", "endPort", "bidirectional",
		"delay", "manualCost", "monetaryCost", "granularity",
		"minResCapacity", "maxResCapacity", "capacity", "resilience",
		"timestamp", "localName", "type", "operationalState",
		"administrativeState"
})
public class Link implements Serializable {

    private static final long serialVersionUID = 5609304394593312339L;
    
    /**
     * real link connecting neighbouring ports
     */
    public static int REGULAR = 0;
    
    /**
     * connection between two non-neighbouring nodes belonging to the same
     * domain, representing intra-domain path as a link at inter-domain level
     */
    public static int VIRTUAL = 1;
    
    /**
     * represents the union of two or more contiguous local links
     */
    public static int COMPOSITE = 2;
    
    /**
     * connecting two nodes belonging to different domains
     */
    public static int INTER_DOMAIN = 3;
    
    private String bodID;
    
    private int kind;
    private Port startPort;
    private Port endPort;
    private boolean bidirectional;
    private int delay;
    
    private double manualCost;
    private double monetaryCost;
    
    private long granularity;
    private long minResCapacity;
    private long maxResCapacity;
    private long capacity;
    
    private String resilience;

    private Date timestamp = null;
    private String localName;
    private LinkType type = null;
    private StateOper operationalState = null;
    private StateAdmin administrativeState = null;
    
    /**
     * Default constructor
     */
    public Link() {
        super();
    }
    
    /**
     * Factory method for creating virtual link connecting specified ports
     * 
     * @param start Port start port of the link
     * @param end Port end port of the link
     * @param capacity Capacity of the link in bps
     * @return virtual <code>Link</code> connecting ports
     */
    public static Link createVirtualLink(Port start, Port end, long capacity) {
        Link l = new Link(VIRTUAL, start, end);
        l.setCapacity(capacity);
        
        return l; 
    }
    
    /**
     * Creates an interdomain link.
     * 
     * @param start Port start port of the link
     * @param end Port end port of the link
     * @param capacity Capacity of the link in bps
     * @return interdomain <code>Link</code> connecting ports
     */
    public static Link createInterDomainLink(Port start, Port end, long capacity) {
    	Link l = new Link(INTER_DOMAIN, start, end);
        l.setCapacity(capacity);
        
        return l;
    }
    
    /**
     * Creates Link object of specified kind, connecting startPort with endPort
     * 
     * @param kind int kind of link
     * @param startPort Port start port of the link
     * @param endPort Port end port of the link
     */
    public Link(int kind, Port startPort, Port endPort) {
        this();
        this.kind = kind;
        this.startPort = startPort;
        this.endPort = endPort;
    }

    /**
     * Creates Link object with specified parameters
     * 
     * @param bodID identifier of the <code>Link</code> at application level
     * @param kind kind of link
     * @param startPort starting port of the link
     * @param endPort ending port of the link
     * @param bidirectional whether the link should be bidirectional 
     * @param delay delay of the link
     * @param manualCost manual cost of the link
     * @param monetaryCost monetary cost of the link
     * @param granularity granularity of capacity on the link
     * @param minResCapacity minimum reservable capacity per one reservation
     * @param maxResCapacity maximum reservable capacity per one reservation
     * @param capacity amount of capacity
     * @param resilience resiliency mode
     */
    public Link(String bodID, int kind, Port startPort, Port endPort, boolean bidirectional,
            int delay, double manualCost, double monetaryCost,
            long granularity, long minResCapacity, long maxResCapacity, long capacity,
            String resilience) {
        this(kind, startPort, endPort);
        this.bodID = bodID;
        this.bidirectional = bidirectional;
        this.delay = delay;
        this.manualCost = manualCost;
        this.monetaryCost = monetaryCost;
        this.granularity = granularity;
        this.minResCapacity = minResCapacity;
        this.maxResCapacity = maxResCapacity;
        this.capacity = capacity;
        this.resilience = resilience;
    }

    /**
     * Inidcates whether link is bidirectional
     * 
     * @return Returns the bidirectionality, true if the link is bidirectional, false otherwise
     */
    public boolean isBidirectional() {
        return bidirectional;
    }

    /**
     * Sets bidirectionality of the link
     * 
     * @param bidirectional The bidirectional to set.
     */
    public void setBidirectional(boolean bidirectional) {
        this.bidirectional = bidirectional;
    }

    /**
     * Returns delay of the link in miliseconds
     * 
     * @return Returns the delay.
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Sets a delay of the link (in miliseconds)
     * 
     * @param delay The delay to set.
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    /**
     * Returns the capacity granularity of the link (in bits)
     * 
     * @return Returns the granularity.
     */
    public long getGranularity() {
        return granularity;
    }

    /**
     * Sets the capacity granularity of the link (in bits)
     * 
     * @param granularity The granularity to set.
     */
    public void setGranularity(long granularity) {
        this.granularity = granularity;
    }

    /**
     * Returns kind of the link.
     *  
     * Values {REGULAR | VIRTUAL | COMPOSITE | INTERDOMAIN | ABSTRACT}. It
     * identifies whether this link is a regular link that is connecting
     * neighbouring ports, virtual, composite, inter-domain or abstract.
     * 
     * @return Returns kind of the link
     */
    public int getKind() {
        return kind;
    }

    /**
     * Sets kind of the link
     * 
     * @param kind The kind to set
     */
    public void setKind(int kind) {
        this.kind = kind;
    }

    /**
     * Returns identifier of the <code>Link</code> at application level.
     * 
     * @return <code>String</code> identifier
     */
    public String getBodID() {
		return bodID;
	}

    /**
     * Sets identifier of the <code>Link</code> at application level.
     * 
     * @param bodAddress identifier to set
     */
	public void setBodID(String bodAddress) {
		this.bodID = bodAddress;
	}

    /**
     * Returns the administrative cost of the link (assigned manually and not computed)
     * 
     * @return Returns the manualCost.
     */
    public double getManualCost() {
        return manualCost;
    }

    /**
     * Sets manual cost
     * 
     * @param manualCost The manualCost to set.
     */
    public void setManualCost(double manualCost) {
        this.manualCost = manualCost;
    }

    /**
     * Returns maximum reservable capacity per one reservation (in bits)
     * 
     * @return Returns the maxResCapacity.
     */
    public long getMaxResCapacity() {
        return maxResCapacity;
    }

    /**
     * Sets the maximum reservable capacity
     * 
     * @param maxResCapacity The maxResCapacity to set.
     */
    public void setMaxResCapacity(long maxResCapacity) {
        this.maxResCapacity = maxResCapacity;
    }

    /**
     * Return the minimum reservable capacity per one reservation
     * 
     * @return Returns the minResCapacity.
     */
    public long getMinResCapacity() {
        return minResCapacity;
    }

    /**
     * Sets the minimum reservable capacity per one reservation
     * 
     * @param minResCapacity The minResCapacity to set.
     */
    public void setMinResCapacity(long minResCapacity) {
        this.minResCapacity = minResCapacity;
    }

    /**
     * Returns the monetary cost of the link
     * 
     * @return Returns the monetaryCost.
     */
    public double getMonetaryCost() {
        return monetaryCost;
    }

    /**
     * Sets the monetary cost of the link
     * 
     * @param monetaryCost The monetaryCost to set.
     */
    public void setMonetaryCost(double monetaryCost) {
        this.monetaryCost = monetaryCost;
    }

    /**
     * Return resilience mode of the link {1+1 | 1:1 | none}. It states the
     * resiliency properties of the link. 1+1 is a complete backup using an
     * alternate path, 1:1 uses the backup via other non-dedicated resources.
     * 
     * @return Returns the resilience.
     */
    public String getResilience() {
        return resilience;
    }

    /**
     * Sets a resilience mode of the link, {1+1 | 1:1 | none}
     * 
     * @param resilience The resilience to set.
     */
    public void setResilience(String resilience) {
        this.resilience = resilience;
    }

    /**
     * Returns <code>Port</code> object that the link starts in
     * 
     * @return Returns the endPort.
     */
    public Port getEndPort() {
        return endPort;
    }

    /**
     * Sets <code>Port</code> object that the link starts in
     * 
     * @param endPort The endPort to set.
     */
    public void setEndPort(Port endPort) {
        this.endPort = endPort;
    }

    /**
     * Returns <code>Port</code> object that the link ends in
     * 
     * @return Returns the startPort.
     */
    public Port getStartPort() {
        return startPort;
    }

    /**
     * Sets <code>Port</code> object that the link ends in
     * 
     * @param startPort The startPort to set.
     */
    public void setStartPort(Port startPort) {
        this.startPort = startPort;
    }
    
    /**
     * Returns the capacity of the link (in bits)
     * 
     * @return Returns the capacity.
     */
    public long getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the link (in bits)
     * 
     * @param capacity The capacity to set.
     */
    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }
    
    public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public LinkType getType() {
		return type;
	}

	public void setType(LinkType type) {
		this.type = type;
	}

	public StateOper getOperationalState() {
		return operationalState;
	}

	public void setOperationalState(StateOper operationalState) {
		this.operationalState = operationalState;
	}

	public StateAdmin getAdministrativeState() {
		return administrativeState;
	}

	public void setAdministrativeState(StateAdmin administrativeState) {
		this.administrativeState = administrativeState;
	}

	/**
     * Returns identifier of an AdminDomain object that the link starts in
     * 
     * @return String identifier of domain
     */
    public String getStartDomainID() {
        return startPort.getAdminDomainID();
    }
    
    /**
     * Returns identifier of an AdminDomain object that the link starts in
     * 
     * @return String identifier of domain
     */
    public String getEndDomainID() {
        return endPort.getAdminDomainID();
    }

    /**
     * Indicates whether requested capacity can be reserved on the link,
     * considering current usage
     * 
     * @param currentUsage
     *            long current usage of the link (in bits)
     * @param requested
     *            long requested usage
     * @return boolean true if the requested capacity can be reserved on the
     *         link, false - otherwise
     */
    public boolean canReserve(long currentUsage, long requested) {
        if(requested < minResCapacity || requested > maxResCapacity)
            return false;
        
        if(capacity - currentUsage < requested)
            return false;
        
        return true;
    }

    /**
     * Indicates whether the <code>Link</code> is virtual.
     * 
     * @return <code>boolean</code> true if the <code>Link</code> os
     *         virtual, false - otherwise
     */
    public boolean isVirtual() {
        return kind == VIRTUAL;
    }
    
    /**
     * Returns <code>String</code> representation (key) of the
     * <code>Link</code>. Key is in form: [start port id];[end port id].
     * 
     * @return <code>String</code> key of the link
     */
    public String getBodKey() {
    	return getStartPort().getBodID() + ";" + getEndPort().getBodID();
    }

	/**
	 * Creates link with the same parameters but in the opposite direction. That
	 * means start port becomes end port, and end port becomes start port.
	 * 
	 * @return Inversed link
	 */
    public Link inverse() {
        Link l = new Link();

        l.setStartPort(this.getEndPort());
        l.setEndPort(this.getStartPort());
        l.setCapacity(this.getCapacity());
        l.setKind(this.getKind());
        
        l.setBodID("link" + endPort.getBodID() + "-" + startPort.getBodID());
        
        return l;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(obj == null || !(obj instanceof Link))
            return false;
        
        Link l2 = (Link) obj;
        
        return getBodID().equals(l2.getBodID());
    }

    @Override
    public int hashCode() {
        return getBodID().hashCode();
    }

    @Override
    public String toString() {
        return "[" + startPort + " -> " + endPort + "] (" + getBodID() + ")";
    }
}
