/**
 * 
 */
package net.geant.autobahn.idcp;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;


import net.geant.autobahn.idm.AccessPoint;

import org.apache.log4j.Logger;

/**
 * Used to retrieve properties for an idcp domain identified by its url adddress
 * Manages notifications ((ub)subscribe, renew)
 * @author PCSS
 *
 */
public final class IdcpManager {
	
	private static Logger log = Logger.getLogger(IdcpManager.class);
	public static final String NOTIFY_SERVICE = "notify.service"; // read from idm.properties
	public static final String NOTIFY_URL = "notify.url";
	private static Map<String, String> idcps = new HashMap<String, String>();
	private static String notifyServiceUrl;
	
	private static List<SubscriptionInfo> subscribers = new ArrayList<SubscriptionInfo>();
	private static List<SubscriptionInfo> subscriptions = new ArrayList<SubscriptionInfo>();
	
	private IdcpManager() { }
	
	static {
		
		// get local notification service url
		notifyServiceUrl = AccessPoint.getInstance().getProperty(NOTIFY_SERVICE);
		//notifyServiceUrl = "https://idcdev0.internet2.edu:8443/axis2/services/OSCARS";

	}
	
	/**
	 * Generates subscription id used for notifications
	 * @return
	 */
	public static String generateSubscriptionId() {
		
		UUID id = UUID.randomUUID();
		return id.toString();
	}
	
	public static String getNotifyServiceUrl() {
		
		return notifyServiceUrl;
	}


	public static void addIdcp(String url, String name) {
		idcps.put(url, name);
	}
	
	public static String getName(String url) {
		return idcps.get(url);
	}
	
	public static Properties getProperties(String url) {
		
		if (!idcps.containsKey(url))
			return null;
		
		Properties properties = new Properties();
		try {
			FileInputStream in = new FileInputStream("etc/" + idcps.get(url) + ".properties");
			properties.load(in);
			in.close();
			return properties;
		} catch (Exception e) {
			log.info("IdcpManager - cannot get properties, " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Subscribes to all notify producers defined in each idcp.*.properties
	 */
	public static void startSubscriptions() {
		
		for (String url : idcps.keySet()) {
			
			Properties properties = getProperties(url);
			if (properties.containsKey(NOTIFY_URL)) {
				
				String producerUrl = properties.getProperty(NOTIFY_URL);
				
			}
		}
	}
	
	/**
	 * Sends unsubscribe message for each notify producers defined in each idcp.*.properties
	 */
	public static void stopSubscriptions() {
		
	}
	
	// SUBSCRIBERS
	
	public static void addSubscriber(SubscriptionInfo subInfo) { 
		
		subscribers.add(subInfo);
		log.info("added new subscriber");
	}
	
	public static void removeSubscriber(SubscriptionInfo subInfo) {
		
		subscribers.remove(subInfo);
		log.info("removed subscriber");
	}
	
	public static SubscriptionInfo findSubscriber(String consumerUrl, String subscriptionId) {
		
		for (SubscriptionInfo si : subscribers) {
			
			if (si.getSubscriptionId().equals(subscriptionId))
				return si;
			
		}
		return null;
	}
	
	public static List<SubscriptionInfo> getSubscribers() { 
		
		return subscribers;
	}
	
	private static void saveSubscribers() { 
		
		
	}
	
	private static void restoreSubscribers() { 
		
		
	}
	
	
	// SUBSCRIPTIONS
	public static List<SubscriptionInfo> getSubscriptions() { 
		
		return subscriptions;
	}
}
