package net.geant.autobahn.intradomain.sdh;

import java.io.Serializable;

public class HoVcType implements Serializable {
	
	private static final long serialVersionUID = 1805611858365453026L;
	
	private long hoVcTypeId;
	private String name;
	private long bandwidth;
	private long payload;
	
	public long getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(long bandwidth) {
		this.bandwidth = bandwidth;
	}
	public long getHoVcTypeId() {
		return hoVcTypeId;
	}
	public void setHoVcTypeId(long hoVcTypeId) {
		this.hoVcTypeId = hoVcTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPayload() {
		return payload;
	}
	public void setPayload(long payload) {
		this.payload = payload;
	}
	
	

}
