package net.geant.autobahn.gui;

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
