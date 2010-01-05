package net.geant2.jra3.autoBahnGUI.webflow;

import java.io.Serializable;

import net.geant2.jra3.useraccesspoint.ReservationRequest;


public class ReservatiomDepandentOnTimezone implements Serializable {

	private String timezone;
	private ReservationRequest request;
	
	
	public String getTimezone() {
		return timezone;
	}
	public ReservationRequest getRequest() {
		return request;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public void setRequest(ReservationRequest request) {
		this.request = request;
	}
}
