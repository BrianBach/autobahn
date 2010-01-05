package net.geant2.jra3.useraccesspoint;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ModifyRequest", namespace="useraccesspoint.jra3.geant2.net", propOrder={
		"resId", "startTime", "endTime"
})
public class ModifyRequest {
	private String resId;
	private Calendar startTime;
	private Calendar endTime;

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public Calendar getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
	
	public Calendar getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
}
