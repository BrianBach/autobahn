package net.geant.autobahn.useraccesspoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author Michal
 */

@XmlAccessorType(XmlAccessType.FIELD)
public enum Resiliency {

	NONE, ONE_TO_ONE, ONE_PLUS_ONE
}
