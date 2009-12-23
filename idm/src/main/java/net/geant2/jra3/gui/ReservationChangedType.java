/**
 * 
 */
package net.geant2.jra3.gui;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author Michal
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
public enum ReservationChangedType {
	
	ACTIVE, CANCELLED, FINISHED, FAILED, SCHEDULED
}
