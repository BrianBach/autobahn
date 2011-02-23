package net.geant2.jra3.tool;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

public class ToolClient {
	
	private final static Logger log = Logger.getLogger(Tool.class);
	
	private Tool tool;
	
	/**
	 * 
	 * @param endPoint
	 */
	public ToolClient(String endPoint) {
		if(endPoint.equals("none")) {
            log.info("TP location was specified as none, TP-Proxy->TP communication impossible");
            return;
        }
        
        try {
            new URL(endPoint);
            log.debug("TP location seems a valid URL, trying to connect to it");
        } catch (MalformedURLException e) {
            log.error("No valid TP location ("+ endPoint +") could be found, TP-Proxy->TP communication impossible");
            return;
        }
		
		ToolService service = new ToolService(endPoint);
		tool = service.getToolPort();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.tool.Tool#addReservation(java.lang.String, java.util.List, net.geant2.jra3.reservation.ReservationParams)
	 */
    public void addReservation(String resID, List<net.geant2.jra3.intradomain.common.GenericLink> links,
            net.geant2.jra3.reservation.ReservationParams params
        ) throws RequestException_Exception, SystemException_Exception, ResourceNotFoundException_Exception, AAIException_Exception {
		
		if(tool != null) {
		    tool.addReservation(resID, links, params);			        
		}
		log.info("TP-Proxy -> TP: Adding reservation  " + resID + " end");
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.tool.Tool#removeReservation(java.lang.String, java.util.List, net.geant2.jra3.reservation.ReservationParams)
	 */
    public void removeReservation(String resID, List<net.geant2.jra3.intradomain.common.GenericLink> links,
            net.geant2.jra3.reservation.ReservationParams params
        ) throws RequestException_Exception, SystemException_Exception, ReservationNotFoundException_Exception, AAIException_Exception {
				
		if(tool != null) {
			tool.removeReservation(resID, links, params);
		}
        log.info("TP-Proxy -> TP: Removing reservation  " + resID + " end");
	}
}
