/**
 * VlanPort.java
 * 2007-03-28
 */


package net.geant.autobahn.intradomain.ethernet;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.intradomain.common.GenericInterface;

/**
 * This class represents a VLAN port.
 * It extends the GenericInterface class
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="VlanPort", namespace="ethernet.intradomain.autobahn.geant.net", propOrder={
        "genericInterface", "vlan"
})
public class VlanPort implements Serializable {
	
	private static final long serialVersionUID = 8566319983710078304L;
	
	private GenericInterface genericInterface;
	private Vlan vlan;
	
	/**
	 * This method returns the generic interface that is associated with the VLAN port
	 * 
	 * @return Returns the generic interface that is associated with the VLAN port
	 */
	public GenericInterface getGenericInterface() {
		return genericInterface;
	}
	
	/**
	 * This method sets the generic interface that is associated with the VLAN port
	 * 
	 * @param genericInterface the generic interface that is associated with the VLAN port
	 */
	public void setGenericInterface(GenericInterface genericInterface) {
		this.genericInterface = genericInterface;
	}
	
	/**
	 * This method returns the VLAN of the VLAN port
	 * 
	 * @return Returns the VLAN of the VLAN port
	 */
	public Vlan getVlan() {
		return vlan;
	}
	
	/**
	 * This method sets the VLAN of the VLAN port
	 * 
	 * @param vlan the VLAN of the VLAN port
	 */
	public void setVlan(Vlan vlan) {
		this.vlan = vlan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((genericInterface == null) ? 0 : genericInterface.hashCode());
		result = prime * result + ((vlan == null) ? 0 : vlan.hashCode());
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
		final VlanPort other = (VlanPort) obj;
		if (genericInterface == null) {
			if (other.genericInterface != null)
				return false;
		} else if (!genericInterface.equals(other.genericInterface))
			return false;
		if (vlan == null) {
			if (other.vlan != null)
				return false;
		} else if (!vlan.equals(other.vlan))
			return false;
		return true;
	}
}
