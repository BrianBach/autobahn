/*
 * Node.java
 *
 * 2006-08-30
 */
package net.geant.autobahn.network;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * The Node class represents a network element that is part of a network of a domain.
 * It is assigned to a provisioning domain. Node contains Ports.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Node", namespace="network.autobahn.geant.net", propOrder={
		"bodID", "type", "address", "provisioningDomain",
		"name", "country", "city", "institution",
		"longitude", "latitude"
})
public class Node implements Serializable {

    private static final long serialVersionUID = 9218632694072249026L;

    private String bodID;

    private String type;
    private String address;
    private ProvisioningDomain provisioningDomain;
    
    private String name;
    private String country;
    private String city;
    private String institution;
    private String longitude;
    private String latitude;
    
    /**
     * Default constructor.
     */
    public Node() {
        super();
    }

    /**
     * Creates a Node object assigned to a ProvisioningDomain, with given type,
     * address, bodAdress.
     * 
     * @param type
     *            String type of port {PSC | L2SC | TDM | LSC | FSC | … }. It
     *            identifies the type of the particular network element,
     *            according to the GMPLS architecture [RFC 3945].
     * @param address
     *            String The technology-specific address that is used to access
     *            the particular network device, eg IP address
     * @param bodID
     *            String The control plane addresses that BoD system modules
     *            (i.e. both IDM and DM modules) will use to refer to the
     *            particular network node
     * @param provisioningDomain
     *            ProvisioningDomain that the Node is assigned to
     */
    public Node(String type, String address, String bodID, ProvisioningDomain provisioningDomain) {
        this();
        this.type = type;
        this.address = address;
        this.bodID = bodID;
        this.provisioningDomain = provisioningDomain;
    }

    /**
     * The technology-specific address that is used to access the particular
     * network device, eg IP address
     * 
     * @return Returns the address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address of the <code>Node</code>
     * 
     * @param address The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * The control plane addresses that BoD system modules (i.e. both IDM and DM
     * modules) will use to refer to the particular network node
     * 
     * @return Returns the bodAddress.
     */
    public String getBodID() {
        return bodID;
    }

    /**
     * Sets the control plane identifier.
     * 
     * @param bodID The bodID to set.
     */
    public void setBodID(String bodID) {
        this.bodID = bodID;
    }

    /**
     * Returns <code>ProvisioningDomain</code> object that the node belongs
     * to.
     * 
     * @return Returns the pDomain.
     */
    public ProvisioningDomain getProvisioningDomain() {
        return provisioningDomain;
    }

    /**
     * Sets a <code>ProvisioningDomain</code> object that the node belongs to.
     * 
     * @param domain The pDomain to set.
     */
    public void setProvisioningDomain(ProvisioningDomain domain) {
        provisioningDomain = domain;
    }

    /**
     * Type of port, according to the GMPLS architecture [RFC 3945].
     * 
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Type of port to set: {PSC | L2SC | TDM | LSC | FSC | … }. It identifies
     * the type of the particular network element, according to the GMPLS
     * architecture [RFC 3945].
     * 
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }
    
    public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * 
	 * @return
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * 
	 * @param latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
     * Returns identifier of the admin domain that the Node belongs to.
     * 
     * @return String identifier of the admin domain
     */
    public String getAdminDomainID() {
        return provisioningDomain.getAdminDomainID();
    }
    
    /**
     * Indicates whether the node belongs to a client domain.
     * 
     * @return true if the node belongs to a client domain, false - otherwise
     */
    public boolean isClientNode() {
        return provisioningDomain.getAdminDomain().isClientDomain();
    }
    
    /**
     * Indicates whether the node belongs to the same domain that specified
     * node2 does.
     * 
     * @param node2 another <code>Node</code> object
     * @return true if nodes belong to the same domain, false - otherwise
     */
    public boolean sameDomain(Node node2) {
        String ad1 = getAdminDomainID(); 
        String ad2 = node2.getAdminDomainID();
        
        return ad1.equals(ad2);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        
        if(obj == null || !(obj instanceof Node))
            return false;
        
        Node n2 = (Node) obj;
        
        return getBodID().equals(n2.getBodID());
    }

    @Override
    public int hashCode() {
        return getBodID().hashCode();
    }

    @Override
    public String toString() {
        return "Node [" + bodID + "]";
    }
}
