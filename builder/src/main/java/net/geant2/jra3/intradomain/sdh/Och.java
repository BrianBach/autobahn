package net.geant2.jra3.intradomain.sdh;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Och", propOrder={
		"payload", "Status"
})
public class Och implements Serializable {
	
	private static final long serialVersionUID = 5398236409010017977L;
	@XmlTransient
	private long ochId;
	private String payload;
	private String Status;
	
	public long getOchId() {
		return ochId;
	}
	public void setOchId(long ochId) {
		this.ochId = ochId;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	

}
