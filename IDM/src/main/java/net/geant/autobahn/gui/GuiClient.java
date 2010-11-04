package net.geant.autobahn.gui;

import java.net.MalformedURLException;
import java.util.List;

import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.Status;

import org.apache.log4j.Logger;

/**
 * Sends message notification to gui
 * @author Michal
 */

public class GuiClient implements Gui {
	
	private Logger log = Logger.getLogger(GuiClient.class);
	private Gui gui;
	private String endPoint;
	
	public GuiClient(String endPoint) {
		
		GuiService service = new GuiService(endPoint);
		gui = service.getGuiPort();
		this.endPoint = endPoint;
	}
	
	public void setAddress(String endPoint) throws MalformedURLException {
		
		GuiService service = new GuiService(endPoint);
		gui = service.getGuiPort();
		this.endPoint = endPoint;
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.gui.Gui#reservationChanged(java.lang.String, java.lang.String)
	 */
	public void reservationChanged(String idm, String serviceId, String resID, 
			ReservationChangedType state, String message) {

		try {
			gui.reservationChanged(idm, serviceId, resID, state, message);
		} catch (Exception e) {
			log.debug("cannot update gui(reservation) - " + endPoint);
		}
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.gui.Gui#statusUpdated(java.lang.String, java.lang.String, net.geant.autobahn.gui.Status)
	 */
	public void statusUpdated(String idmName, String idmUrl, Status status) {
	
		try {
			gui.statusUpdated(idmName, idmUrl, status);
		} catch (Exception e) {
			log.debug("cannot update gui(status) - " + endPoint);
		}
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.gui.Gui#update(net.geant.autobahn.gui.EventType, java.util.Properties)
	 */
	public void update(String idm, EventType event, List<KeyValue> properties) {

		try {
			gui.update(idm, event, properties);
		} catch (Exception e) {
			log.debug("cannot update gui(event) - " + endPoint);
		}
	}
}
