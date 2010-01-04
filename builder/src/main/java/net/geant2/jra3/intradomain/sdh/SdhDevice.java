package net.geant2.jra3.intradomain.sdh;

import java.io.Serializable;
import net.geant2.jra3.intradomain.common.Node;

public class SdhDevice implements Serializable {
	
	private static final long serialVersionUID = 1309095060949387632L;
	
	private Node node;
	private SdhDomain sdhDomain;
	private String name;
	private long nsap;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	public long getNsap() {
		return nsap;
	}
	public void setNsap(long nsap) {
		this.nsap = nsap;
	}
	public SdhDomain getSdhDomain() {
		return sdhDomain;
	}
	public void setSdhDomain(SdhDomain sdhDomain) {
		this.sdhDomain = sdhDomain;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((sdhDomain == null) ? 0 : sdhDomain.hashCode());
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
		final SdhDevice other = (SdhDevice) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sdhDomain == null) {
			if (other.sdhDomain != null)
				return false;
		} else if (!sdhDomain.equals(other.sdhDomain))
			return false;
		return true;
	}
	@Override
	public String toString() {
		if (node != null)
			return node.getName();
		return "SDH-Device";
			
	}
}
