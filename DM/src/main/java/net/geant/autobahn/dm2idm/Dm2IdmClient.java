package net.geant.autobahn.dm2idm;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import net.geant.autobahn.dm2idm.Dm2Idm;
import net.geant.autobahn.intradomain.AccessPoint;
import net.geant.autobahn.lookup.LookupService;
import net.geant.autobahn.lookup.LookupServiceException;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.LinkIdentifiers;

import org.apache.log4j.Logger;


/**
 * Communicates with an IDM instance through web services.
 * 
 * @author Michal
 */
public class Dm2IdmClient implements Dm2Idm {
	
	private final static Logger log = Logger.getLogger(Dm2Idm.class);
	
	private Dm2Idm dm2idm = null;
	
	/**
	 * Creates an instance sending requests to a given endpoint.
	 * 
	 * @param endPoint URL address of an IDM
	 */
	public Dm2IdmClient(String endPoint) {
		if("none".equals(endPoint))
			return;
		
		log.debug("DM client tries to connect to " + endPoint);
		
		// Query IDM location from Lookup Service
        String host = AccessPoint.getInstance().getProperty("lookuphost");
		String idmLocation = null;
		
		if (host != null && host != "") {
	        LookupService lookup = new LookupService(host);
	        try {
	            // The IDM endpoint is the /interdomain interface. Here we need
	            // the /dm2idm interface, so we have to modify the connection URL
	            String interdomainEndpoint = endPoint.replaceFirst("/autobahn/dm2idm", "/autobahn/interdomain");
	        	idmLocation = lookup.QueryIdmLocation(interdomainEndpoint);
	        } catch (LookupServiceException e) {
	        	log.info("No query to the Lookup Service could be performed in order to locate IDM.");
	        	log.info(e.getMessage());
	        }
		}
		
        if (idmLocation != null && idmLocation != "") {
            // It seems we have found the IDM location at the LS,
            // so use this location as the endPoint
            // First however change the /interdomain back to /dm2idm
        	endPoint = idmLocation.replaceFirst("/autobahn/interdomain", "/autobahn/dm2idm");
        }
        else {
            log.debug("IDM location could not be obtained from Lookup, falling back to direct contact");
            // However, this is not a fatal error, as the IDM location might be
            // available through another channel (e.g. in properties files), and
            // so the provided endPoint parameter might be a valid URL
        }
        
		Dm2IdmService service = new Dm2IdmService(endPoint);
		dm2idm = service.getDm2IdmPort();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.dm2idm.Dm2Idm#activate(java.lang.String, boolean)
	 */
	public void activate(String resID, boolean success) {
		String msg = success ? "sucessfull" : "failed";
		log.info("DM -> IDM: activation of reservation: " + resID + ": " + msg);
		if(dm2idm != null)
			dm2idm.activate(resID, success);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.dm2idm.Dm2Idm#finish(java.lang.String, boolean)
	 */
	public void finish(String resID, boolean success) {
		log.info("DM -> IDM: reservation: " + resID + " finished" );
		if(dm2idm != null)
			dm2idm.finish(resID, success);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.dm2idm.Dm2Idm#injectAbstractLinks(java.util.List)
	 */
	public void injectAbstractLinks(List<Link> links) {
		if(dm2idm != null)
			dm2idm.injectAbstractLinks(links);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.dm2idm.Dm2Idm#getIdentifiers(java.lang.String, java.lang.String, java.lang.String)
	 */
	public LinkIdentifiers getIdentifiers(String domain, String portName, String linkBodId) {
		if(dm2idm != null)
			return dm2idm.getIdentifiers(domain, portName, linkBodId);
		
		return null;
	}

    /* (non-Javadoc)
     * @see net.geant.autobahn.dm2idm.Dm2Idm#saveReservationStatusDB(java.lang.String, int)
     */
    public boolean saveReservationStatusDB(String res, int st) {
        if(dm2idm != null)
            return dm2idm.saveReservationStatusDB(res, st);
        
        return false;
    }
}
