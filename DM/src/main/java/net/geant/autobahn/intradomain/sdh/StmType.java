package net.geant.autobahn.intradomain.sdh;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="StmType", namespace="sdh.intradomain.autobahn.geant.net", propOrder={
		"stmTypeId", "name", "bandwidth"
})
public class StmType implements Serializable {
	
	private static final long serialVersionUID = -4369309441762547064L;
	//@XmlTransient
	private long stmTypeId;
	private String name;
	private long bandwidth;
	
	public long getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(long bandwidth) {
		this.bandwidth = bandwidth;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getStmTypeId() {
		return stmTypeId;
	}
	public void setStmTypeId(long stmTypeId) {
		this.stmTypeId = stmTypeId;
	}
	

}
