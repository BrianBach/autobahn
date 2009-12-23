package net.geant2.jra3.dm2idm;

import java.util.List;

import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.LinkIdentifiers;

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
		
		Dm2IdmService service = new Dm2IdmService(endPoint);
		dm2idm = service.getDm2IdmPort();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.dm2idm.Dm2Idm#activate(java.lang.String, boolean)
	 */
	public void activate(String resID, boolean success) {
		String msg = success ? "sucessfull" : "failed";
		log.info("DM -> IDM: activation of reservation: " + resID + ": " + msg);
		if(dm2idm != null)
			dm2idm.activate(resID, success);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.dm2idm.Dm2Idm#finish(java.lang.String, boolean)
	 */
	public void finish(String resID, boolean success) {
		log.info("DM -> IDM: reservation: " + resID + " finished" );
		if(dm2idm != null)
			dm2idm.finish(resID, success);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.dm2idm.Dm2Idm#injectAbstractLinks(java.util.List)
	 */
	public void injectAbstractLinks(List<Link> links) {
		if(dm2idm != null)
			dm2idm.injectAbstractLinks(links);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.dm2idm.Dm2Idm#getIdentifiers(java.lang.String, java.lang.String, java.lang.String)
	 */
	public LinkIdentifiers getIdentifiers(String domain, String portName, String linkBodId) {
		if(dm2idm != null)
			return dm2idm.getIdentifiers(domain, portName, linkBodId);
		
		return null;
	}

    /* (non-Javadoc)
     * @see net.geant2.jra3.dm2idm.Dm2Idm#saveReservationStatusDB(java.lang.String, int)
     */
    public boolean saveReservationStatusDB(String res, int st) {
        if(dm2idm != null)
            return dm2idm.saveReservationStatusDB(res, st);
        
        return false;
    }
}
