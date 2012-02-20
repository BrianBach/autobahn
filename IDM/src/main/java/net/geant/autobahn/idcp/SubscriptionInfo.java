/**
 *
 */
package net.geant.autobahn.idcp;

import java.util.Calendar;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

/**
 * Holds information related to subscription. Used to keep subscribers info as well as subscription that has been sent.
 *
 * @author PCSS
 */
public class SubscriptionInfo {

	private final String consumerUrl;
	private final String notifierUrl;
	private final String producerUrl;
	private final String publisherId;
	private final String topic;
	private Calendar termination;
	private String subscriptionId;
	private EndpointReferenceType subscriptionReference;

	public SubscriptionInfo(String consumerUrl, String notifierUrl, String producerUrl, String subscriptionId, String publisherId, String topic)
	{
		this.consumerUrl = consumerUrl;
		this.notifierUrl = notifierUrl;
		this.producerUrl = producerUrl;
		this.subscriptionId = subscriptionId;
		this.publisherId = publisherId;
		this.topic = topic;
	}

	/**
	 * Sets the subscriptionId
	 * @param subscriptionId
	 */
	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public void setSubscriptionReference(EndpointReferenceType reference) {
		subscriptionReference = reference;

		String id = reference.getReferenceParameters().getSubscriptionId();
		if (id != null && !id.equals(getSubscriptionId())) {
			setSubscriptionId(id);
		}
	}

	public EndpointReferenceType getSubscriptionReference() {
		return subscriptionReference;
	}

	/**
	 * @return the consumerUrl
	 */
	public String getConsumerUrl() {
		return consumerUrl;
	}

	/**
	 * @return the notifierUrl
	 */
	public String getNotifierUrl() {
		return notifierUrl;
	}

	/**
	 * @return the producerUrl
	 */
	public String getProducerUrl() {
		return producerUrl;
	}

	/**
	 * @return the subscriptionId
	 */
	public String getSubscriptionId() {
		return subscriptionId;
	}

	/**
	 * @return the publisherId
	 */
	public String getPublisherId() {
		return publisherId;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @return the termination
	 */
	public Calendar getTermination() {
		return termination;
	}

	/**
	 * Sets the termination
	 */
	public void setTermination(Calendar termination) {
		this.termination = termination;
	}

	public void setTerminationTimeIn(long millis)
	{
		termination = timeInFuture(millis);
	}

	private Calendar timeInFuture(long millis)
	{
		Calendar time = Calendar.getInstance();
		time.add(Calendar.SECOND, (int) MILLISECONDS.toSeconds(millis));
		return time;
	}

	@Override
	public String toString()
	{
		return String.format("Subscription[broker=%s,producer=%s,consumer=%s,id=%s,termination=%tc]",
							 notifierUrl, producerUrl, consumerUrl, subscriptionId, termination);
	}
}
