/**
 *
 */
package net.geant.autobahn.idcp;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.ReferenceParametersType;
import org.apache.log4j.Logger;
import org.oasis_open.docs.wsn.b_2.FilterType;
import org.oasis_open.docs.wsn.b_2.MessageType;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.Notify;
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

	private XMLGregorianCalendar toXmlCalendar(Date time) {

		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(time);
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (Exception e) {
			log.error("could not create xml calendar from Date - " + time.toString());
			return null;
		}
	}

	private XMLGregorianCalendar toXMLCalendar(Calendar cal) {

		if (cal == null)
			return null;

		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(cal.getTimeInMillis());
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		} catch (DatatypeConfigurationException e) {
			log.error("Error when converting from Calendar to XMLGreogorianCalendar");
			return null;
		}
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
	public void subscribe(SubscriptionInfo subscription) throws IdcpException
	{
		final String consumerUrl = subscription.getConsumerUrl();
		final String producerUrl = subscription.getProducerUrl();
		final String subscriptionId = subscription.getSubscriptionId();
		final String publisherId = subscription.getPublisherId();
		final String topic = subscription.getTopic();

		Subscribe request = new Subscribe();

		request.setConsumerReference(createEndpoint(consumerUrl, subscriptionId, publisherId));
		FilterType filter = new FilterType();
		TopicExpressionType topicExpression = new TopicExpressionType();
		topicExpression.setDialect(Idcp.TOPIC_DIALECT);
		topicExpression.setValue(topic);
		filter.getTopicExpression().add(topicExpression);

		QueryExpressionType query = new QueryExpressionType();
		query.setDialect(Idcp.QUERY_DIALECT);
		query.setValue("/wsa:Address='" + producerUrl + "'");
		filter.getProducerProperties().add(query);
		request.setFilter(filter);

		// null as axis2 has problems with it
		request.setInitialTerminationTime(null);
		request.setSubscriptionPolicy(null);

		try {
			SubscribeResponse response = idcpNotify.subscribe(request);

			subscription.setTermination(response.getTerminationTime().toGregorianCalendar());
			subscription.setSubscriptionReference(response.getSubscriptionReference());

			if (response.getCurrentTime() != null) {
				log.info("Subscribed to " + subscription + " at " +
						 response.getCurrentTime() + " remote time");
			} else {
				log.info("Subscribed to " + subscription);
			}
		} catch (Exception e) {
			log.warn("IDCP subscribe failed - " + e.getMessage(), e);
			throw new IdcpException(e.getMessage(), e);
		}
	}

	public void unsubscribe(SubscriptionInfo subscription)
		throws IdcpException
	{
		unsubscribe(subscription.getSubscriptionReference());
	}

	public void unsubscribeAll(String notifierUrl)
		throws IdcpException
	{
		unsubscribe(notifierUrl, "ALL", null);
	}

	public void unsubscribe(String notifierUrl, String subscriptionId,
							String publisherId)
		throws IdcpException
	{
		unsubscribe(createEndpoint(notifierUrl, subscriptionId, publisherId));
	}

	private void unsubscribe(EndpointReferenceType reference)
		throws IdcpException
	{
		try {
			Unsubscribe request = new Unsubscribe();
			request.setSubscriptionReference(reference);
			UnsubscribeResponse response = idcpNotify.unsubscribe(request);
			log.info("Unsubscribed [notifier=" +
					 reference.getAddress().getValue() + ",id=" +
					 reference.getReferenceParameters().getSubscriptionId() +
					 "]");
		} catch (Exception e) {
			log.warn("IDCP unsubscribe failed - " + e.getMessage());
			throw new IdcpException(e.getMessage(), e);
		}
	}

	/**
	 * Sends renewal message
	 * @param subscriptionReference
	 * @param termination
	 * @return
	 * @throws IdcpException
	 */
	public void renew(SubscriptionInfo subscription)
		throws IdcpException
	{
		try {
			final EndpointReferenceType reference =
				subscription.getSubscriptionReference();
			final Calendar termination =
				subscription.getTermination();
			Renew request = new Renew();
			request.setSubscriptionReference(reference);
			request.setTerminationTime(null); // FIXME

			String subscriptionId = subscription.getSubscriptionId();
			RenewResponse response = idcpNotify.renew(request);

			subscription.setTermination(response.getTerminationTime().toGregorianCalendar());

			if (response.getCurrentTime() != null) {
				log.info("Renewed " + subscription + " at " +
						 response.getCurrentTime() + " remote time");
			} else {
				log.info("Renewed " + subscription);
			}
		} catch (Exception e) {
			log.debug("IDCP renew failed - " + e.getMessage());
			throw new IdcpException(e.getMessage(), e);
		}
	}

	/**
	 * Used by reservations to send async notification to subscribers
	 * @param resId
	 * @param desc
	 * @param start
	 * @param end
	 * @param bandwidth
	 * @param pathInfo
	 * @param eventType
	 * @param status
	 * @param errorMessage
	 * @param subId
	 * @throws IdcpException
	 */
	public void notification(String resId, String desc, long start, long end, long create, int bandwidth, PathInfo pathInfo,
				String eventType, String status, String errorMessage, String subId) throws IdcpException {

		Notify request = new Notify();
		NotificationMessageHolderType notificationMessage = new NotificationMessageHolderType();

		EventContent event = new EventContent();
		event.setType(eventType);
		event.setId(UUID.randomUUID().toString());

		event.setErrorSource("autobahn");
		event.setErrorCode("autobahn");
		event.setErrorMessage(errorMessage);
		event.setTimestamp(System.currentTimeMillis() / Idcp.TIME_SCALE);
		event.setUserLogin("idcp");

		ResDetails res = new ResDetails();
		res.setPathInfo(pathInfo);
		res.setDescription(desc);
		res.setLogin("autobahn");
		res.setStartTime(start / Idcp.TIME_SCALE);
		res.setEndTime(end / Idcp.TIME_SCALE);
		res.setCreateTime(create / Idcp.TIME_SCALE);
		res.setBandwidth(bandwidth / Idcp.BANDWIDTH_SCALE);
		res.setStatus(status);
		res.setGlobalReservationId(resId);
		event.setResDetails(res);

		MessageType message = new MessageType();
		message.getEvent().add(event);
		notificationMessage.setMessage(message);

		TopicExpressionType topicExpression = new TopicExpressionType();
		topicExpression.setDialect(Idcp.TOPIC_DIALECT);
		topicExpression.setValue(Idcp.TOPIC_IDC);
		notificationMessage.setTopic(topicExpression);

		final String oscarsUrl = IdcpManager.getIdcpUrl();
		final String notifierUrl = IdcpManager.getIdcpNotifyUrl();
		notificationMessage.setProducerReference(createEndpoint(oscarsUrl, subId, null));
		notificationMessage.setSubscriptionReference(createEndpoint(notifierUrl, subId, null));

		request.getNotificationMessage().add(notificationMessage);

		try {
			idcpNotify.notify(request);
		} catch (Exception e) {
			log.warn("IDCP notify failed - " + e.getMessage());
			throw new IdcpException(e.getMessage());
		}
	}
}
