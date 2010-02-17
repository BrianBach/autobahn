package net.geant.autobahn.intradomain.sdh;

import java.io.Serializable;

public class OchLink implements Serializable {
	
	private static final long serialVersionUID = -2574126008840296995L;
	
	private long ochLinkId;
	private OpsLink opsLink;
	private Och och;
	private double frequency;
	private String status;
	
	public double getFrequency() {
		return frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	public Och getOch() {
		return och;
	}
	public void setOch(Och och) {
		this.och = och;
	}
	public long getOchLinkId() {
		return ochLinkId;
	}
	public void setOchLinkId(long ochLinkId) {
		this.ochLinkId = ochLinkId;
	}
	public OpsLink getOpsLink() {
		return opsLink;
	}
	public void setOpsLink(OpsLink opsLink) {
		this.opsLink = opsLink;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ochLinkId ^ (ochLinkId >>> 32));
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
		final OchLink other = (OchLink) obj;
		if (ochLinkId != other.ochLinkId)
			return false;
		return true;
	}
}
