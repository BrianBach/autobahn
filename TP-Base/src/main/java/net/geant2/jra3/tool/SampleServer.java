package net.geant2.jra3.tool;

import javax.xml.ws.Endpoint;

public class SampleServer {
	public static void main(String[] args) throws Exception {
		
		//SampleToolImpl tool = new SampleToolImpl();
		ToolImpl tool = new ToolImpl();
		
		Endpoint.publish("http://localhost:8081/autobahn/tool", tool);
		
		Thread.sleep(Long.MAX_VALUE);
		
		System.out.println("server quit...");
		System.exit(0);
	}
}
