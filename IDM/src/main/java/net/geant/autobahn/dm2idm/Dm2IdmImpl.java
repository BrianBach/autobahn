package net.geant.autobahn.dm2idm;

import java.util.List;

import javax.jws.WebService;

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

	public void dispose() {
		AccessPoint.getInstance().dispose();
	}
	
	public void activate(String arg0, boolean arg1) {
		AccessPoint.getInstance().activate(arg0, arg1);
	}

	public void finish(String arg0, boolean arg1) {
		AccessPoint.getInstance().finish(arg0, arg1);
	}

	public LinkIdentifiers getIdentifiers(String domain, String portName, String bodId) {
		return AccessPoint.getInstance().getIdentifiers(domain, portName, bodId);
	}

	public void injectAbstractLinks(List<Link> links) {
		AccessPoint.getInstance().injectAbstractLinks(links);
	}
    
    public boolean saveReservationStatusDB(String res, int st) {
        return AccessPoint.getInstance().saveReservationStatusDB(res, st);
    }
}
