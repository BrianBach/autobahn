package net.geant2.jra3.autoBahnGUI.webflow;

import java.util.Locale;

import net.geant2.jra3.useraccesspoint.ServiceRequest;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class ServiceValidator  extends AbstractAction implements MessageSourceAware{
	
	MessageSource messages;

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		ServiceRequest request =(ServiceRequest)context.getFlowScope().get("service");
		if (request == null){
			context.getFlowScope().put("error", messages.getMessage("service.not.provided", null, Locale.getDefault()));
			return error();
		}
		if (request.getReservations()== null || request.getReservations().size()==0){
			context.getFlowScope().put("error", messages.getMessage("service.empty.reservations", null, Locale.getDefault()));
			return error();
		}		
		return success();
	}

	public void setMessageSource(MessageSource messages) {
		this.messages= messages;
	}
}
