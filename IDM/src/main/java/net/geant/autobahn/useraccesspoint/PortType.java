package net.geant.autobahn.useraccesspoint;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PortType", namespace = "useraccesspoint.autobahn.geant.net", propOrder = {
    "address", "mode", "vlan"
})
public class PortType implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String address;
	private Mode mode;
	private int vlan;
	
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
	
}
