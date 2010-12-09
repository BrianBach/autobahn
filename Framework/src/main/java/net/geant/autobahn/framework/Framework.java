package net.geant.autobahn.framework;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.geant.autobahn.framework.commands.AutobahnCommand;
import net.geant.autobahn.framework.commands.ClientPortsCommand;
import net.geant.autobahn.framework.commands.HelpCommand;
import net.geant.autobahn.framework.commands.QuitCommand;
import net.geant.autobahn.framework.commands.RemoveReservationCommand;
import net.geant.autobahn.framework.commands.ReservationCommand;
import net.geant.autobahn.framework.commands.ReservationsCommand;
import net.geant.autobahn.framework.commands.RestartCommand;
import net.geant.autobahn.framework.commands.ServicesCommand;
import net.geant.autobahn.framework.commands.ShutdownCommand;
import net.geant.autobahn.framework.commands.TopologyCommand;
import net.geant.autobahn.framework.commands.UptimeCommand;
import net.geant.autobahn.framework.commands.IdcpTopologyCommand;

import org.apache.log4j.Logger;

/**
 * @author Michal
 */

public class Framework {

	private static final Logger log = Logger.getLogger(Framework.class);
	public static final String PROMPT = "AutoBahn>";
	
	private net.geant.autobahn.intradomain.AccessPoint dm = null;
	private net.geant.autobahn.idm.AccessPoint idm = null;
    private net.geant.autobahn.converter.AccessPoint ta = null;
    private net.geant.autobahn.calendar.AccessPoint cal = null;
	
	private boolean stop = false;
	private boolean shutdown = false;

	private Properties properties;
		
	public static Map<String, AutobahnCommand> commands = new HashMap<String, AutobahnCommand>();
		
	static {
		commands.put("quit", new QuitCommand());
		commands.put("exit", new QuitCommand());
		commands.put("topology", new TopologyCommand());
		commands.put("clientports", new ClientPortsCommand());
		commands.put("reservation", new ReservationCommand());
		commands.put("remove", new RemoveReservationCommand());
		commands.put("reservations", new ReservationsCommand());
		commands.put("restart", new RestartCommand());
		commands.put("services", new ServicesCommand());
		commands.put("help", new HelpCommand());
		commands.put("shutdown", new ShutdownCommand());
		commands.put("halt", new ShutdownCommand());
		commands.put("uptime", new UptimeCommand());
		commands.put("idcptopo", new IdcpTopologyCommand());
	}

	public static Properties loadProperties(String filename) throws Exception {
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(filename);
		properties.load(fis);
		fis.close();
		
		return properties;
	}
	
	public void startIdm() {
		try {
			
			Properties idmProps = loadProperties("etc/idm.properties");
			
			if(properties.containsKey("startup.notify")) {
				idmProps.put("startup.notify", properties.getProperty("startup.notify"));
			}
			
			idm = net.geant.autobahn.idm.AccessPoint.getInstance();
			idm.init(idmProps);
        } catch (Exception e) {
            log.error("Could not start IDM, " + e.getMessage());
        }
	}
	
	private void init(Properties props) throws Exception {
		
		properties = props;
		
        // Start Topology Abstraction before DM or IDM
        ta = net.geant.autobahn.converter.AccessPoint.getInstance();
        // Start Calendar
        cal = net.geant.autobahn.calendar.AccessPoint.getInstance();

		dm = net.geant.autobahn.intradomain.AccessPoint.getInstance();
		dm.init();
		startIdm();
		
		
		// choose command liner
		String cmdLiner = properties.getProperty("framework.commandLine");
		if (cmdLiner.equals("interactive"))
			commandLine();
		else if (cmdLiner.equals("localhost"))
			telnetCommandLine(false);
		else if (cmdLiner.equals("remote"))
			telnetCommandLine(true);

	}

	private void telnetCommandLine(boolean allowRemote) throws IOException {

		int port = Integer.parseInt(properties.getProperty("framework.port"));
		ServerSocket server = new ServerSocket(port);

		Socket client = null;

		PrintWriter out = null;
		BufferedReader in = null;

		while (true) {

			if (client == null) {
				client = server.accept();
				stop = false;
				if (!allowRemote && !client.getInetAddress().isLoopbackAddress()) {
					client.close();
					log.warn("attempt to connect from remote location: "
									+ client.getInetAddress().getHostName());
					client = null;
					continue;
				}

				out = new PrintWriter(client.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(client
						.getInputStream()));

				out.println("-------------------------");
				out.println(" Autobahn framework '08");
				out.println("  quit - to quit");
				out.println("  help - to display help");
				out.println("-------------------------");
			}

			try {
				out.print(PROMPT);
				out.flush();

				String line = in.readLine();

				String[] elems = line.split("\\s");
				String cmd = elems[0].trim().toLowerCase();
				
				AutobahnCommand command = commands.get(cmd);

				if (command != null) {
					String response = command.execute(this, elems);
					out.println(response);
				} else {
					out.println("command not recognized");
				}
			} catch (Exception e1) {
				log.error("connection dropped", e1);
				client = null;
			}

			if (stop) {
				out.close();
				in.close();
				client.close();
				client = null;

				if (shutdown) {
					server.close();
					break;
				}
			}
		}
	}
	
	
	static void info() {
		System.out.println("-------------------------");
		System.out.println(" Autobahn framework '08");
		System.out.println("  quit - to quit");
		System.out.println("  help - to display help");
		System.out.println("-------------------------");
	}

	private void commandLine() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.println("enter command");
			String line = br.readLine();

			String[] elems = line.split("\\s");

			String cmd = elems[0].trim().toLowerCase();

			AutobahnCommand command = commands.get(cmd);

			if (command != null) {
				String response = command.execute(this, elems);
				System.out.println(response);
			} else {
				System.out.println("command not recognized");
			}

			if (stop)
				break;
		}
	}

	public net.geant.autobahn.intradomain.AccessPoint getDm() {
		return dm;
	}

	public net.geant.autobahn.idm.AccessPoint getIdm() {
		return idm;
	}

    public net.geant.autobahn.converter.AccessPoint getTopologyAbstraction() {
        return ta;
    }

	public void stop(boolean shutdown) {
		this.stop = true;
		this.shutdown = shutdown;
	}

	public static void main(String[] args) throws Exception {
		Properties props = Framework.loadProperties("etc/framework.properties");
		
		for(int i = 0; i < args.length; i++) {
			if("--startup-notifier".equals(args[i])) {
				String callbackUrl = args[i + 1];
				props.put("startup.notify", callbackUrl);
			}
		}
		
		if("start".equals(args[0])) {
			info();
	
			Framework autobahn = new Framework();
			autobahn.init(props);

			log.info("autobahn framework shutdown");
			
		} else if ("stop".equals(args[0])){
			int port = Integer.parseInt(props.getProperty("framework.port"));

			MyTelnetClient cli = new MyTelnetClient("localhost", port);
			cli.write("halt");
			cli.disconnect();
		}
		
		System.exit(0);
	}
}
