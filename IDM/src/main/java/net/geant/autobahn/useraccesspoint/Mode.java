package net.geant.autobahn.useraccesspoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public enum Mode {
	
	VLAN, UNTAGGED, TRANSPARENT
}