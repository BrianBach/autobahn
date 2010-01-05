/**
 * VtpDomain.java
 * 2007-03-28
 */


package net.geant2.jra3.intradomain.ethernet;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * This class represents a VTP domain
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="VtpDomain", propOrder={
		"vtpDomainId", "name", "srvIpv4Addr"
})
public class VtpDomain implements Serializable {
	
	private static final long serialVersionUID = 5967513567569716063L;
	//@XmlTransient
	private long vtpDomainId;
	private String name; 
	private String srvIpv4Addr;
    
    public VtpDomain() { }
    
    public VtpDomain(long domainId, String name, String ipAddress) {
        
        this.vtpDomainId = domainId;
        this.name = name;
        this.srvIpv4Addr = ipAddress;
    }
	
	
    /**
     * This method returns the name of the VTP domain
     * 
     * @return Returns the name of the VTP domain
     */
    public String getName() {
		return name;
	}
	
    /**
     * This method sets the name of the VTP domain
     * 
     * @param name the name of the VTP domain
     */
    public void setName(String name) {
		this.name = name;
	}
	
    /**
     * This method returns the serving IP address of the VTP domain
     * 
     * @return Returns the serving IP address of the VTP domain
     */
    public String getSrvIpv4Addr() {
		return srvIpv4Addr;
	}
	
    /**
     * This method sets the serving IP address of the VTP domain
     * 
     * @param srvIpv4Addr the serving IP address of the VTP domain
     */
    public void setSrvIpv4Addr(String srvIpv4Addr) {
		this.srvIpv4Addr = srvIpv4Addr;
	}
	
    /**
     * This method returns the id of the VTP domain
     * 
     * @return Returns the id of the VTP domain
     */
    public long getVtpDomainId() {
		return vtpDomainId;
	}
	
    /**
     * This method sets the id of the VTP domain
     * 
     * @param vtpDomainId the id of the VTP domain
     */
    public void setVtpDomainId(long vtpDomainId) {
		this.vtpDomainId = vtpDomainId;
	}


}
