package net.geant.autobahn.autoBahnGUI.manager;

import java.util.List;

import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.Status;
import net.geant.autobahn.gui.EventType;
import net.geant.autobahn.gui.ReservationChangedType;

/**
 * This interface is used for notifying  changes in IDMs.
 * Notifier is used as bridge between web service GUI interface and manager module  
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public interface ManagerNotifier {
	/**
	 * Notifies on status update in specified IDM 
	 * @param idm identifier of IDM 
	 * @param status just updated status
	 */
	public void statusUpdated (String idm, Status status);
	/**
	 * Notifies on new event appearance in specified JR3 IDM
	 *  
	 * @param idm identifier of IDM
	 * @param event new event type
	 * @param properties event properties
	 */
	public void update (String idm,EventType event, List<KeyValue> properties);
	/**
	 * Notifies on Reservation state change in specified IDM
	 * 
	 * @param idm identifier of IDM
	 * @param serviceId identifier of Service
	 * @param resID	identifier of Reservation
	 * @param state	new reservation state
	 * @param message	comment
	 */
	public void reservationChanged(String idm,String serviceId,String resID,ReservationChangedType state,String message);
}