/**
 * 
 */
package net.geant2.jra3.framework.manager;

import javax.jws.WebService;

import net.geant2.jra3.framework.Framework;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
@WebService(name = "AutobahnManager", serviceName = "AutobahnManagerService",
        portName = "AutobahnManagerPort",
        targetNamespace = "http://amanager.jra3.geant2.net/", 
        endpointInterface = "net.geant2.jra3.framework.manager.AutobahnManager")
public class AutobahnManagerImpl implements AutobahnManager {

	private Framework server = null;
	
	public AutobahnManagerImpl(Framework server) {
		this.server = server;
	}
	
	/* (non-Javadoc)
	 * @see net.geant2.jra3.framework.manager.AutobahnManager#getServices()
	 */
	public String[] getServices() {
		return server.getRegisteresServices();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.framework.manager.AutobahnManager#unregisterService(java.lang.String)
	 */
	public void unregisterService(String name) {
		server.unregisterService(name);
	}

	public void halt() {
		
		Runnable cmd = new Runnable() {
			public void run() {
				try {
					Thread.sleep(2000);
				} catch(InterruptedException ex) {
					// ignore it
				}
				System.out.println("Stopping AutoBAHN...");
				try {
					Framework.main(new String[] {"stop"});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		new Thread(cmd).start();
	}

}
