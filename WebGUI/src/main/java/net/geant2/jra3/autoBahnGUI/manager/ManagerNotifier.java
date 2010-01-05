package net.geant2.jra3.autoBahnGUI.manager;

import java.util.List;
import net.geant2.jra3.administration.KeyValue;
import net.geant2.jra3.administration.Status;
import net.geant2.jra3.gui.EventType;
import net.geant2.jra3.gui.ReservationChangedType;

/**
 * This interface is used for notifying  changes in JRA3 IDMs.
 * Notifier is used as bridge between web service GUI interface and manager module  
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public interface ManagerNotifier {
	/**
	 * Notifies on status update in specified JRA3 IDM 
	 * @param idm identifier of JRA3 IDM 
	 * @param status just updated status
	 */
	public void statusUpdated (String idm, Status status);
	/**
	 * Notifies on new event appearance in specified JR3 IDM
	 *  
	 * @param idm identifier of JRA3 IDM
	 * @param event new event type
	 * @param properties event properties
	 */
	public void update (String idm,EventType event, List<KeyValue> properties);
	/**
	 * Notifies on Reservation state change in specified JRA3 IDM
	 * 
	 * @param idm identifier of JRA3 IDM
	 * @param serviceId identifier of Service
	 * @param resID	identifier of Reservation
	 * @param state	new reservation state
	 * @param message	comment
	 */
	public void reservationChanged(String idm,String serviceId,String resID,ReservationChangedType state,String message);
}