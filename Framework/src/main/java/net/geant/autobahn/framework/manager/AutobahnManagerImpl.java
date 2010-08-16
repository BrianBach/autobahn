/**
 * 
 */
package net.geant.autobahn.framework.manager;

import javax.jws.WebService;

import net.geant.autobahn.framework.Framework;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
@WebService(name = "AutobahnManager", serviceName = "AutobahnManagerService",
        portName = "AutobahnManagerPort",
        targetNamespace = "http://amanager.autobahn.geant.net/", 
        endpointInterface = "net.geant.autobahn.framework.manager.AutobahnManager")
public class AutobahnManagerImpl implements AutobahnManager {

	private Framework server = null;
	
	public AutobahnManagerImpl(Framework server) {
		this.server = server;
	}
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.manager.AutobahnManager#getServices()
	 */
	public String[] getServices() {
		return null;		
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.manager.AutobahnManager#unregisterService(java.lang.String)
	 */
	public void unregisterService(String name) {
		
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
