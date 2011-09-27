package net.geant.autobahn.dm2idm;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import net.geant.autobahn.dm2idm.Dm2Idm;
import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.LinkIdentifiers;

/**
 * @author Michal
 */

@WebService(name = "Dm2Idm", serviceName = "Dm2IdmService",
        portName = "Dm2IdmPort",
        targetNamespace = "http://dm2idm.autobahn.geant.net/", 
        endpointInterface = "net.geant.autobahn.dm2idm.Dm2Idm")
public class Dm2IdmImpl implements Dm2Idm {

    private final Logger log = Logger.getLogger(Dm2IdmImpl.class);
    
	public void dispose() {
	    try {
	        AccessPoint.getInstance().dispose();
        } catch (Exception e) {
            log.error("IDM dispose failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}
	
	public void activate(String arg0, boolean arg1) {
	    try {
	        AccessPoint.getInstance().activate(arg0, arg1);
        } catch (Exception e) {
            log.error("IDM activate failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}

	public void finish(String arg0, boolean arg1) {
	    try {
	        AccessPoint.getInstance().finish(arg0, arg1);
        } catch (Exception e) {
            log.error("IDM finish failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}

	public LinkIdentifiers getIdentifiers(String domain, String portName, String bodId) {
	    try {
	        return AccessPoint.getInstance().getIdentifiers(domain, portName, bodId);
        } catch (Exception e) {
            log.error("IDM getIdentifiers failed: " + e.getMessage());
            log.debug("Exception info: ", e);
            return null;
        }
	}

	public void injectAbstractLinks(List<Link> links) {
	    try {
	        AccessPoint.getInstance().injectAbstractLinks(links);
        } catch (Exception e) {
            log.error("IDM injectAbstractLinks failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}
    
	public void restorationCompleted() {
	    try {
	        AccessPoint.getInstance().restorationCompleted();
        } catch (Exception e) {
            log.error("IDM restorationCompleted failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}

	public boolean saveReservationStatusDB(String res, int st) {
	    try {
	        return AccessPoint.getInstance().saveReservationStatusDB(res, st);
        } catch (Exception e) {
            log.error("IDM saveReservationStatusDB failed: " + e.getMessage());
            log.debug("Exception info: ", e);
            return false;
        }
    }
}
