/*
 * Path.java
 *
 * 2006-08-30
 */
package net.geant.autobahn.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Path class represents end-to-end path from a start port to an end port
 * consisting of links.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Path", namespace="network.autobahn.geant.net", propOrder={
		"links", "monetary_cost", "manual_cost", "capacity"
})
public class Path implements Serializable {

	private static final long serialVersionUID = -8340881450016772656L;

	@XmlTransient
    private long pathID;
    
	@XmlElement(required = true, nillable = true)
    private List<Link> links;
    private double monetary_cost;
    private double manual_cost;
    
    private long capacity = Long.MAX_VALUE;
    
    /**
     * Default constructor 
     */
    public Path() {
        links = new ArrayList<Link>();
    }
    
    /**
     * Creates a Path object with costs assigned
     * 
     * @param monetary_cost monetary cost of the path
     * @param manual_cost administrative routing cost of the path
     */
    public Path(double monetary_cost, double manual_cost) {
        this();
        this.monetary_cost = monetary_cost;
        this.manual_cost = manual_cost;
    }

    /**
     * Returns a list of links of the path
     * 
     * @return Returns the links.
     */
    public List<Link> getLinks() {
        return links;
    }

    /**
     * Sets links of the path
     * 
     * @param links The links to set.
     */
    public void setLinks(List<Link> links) {
    	this.links = new ArrayList<Link>();
    	
    	for(Link l : links) {
    		this.add(l);
    	}
    }

    /**
     * Returns administrative routing cost of the path
     * 
     * @return Returns the manual_cost.
     */
    public double getManual_cost() {
        return manual_cost;
    }

    /**
     * Sets administrative routing cost of the path
     * 
     * @param manual_cost The manual_cost to set.
     */
    public void setManual_cost(double manual_cost) {
        this.manual_cost = manual_cost;
    }

    /**
     * Returns monetary cost of the path
     * 
     * @return Returns the monetary_cost.
     */
    public double getMonetary_cost() {
        return monetary_cost;
    }

    /**
     * Sets monetary cost of the path
     * 
     * @param monetary_cost The monetary_cost to set.
     */
    public void setMonetary_cost(double monetary_cost) {
        this.monetary_cost = monetary_cost;
    }
    
    /**
     * Returns capacity of the link - that means capacity of the link that
     * possess the least capacity
     * 
     * @return Returns the capacity.
     */
    public long getCapacity() {
        return capacity;
    }

    /**
     * Returns path identifier
     * 
     * @return Returns the pathID.
     */
    public long getPathID() {
        return pathID;
    }

    /**
     * Sets the path identifier
     * 
     * @param pathID The pathID to set.
     */
    public void setPathID(long pathID) {
        this.pathID = pathID;
    }

    /**
     * Adds the link to the path and recalculates all properties of path:
     * capacity, manual_cost, monetary_cost.
     * 
     * @param link Link to be added to the path
     */
    public void add(Link link) {
        links.add(link);
        
        capacity = Math.min(capacity, link.getCapacity());
    }

	/**
	 * Returns number of domain on the reservation path. Does not includes
	 * client domains.
	 * 
	 * @return NUmber of domains on the path
	 */
    public int getDomainCount() {
    	Set<String> domains = new HashSet<String>();
    	
    	for(Link l : links) {
    		if(!l.getStartPort().isClientPort())
    			domains.add(l.getStartDomainID());
    		
    		if(!l.getEndPort().isClientPort())
    			domains.add(l.getEndDomainID());
    	}
    	
    	return domains.size();
    }

	/**
	 * Returns number of domains from the path that are after specified domain.
	 * Does not include client domains.
	 * 
	 * @param domainID identifier of a domain
	 * @return NUmber od domains after the specified domain
	 */
    public int getAfterDomainCount(String domainID) {
    	Set<String> domains = new HashSet<String>();
    	
    	Link egress = getEgress(domainID);
    	
    	int index = links.indexOf(egress);
    	
    	if(index > -1) {
    		for(int i = index + 1; i < links.size(); i++) {
    			Link l = links.get(i);
    			
        		if(!l.getStartPort().isClientPort())
        			domains.add(l.getStartDomainID());
        		
        		if(!l.getEndPort().isClientPort())
        			domains.add(l.getEndDomainID());
    		}
    	}
    	
    	// remove this domain if exists
    	domains.remove(domainID);
    	
    	return domains.size();
    }
    
    /**
     * Returns control plane identifier of a first domain of the path that is
     * not a client domain.
     * 
     * @return <code>String</code> identifier of the home domain
     */
    public String getHomeDomainID() {
        
        // find the first link that doesn't end in a client domain
        if(links.size() > 0) {
            Link firstLink = links.get(0);
            
            if(!firstLink.getStartPort().isClientPort())
                return firstLink.getStartDomainID();
            
            if(!firstLink.getEndPort().isClientPort())
                return firstLink.getEndDomainID();
        }
        
        return null;
    }

    /**
     * Returns control plane identifier of a last domain of the path that is not
     * a client domain
     * 
     * @return <code>String</code> identifier of the last domain
     */
    public String getLastDomainID() {

        int lastIndex = links.size() - 1;
        
        // find the first link that doesn't end in a client domain
        if(lastIndex >= 0) {
            Link lastLink = links.get(lastIndex);
            
            if(!lastLink.getStartPort().isClientPort())
                return lastLink.getStartDomainID();
            
            if(!lastLink.getEndPort().isClientPort())
                return lastLink.getEndDomainID();
        }
        
        return null;
    }
    
    /**
     * Indicates whether specified domain is the home domain of the
     * <code>Path</code>.
     * 
     * @param domainID <code>String</code> identifier of a domain
     * @return <code>boolean</code> true if specified domain is home domain of
     *         the path, false - otherwise
     */
    public boolean isHomeDomain(String domainID) {
        return domainID.equals(this.getHomeDomainID());
    }

    /**
     * Indicates whether specified domain is the last domain of the
     * <code>Path</code>.
     * 
     * @param domainID <code>String</code> identifier of a domain
     * @return <code>boolean</code> true if specified domain is last domain of
     *         the path, false - otherwise
     */
    public boolean isLastDomain(String domainID) {
        return domainID.equals(this.getLastDomainID());
    }
    
    /**
     * Returns link ingoing to a specified domain 
     * 
     * @param domainID control plane identifier of a domain
     * @return <code>Link</code> that outgoes to the domain, null if such
     *         link does not exist.
     */
    public Link getIngress(String domainID) {
    	
    	Link[] selected = getLinksForDomain(domainID);
    	
    	return selected[0];
    }

    /**
     * Returns link outgoing from a specified domain
     * 
     * @param domainID control plane identifier of a domain
     * @return <code>Link</code> that outgoes from the domain, null if such
     *         link does not exist.
     */
    public Link getEgress(String domainID) {
    	
    	Link[] selected = getLinksForDomain(domainID);
        
        return selected[selected.length - 1];
    }

    /**
     * Returns array of abstract links belonging to a domain.
     * 
     * @param domainID String identifier of a domain
     * @return array of links from the domain
     */
    public Link[] getLinksForDomain(String domainID) {
    	List<Link> selected = new ArrayList<Link>();
    	
    	for(Link link : links) {
    		if (domainID.equals(link.getStartDomainID())
					|| domainID.equals(link.getEndDomainID())) {
    			selected.add(link);
    		}
    	}

    	Link[] result = new Link[selected.size()];
   		result = selected.toArray(result);
    	
    	return result;
    }

    /**
     * Returns link object idenitfied by the given id.
     * 
     * @param bodId identifier of a link
     * @return Link object
     */
    public Link getLink(String bodId) {
    	for(Link l : links) {
    		if(l.getBodID().equals(bodId))
    			return l;
    	}
    	
    	return null;
    }
    
    public boolean samePath(Path p2) {
        return links.equals(p2.links);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(obj == null || !(obj instanceof Path)) {
            return false;
        }
        
        Path p2 = (Path) obj;
        
        return getPathID() == p2.getPathID();
    }

    @Override
    public int hashCode() {
        return (int) getPathID();
    }

    @Override
    public String toString() {
        
        StringBuffer sb = new StringBuffer("");
        
        for(Link link : links) {
            sb.append(link.toString() + ", ");
        }
        
        final int len = sb.length();
        if(len > 0) {
            sb.delete(len - 2, len);
        }
        
        return sb.toString();
    }
}
