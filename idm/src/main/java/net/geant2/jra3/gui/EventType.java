package net.geant2.jra3.gui;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Event ID
 * @author Michal
 */

@XmlAccessorType(XmlAccessType.FIELD)
public enum EventType {
	
	STATUS, RESERVATION_CHANGED, TOPOLOGY_CHANGED

}
