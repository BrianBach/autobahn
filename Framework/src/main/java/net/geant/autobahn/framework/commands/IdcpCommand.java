package net.geant.autobahn.framework.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.geant.autobahn.framework.Framework;
import net.geant.autobahn.idcp.Idcp;
import net.geant.autobahn.idcp.IdcpClient;
import net.geant.autobahn.idcp.IdcpDomain;
import net.geant.autobahn.idcp.IdcpManager;
import net.geant.autobahn.idcp.IdcpNotifyClient;
import net.geant.autobahn.idcp.ResDetails;
import net.geant.autobahn.idcp.SubscriptionInfo;
import net.geant.autobahn.network.Link;

/**
 * Allows for calling some methods on IdcpClient
 * @author PCSS
 **/
public final class IdcpCommand implements AutobahnCommand {

	private final static String NL = "\r\n";
	private final static String invalid = "specified arguments were invalid";
	private static HashMap<String, String> friends;

	static {
		friends = new HashMap<String, String>();
		friends.put("local", "https://localhost:8090/autobahn/oscars");
		friends.put("localNotify", "https://localhost:8090/autobahn/oscarsnotify");
		friends.put("esnet", "https://oscars-dev.es.net/axis2/services/OSCARS");
		friends.put("esnetNotify", "https://oscars-dev.es.net/axis2/services/OSCARSNotify");
		friends.put("internet2", "https://idcdev0.internet2.edu:8443/axis2/services/OSCARS");
		friends.put("internet2Notify", "https://idcdev0.internet2.edu:8443/axis2/services/OSCARSNotify");
		friends.put("geant", "https://autobahn.par.fr.geant2.net:8090/autobahn/oscars");
		friends.put("geantNotify", "https://autobahn.par.fr.geant2.net:8090/autobahn/oscarsnotify");
	}

	private String dumpResDetails(ResDetails res) {

		StringBuffer sb = new StringBuffer();
		sb.append("reservation id - " + res.getGlobalReservationId() + NL);
		sb.append("status - " + res.getStatus() + NL);
		sb.append("bandwidth - " + res.getBandwidth() + NL);
		sb.append("start time - " + new Date(res.getStartTime() * 1000).toString() + NL);
		sb.append("end time - " + new Date(res.getEndTime() * 1000).toString() + NL);
		sb.append("source - " + res.getPathInfo().getLayer2Info().getSrcEndpoint() + NL);
		sb.append("dest - " + res.getPathInfo().getLayer2Info().getDestEndpoint() + NL);
		return sb.toString();
	}

	@Override
	public String execute(Framework autobahn, String[] args) {

		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("friends")) {
				StringBuffer sb = new StringBuffer();
				for (String key : friends.keySet())
					sb.append(key + " - " + friends.get(key) + NL);
				return sb.toString();
			} else if (args[1].equalsIgnoreCase("clientports")) {
				StringBuffer sb = new StringBuffer();
				List<String> ports = autobahn.getIdm().getIdcpLinks();
				for (String p : ports)
					sb.append(p + NL);
				return sb.toString();
			} else if (args[1].equalsIgnoreCase("subscribers")) {
				StringBuffer sb = new StringBuffer();
				List<SubscriptionInfo> subscribers = IdcpManager.getSubscribers();
				for (SubscriptionInfo si : subscribers)
					sb.append(si + NL);
				return sb.toString();
			} else if (args[1].equalsIgnoreCase("domains")) {
				StringBuffer sb = new StringBuffer();
				for (IdcpDomain id : IdcpManager.getIdcpDomains())
					sb.append(id + NL);
				return sb.toString();
			}
		}

		// at least 3 args required - idcp address operation
		if (args.length < 4)
			return invalid;

		final String address = friends.containsKey(args[1]) ? friends.get(args[1]) : args[1];
		final String operation = args[2];

		if (operation.equalsIgnoreCase("topology")) {
			String format, filename;
			if (args.length == 4) {
				format = args[3];
				filename = null;
			} else if (args.length == 5) {
				format = args[3];
				filename = args[4];
			} else
				return "too many arguments specified";

			if (!format.equalsIgnoreCase("autobahn") && !format.equalsIgnoreCase("idcp"))
				return "topology format can be either in autobahn or idcp format";

			try {
				StringBuffer sb = new StringBuffer();
				IdcpClient idcp = new IdcpClient(address);

				if (format.equalsIgnoreCase("autobahn")) {
					List<Link> links = idcp.getTopology();
					for (Link l : links)
						sb.append(l + "\r\n");
				} else if (format.equalsIgnoreCase("idcp")) {
					List<String> links = idcp.getIdcpTopology();
					for (String l : links)
						sb.append(l + "\r\n");
				}
				if (filename != null) {
					Writer output = new BufferedWriter(new FileWriter(filename));
					try {
						output.write(sb.toString());
					} finally {
						output.close();
					}
				}
				return sb.toString();
			} catch (Exception e) {
				return "topology could not be obtained - " + e.getMessage();
			}
		} else if (operation.equals("subscribe")) {

			if (args.length != 6)
				return invalid;

			String consumer = friends.containsKey(args[3]) ? friends.get(args[3]) : args[3];
			String producer = friends.containsKey(args[4]) ? friends.get(args[4]) : args[4];
			String subId = args[5];
			if (subId.equalsIgnoreCase("auto"))
				subId = Idcp.generateSubscriptionId();

			try {
				IdcpNotifyClient idcpNotify = new IdcpNotifyClient(address);

				idcpNotify.subscribe(new SubscriptionInfo(consumer, producer, producer, subId, null, null));

				return "subscribed";
			} catch (Exception e) {
				return "failed to subscribe";
			}
		} else if (operation.equals("unsubscribe")) {

			if (args.length != 5)
				return invalid;

			String notifier = friends.containsKey(args[3]) ? friends.get(args[3]) : args[3];
			String subId = args[4];

			try {
				IdcpNotifyClient idcpNotify = new IdcpNotifyClient(address);
				idcpNotify.unsubscribe(notifier, subId, null);

				return "unsubscribed";
			} catch (Exception e) {
				return "failed to unsubscribe";
			}
		} else
			return operation + " is not supported";
	}

	@Override
	public String commandInfo() {

		StringBuffer sb = new StringBuffer();
		sb.append("allows creating, cancelling and querying resources in idcp domains" + NL);
		sb.append("\t'friends' - returns a list of known aliases that can be used instead of full address" + NL);
		sb.append("\t'clientports' - returns a list of client ports in idcp format" + NL);
		sb.append("\t'domains' - displays all idcp domains" + NL);
		sb.append("\t'subscribers' - prints all subscribers" + NL);
		sb.append("\t'address topology autobahn|idcp [filename]' - gets topology either in autobahn or idcp format," +
				"optionally saves it to a file" + NL);
		sb.append("\t'notifyAddress subscribe consumer producer subscriptionId|auto' - subscribes for asynchronous event notifications" + NL);
		sb.append("\t'notifyAddress unsubscribe notifier subscriptionId' - unsubscribes to stop receiving event notifications" + NL);

		return sb.toString();
	}
}
