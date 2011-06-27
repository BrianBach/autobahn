/**
 * 
 */
package net.geant.autobahn.idcp;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.geant.autobahn.idcp.notify.IdcpNotifyClient;

import org.apache.log4j.Logger;

/**
 * Used to retrieve properties for an idcp domain identified by its url adddress
 * Manages notifications ((ub)subscribe, renew)
 * @author PCSS
 *
 */
public final class IdcpManager {
	
	private static Logger log = Logger.getLogger(IdcpManager.class);
	public static final String NOTIFY_URL = "notify.url";
	private static Map<String, String> idcps = new HashMap<String, String>();
	private static Map<String, String> subscriptions = new HashMap<String, String>();

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
}
