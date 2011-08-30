/**
 * 
 */
package net.geant.autobahn.idcp;

import java.util.Calendar;

import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.ReferenceParametersType;
import org.apache.log4j.Logger;
import org.oasis_open.docs.wsn.b_2.FilterType;
import org.oasis_open.docs.wsn.b_2.QueryExpressionType;
import org.oasis_open.docs.wsn.b_2.Renew;
import org.oasis_open.docs.wsn.b_2.RenewResponse;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import org.oasis_open.docs.wsn.b_2.Unsubscribe;
import org.oasis_open.docs.wsn.b_2.UnsubscribeResponse;

/**
 * Allows for sending (un)subscribe, renew and notify messages
 * 
 * @author PCSS
 */
public final class IdcpNotifyClient {
	
	private Logger log = Logger.getLogger(this.getClass()); 
	public static final String TOPIC_IDC = "idc:IDC";
	public static final String TOPIC_INFO = "idc:INFO";
	public static final String TOPIC_DEBUG = "idc:DEBUG";
	public static final String TOPIC_ERROR = "idc:ERROR";
	public static final String TOPIC_DIALECT = "http://docs.oasis-open.org/wsn/t-1/TopicExpression/Full";
	public static final String QUERY_DIALECT = "http://www.w3.org/TR/1999/REC-xpath-19991116";
	
	private final String url;
	private final OSCARSNotify idcpNotify;
	
	public IdcpNotifyClient(String url) {
		
		this.url = url;
		this.idcpNotify = new OSCARSNotify_Service(url).getOSCARSNotify();
	}
	
	private EndpointReferenceType createEndpoint(String consumerUrl, String subscriptionId, String publisherId) {
		
		EndpointReferenceType endpoint = new EndpointReferenceType();
		endpoint.setMetadata(null);
		AttributedURIType address = new AttributedURIType();
		address.setValue(consumerUrl);
		endpoint.setAddress(address);
		ReferenceParametersType ref = new ReferenceParametersType();
		ref.setSubscriptionId(subscriptionId);
		ref.setPublisherRegistrationId(publisherId);
		endpoint.setReferenceParameters(ref);
		
		return endpoint;
	}
	
	private void printEndpoint(EndpointReferenceType endpoint) {
		
		log.info("consumerUrl: " + endpoint.getAddress().getValue() + ", subId: " + endpoint.getReferenceParameters().getSubscriptionId() + 
						", pubId: " + endpoint.getReferenceParameters().getPublisherRegistrationId()); 
	}
	

	/**
	 * Sends subscribe message
	 * @param producerUrl - IDC generating events
	 * @param consumerUrl - where notifications should be sent
	 * @param subscriptionId - identities subscriptions, can be null  
	 * @param publisherId - identifies publisher, can be null
	 * @param topic - defines scope of notifications
	 * @param termination - defines for how long notifications should be sent by producers
	 * @return 
	 * @throws IdcpException
	 */
	public SubscriptionInfo subscribe(String producerUrl, String consumerUrl, String subscriptionId, String publisherId, String topic, Calendar termination) throws IdcpException {
		
		Subscribe request = new Subscribe();
		request.setConsumerReference(createEndpoint(consumerUrl, subscriptionId, publisherId));
		FilterType filter = new FilterType();
		TopicExpressionType topicExpression = new TopicExpressionType();
		topicExpression.setDialect(TOPIC_DIALECT);
		topicExpression.setValue(topic == null ? TOPIC_IDC : topic);
		filter.getTopicExpression().add(topicExpression);
		
		QueryExpressionType query = new QueryExpressionType();
		query.setDialect(QUERY_DIALECT);
		query.setValue(producerUrl);
		//filter.getProducerProperties().add(query); // this should be set however error is thrown if producerUrl contains port number
		request.setFilter(filter);
		request.setInitialTerminationTime(null);
		request.setSubscriptionPolicy(null);
		
		try {
			
			SubscribeResponse response = idcpNotify.subscribe(request);
			printEndpoint(response.getSubscriptionReference());
			log.info("termination time: " + response.getCurrentTime().toString() + ", current time: " + response.getCurrentTime().toString());
			// should return subscription info
			log.info("IDCP subscribed to " + url);
			return null;
		} catch (Exception e) { 
			e.printStackTrace();
			log.info("IDCP subscribe failed - " + e.getMessage());
			throw new IdcpException(e.getMessage());
		}
	}
	
	/**
	 * Sends unsubscribe message
	 * @param notifierUrl
	 * @param subscriptionId
	 * @param publisherId
	 * @throws IdcpException
	 */
	public void unsubscribe(String notifierUrl, String subscriptionId, String publisherId) throws IdcpException {
		
		Unsubscribe request = new Unsubscribe();
		request.setSubscriptionReference(createEndpoint(notifierUrl, subscriptionId, publisherId));
		
		try {
			UnsubscribeResponse response = idcpNotify.unsubscribe(request);
			printEndpoint(response.getSubscriptionReference());
			log.info("IDCP unsubscribed from " + url);
		} catch (Exception e) { 
			log.info("IDCP unsubscribe failed - " + e.getMessage());
			throw new IdcpException(e.getMessage());
		}
	}
	
	/**
	 * Sends renewal message
	 * @param consumerUrl
	 * @param subscriptionId
	 * @param publisherId
	 * @param termination
	 * @return
	 * @throws IdcpException
	 */
	public SubscriptionInfo renew(String consumerUrl, String subscriptionId, String publisherId, Calendar termination) throws IdcpException {
		
		Renew request = new Renew();
		request.setSubscriptionReference(createEndpoint(consumerUrl, subscriptionId, publisherId));
		request.setTerminationTime(termination.toString());
		
		try {
			
			RenewResponse response = idcpNotify.renew(request);
			printEndpoint(response.getSubscriptionReference());
			log.info("termination time: " + response.getCurrentTime().toString() + ", current time: " + response.getCurrentTime().toString());
			log.info("IDCP renew subscription in " + url);
			return null;
		} catch (Exception e) { 
			log.info("IDCP renew failed - " + e.getMessage());
			throw new IdcpException(e.getMessage());
		}
	}
}
