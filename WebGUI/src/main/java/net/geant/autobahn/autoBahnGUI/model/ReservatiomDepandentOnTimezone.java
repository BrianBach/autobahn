package net.geant.autobahn.autoBahnGUI.model;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import net.geant.autobahn.useraccesspoint.ReservationRequest;


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
	
	public void validateAddReservation(ValidationContext context) {
		
		MessageContext messages = context.getMessageContext();
        Calendar now = Calendar.getInstance();
		Calendar startTime = this.getRequest().getStartTime();
		
		if (startTime.compareTo(now) < 0) {
			messages.addMessage(new MessageBuilder().error().source("request.startTime").
                code("startTime.past").build());
		}
		
		if (startTime.compareTo(this.getRequest().getEndTime()) >= 0) { 
			messages.addMessage(new MessageBuilder().error().source("request.startTime").
	                code("startTime.greater").build());
    	}
		
		if (this.getRequest().getEndTime().compareTo(now) < 0) {
			messages.addMessage(new MessageBuilder().error().source("request.endTime").
                code("endTime.past").build());
		}
		
    }
}
