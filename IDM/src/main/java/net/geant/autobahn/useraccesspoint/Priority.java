package net.geant.autobahn.useraccesspoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Describes reservation priority
 * @author Michal
 */

@XmlAccessorType(XmlAccessType.FIELD)
public enum Priority {
	
	NORMAL, HIGH, HIGHEST
}
