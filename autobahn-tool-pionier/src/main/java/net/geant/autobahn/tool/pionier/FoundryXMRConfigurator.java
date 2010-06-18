/**
 * 
 */
package net.geant.autobahn.tool.pionier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;



/**
 * @author Michal
 *
 */
public class FoundryXMRConfigurator {
	
	//XMR commands
	private final static String ENABLE = "enable\n";
	private final static String ENABLE_PASSWORD = "xmrdabest\n";
	private final static String CONFIG = "configure terminal\n";
	private final static String MPLS = "router mpls\n";
	private final static String VLL = "vll ";
	private final static String RAW_MODE = "raw-mode\n";
	private final static String VLL_PEER = "vll-peer ";
	private final static String UNTAGGED_E = "untagged e ";
	private final static String VLAN = "vlan ";
	private final static String TAGGED_E = "tagged e ";
	private final static String NO_VLL = "no vll ";
	private final static String EXIT = "exit\n";
	
	public final static boolean  START = true;
	public final static boolean  END = false;
	

	
	public boolean addReservation(ReservationParameters parameters) {
		
	
		if(setXmrVllConf(parameters, START)) {
			System.out.println("Reservation added on XMR1");
			if(setXmrVllConf(parameters, END)) {
				System.out.println("Reservation added on XMR2");
				System.out.println("VLL OK!");
				return true;
			}
			else return false;
		}
		else
			return false;
	}
	
	
	public boolean remReservation(ReservationParameters parameters) {
		
		if(delXmrVllConf(parameters, START)) {
			System.out.println("Reservation removed from XMR1");
			if(delXmrVllConf(parameters, END)) {
				System.out.println("Reservation removed from XMR2");
				System.out.println("NO VLL OK!");
				return true;
			}
			else return false;
		}
		else
			return false;
	}
	
	
	/*
	 * Creates VLL on XMR 
	 */
	private boolean setXmrVllConf(ReservationParameters parameters, boolean isStart) {
		
		Connection sshConnection;
		
		if(isStart)
			sshConnection = new SshConnectionManager().GetSshConnection(parameters.getSshConnectionStartParameters());
		else
			sshConnection = new SshConnectionManager().GetSshConnection(parameters.getSshConnectionEndParameters());
		
		if (sshConnection == null)
			return false;
		else {

			try {
				/* Create a session */
				Session sess = sshConnection.openSession();
				sess.startShell();

		    	String command;

		    	if(isStart)
		    		command = ENABLE+ENABLE_PASSWORD+
		    				  CONFIG+MPLS+
		    				  VLL+parameters.getVllName()+" "+parameters.getVllVcId()+" "+RAW_MODE+
		    				  VLL_PEER+parameters.getVllStartIP()+"\n"+
		    				  UNTAGGED_E+parameters.getVllStartPort()+"\n"+
		    				  EXIT+EXIT+EXIT+EXIT+EXIT;
		    	else
		    		command = ENABLE+ENABLE_PASSWORD+
		    				  CONFIG+MPLS+
		    				  VLL+parameters.getVllName()+" "+parameters.getVllVcId()+" "+RAW_MODE+
		    				  VLL_PEER+parameters.getVllEndIP()+"\n"+
		    				  UNTAGGED_E+parameters.getVllEndPort()+"\n"+
		    				  EXIT+EXIT+EXIT+EXIT+EXIT;

//Tagged vll		    				  
//		    				  VLAN+parameters.getVlanTag()+"\n"+
//		    				  TAGGED_E+parameters.getVllEndPort()+"\n"+
//		    				  EXIT+EXIT+EXIT+EXIT+EXIT+EXIT;
		    					 
//		    	System.out.println("###"+command+"###\n");
		    	
		    	// send command
		    	OutputStream out = sess.getStdin();
		    	out.write(command.getBytes());

				InputStream stdout = new StreamGobbler(sess.getStdout());
				BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
				char buffer[] = new char[512];
		    	int read; 
		    	while((read = br.read(buffer)) > 0) {
			    	  String sessionOut = new String(buffer, 0, read);
			    	  //System.out.println(sessionOut);
		    	}
				sess.close();
				sshConnection.close();
				return true;

			}
			catch (IOException e)
			{
				e.printStackTrace(System.err);
				System.exit(2);
			}
		}

  	    sshConnection.close();
		return false;
	}
	

	/*
	 * Removes VLL from XMR 
	 */
	private boolean delXmrVllConf(ReservationParameters parameters, boolean isStart) {
		
		Connection sshConnection;
		
		if(isStart)
			sshConnection = new SshConnectionManager().GetSshConnection(parameters.getSshConnectionStartParameters());
		else
			sshConnection = new SshConnectionManager().GetSshConnection(parameters.getSshConnectionEndParameters());
		
		if (sshConnection == null)
			return false;
		else {
			try {
				/* Create a session */
				Session sess = sshConnection.openSession();
				sess.startShell();

		    	String command = ENABLE+ENABLE_PASSWORD+
		    					 CONFIG+MPLS+
		    				     NO_VLL+parameters.getVllName()+" "+parameters.getVllVcId()+"\n"+
		    				     EXIT+EXIT+EXIT+EXIT+EXIT;

		    	//System.out.println("###"+command+"###\n");
		    	
		    	// send command
		    	OutputStream out = sess.getStdin();
		    	out.write(command.getBytes());

				InputStream stdout = new StreamGobbler(sess.getStdout());
				BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
				char buffer[] = new char[512];
		    	int read; 
		    	while((read = br.read(buffer)) > 0) {
			    	  String sessionOut = new String(buffer, 0, read);
			    	  //System.out.println(sessionOut);
		    	}
				sess.close();
				sshConnection.close();
				return true;

			}
			catch (IOException e)
			{
				e.printStackTrace(System.err);
				System.exit(2);
			}
		}

  	    sshConnection.close();
		return false;
	}
}
