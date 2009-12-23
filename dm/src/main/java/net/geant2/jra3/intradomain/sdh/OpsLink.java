package net.geant2.jra3.intradomain.sdh;

import java.io.Serializable;

import net.geant2.jra3.intradomain.common.GenericLink;

public class OpsLink implements Serializable {
	
	private static final long serialVersionUID = -7873511604109871707L;
	
	private GenericLink opsLink;
	private long maxNoLambdas;
	private long bitrate;
	private String status;
	
	public long getBitrate() {
		return bitrate;
	}
	public void setBitrate(long bitrate) {
		this.bitrate = bitrate;
	}
	public long getMaxNoLambdas() {
		return maxNoLambdas;
	}
	public void setMaxNoLambdas(long maxNoLambdas) {
		this.maxNoLambdas = maxNoLambdas;
	}
	public GenericLink getOpsLink() {
		return opsLink;
	}
	public void setOpsLink(GenericLink opsLink) {
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
		result = prime * result + ((opsLink == null) ? 0 : opsLink.hashCode());
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
		final OpsLink other = (OpsLink) obj;
		if (opsLink == null) {
			if (other.opsLink != null)
				return false;
		} else if (!opsLink.equals(other.opsLink))
			return false;
		return true;
	}
}
