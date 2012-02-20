/**
 *
 */
package net.geant.autobahn.idcp;

import edu.internet2.perfsonar.PSTopologyClient;
import edu.internet2.perfsonar.TSLookupClient;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import net.geant.autobahn.network.Link;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.transform.JDOMSource;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent;

/**
 * Keeps configuration info related to idcp (this domain and peering domains)
 * Manages subscriptions and event notifications (both incoming and outcoming)
 *
 * @author PCSS
 */
public final class IdcpManager {

	private static final Logger log = Logger.getLogger(IdcpManager.class);

	public static final String IDCP_PROPERTY_FILE = "idcp.properties";
	public static final String IDCP_NONE = "none"; // default property value
	public static final String PROPERTY_INTEROPERABILITY_ENABLED = "interoperability.enabled";
	public static final String PROPERTY_DEBUGGING_ENABLED = "debugging.enabled";
	public static final String PROPERTY_MAX_SUBSCRIPTION = "max.subscription.time";
	public static final String PROPERTY_TOPIC = "topic";
	public static final String PROPERTY_DIALECT = "dialect";
	public static final String PROPERTY_DOMAIN_NAME = "domain.name";
	public static final String PROPERTY_PSTOPOLOGY_URL = "pstopology.url";
	public static final String PROPERTY_PSTOPOLOGY_FILE = "pstopology.file";
	public static final String PROPERTY_STATIC_ROUTE = "static.route";
	public static final String PROPERTY_IDCP_URL = "oscars.url";
	public static final String PROPERTY_IDCPNOTIFY_URL = "oscarsnotify.url";
	public static final String PROPERTY_IDCPNOTIFYONLY_URL = "oscarsnotifyonly.url";
	public static final String PROPERTY_ENDPOINTS_WHITELIST = "endpoints.whitelist";

	public static final String IDCP_SUBSCRIBERS_FILE = "etc/subscribers.txt";
	public static final int DEFAULT_SUBSCRIPTION_TIME = 50; // in minutes


	private static String idcpUrl, idcpNotifyUrl, idcpNotifyOnlyUrl;
	private static String domainName, psTopologyUrl, psTopologyFile;
	private static final Map<String, IdcpDomain> idcpDomains = new HashMap<String, IdcpDomain>(); // domainName, IdcpDomain

	private static boolean debugging, initialized;
	private static Map<String, Properties> idcps = new HashMap<String, Properties>(); // url, properties
	private static String idcpEndpointsFile;
	private static HashSet<String> idcpEndpoints;

	private static List<SubscriptionInfo> subscribers = new ArrayList<SubscriptionInfo>();

    private static SubscriptionManager subscriptionManager =
        new SubscriptionManager();


	private IdcpManager() { }

	/**
	 * Reads idcp.properties file and other referenced files
	 */
	public static boolean initialize()  {

		try {

			FileInputStream in = new FileInputStream("etc/" + IDCP_PROPERTY_FILE);
			Properties properties = new Properties();
			properties.load(in);
			in.close();

			idcpUrl = properties.getProperty(PROPERTY_IDCP_URL);
			idcpNotifyUrl = properties.getProperty(PROPERTY_IDCPNOTIFY_URL);
			idcpNotifyOnlyUrl = properties.getProperty(PROPERTY_IDCPNOTIFYONLY_URL);
			if (idcpUrl == null || idcpNotifyUrl == null || idcpNotifyOnlyUrl == null) {
				log.info("idcp properties wrongly defined");
				return false;
			}
			domainName = properties.getProperty(PROPERTY_DOMAIN_NAME);
			if (domainName == null) {
				log.info("domain name must be defined");
				return false;
			}

			psTopologyUrl = properties.getProperty(PROPERTY_PSTOPOLOGY_URL, IDCP_NONE);
			psTopologyFile = properties.getProperty(PROPERTY_PSTOPOLOGY_FILE, IDCP_NONE);
			idcpEndpointsFile = properties.getProperty(PROPERTY_ENDPOINTS_WHITELIST, IDCP_NONE);

			boolean enabled = Boolean.parseBoolean(properties.getProperty(PROPERTY_INTEROPERABILITY_ENABLED, "false"));
			if (!enabled) {
				log.info("IdcpManager functionality disabled");
				return false;
			}
			debugging = Boolean.parseBoolean(properties.getProperty(PROPERTY_DEBUGGING_ENABLED, "false"));

			// read referenced files
			final String prefix = "idcp.";
			for (Enumeration e = properties.propertyNames(); e.hasMoreElements(); ) {

				String name = (String)e.nextElement();
				try {
					if (name.startsWith(prefix)) {
						in = new FileInputStream("etc/" + name + ".properties");
						Properties idcpProperties = new Properties();
						idcpProperties.load(in);
						in.close();

						String idcpDomainName = properties.getProperty(name);
						if (idcpDomainName == null) {
							log.info("idcp property " + name + " has no valid domain name");
							continue;
						}
						String idcpUrl = idcpProperties.getProperty(PROPERTY_IDCP_URL, IDCP_NONE);
						String idcpNotifyUrl = idcpProperties.getProperty(PROPERTY_IDCPNOTIFY_URL, IDCP_NONE);
						String idcpPsTopologyUrl = idcpProperties.getProperty(PROPERTY_PSTOPOLOGY_URL, IDCP_NONE);
						String idcpStaticRoute = idcpProperties.getProperty(PROPERTY_STATIC_ROUTE);
						IdcpDomain idcpDomain = new IdcpDomain(idcpUrl, idcpNotifyUrl, idcpDomainName, idcpPsTopologyUrl, idcpStaticRoute, idcpProperties);
						idcpDomains.put(idcpDomainName, idcpDomain);
					}
				} catch (IOException e1) {
					log.info("IdcpManager could not load " + name + " properties");
				}
			}

			// export autobahn topology to defined ps topology service
			if (psTopologyUrl.equals(IDCP_NONE) || psTopologyFile.equals(IDCP_NONE)) {
				//log.info("autobahn topology file configured to be not exported");
			} else {
				try {
					String TopologyXml = loadTopology(psTopologyFile);
					exportTopology(psTopologyUrl, TopologyXml);
					log.info("autobahn topology file exported to " + psTopologyUrl);
				} catch (Exception e) {
					log.info(psTopologyFile + " not exported to " + psTopologyUrl + ", " + e.getMessage());
					return false;
				}
			}

		} catch (IOException e) {
			log.info("IdcpManager initialization error - " + e.getMessage());
		}
		log.info("IdcpManager initialized successfully - " + idcpDomains.size() + " idcp domains");
		initialized = true;
		return initialized;
	}

	/**
	 * True if IdcpManager has been initialized successfully, otherwise false
	 */
	public static boolean isInitialized() {

		return initialized;
	}

	/**
	 * Returns true if debugging is turned on, false otherwise
	 * @return
	 */
	public static boolean isDebugging() {

		return debugging;
	}

	/**
	 * Returns IdcpDomain for give domainName
	 * @param domainName
	 */
	public static IdcpDomain getIdcpDomain(String domainName) {

		return idcpDomains.get(domainName);
	}

	/**
	 * Returns a collection of IdcpDomain
	 * @return
	 */
	public static List<IdcpDomain> getIdcpDomains() {

		// sort peered domains first, then non peered ones
		List<IdcpDomain> domains = new ArrayList<IdcpDomain>(idcpDomains.values());
		Collections.sort(domains, new Comparator<IdcpDomain>() {
			@Override
			public int compare(IdcpDomain d1, IdcpDomain d2) {
				if (d1.isPeered() && !d2.isPeered())
					return -1;
				else
					return 1;
			}
		});
		return domains;
	}

	/**
	 * Returns string representing url of this oscars instance
	 * @return
	 */
	public static String getIdcpUrl() {

		return idcpUrl;
	}

	/**
	 * Returns string representing url of this oscarsnotify instance
	 * @return
	 */
	public static String getIdcpNotifyUrl() {

		return idcpNotifyUrl;
	}

	/**
	 * Returns string representing url of this oscarsnotifyonly instance
	 * @return
	 */
	public static String getIdcpNotifyOnlyUrl() {

		return idcpNotifyOnlyUrl;
	}

	/**
	 * Returns domain name(for autobahn) as registered in ps topology service
	 * @return
	 */
	public static String getDomainName() {

		return domainName;
	}

	/**
	 * Returns valid idcp endpoints read from a csv file
	 * @return
	 */
	public static HashSet<String> getEndpointsWhiteList() {

		if (idcpEndpoints != null)
			return idcpEndpoints;

		if (idcpEndpointsFile.equals(IDCP_NONE))
			return null;

		File file = new File(idcpEndpointsFile);
		if (!file.exists()) {
			log.info(idcpEndpointsFile + " not found");
			return null;
		}

		idcpEndpoints = new HashSet<String>();
		try {
            BufferedReader in = new BufferedReader(new FileReader(idcpEndpointsFile));
            String line = in.readLine(); // skip first line
		    while ((line = in.readLine()) != null) {
		    	String[] tokens = line.split(",");
		        if (tokens != null) {
		        	idcpEndpoints.add(tokens[1]);
		        }
		    }
		    in.close();
		    log.info(idcpEndpointsFile + " loaded with " + idcpEndpoints.size() + " endpoints");
		    return idcpEndpoints;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Sends topology xml file (starting with domain tag) to perfsonar topology service
	 * @param psTopologyServiceUrl
	 * @param topologyXml
	 */
	public static void exportTopology(String psTopologyServiceUrl, String topologyXml) {

		if (psTopologyServiceUrl == null || psTopologyServiceUrl.equals("none")) {
			log.info("exporting topology to perfsonar service not defined");
		} else {
			PSTopologyClient ps = new PSTopologyClient(psTopologyServiceUrl);
			boolean result = ps.addReplaceDomain(topologyXml);
			log.info("exporting topology to perfsonar service at " + psTopologyServiceUrl + " ended with status " + result);
		}
	}

	private static String loadTopology(String filename) {

		File file = new File(filename);
		if (!file.exists())
			return null;

		try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            StringBuffer sb = new StringBuffer();
		    String str;
		    while ((str = in.readLine()) != null)
		        sb.append(str);
		    in.close();
			return sb.toString();

		} catch (IOException e) {
			log.info("xml topology not loaded - " + e.getMessage());
			return null;
		}
	}

	public static List<Link> getTopology(String psTopologyUrl, String domain) throws Exception {

		if (debugging) {
			log.info("pulling topology from " + psTopologyUrl + " for domain " + domain);
		}

		final String namespace = "http://ogf.org/schema/network/topology/ctrlPlane/20080828/";
		final String domainName = "urn:ogf:network:domain=" + domain;

		TSLookupClient psClient = new TSLookupClient(null, null, new String[] { psTopologyUrl });
		//psClient.setTSList(new String[] { psTopologyUrl });
		Element domainElement = psClient.getDomain(domainName, namespace);
		Namespace ns = Namespace.getNamespace(namespace);
		Element topo = domainElement.getChild("domain", ns);
		topo.detach();
		Document doc = new Document(topo);

		Source src = new JDOMSource(doc);
        JAXBContext context = JAXBContext.newInstance("org.ogf.schema.network.topology.ctrlplane._20080828");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        JAXBElement jaxbObj = (JAXBElement) unmarshaller.unmarshal(src);
        CtrlPlaneDomainContent topoContent = (CtrlPlaneDomainContent) (jaxbObj.getValue());

        List<CtrlPlaneDomainContent> domains = new ArrayList<CtrlPlaneDomainContent>();
        domains.add(topoContent);
		List<Link> links = OscarsConverter.getGeantTopology(domains);
		if (debugging)
			log.info(links.size() + " links retrieved from " + psTopologyUrl);

		return links;
	}

	/**
	 * Uses getNetworkTopology to obtain other domain's topology, deprecated
	 * @param idcpUrl
	 * @return
	 * @throws Exception
	 */
	public static List<Link> getTopology(String idcpUrl) throws Exception {

		IdcpClient idcp = new IdcpClient(idcpUrl);
		return idcp.getTopology();
	}

	/**
	 * Subscribes to all notify producers defined in each idcp.*.properties
	 */
	public static void startSubscriptions() {
		for (IdcpDomain dom : idcpDomains.values()) {
            final String notifierUrl = dom.getIdcpNotifyUrl();
			final String producerUrl = dom.getIdcpUrl();

			if (notifierUrl.equals(IDCP_NONE)) {
				continue;
            }

    		log.info("Subscribing to [notifier=" + notifierUrl +
					 ",producer=" + producerUrl +
					 ",consumer=" + idcpNotifyOnlyUrl + "]");
            SubscriptionInfo subscription =
                subscriptionManager.subscribe(notifierUrl,
                                              producerUrl,
                                              idcpNotifyOnlyUrl);
		}

        // restore subscribers if any
		restoreSubscribers();
	}

	/**
	 * Sends unsubscribe message for each notify producers defined in each idcp.*.properties
	 */
	public static void stopSubscriptions() {
        subscriptionManager.shutdown();
	}

	// SUBSCRIBERS

	/**
	 * Called from OSCARSNotify subscribe
	 */
	public static synchronized boolean addSubscriber(SubscriptionInfo subInfo) {

		for (SubscriptionInfo si : subscribers) {
			if (si.getSubscriptionId().equals(subInfo.getSubscriptionId()))
				return false;

			if (si.getConsumerUrl().equals(subInfo.getConsumerUrl())) {

				log.info("addSubscriber: " + subInfo.getConsumerUrl() + " already exists, overwriting subId");
				si.setSubscriptionId(subInfo.getSubscriptionId());
				saveSubscribers();
				return true;
			}
		}
		subscribers.add(subInfo);
		saveSubscribers();
		log.info("new idcp subscriber added - " + subInfo.getConsumerUrl());
		return true;
	}

	/**
	 * Called from OSCARSNotify unsubscribe
	 * @param subInfo
	 */
	public static synchronized boolean removeSubscriber(String address, String subscriptionId) {

		for (SubscriptionInfo si : subscribers) {

			if (si.getSubscriptionId().equals(subscriptionId)) {
				subscribers.remove(si);
				return true;
			}
		}
		log.info("unsubscribe - subscriptionId: " + subscriptionId + " not found");
		return false;
	}

	/**
	 * Updates time on subscription identified by subscription id
	 * @param address
	 * @param subscriptionId
	 * @param newTime
	 * @return
	 */
	public static synchronized boolean updateSubscriber(String address, String subscriptionId, Calendar newTime) {

		for (SubscriptionInfo si : subscribers) {

			if (si.getSubscriptionId().equals(subscriptionId)) {
				si.setTermination(newTime);
				log.info("renew - subscriptionId: " + subscriptionId + " updated");
				return true;
			}
		}
		log.info("renew - subscriptionId: " + subscriptionId + " not found");
		return false;
	}

	/**
	 * Returns a list of subscribers
	 * @return
	 */
	public static List<SubscriptionInfo> getSubscribers() {

		return Collections.unmodifiableList(subscribers);
	}

	private static synchronized void saveSubscribers() {

		try {
			File file = new File(IDCP_SUBSCRIBERS_FILE);
			file.delete();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (SubscriptionInfo si : subscribers) {

            	StringBuffer sb = new StringBuffer();
            	sb.append(si.getConsumerUrl() + " ");
            	sb.append(si.getNotifierUrl() + " ");
            	sb.append(si.getProducerUrl() + " ");
            	sb.append(si.getSubscriptionId() + " ");
            	sb.append(si.getPublisherId() != null ? si.getPublisherId() : "pubId");
            	sb.append(" ");
            	sb.append(si.getTopic() + " ");
            	sb.append(si.getTermination() != null ? si.getTermination().getTimeInMillis(): 0);
            	writer.write(sb.toString());
            	writer.newLine();
            }
            writer.flush();
            writer.close();
		} catch (IOException e) {
			log.info("Could not save subscribers to " + IDCP_SUBSCRIBERS_FILE);
		}
	}

	private static synchronized void restoreSubscribers() {

		try {
			File file = new File(IDCP_SUBSCRIBERS_FILE);
			if (!file.exists())
				return;
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {

				String line = scanner.nextLine();
				String[] tokens = line.split(" ");
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(Long.parseLong(tokens[6]));
				SubscriptionInfo si = new SubscriptionInfo(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
                si.setTermination(cal);
				subscribers.add(si);
				log.info("Idcp subscriber restored: " + si.getConsumerUrl());
			}
			scanner.close();
		} catch (IOException e) {
			log.info("Could not restore subscribers from " + IDCP_SUBSCRIBERS_FILE);
		}
	}
}
