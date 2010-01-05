package net.geant2.jra3.gui;

import java.util.List;

import javax.jws.WebService;
import javax.jws.WebParam;
import net.geant2.jra3.administration.Status;
import net.geant2.jra3.administration.KeyValue;

/**
 * Provides notification to gui
 * @author Michal
 */

@WebService(targetNamespace = "http://gui.jra3.geant2.net/", name = "Gui")
public interface Gui {
	
	void statusUpdated(@WebParam(name="idm")String idm, @WebParam(name="status")Status status);
	
	void reservationChanged(@WebParam(name="idm")String idm, 
			@WebParam(name="serviceId")String serviceId, @WebParam(name="resID")String resID,
			@WebParam(name="state")ReservationChangedType state,
			@WebParam(name="message")String message);
	
	void update(@WebParam(name="idm")String idm, @WebParam(name="event")EventType event,
				@WebParam(name="properties")List<KeyValue> properties);

}
