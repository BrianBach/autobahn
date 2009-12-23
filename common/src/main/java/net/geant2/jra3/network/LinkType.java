package net.geant2.jra3.network;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Describes types of a link.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="LinkType", namespace="network.jra3.geant2.net", propOrder={
		"type"
})
public class LinkType implements Serializable {

	private int type = 0;

	public static final LinkType NREN_LINK = new LinkType(1);
	public static final LinkType ID_LINK = new LinkType(2);
	public static final LinkType ID_LINK_PARTIAL_INFO = new LinkType(3);
	
	public LinkType() {
		
	}
	
	public LinkType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final LinkType other = (LinkType) obj;
		if (type != other.type)
			return false;
		return true;
	}
}
