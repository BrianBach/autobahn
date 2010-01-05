package net.geant2.jra3.intradomain.sdh;

import java.io.Serializable;
import java.util.Date;

public class HoVcLink implements Serializable {
	
	private static final long serialVersionUID = 730368901977762856L;
	
	private long hoVcLinkId;
	private HoVcGroup hoVcGroup;
	private StmLink stmLink;
	private HoVcType hoVcType;
	private long timeSlot;
	private Date dateModified;
	private long groupSequence;
	private String status;
	
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public long getGroupSequence() {
		return groupSequence;
	}
	public void setGroupSequence(long groupSequence) {
		this.groupSequence = groupSequence;
	}
	public HoVcGroup getHoVcGroup() {
		return hoVcGroup;
	}
	public void setHoVcGroup(HoVcGroup hoVcGroup) {
		this.hoVcGroup = hoVcGroup;
	}
	public long getHoVcLinkId() {
		return hoVcLinkId;
	}
	public void setHoVcLinkId(long hoVcLinkId) {
		this.hoVcLinkId = hoVcLinkId;
	}
	public HoVcType getHoVcType() {
		return hoVcType;
	}
	public void setHoVcType(HoVcType hoVcType) {
		this.hoVcType = hoVcType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public StmLink getStmLink() {
		return stmLink;
	}
	public void setStmLink(StmLink stmLink) {
		this.stmLink = stmLink;
	}
	public long getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(long timeSlot) {
		this.timeSlot = timeSlot;
	}
	

}
