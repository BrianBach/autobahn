package net.geant.autobahn.useraccesspoint.textclient;

import java.io.IOException;

import net.geant.autobahn.testplatform.clients.UserAccessPointClient;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;

public class AutobahnClient {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public static void main(String[] args) throws Exception {

		if(args.length < 2) {
			System.out.println("Wrong usage, specify path to a file with request and IDM URL");
			System.exit(-1);
		}
		
		String targetIDM = args[1];
		
        UserAccessPointClient ss = new UserAccessPointClient(targetIDM);
		UserAccessPoint uap = ss.getUserAccessPointPort();
		
		RequestParser parser = new RequestParser();
		ServiceRequest sreq = parser.readServiceRequest(args[0]); 
		
		System.out.println("Request");
		System.out.println(sreq);
		
		uap.submitService(sreq);
	}

}
