/**
 * 
 */
package net.geant.autobahn.idcp.notify;


import net.geant.autobahn.reservation.Reservation;


import org.apache.log4j.Logger;
import java.rmi.RemoteException;
import java.util.Calendar;

import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.ReferenceParametersType;
import org.oasis_open.docs.wsn.b_2.FilterType;
import org.oasis_open.docs.wsn.b_2.MessageType;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.b_2.QueryExpressionType;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import org.oasis_open.docs.wsn.b_2.Unsubscribe;


/**
 * Used to send messages(events) to OSCARSNotify web service
 * @author Michal
 */
public class OscarsNotifyClient {
	
	private Logger log = Logger.getLogger(this.getClass()); 
	
	private OSCARSNotify oscarsNotify;
	
	public OscarsNotifyClient() {
		
		OSCARSNotify_Service service = new OSCARSNotify_Service();
		oscarsNotify = service.getOSCARSNotify();
	}
	
	public OscarsNotifyClient(String endpoint) {
		
		OSCARSNotify_Service service = new OSCARSNotify_Service(endpoint);
		oscarsNotify = service.getOSCARSNotify();
	}
	
	
	
	public void subscribe(String notifyReceiverAddress, String notifySenderAddress) throws RemoteException {
		
		final String oscarsService = "https://oscars-dev.es.net/axis2/services/OSCARS";
		
		
		Subscribe subscribe = new Subscribe();
		
		// ConsumerReference
		EndpointReferenceType consumer = new  EndpointReferenceType();
		
		
		//MetadataType meta = new MetadataType();
		//consumer.setMetadata(meta);
		
		ReferenceParametersType params = new ReferenceParametersType();
		params.setPublisherRegistrationId("none");
		params.setSubscriptionId("none");
		consumer.setReferenceParameters(params);
		
		AttributedURIType notifyReceiver = new AttributedURIType();
		notifyReceiver.setValue(notifyReceiverAddress);
		consumer.setAddress(notifyReceiver);
		subscribe.setConsumerReference(consumer);
		
		// Filter
		FilterType filter = new FilterType();
		TopicExpressionType topic = new TopicExpressionType();
		topic.setValue("idc:IDC");
		topic.setDialect("http://docs.oasis-open.org/wsn/t-1/TopicExpression/Full");
		filter.getTopicExpression().add(topic);
		QueryExpressionType query = new QueryExpressionType();
		query.setDialect("http://www.w3.org/TR/1999/REC-xpath-19991116");
		query.setValue(notifySenderAddress);
		filter.getProducerProperties().add(query);
		subscribe.setFilter(filter);

		// policy and initial time not required
		
		try {
			
			log.info("Sending subscribe");
			SubscribeResponse response = oscarsNotify.subscribe(subscribe);
			
			String subsRef = response.getSubscriptionReference().getAddress().getValue();
			String pubId = response.getSubscriptionReference().getReferenceParameters().getPublisherRegistrationId();
			String subsId = response.getSubscriptionReference().getReferenceParameters().getSubscriptionId();
			
			String termination = response.getTerminationTime().toGregorianCalendar().getTime().toString();
			System.out.println("idcp subscribe - " + subsRef + ", pubId: " + pubId + ", subsId: " + subsId + ", term: " + termination);
						
		} catch (Exception e) {
			
			log.info("idcp subscribe error - " + e.getMessage());
			throw new RemoteException(e.getMessage());
		}
	}
	
	public void unsubscribe(String notifyReceiverAddress) throws RemoteException {

		EndpointReferenceType consumer = new  EndpointReferenceType();
		
		//MetadataType meta = new MetadataType();
		//consumer.setMetadata(meta);
		
		ReferenceParametersType params = new ReferenceParametersType();
		params.setPublisherRegistrationId("none");
		params.setSubscriptionId("none");
		consumer.setReferenceParameters(params);
		
		AttributedURIType notifyReceiver = new AttributedURIType();
		notifyReceiver.setValue(notifyReceiverAddress);
		consumer.setAddress(notifyReceiver);
		
		Unsubscribe unsubscribe = new Unsubscribe();
		unsubscribe.setSubscriptionReference(consumer);
	
		try {
			
			oscarsNotify.unsubscribe(unsubscribe);
		} catch (Exception e) { 
			
			log.info("idcp unsubscribe error - " + e.getMessage());
			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Sends reservation update   
	 * @param resv
	 * @param event
	 */
	public void Notify(Reservation resv, String eventName) throws RemoteException {
	
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
				
		try {
			log.debug("sending notification - " + eventName);
			oscarsNotify.notify(notify);
			
		} catch (Exception e) { 
			log.debug("Notify error: " + e.getMessage());
			throw new RemoteException(e.getMessage());
		}
	}
}
