package net.geant.autobahn.framework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import net.geant.autobahn.framework.commands.AutobahnCommand;
import net.geant.autobahn.framework.commands.CancelServiceCommand;
import net.geant.autobahn.framework.commands.ClientPortsCommand;
import net.geant.autobahn.framework.commands.HelpCommand;
import net.geant.autobahn.framework.commands.IdcpCommand;
import net.geant.autobahn.framework.commands.NeighborsCommand;
import net.geant.autobahn.framework.commands.QuitCommand;
import net.geant.autobahn.framework.commands.RemoveReservationCommand;
import net.geant.autobahn.framework.commands.ReservationCommand;
import net.geant.autobahn.framework.commands.ReservationsCommand;
import net.geant.autobahn.framework.commands.RestartCommand;
import net.geant.autobahn.framework.commands.ServicesCommand;
import net.geant.autobahn.framework.commands.ShutdownCommand;
import net.geant.autobahn.framework.commands.StatisticsCommand;
import net.geant.autobahn.framework.commands.TopologyCommand;
import net.geant.autobahn.framework.commands.UptimeCommand;
import net.geant.autobahn.resources.ResourcePath;

import org.apache.log4j.Logger;
import org.apache.commons.codec.binary.Hex;

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
		
    private ServerSocket server;
    private Socket client = null;

    private PrintWriter out = null;
    private BufferedReader in = null;
    private BufferedReader br = null;
    
    //Used for telnet codes
    private BufferedWriter rawout = null;

    private static Framework instance = null;
    
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
        commands.put("statistics", new StatisticsCommand());
        commands.put("neighbors", new NeighborsCommand());
        commands.put("cancel", new CancelServiceCommand());
        commands.put("idcp", new IdcpCommand());
	}

    private static String info = "-------------------------\n" +
                                 " Autobahn framework\n" +
                                 "  quit - to quit\n" +
                                 "  help - to display help\n" +
                                 "-------------------------\n";
    
    public synchronized static Framework getInstance() {
    	if(instance == null) {
    		instance = new Framework();
    	}
    	
    	return instance;
    }
    
	public static Properties loadProperties(String filename) throws Exception {
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(filename);
		properties.load(fis);
		fis.close();
		
		return properties;
	}
	
    public static String md5(String s)
    {
        byte[] hash;
        String result = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            hash = digest.digest();
            result =new String ( Hex.encodeHex(hash) );
        } catch (NoSuchAlgorithmException nsae) {
            log.error("Could not find the md5 algorithm");
            log.debug("Exception info: ", nsae);
            return result;
        }

        return result;
    }

	public net.geant.autobahn.idm.AccessPoint.State startIdm() {
		try {
	        String prop_file = "etc/autobahn.properties";
	        if (!new File(prop_file).exists()) {
	            prop_file = "etc/idm.properties";
	        }

			Properties idmProps = loadProperties(prop_file);
			
			if(properties.containsKey("startup.notify")) {
				idmProps.put("startup.notify", properties.getProperty("startup.notify"));
			}
			
			idm = net.geant.autobahn.idm.AccessPoint.getInstance();
			return idm.init(idmProps);
        } catch (Exception e) {
            log.error("Could not start IDM, " + e.getMessage());
            log.debug("Exception info: ", e);
            return net.geant.autobahn.idm.AccessPoint.State.ERROR;
        }
	}
	
	private synchronized void init(Properties props) throws Exception {
		
		properties = props;

        // Start Topology Abstraction before DM or IDM
        ta = net.geant.autobahn.converter.AccessPoint.getInstance();
        // Start Calendar
        cal = net.geant.autobahn.calendar.AccessPoint.getInstance();

		dm = net.geant.autobahn.intradomain.AccessPoint.getInstance();
		if (dm.init() == net.geant.autobahn.intradomain.AccessPoint.State.ERROR) {
		    log.error("DM did not initialize successfully, shutting framework down");
		    System.exit(-1);
		}

        if (startIdm() == net.geant.autobahn.idm.AccessPoint.State.ERROR) {
            log.error("IDM did not initialize successfully, shutting framework down");
            System.exit(-1);
        }

		// choose command liner
		String cmdLiner = properties.getProperty("framework.commandLine");
		if (cmdLiner.equalsIgnoreCase("interactive")) {
			commandLine();
		}
		else if (cmdLiner.equalsIgnoreCase("localhost")) {
			telnetCommandLine(false);
		}
		else if (cmdLiner.equalsIgnoreCase("remote")) {
			telnetCommandLine(true);
		}
        else if (cmdLiner.equals("none")) {
            wait();
        }

	}

	private void telnetCommandLine(boolean allowRemote) throws IOException {

		int port = Integer.parseInt(properties.getProperty("framework.port"));
		
		try {       
		    server = new ServerSocket(port);        
        } catch (IOException e) {
            log.error("Telnet server could not listen on port " + port);
            log.debug("Exception info: ", e);
            System.exit(-1);
        }

		while (true) {

			if (client == null) {
			    try {
			        client = server.accept();
			    } catch (IOException e) {
			        log.info("Telnet server will accept no more incoming connections");
                    server.close();
                    System.exit(-1);                
			    }
				stop = false;
				if (!allowRemote && !client.getInetAddress().isLoopbackAddress()) {
					client.close();
					log.warn("attempt to connect from remote location: "
									+ client.getInetAddress().getHostName());
					client = null;
					continue;
				}

				out = new PrintWriter(client.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(
                        client.getInputStream(), "ISO-8859-1"));

                rawout = new BufferedWriter(new OutputStreamWriter(
                        client.getOutputStream(), "ISO-8859-1"));

				out.println(info);
				
				/*
				 * Password check
				 */
				out.print("Give password:");
				out.flush();
				
				// Send IAC WILL ECHO
                rawout.write((char)0xff);
                rawout.write((char)0xfb);
                rawout.write((char)0x01);
                rawout.flush();
                out.flush();
                
                // Telnet client will probably respond with IAC DONT ECHO
                // (0xff 0xfd 0x01)
                // so we have to remove these commands from incoming stream
                String line = trimTelnetNegotiationCommands(in.readLine());
                line = md5(line);
                if(!line.equals(properties.getProperty("framework.password"))) {
                    client.close();
                    log.warn("wrong password");
                    client = null;
                    continue;
                }

                // Send IAC WONT ECHO
                rawout.write((char)0xff);
                rawout.write((char)0xfc);
                rawout.write((char)0x01);
                rawout.flush();
                
                out.println();
                out.flush();
			}

			try {
				out.print(PROMPT);
				out.flush();

                // Telnet client will probably initially send 
				// IAC DO ECHO so read and ignore it
				String line = trimTelnetNegotiationCommands(in.readLine());

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
			    stop(shutdown);
			}
		}
	}

	private void commandLine() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));

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

    public net.geant.autobahn.calendar.AccessPoint getCalendar() {
        return cal;
    }

	public void stop(boolean shutdown) {
		
		this.stop = true;
		this.shutdown = shutdown;
		
        if (out != null) {       
            out.close();            
        }
        try {       
            if (br != null) {                       
                br.close();         
            }       
            if (in != null) {           
                in.close();             
            }           
            if (rawout != null) {           
                rawout.close();             
            }           
            if (client != null) {           
                client.close();            
            }
            client = null;          
            if (shutdown && server!=null) {
                server.close();
            }
        } catch (IOException e) {       
            log.error("Error while closing telnet ports");
            log.debug("Exception info: ", e);
        }
	}

	public static boolean running = true;

	public static void main(String[] args) throws Exception {
        ResourcePath resource = new ResourcePath(false);
	    String prop_file = "etc/autobahn.properties";
        if (!new File(prop_file).exists()) {
            prop_file = "etc/framework.properties";
        }
		final Properties props = Framework.loadProperties(prop_file);
		
		for(int i = 0; i < args.length; i++) {
			if("--startup-notifier".equalsIgnoreCase(args[i])) {
				String callbackUrl = args[i + 1];
				props.put("startup.notify", callbackUrl);
			}
		}
		
		System.out.println(info);
	
		final Framework autobahn = Framework.getInstance();

        SignalHandler handler = new SignalHandler () {
            public void handle(Signal sig) {
                log.info("Autobahn framework was ordered to shut down");
                if (running) {
                    running = false;
                    autobahn.stop(true);
                    System.exit(0);
                }
                else {
                    // only on the second attempt do we exit
                    log.info("Previous autobahn framework shutdown was interrupted");
                    System.exit(0);
                }
            }
        };

        Signal.handle(new Signal("TERM"), handler);

		autobahn.init(props);

		log.info("Autobahn framework was shut down");
	
		System.exit(0);
	}

    /**
     * Removes Telnet negotiation 3-byte sequences from supplied String
     * Searches for IAC byte and removes it along with next two bytes
     * 
     * @param line
     * @return String without telnet negotiation bytes
     */
    public String trimTelnetNegotiationCommands(String line) {
        if (line == null) {
            return null;
        }
        
        byte[] byteline;
        try {
            byteline = line.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            return line;
        }
        for (int i=0; i<byteline.length; i++) {
            // 0xff is IAC (Interpret as command) character
            // It is followed by one or two bytes (RFC 854)
            // We assume 3-byte sequences will be received:
            // IAC <type of operation> <option>
            // 0xff is stored as -1 in signed arithmetic
            if (byteline[i] == -1) {
                try {
                    if (i>0) {
                        return trimTelnetNegotiationCommands(line.substring(0,i)+line.substring(i+3));
                    } else {
                        return trimTelnetNegotiationCommands(line.substring(i+3));
                    }
                } catch (IndexOutOfBoundsException e) {
                    return "";
                }
            }
        }
        return line;
    }
}
