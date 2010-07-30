/**
 * 
 */
package net.geant.autobahn.idcp.notify;

import net.geant.autobahn.reservation.Reservation;



import java.util.Calendar;
import org.apache.log4j.Logger;
import org.oasis_open.docs.wsn.b_2.MessageType;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;


/**
 * Used to send messages(events) to OSCARSNotify web service
 * Only Notify method is supported as others are not needed by Autobahn
 * @author Michal
 *
 */
public class OscarsNotifyClient {
	
	private OSCARSNotify oscarsNotify;
	
	public OscarsNotifyClient() {
		
		OSCARSNotify_Service service = new OSCARSNotify_Service();
		oscarsNotify = service.getOSCARSNotify();
	}

	/**
	 * Send reservation update   
	 * @param resv
	 * @param event
	 */
	public void Notify(Reservation resv, String eventName) {
	
		if (resv == null)
			return;
		
		
		ResDetails res = new ResDetails();
		res.setStatus(eventName);
		res.setGlobalReservationId(resv.getBodID());
		res.setBandwidth((int)resv.getCapacity());
		res.setStartTime(resv.getStartTime().getTimeInMillis());
		res.setDescription(resv.getDescription());
		res.setEndTime(resv.getEndTime().getTimeInMillis());
		res.setLogin("AutoBAHN");
				
		EventContent event = new EventContent();
		event.setTimestamp(Calendar.getInstance().getTimeInMillis());
		event.setUserLogin("Autobahn"); // should be taken from user info
		event.setType("idc:IDC");
		event.setId("Autobahn - " + Calendar.getInstance().toString());
		event.setResDetails(res);

		MessageType messageType = new MessageType();
		messageType.getAny().add(event);
		
		TopicExpressionType topic = new TopicExpressionType();
		topic.setValue("idc:IDC");
		
		NotificationMessageHolderType message = new NotificationMessageHolderType();
		message.setTopic(topic);
		message.setMessage(messageType);
		
		Notify notify = new Notify();
		notify.getNotificationMessage().add(message);
				
		oscarsNotify.notify(notify);
		
	}
}
