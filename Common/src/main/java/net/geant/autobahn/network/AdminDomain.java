/*
 * AdminDomain.java
 *
 * 2006-10-24
 */
package net.geant.autobahn.network;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * The AdminDomain class represents a domain, under the control of a single
 * administrative authority, and refers (for example) to an NREN, GÉANT2 and
 * potential BoD clients.
 * 
 * Information whether an AdminDomain is a client domain is provided by
 * <code>isClientDomain()</code> method.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AdminDomain", namespace="network.autobahn.geant.net", propOrder={
		"bodID", "ASID", "name", "clientDomain", "idcpServer"
})
public class AdminDomain implements Serializable {

    private static final long serialVersionUID = -8081452048706037470L;

    private String bodID;
    
    private String ASID;
    private String name;
    private boolean clientDomain;
    
    // The URL where IDCP cloud server is listening, null if this
    // AdminDomain is not an IDCP cloud
    private String idcpServer;
    
    /**
     * Default constructor 
     */
    public AdminDomain() {
        super();
    }

    /**
     * Creates client or nren AdminDomain object, with given domainID
     * 
     * @param domainID
     *            String domain identifier
     * @param isClientDomain
     *            boolean true if the domain should be a client domain,
     *            otherwise false
     */
    public AdminDomain(String domainID, boolean isClientDomain) {
        this();
        this.bodID = domainID;
        this.clientDomain = isClientDomain;
    }

    /**
     * Creates client or nren AdminDomain object, with given domainID, ASID and
     * name
     * 
     * @param domainID
     *            String domain identifier
     * @param asid
     *            String domain ASID
     * @param name
     *            String domain name
     * @param isClientDomain
     *            boolean true if the domain should be a client domain,
     *            otherwise false
     */
    public AdminDomain(String domainID, String asid, String name, boolean isClientDomain) {
        this(domainID, isClientDomain);
        this.ASID = asid;
        this.name = name;
    }

    /**
     * Returns the ASID of the AdminDomain, unique identifier of an Autonomous
     * System represetned by the AdminDomain.
     * 
     * @return Returns the ASID.
     */
    public String getASID() {
        return ASID;
    }

    /**
     * @param asid The aSID to set.
     */
    public void setASID(String asid) {
        ASID = asid;
    }

    /**
     * Returns the AdminDomainID of the AdminDomain, unique identifier of an
     * AdminDomain.
     * 
     * @return Returns the domainID.
     */
    public String getBodID() {
        return bodID;
    }

    /**
     * @param domainID The domainID to set.
     */
    public void setBodID(String bodID) {
        this.bodID = bodID;
    }

    /**
     * Indicates whether the AdminDomain is an IDCP cloud.
     * 
     * @return boolean true if the AdminDomain is an IDCP cloud, false otherwise
     */
    public boolean isIdcpCloud() {
        return (idcpServer!=null);
    }

    /**
     * Returns idcpServer of an AdminDomain that is IDCP cloud, null otherwise.
     * 
     * @return Returns the URL of the IDCP server.
     */
    public String getIdcpServer() {
        return idcpServer;
    }

    /**
     * Sets idcpServer property.
     * 
     * @param idcpServer
     */
    public void setIdcpServer(String idcpServer) {
        this.idcpServer = idcpServer;
        
        // Make sure that IDCP clouds are also client domains
        if (isIdcpCloud()) {
            this.clientDomain = true;
        }
    }

    /**
     * Indicates whether the AdminDomain is a client domain.
     * 
     * @return boolean true if the AdminDomain is a client domain, false otherwise
     */
    public boolean isClientDomain() {
        return clientDomain;
    }

    /**
     * Sets isClientDomain property.
     * 
     * @param isClientDomain
     *            boolean true if the AdminDomain should be a client domain,
     *            false otherwise
     */
    public void setClientDomain(boolean isClientDomain) {
        this.clientDomain = isClientDomain;
    }

    /**
     * Returns name of the AdminDomain.
     * 
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of the AdminDomain
     * 
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

	@Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        
        if(obj == null || !(obj instanceof AdminDomain)) {
            return false;
        }
        
        AdminDomain ad2 = (AdminDomain) obj;
        
        return getBodID().equals(ad2.getBodID());
    }

    @Override
    public int hashCode() {
        return getBodID().hashCode();
    }

    @Override
    public String toString() {
        return "AD [" + ASID + "]";
    }
}
