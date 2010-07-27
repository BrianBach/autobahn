package net.geant.autobahn.tool.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.ws.Endpoint;

public class MockToolServer {

	private int port;
	private List<MockTool> mocks = new ArrayList<MockTool>();
	private Collection<Endpoint> published = new ArrayList<Endpoint>();
	
	public MockToolServer(int port) {
		this.port = port;
	}
	
	public void addMockTool(MockTool mock) {
		mocks.add(mock);
		mock.setPort(port);
	}
	
	public void start(boolean inBackground) {
		Runnable cmd = new Runnable() {
			public void run() {

				for(MockTool mock : mocks) {
					Endpoint e1 = Endpoint.publish(mock.getProgrammerAddress(), mock.getProgrammer());	
					Endpoint e2 = Endpoint.publish(mock.getToolAddress(), mock.getTool());
					published.add(e1);
					published.add(e2);
				}
		
				try {
					synchronized (this) {
						this.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}};
		
		if(inBackground)
			new Thread(cmd).start();
		else
			cmd.run();
	}
	
	public void stop() {
		for(Endpoint service : published)
			service.stop();
		
		published.clear();
		
		synchronized(this) {
			this.notify();
		}
	}

}
