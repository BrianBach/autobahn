package net.geant2.jra3.intradomain.sdh;

import java.io.Serializable;

import net.geant2.jra3.intradomain.common.GenericInterface;

public class SdhPort implements Serializable {
	
	private static final long serialVersionUID = -2985366372297111504L;
	
	private GenericInterface genericInterface;
	private String address;
	private String phyPortType;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public GenericInterface getGenericInterface() {
		return genericInterface;
	}
	public void setGenericInterface(GenericInterface genericInterface) {
		this.genericInterface = genericInterface;
	}
	public String getPhyPortType() {
		return phyPortType;
	}
	public void setPhyPortType(String phyPortType) {
		this.phyPortType = phyPortType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
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
		final SdhPort other = (SdhPort) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		return true;
	}
}
