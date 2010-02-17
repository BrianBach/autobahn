package net.geant.autobahn.gui;

import java.util.List;

import javax.jws.WebService;
import javax.jws.WebParam;

import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.Status;

/**
 * Provides notification to gui
 * @author Michal
 */

@WebService(targetNamespace = "http://gui.autobahn.geant.net/", name = "Gui")
public interface Gui {
	
	void statusUpdated(@WebParam(name="idm")String idm, @WebParam(name="status")Status status);
	
	void reservationChanged(@WebParam(name="idm")String idm, 
			@WebParam(name="serviceId")String serviceId, @WebParam(name="resID")String resID,
			@WebParam(name="state")ReservationChangedType state,
			@WebParam(name="message")String message);
	
	void update(@WebParam(name="idm")String idm, @WebParam(name="event")EventType event,
				@WebParam(name="properties")List<KeyValue> properties);

}
