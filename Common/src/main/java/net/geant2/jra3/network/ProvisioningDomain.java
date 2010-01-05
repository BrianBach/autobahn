/*
 * ProvisioningDomain.java
 *
 * 2006-08-30
 */
package net.geant2.jra3.network;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * As an administrative domain can potentially include more than one
 * "provisioning domains", that is subsets in the administrative domain that use
 * a common provisioning method. Each of those subsets is represented by a
 * ProvisioningDomain class.
 * 
 * Provisioning method is one of following: {lambda | SDH | SONET | Ethernet |
 * L2 MPLS VPN QoS | PIP | IP MPLS QoS | GMPLS | UCLP }
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ProvisioningDomain", namespace="network.jra3.geant2.net", propOrder={
		"bodID", "provType", "adminDomain"
})
public class ProvisioningDomain implements Serializable {

    private static final long serialVersionUID = 5686337644681912604L;

    private String bodID;
    
    private String provType;
    private AdminDomain adminDomain;

    /**
     * Default constructor
     */
    public ProvisioningDomain() {
        super();
    }

    /**
     * Creates a ProvisioningDomain object belonging to an AdminDomain with
     * given ID and provisioningType.
     * 
     * @param provDomainID
     *            String provisioning domain identifier
     * @param provType
     *            String provisioning method used in the ProvisioningDomain
     * @param adminDomain
     *            AdminDomain object that the ProvisioningDomain belongs to
     */
    public ProvisioningDomain(String bodID, String provType, AdminDomain adminDomain) {
        this();
        this.bodID = bodID;
        this.provType = provType;
        this.adminDomain = adminDomain;
    }

    /**
     * Returns AdminDomain object that the ProvisioningDomain belongs to.
     * 
     * @return Returns the AdminDomain object
     */
    public AdminDomain getAdminDomain() {
        return adminDomain;
    }

    /**
     * Sets AdminDomain object that the ProvisioningDomain belongs to.
     * 
     * @param AdminDomain The adminDomain to set.
     */
    public void setAdminDomain(AdminDomain adminDomain) {
        this.adminDomain = adminDomain;
    }

    
    /**
     * The control plane address that BoD system modules (i.e. both IDM and DM
     * modules) will use to refer to the particular provisioning domain.
     * 
     * @return <code>String</code> control plane address
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
     * Gets the provisioning method of the ProvisioningDomain: Method is one of
     * following: {lambda | SDH | SONET | Ethernet | L2 MPLS VPN QoS | PIP | IP
     * MPLS QoS | GMPLS | UCLP }
     * 
     * @return Returns the provType.
     */
    public String getProvType() {
        return provType;
    }

    /**
     * Sets the provisioning method of the ProvisioningDomain: Method should be
     * one of following: {lambda | SDH | SONET | Ethernet | L2 MPLS VPN QoS |
     * PIP | IP MPLS QoS | GMPLS | UCLP }
     * 
     * @param provType
     *            String The provisioning type to set.
     */
    public void setProvType(String provType) {
        this.provType = provType;
    }
    
    /**
     * Returns admin domain Identifier of the AdminDomain object that the ProvisioningDomain belongs to.
     * 
     * @return String admin domain identifier
     */
    public String getAdminDomainID() {
        return adminDomain.getBodID();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(obj == null || !(obj instanceof ProvisioningDomain))
            return false;
        
        ProvisioningDomain pd2 = (ProvisioningDomain) obj;
        
        return getBodID().equals(pd2.getBodID());
    }

    @Override
    public int hashCode() {
        return getBodID().hashCode();
    }

    @Override
    public String toString() {
        return "PD: " + adminDomain.toString() + ", type: " + provType;
    }
}
