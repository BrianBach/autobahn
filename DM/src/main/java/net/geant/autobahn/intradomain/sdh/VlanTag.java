package net.geant.autobahn.intradomain.sdh;

import java.io.Serializable;
import java.util.Date;

public class VlanTag implements Serializable {
	
	private static final long serialVersionUID = -8668906012866409589L;
	
	private long vlanTagId;
	private String name;
	private Date dateModified;
	
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getVlanTagId() {
		return vlanTagId;
	}
	public void setVlanTagId(long vlanTagId) {
		this.vlanTagId = vlanTagId;
	}
	

}
