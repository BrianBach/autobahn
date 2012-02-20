package net.geant.autobahn.useraccesspoint;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.network.Port;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PortType", namespace = "useraccesspoint.autobahn.geant.net", propOrder = {
    "address", "mode", "vlan", "description", "isIdcp", "isClient"
})
public class PortType implements Serializable, Comparable<PortType> {
	
	private static final long serialVersionUID = 1L;

	private String address;
	private Mode mode;
	private int vlan;
	private String description;
	private boolean isClient;
	private boolean isIdcp;
	
	public PortType() {
		
	}
	
	public PortType(String address) {
		this.address = address;
	}
	
	public PortType(String address, Mode mode, int vlan) {
		super();
		this.address = address;
		this.mode = mode;
		this.vlan = vlan;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public int getVlan() {
		return vlan;
	}

	public void setVlan(int vlan) {
		this.vlan = vlan;
	}
	
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isClient() {
		return isClient;
	}

	public void setClient(boolean isClient) {
		this.isClient = isClient;
	}

	public boolean isIdcp() {
		return isIdcp;
	}

	public void setIdcp(boolean isIdcp) {
		this.isIdcp = isIdcp;
	}
	
	public String getFriendlyName() {
		if(description == null || description.trim().equals("")
             || description.trim().equalsIgnoreCase("null") || description.trim() == address.trim()) {
			return address;
		} else {
			return description.trim() + " (" + address + ")";
		}
	}

	public String toString() {
        return address;
    }
	
	public static PortType convert(Port port) {
		PortType pType = new PortType();
		pType.setAddress(port.getBodID());
		pType.setDescription(port.getDescription());
		pType.setIdcp(port.isIdcpPort());
		pType.setClient(port.isClientPort());
		
		return pType;
	}
	
	public static PortType convert(String port, int vlan, String description) {
		PortType pType = new PortType();
		pType.setAddress(port);
		pType.setVlan(vlan);
		pType.setDescription(description);
		
		return pType;
	}

    @Override
    public int compareTo(PortType p2) {
        if (p2 == null) {
            return 1;
        }
        if (this.getAddress() == null) {
            return -1;
        }
        return this.getAddress().compareTo(p2.getAddress());
    }
}
