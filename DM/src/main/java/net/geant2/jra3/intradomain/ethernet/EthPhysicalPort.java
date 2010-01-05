/**
 * EthPhysicalPort.java
 * 2007-03-28
 */

package net.geant2.jra3.intradomain.ethernet;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant2.jra3.intradomain.common.GenericInterface;

/**
 * This class represents a physical Ethernet port.
 * It extends the GenericInterface class
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="EthPhysicalPort", namespace="ethernet.intradomain.jra3.geant2.net", propOrder={
        "genericInterface", "interfaceName", "macAddress", "duplex", "mediumDependentInterface", "isTagged"
})
public class EthPhysicalPort implements Serializable {
		
	private static final long serialVersionUID = 9106861193566292429L;
	
	private GenericInterface genericInterface; 
	private String interfaceName; 
	private String macAddress; 
	private String duplex; 
	private String mediumDependentInterface; 
	private boolean isTagged;
	
	/**
	 * This method returns the duplex type of the Ethernet physical interface 
	 * 
	 * @return Returns the duplex type of the Ethernet physical interface 
	 */
	public String getDuplex() {
		return duplex;
	}
	
	/**
	 * This method sets the duplex type of the Ethernet physical interface
	 * 
	 * @param duplex the duplex type of the Ethernet physical interface 
	 */
	public void setDuplex(String duplex) {
		this.duplex = duplex;
	}
	
	/**
	 * This method returns the generic interface that is associated with the Ethernet physical port
	 * 
	 * @return Returns the generic interface that is associated with the Ethernet physical port
	 */
	public GenericInterface getGenericInterface() {
		return genericInterface;
	}
	
	/**
	 * This method sets the generic interface that is associated with the Ethernet physical port
	 * 
	 * @param genericInterface the generic interface that is associated with the Ethernet physical port
	 */
	public void setGenericInterface(GenericInterface genericInterface) {
		this.genericInterface = genericInterface;
	}
	
	/**
	 * This method returns the name of the Ethernet physical interface
	 * 
	 * @return Returns the name of the Ethernet physical interface
	 */
	public String getInterfaceName() {
		return interfaceName;
	}
	
	/**
	 * This method sets the name of the Ethernet physical interface
	 * 
	 * @param interfaceName the name of the Ethernet physical interface
	 */
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
	/**
	 * This method returns true if the Ethernet physical interface is tagged
	 * 
	 * @return Returns true if the Ethernet physical interface is tagged
	 */
	public boolean getIsTagged() {
		return isTagged;
	}
	
	/**
	 * This method sets the state of the Ethernet physical interface regarding whether it is tagged
	 * 
	 * @param isTagged the state of the Ethernet physical interface regarding whether it is tagged
	 */
	public void setIsTagged(boolean isTagged) {
		this.isTagged = isTagged;
	}
	
	/**
	 * This method returns the MAC address of the Ethernet physical interface
	 * 
	 * @return Returns the MAC address of the Ethernet physical interface
	 */
	public String getMacAddress() {
		return macAddress;
	}
	
	/**
	 * This method sets the MAC address of the Ethernet physical interface
	 * 
	 * @param macAddress the MAC address of the Ethernet physical interface
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	/**
	 * This method returns 
	 * 
	 * @return
	 */
	public String getMediumDependentInterface() {
		return mediumDependentInterface;
	}
	
	/**
	 * This method sets
	 * 
	 * @param mediumDependentInterface
	 */
	public void setMediumDependentInterface(String mediumDependentInterface) {
		this.mediumDependentInterface = mediumDependentInterface;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((genericInterface == null) ? 0 : genericInterface.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final EthPhysicalPort other = (EthPhysicalPort) obj;
		if (genericInterface == null) {
			if (other.genericInterface != null)
				return false;
		} else if (!genericInterface.equals(other.genericInterface))
			return false;
		return true;
	}
}
