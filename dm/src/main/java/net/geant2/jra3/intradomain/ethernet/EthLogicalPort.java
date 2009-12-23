/**
 * EthLogicalPort.java
 * 2007-03-28
 */

package net.geant2.jra3.intradomain.ethernet;

import java.io.Serializable;

import net.geant2.jra3.intradomain.common.GenericInterface;

/**
 * This class represents a logical Ethernet port.
 * It extends the GenericInterface class
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */

public class EthLogicalPort implements Serializable{
	
	private static final long serialVersionUID = -4323758546468373965L;
	
	private GenericInterface genericInterface;

	/**
	 * This method returns the generic interface that is associated with the Ethernet logical port
	 * 
	 * @return Returns the generic interface that is associated with the Ethernet logical port
	 */
	public GenericInterface getGenericInterface() {
		return genericInterface;
	}

	/**
	 * This method sets the generic interface that is associated with the Ethernet logical port
	 * 
	 * @param genericInterface the generic interface that is associated with the Ethernet logical port
	 */
	public void setGenericInterface(GenericInterface genericInterface) {
		this.genericInterface = genericInterface;
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
		final EthLogicalPort other = (EthLogicalPort) obj;
		if (genericInterface == null) {
			if (other.genericInterface != null)
				return false;
		} else if (!genericInterface.equals(other.genericInterface))
			return false;
		return true;
	}
}
