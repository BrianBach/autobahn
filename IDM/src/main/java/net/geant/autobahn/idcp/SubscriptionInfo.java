/**
 * 
 */
package net.geant.autobahn.idcp;

import java.util.Calendar;

/**
 * Holds information related to subscription. Used to keep subscribers info as well as subscription that has been sent. 
 * 
 * @author PCSS
 */
public class SubscriptionInfo {
	
	private String consumerUrl;
	private String notifierUrl;
	private String producerUrl;
	private String subscriptionId;
	private String publisherId;
	private String topic;
	private Calendar termination;
	
	public SubscriptionInfo(String consumerUrl, String notifierUrl, String subscriptionId, String publisherId,	String topic, Calendar termination) {
		
		this(consumerUrl, notifierUrl, notifierUrl, subscriptionId, publisherId, topic, termination);
	}
	
	public SubscriptionInfo(String consumerUrl, String notifierUrl, String producerUrl, String subscriptionId, String publisherId,
			String topic, Calendar termination) {
		
		this.consumerUrl = consumerUrl; 
		this.notifierUrl = notifierUrl;
		this.producerUrl = producerUrl;
		this.subscriptionId = subscriptionId;
		this.publisherId = publisherId;
		this.topic = topic;
		this.termination = termination;
	}

	/**
	 * Sets the subscriptionId
	 * @param subscriptionId
	 */
	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
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

	@Override
	public String toString() {
		
		return "ConsumerUrl: " + consumerUrl + ", subscriptionId: " + subscriptionId; 	
	}
}
