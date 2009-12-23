/**
 * SpanningTree.java
 * 2007-03-28
 */

package net.geant2.jra3.intradomain.ethernet;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * This class represents a spanning tree instance for VTP domains and 
 * their VLANs. There are multiple entries per EthLink (one per VLAN, when
 * the EthLink is a trunk)
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="SpanningTree", namespace="ethernet.intradomain.jra3.geant2.net", propOrder={
		"ethLink", "vlan", "state", "cost"
})
public class SpanningTree implements Serializable {
	
	private static final long serialVersionUID = 6110613408326891012L;
	
	private EthLink ethLink; 
	private Vlan vlan; 
	private String state; 
	private long cost;
	
    public SpanningTree() { }
    
    public SpanningTree(String state, long cost) {
        
        this.state = state;
        this.cost = cost;
    }
    
    public SpanningTree(EthLink link, Vlan vlan,
                String state, long cost) {
        
        this(state, cost);
        this.ethLink = link;
        this.vlan = vlan;
    }
    
	/**
	 * This method returns the cost of the spanning tree instance
	 * 
	 * @return Returns the cost of the spanning tree instance
	 */
    public long getCost() {
		return cost;
	}
	
    /**
     * This method sets the cost of the spanning tree instance
     * 
     * @param cost the cost of the spanning tree instance
     */
    public void setCost(long cost) {
		this.cost = cost;
	}
	
    /**
     * This method returns the Ethernet link to which the spanning tree instance corresponds
     * 
     * @return Returns the Ethernet link to which the spanning tree instance corresponds
     */
    public EthLink getEthLink() {
		return ethLink;
	}
	
    /**
     * This method sets the Ethernet link to which the spanning tree instance corresponds
     * 
     * @param ethLink the Ethernet link to which the spanning tree instance corresponds
     */
    public void setEthLink(EthLink ethLink) {
		this.ethLink = ethLink;
	}
	
    /**
     * This method returns the state of the spanning tree instance
     * 
     * @return Returns the state of the spanning tree instance
     */
    public String getState() {
		return state;
	}
	
    /**
     * This method sets the state of the spanning tree instance
     * 
     * @param state the state of the spanning tree instance
     */
    public void setState(String state) {
		this.state = state;
	}
	
    /**
     * This method returns the VLAN of the spanning tree instance
     * 
     * @return Returns the VLAN of the spanning tree instance
     */
    public Vlan getVlan() {
		return vlan;
	}
	
    /**
     * This method sets the VLAN of the spanning tree instance
     * 
     * @param vlan the VLAN of the spanning tree instance
     */
    public void setVlan(Vlan vlan) {
		this.vlan = vlan;
	}

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        
        if(obj == null || !(obj instanceof SpanningTree)) {
            return false;
        }
        
        SpanningTree st2 = (SpanningTree) obj;
        
        return vlan.equals(st2.getVlan()) && ethLink.equals(st2.getEthLink());
    }

    @Override
    public int hashCode() {
        return vlan.hashCode() ^ ethLink.hashCode();
    }

    @Override
    public String toString() {
        return "ST: " + ethLink + " " + vlan;
    }
}
