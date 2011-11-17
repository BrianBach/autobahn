package net.geant.autobahn.network;

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
@XmlType(name="LinkType", namespace="network.autobahn.geant.net", propOrder={
		"type", "desc"
})
public class LinkType implements Serializable {

	private int type = 0;
	private String desc = null;
	
	public static final LinkType NREN_LINK = new LinkType(1, "NREN_LINK");
	public static final LinkType ID_LINK = new LinkType(2, "ID_LINK");
	public static final LinkType ID_LINK_PARTIAL_INFO = new LinkType(3, "ID_LINK_PARTIAL_INFO");
	
	public static LinkType[] types = new LinkType[] {NREN_LINK, ID_LINK, ID_LINK_PARTIAL_INFO};
	
	public LinkType() {
		
	}
	
	public LinkType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public int getId() {
		return type;
	}

	public void setId(int type) {
		this.type = type;
	}

	/**
	 * @return the desc
	 */
	public String getDescription() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDescription(String desc) {
		this.desc = desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getId();
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
		if (this.getId() != other.getId())
			return false;
		return true;
	}
}
